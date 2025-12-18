package com.gustcustodio.dscatalog.services;

import com.gustcustodio.dscatalog.dtos.EmailDTO;
import com.gustcustodio.dscatalog.dtos.NewPasswordDTO;
import com.gustcustodio.dscatalog.entities.PasswordRecover;
import com.gustcustodio.dscatalog.entities.User;
import com.gustcustodio.dscatalog.repositories.PasswordRecoverRepository;
import com.gustcustodio.dscatalog.repositories.UserRepository;
import com.gustcustodio.dscatalog.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class AuthService {

    @Value("${email.password-recover.token.minutes}")
    private Long tokenMinutes;

    @Value(("${email.password-recover.uri}"))
    private String recoverUri;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordRecoverRepository passwordRecoverRepository;

    @Transactional
    public void createRecoverToken(EmailDTO emailDTO) {
        User user = userRepository.findByEmail(emailDTO.getEmail());
        if (user == null) throw new ResourceNotFoundException("Email não encontrado");

        String token = UUID.randomUUID().toString();
        PasswordRecover passwordRecover = new PasswordRecover();
        passwordRecover.setEmail(emailDTO.getEmail());
        passwordRecover.setToken(token);
        passwordRecover.setExpirationTime(Instant.now().plusSeconds(tokenMinutes * 60L));
        passwordRecoverRepository.save(passwordRecover);

        String body =
                "Acesse o link para definir uma nova senha\n\n" + recoverUri + token + ". Validade de " + tokenMinutes + " minutos.";

        emailService.sendEmail(emailDTO.getEmail(), "Recuperação de senha", body);
    }

    @Transactional
    public void saveNewPassword(NewPasswordDTO newPasswordDTO) {
        List<PasswordRecover> result = passwordRecoverRepository.searchValidTokens(newPasswordDTO.getToken(), Instant.now());
        if (result.isEmpty()) throw new ResourceNotFoundException("Token inválido");
        User user = userRepository.findByEmail(result.getFirst().getEmail());
        user.setPassword(passwordEncoder.encode(newPasswordDTO.getPassword()));
        userRepository.save(user);
    }

}
