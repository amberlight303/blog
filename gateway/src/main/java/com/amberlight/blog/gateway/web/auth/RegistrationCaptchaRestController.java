package com.amberlight.blog.gateway.web.auth;



import com.amberlight.blog.gateway.auth.captcha.CaptchaServiceV3;
import com.amberlight.blog.gateway.auth.captcha.ICaptchaService;
import com.amberlight.blog.gateway.auth.registration.OnRegistrationCompleteEvent;
import com.amberlight.blog.gateway.auth.service.IUserService;
import com.amberlight.blog.gateway.dto.auth.UserDto;
import com.amberlight.blog.struct.security.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class RegistrationCaptchaRestController {

    private static final Logger logger = LogManager.getLogger(RegistrationCaptchaRestController.class);

    @Autowired
    private IUserService userService;

    @Autowired
    private ICaptchaService captchaService;
    
    @Autowired
    private ICaptchaService captchaServiceV3;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public RegistrationCaptchaRestController() {
        super();
    }

    // Registration
    @PostMapping("/user/registrationCaptcha")
    public void captchaRegisterUserAccount(@Valid final UserDto accountDto, final HttpServletRequest request) {
        final String response = request.getParameter("g-recaptcha-response");
        captchaService.processResponse(response);
        registerNewUserHandler(accountDto, request);
    }
    
    // Registration reCaptchaV3
    @PostMapping("/user/registrationCaptchaV3")
    public void captchaV3RegisterUserAccount(@Valid final UserDto accountDto, final HttpServletRequest request) {
        final String response = request.getParameter("response");
        captchaServiceV3.processResponse(response, CaptchaServiceV3.REGISTER_ACTION);
        registerNewUserHandler(accountDto, request);
    }
    
    private void registerNewUserHandler(final UserDto accountDto, final HttpServletRequest request) {
        logger.debug("Registering user account with information: {}", accountDto);
        final User registered = userService.registerNewUserAccount(accountDto);
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), getAppUrl(request)));
    }
    

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

}
