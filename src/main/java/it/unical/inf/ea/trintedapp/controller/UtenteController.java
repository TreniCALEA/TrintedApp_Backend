package it.unical.inf.ea.trintedapp.controller;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unical.inf.ea.trintedapp.data.service.UtenteService;
import it.unical.inf.ea.trintedapp.dto.UtenteBasicDto;
import it.unical.inf.ea.trintedapp.dto.UtenteDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/user-api/")
@RestController
public class UtenteController {

    private final UtenteService utenteService;

    @PostMapping("/users")
    public ResponseEntity<UtenteDto> add(@RequestBody @Valid UtenteDto utente) {
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
    public HttpStatus delete(@PathVariable("idUser") Long id) {
        utenteService.delete(id);
        return HttpStatus.OK;
    }

    @GetMapping("/users/all/{page}")
    public ResponseEntity<Page<UtenteBasicDto>> getAllPaged(@PathVariable("page") int page) {
        return ResponseEntity.ok(utenteService.getAllPaged(page));
    }

    @GetMapping("/users/{name}/{page}")
    public ResponseEntity<Page<UtenteBasicDto>> getAllByNameLikePaged(@PathVariable("name") String n,
            @PathVariable("page") int page) {
        return ResponseEntity.ok(utenteService.getAllByNomeLike(n, page));
    }

}
