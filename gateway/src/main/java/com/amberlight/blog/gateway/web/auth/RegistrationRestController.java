package com.amberlight.blog.gateway.web.auth;


import com.amberlight.blog.gateway.dto.auth.PasswordDto;
import com.amberlight.blog.gateway.dto.auth.UserDto;
import com.amberlight.blog.struct.exception.BusinessLogicException;
import com.amberlight.blog.struct.security.User;
import com.amberlight.blog.struct.security.VerificationToken;
import com.amberlight.blog.gateway.auth.registration.OnRegistrationCompleteEvent;
import com.amberlight.blog.gateway.auth.security.security.ISecurityUserService;
import com.amberlight.blog.gateway.auth.service.IUserService;
import com.amberlight.blog.struct.security.auth.InvalidOldPasswordException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class RegistrationRestController {

    private static final Logger logger = LogManager.getLogger(RegistrationRestController.class);

    @Autowired
    private IUserService userService;

    @Autowired
    private ISecurityUserService securityUserService;

    @Autowired
    private MessageSource messages;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private Environment env;

    public RegistrationRestController() {
        super();
    }

    @PostMapping("/user/registration")
    public void registerUserAccount(@Valid final UserDto accountDto, final HttpServletRequest request) {
        logger.debug("Registering user account with information: {}", accountDto);
        final User registered = userService.registerNewUserAccount(accountDto);
        userService.addUserLocation(registered, getClientIP(request));
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(),
                                                                    getAppUrl(request)));
    }

    @GetMapping("/user/resendRegistrationToken")
    public void resendRegistrationToken(final HttpServletRequest request,
                                        @RequestParam("token") final String existingToken) {
        final VerificationToken newToken = userService.generateNewVerificationToken(existingToken);
        final User user = userService.getUser(newToken.getToken());
        mailSender.send(constructResendVerificationTokenEmail(getAppUrl(request), request.getLocale(), newToken, user));
    }

    @PostMapping("/user/resetPassword")
    public void resetPassword(final HttpServletRequest request, @RequestParam("email") final String userEmail) {
        final User user = userService.findUserByEmail(userEmail);
        if (user == null) throw new BusinessLogicException("User doesn't exist.");
        final String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);
        mailSender.send(constructResetTokenEmail(getAppUrl(request), request.getLocale(), token, user));

    }

    @PostMapping("/user/savePassword")
    public void savePassword(final Locale locale, @Valid PasswordDto passwordDto) {
        final String result = securityUserService.validatePasswordResetToken(passwordDto.getToken());
        if(result != null) {
            throw new BusinessLogicException(messages.getMessage("auth.message." + result, null, locale));
        }
        Optional<User> user = userService.getUserByPasswordResetToken(passwordDto.getToken());
        if(user.isPresent()) {
            userService.changeUserPassword(user.get(), passwordDto.getNewPassword());
        } else {
            throw new BusinessLogicException(messages.getMessage("auth.message.invalid", null, locale));
        }
    }

    @PostMapping("/user/updatePassword")
    public void changeUserPassword(@Valid PasswordDto passwordDto) {
        final User user = userService.findUserByEmail(((User) SecurityContextHolder.getContext()
                                                              .getAuthentication().getPrincipal()).getEmail());
        if (!userService.checkIfValidOldPassword(user, passwordDto.getOldPassword())) {
            throw new InvalidOldPasswordException();
        }
        userService.changeUserPassword(user, passwordDto.getNewPassword());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PostMapping("/user/update/2fa")
    public String modifyUser2FA(@RequestParam("use2FA") final boolean use2FA) throws UnsupportedEncodingException {
        final User user = userService.updateUser2FA(use2FA);
        if (use2FA) {
            return userService.generateQRUrl(user);
        }
        return null;
    }

    @GetMapping("/user/registrationConfirm")
    public void confirmRegistration(@RequestParam("token") final String token) {
        final String result = userService.validateVerificationToken(token);
        if (!result.equals("valid")) throw new BusinessLogicException("Invalid token");
        final User user = userService.getUser(token);
        authWithoutPassword(user);
    }

    @GetMapping("/user/enableNewLoc")
    public void enableNewLoc(@RequestParam("token") String token) {
        final String loc = userService.isValidNewLocationToken(token);
        if (loc == null) throw new BusinessLogicException("Invalid token");
    }

    private void authWithoutPassword(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(p -> new SimpleGrantedAuthority(p.getName()))
                .collect(Collectors.toList());
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private SimpleMailMessage constructResendVerificationTokenEmail(final String contextPath, final Locale locale,
                                                                    final VerificationToken newToken, final User user) {
        final String confirmationUrl = contextPath + "/user/registrationConfirm?token=" + newToken.getToken();
        final String message = messages.getMessage("message.resendToken", null, locale);
        return constructEmail("Resend Registration Token", message + " \r\n" + confirmationUrl, user);
    }

    private SimpleMailMessage constructResetTokenEmail(final String contextPath, final Locale locale,
                                                       final String token, final User user) {
        final String url = contextPath + "/user/changePassword?token=" + token;
        final String message = messages.getMessage("message.resetPassword", null, locale);
        return constructEmail("Reset Password", message + " \r\n" + url, user);
    }

    private SimpleMailMessage constructEmail(String subject, String body, User user) {
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        email.setFrom(env.getProperty("support.email"));
        return email;
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    private String getClientIP(HttpServletRequest request) {
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

}
