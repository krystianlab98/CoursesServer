package com.github.course.features.register;

import com.github.course.features.register.mail.EmailToken;
import com.github.course.features.register.mail.EmailTokenRepository;
import com.github.course.features.register.mail.MailServiceImpl;
import com.github.course.features.user.User;
import com.github.course.features.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
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
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRole("ROLE_USER");
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
