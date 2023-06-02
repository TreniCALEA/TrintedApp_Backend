package it.unical.inf.ea.trintedapp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UtenteRegistrationDto {

    @NotNull
    private String credenzialiUsername;

    @NotNull
    private String credenzialiEmail;

    @NotNull
    @Size(min = 8)
    private String credenzialiPassword;

}
