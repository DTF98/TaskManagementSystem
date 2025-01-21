package ru.DTF98.taskManagementSystem.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.DTF98.taskManagementSystem.auth.model.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long userId;

    private String username;

    private Role role;

    public UserDTO (Long userId) {
        this.userId = userId;
    }
}
