package com.github.course.features.register;

import com.github.course.features.user.model.User;

import javax.mail.MessagingException;

public interface RegisterService {

    void registerNewUser(RegisterDto registerDto);

    void sendToken(User user) throws MessagingException;

    void confirmEmail(String value);
}
