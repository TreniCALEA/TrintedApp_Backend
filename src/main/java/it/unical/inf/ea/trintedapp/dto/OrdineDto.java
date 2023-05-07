package it.unical.inf.ea.trintedapp.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class OrdineDto {
    
    private Long id;

    @NotNull
    @PastOrPresent
    private LocalDate dataAcquisto;

}
