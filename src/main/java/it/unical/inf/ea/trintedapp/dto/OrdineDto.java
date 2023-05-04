package it.unical.inf.ea.trintedapp.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotNull;

public class OrdineDto {
    
    private Long id;

    @NotNull
    private LocalDate dataAcquisto;

}
