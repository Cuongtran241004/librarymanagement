package com.example.librarymanagement.controller;


import com.example.librarymanagement.dto.LoginRequestDTO;
import com.example.librarymanagement.dto.LoginResponseDTO;
import com.example.librarymanagement.dto.RegisterRequestDTO;
import com.example.librarymanagement.entity.User;
import com.example.librarymanagement.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;


    @PostMapping("/registernormaluser")
    public ResponseEntity<User> registerNormalUser(
            @RequestBody RegisterRequestDTO registerRequestDTO
            ){
        return ResponseEntity.ok(authenticationService.registerNormalUser(registerRequestDTO));
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO){
        return ResponseEntity.ok(authenticationService.login(loginRequestDTO));
    }
}
