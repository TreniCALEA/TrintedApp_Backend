package it.unical.inf.ea.trintedapp.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public class OrdineDto {
    
    private Long id;

    @NotNull
    @PastOrPresent
    private LocalDate dataAcquisto;

}
