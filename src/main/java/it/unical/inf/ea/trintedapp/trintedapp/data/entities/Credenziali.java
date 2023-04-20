package it.unical.inf.ea.trintedapp.trintedapp.data.entities;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class Credenziali {
    private String username;
    private String password;
    private String email;
}
