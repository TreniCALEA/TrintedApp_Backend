package it.unical.inf.ea.trintedapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UtenteRegistrationDto {

    @NotNull
    @NotBlank
    private String credenzialiUsername;

    @NotNull
    @NotBlank
    @Email
    private String credenzialiEmail;

    @NotNull
    @Size(min = 8)
    private String credenzialiPassword;

}
