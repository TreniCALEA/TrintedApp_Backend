package it.unical.inf.ea.trintedapp.controller;

import it.unical.inf.ea.trintedapp.data.entities.Recensione;
import it.unical.inf.ea.trintedapp.data.service.RecensioneService;
import it.unical.inf.ea.trintedapp.dto.RecensioneDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review-api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class RecensioneController {

    private final RecensioneService recensioneService;

    @PostMapping("/review")
    public HttpStatus add(@RequestBody @Valid RecensioneDto review, @RequestParam String jwt) {
        return recensioneService.save(review, jwt);
    }

    @GetMapping("/review/{reviewId}")
    public ResponseEntity<List<Recensione>> findAll(@PathVariable("reviewId") Long id) {
        return ResponseEntity.ok(recensioneService.findAll(id));
    }

    @DeleteMapping("/review/{reviewId}")
    public HttpStatus delete(@PathVariable("reviewId") Long id, @RequestParam String jwt) {
        return recensioneService.delete(id, jwt);
    }

    @GetMapping("/review/all/{review}")
    public ResponseEntity<Page<RecensioneDto>> getAllPaged(@PathVariable("review") int review) {
        return ResponseEntity.ok(recensioneService.getAllPaged(review));
    }

}
