package com.amberlight.blog.gateway.auth.service;





import com.amberlight.blog.struct.security.NewLocationToken;
import com.amberlight.blog.struct.security.PasswordResetToken;
import com.amberlight.blog.gateway.dto.auth.UserDto;
import com.amberlight.blog.struct.security.auth.UserAlreadyExistException;
import com.amberlight.blog.struct.security.User;
import com.amberlight.blog.struct.security.VerificationToken;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

public interface IUserService {

    User registerNewUserAccount(UserDto accountDto) throws UserAlreadyExistException;

    User getUser(String verificationToken);

    void saveRegisteredUser(User user);

    void deleteUser(User user);

    void createVerificationTokenForUser(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);

    VerificationToken generateNewVerificationToken(String token);

    void createPasswordResetTokenForUser(User user, String token);

    User findUserByEmail(String email);

    PasswordResetToken getPasswordResetToken(String token);

    Optional<User> getUserByPasswordResetToken(String token);

    Optional<User> getUserByID(long id);

    void changeUserPassword(User user, String password);

    boolean checkIfValidOldPassword(User user, String password);

    String validateVerificationToken(String token);

    String generateQRUrl(User user) throws UnsupportedEncodingException;

    User updateUser2FA(boolean use2FA);

    NewLocationToken isNewLoginLocation(String username, String ip);

    String isValidNewLocationToken(String token);

    void addUserLocation(User user, String ip);

}
