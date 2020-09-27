package com.amberlight.cloud.gateway.auth.service;





import com.amberlight.cloud.gateway.auth.persistence.model.NewLocationToken;
import com.amberlight.cloud.gateway.auth.persistence.model.PasswordResetToken;
import com.amberlight.cloud.gateway.dto.auth.UserDto;
import com.amberlight.cloud.gateway.error.auth.UserAlreadyExistException;
import com.amberlight.cloud.gateway.auth.persistence.model.User;
import com.amberlight.cloud.gateway.auth.persistence.model.VerificationToken;

import java.io.UnsupportedEncodingException;
import java.util.List;
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

//    List<String> getUsersFromSessionRegistry();

    NewLocationToken isNewLoginLocation(String username, String ip);

    String isValidNewLocationToken(String token);

    void addUserLocation(User user, String ip);
}
