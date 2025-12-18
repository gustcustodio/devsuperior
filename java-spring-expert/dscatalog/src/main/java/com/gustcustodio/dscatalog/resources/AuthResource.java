package com.gustcustodio.dscatalog.resources;

import com.gustcustodio.dscatalog.dtos.EmailDTO;
import com.gustcustodio.dscatalog.dtos.NewPasswordDTO;
import com.gustcustodio.dscatalog.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

    @Autowired
    private AuthService authService;

    @PostMapping(value = "/recover-token")
    public ResponseEntity<Void> createRecoverToken(@Valid @RequestBody EmailDTO emailDTO) {
        authService.createRecoverToken(emailDTO);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/new-password")
    public ResponseEntity<Void> saveNewPassword(@Valid @RequestBody NewPasswordDTO newPasswordDTO) {
        authService.saveNewPassword(newPasswordDTO);
        return ResponseEntity.noContent().build();
    }

}
