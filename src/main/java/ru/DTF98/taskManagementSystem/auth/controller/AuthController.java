package ru.DTF98.taskManagementSystem.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.DTF98.taskManagementSystem.auth.dto.JwtAuthenticationResponse;
import ru.DTF98.taskManagementSystem.auth.dto.SignInRequest;
import ru.DTF98.taskManagementSystem.auth.dto.SignUpRequest;
import ru.DTF98.taskManagementSystem.auth.service.AuthenticationService;
import ru.DTF98.taskManagementSystem.auth.service.UserService;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Аутентификация")
@Validated
public class AuthController {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/sign-up")
    public JwtAuthenticationResponse signUp(@RequestBody @Valid SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signIn(request);
    }

    @GetMapping("/get-admin")
    @Operation(summary = "Получить роль ADMIN (для демонстрации и тестов)")
    public String getAdmin() {
        Long id = userService.getAdmin();
        return String.format("Пользователь с id = %d теперь обладает правами администратора", id);
    }
}
