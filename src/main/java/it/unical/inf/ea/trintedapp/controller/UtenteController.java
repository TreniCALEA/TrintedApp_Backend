package it.unical.inf.ea.trintedapp.controller;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.unical.inf.ea.trintedapp.data.service.UtenteService;
import it.unical.inf.ea.trintedapp.dto.UtenteBasicDto;
import it.unical.inf.ea.trintedapp.dto.UtenteCompletionDto;
import it.unical.inf.ea.trintedapp.dto.UtenteDto;
import it.unical.inf.ea.trintedapp.dto.UtenteRegistrationDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/user-api")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UtenteController {

    private final UtenteService utenteService;

    @PostMapping("/users/{idUser}")
    public HttpStatus update(@PathVariable("idUser") Long id,
            @RequestBody @Valid UtenteCompletionDto utente, @RequestParam String jwt) {
        return utenteService.update(id, utente, jwt);
    }

    @PostMapping("/users")
    public ResponseEntity<UtenteRegistrationDto> add(@RequestBody @Valid UtenteRegistrationDto utente) {
        return ResponseEntity.ok(utenteService.save(utente));
    }

    @GetMapping("/users")
    public ResponseEntity<Collection<UtenteBasicDto>> all() {
        return ResponseEntity.ok(utenteService.findAll());
    }

    @GetMapping("/users/{idUser}")
    public ResponseEntity<UtenteDto> getById(@PathVariable("idUser") Long id) {
        UtenteDto utente = utenteService.getById(id);
        return ResponseEntity.ok(utente);
    }

    @DeleteMapping("/users/{idUser}")
    public HttpStatus delete(@PathVariable("idUser") Long id, @RequestParam String jwt) {
        return utenteService.delete(id, jwt);
    }

    @PatchMapping("/users/makeAdmin/{idUser}")
    public HttpStatus makeAdmin(@PathVariable("idUser") Long id, @RequestParam String jwt) {
        return utenteService.makeAdmin(id, jwt);
    }

    @GetMapping("/users/banned")
    public HttpStatus isBanned(@RequestParam String email) {
        return utenteService.isBanned(email);
    }

    @PatchMapping("/users/revokeAdmin/{idUser}")
    public HttpStatus revokeAdmin(@PathVariable("idUser") Long id, @RequestParam String jwt) {
        return utenteService.revokeAdmin(id, jwt);
    }

    @PostMapping("/users/banUser/{idUser}")
    public HttpStatus banUser(@PathVariable("idUser") Long id, @RequestParam String jwt) {
        return utenteService.banUser(id, jwt);
    }

    @GetMapping("/users/all/{page}")
    public ResponseEntity<Page<UtenteBasicDto>> getAllPaged(@PathVariable("page") int page) {
        return ResponseEntity.ok(utenteService.getAllPaged(page));
    }

    @GetMapping("/users/find/{prefix}")
    public ResponseEntity<Collection<UtenteBasicDto>> getAllByUsernameLike(@PathVariable("prefix") String prefix) {
        return ResponseEntity.ok(utenteService.getAllByUsernameLike(prefix));
    }

    @GetMapping("/users/email/{credenzialiEmail}")
    public ResponseEntity<UtenteDto> getByCredenzialiEmail(@PathVariable("credenzialiEmail") String credenzialiEmail) {
        UtenteDto utente = utenteService.getByCredenzialiEmail(credenzialiEmail);
        return ResponseEntity.ok(utente);
    }
}
