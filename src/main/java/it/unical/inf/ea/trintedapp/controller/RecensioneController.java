package it.unical.inf.ea.trintedapp.controller;

import it.unical.inf.ea.trintedapp.data.entities.Recensione;
import it.unical.inf.ea.trintedapp.data.service.RecensioneService;
import it.unical.inf.ea.trintedapp.dto.UtenteDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@RestController
@RequestMapping("/review-api/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class RecensioneController {

    private final RecensioneService recensioneService;

    @GetMapping("/review/{reviewId}")
    public ResponseEntity<Collection<Recensione>> findAll(@PathVariable("reviewId") Long id){
        return ResponseEntity.ok(recensioneService.findAll(id));
    }

    @DeleteMapping("/review/{reviewId}")
    public HttpStatus delete(@PathVariable("reviewId") Long id){
        recensioneService.delete(id);
        return HttpStatus.OK;
    }

    @PostMapping("/review")
    public HttpStatus add(@RequestBody @Valid Recensione recensione) {
        recensioneService.saveOrUpdate(recensione);
        return HttpStatus.OK;
    }

}
