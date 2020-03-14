package net.ujacha.board.api.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class JoinForm {
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String password;

    @NotEmpty
    private String password2;

    @NotEmpty
    private String displayName;
}
