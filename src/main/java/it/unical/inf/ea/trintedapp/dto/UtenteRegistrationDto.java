package it.unical.inf.ea.trintedapp.dto;

import jakarta.validation.constraints.NotNull;

public class UtenteRegistrationDto {

    @NotNull
    private String credenzialiUsername;

    @NotNull
    private String credenzialiEmail;

    @NotNull
    private String credenzialiPassword;

}
