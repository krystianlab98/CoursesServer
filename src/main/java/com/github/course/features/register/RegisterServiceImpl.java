package com.github.course.features.register;

import com.github.course.features.register.mail.EmailToken;
import com.github.course.features.register.mail.EmailTokenRepository;
import com.github.course.features.register.mail.MailServiceImpl;
import com.github.course.features.user.model.Role;
import com.github.course.features.user.model.User;
import com.github.course.features.user.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class RegisterServiceImpl implements RegisterService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private EmailTokenRepository emailTokenRepository;
    private MailServiceImpl mailService;

    @Autowired
    public RegisterServiceImpl(UserRepository userRepository,
                               PasswordEncoder passwordEncoder,
                               EmailTokenRepository emailTokenRepository,
                               MailServiceImpl mailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailTokenRepository = emailTokenRepository;
        this.mailService = mailService;
    }


    @Override
    public void registerNewUser(RegisterDto registerDto) {
        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        List<Role> roles = Arrays.asList(Role.ROLE_USER);
        user.setRoles(roles);
        userRepository.save(user);
        sendToken(user);
    }

    @Override
    public void sendToken(User user) {
        String token = UUID.randomUUID().toString();
        EmailToken emailToken = new EmailToken();
        emailToken.setValue(token);
        emailToken.setUser(user);
        emailTokenRepository.save(emailToken);
        String url = "http://localhost:8080//web/api/token?value=" + token;

        try {
            mailService.sendMail(user.getEmail(), "Potwierdzenie rejestracji", url, false);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void confirmEmail(String value) {
        EmailToken emailToken = emailTokenRepository.findByValue(value);
        User user = emailToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);
    }


}
