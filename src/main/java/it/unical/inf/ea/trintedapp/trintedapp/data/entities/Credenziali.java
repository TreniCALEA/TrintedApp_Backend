package it.unical.inf.ea.trintedapp.trintedapp.data.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Credenziali {
    private String username;
    private String password;
    private String email;
}
