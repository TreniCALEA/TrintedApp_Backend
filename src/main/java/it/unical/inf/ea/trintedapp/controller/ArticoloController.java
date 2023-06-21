package it.unical.inf.ea.trintedapp.controller;

import it.unical.inf.ea.trintedapp.data.service.ArticoloService;
import it.unical.inf.ea.trintedapp.dto.ArticoloDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item-api/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ArticoloController {
    private final ArticoloService articoloService;

    @PostMapping("/item")
    public ResponseEntity<ArticoloDto> add(@RequestBody @Valid ArticoloDto articolo) {
        return ResponseEntity.ok(articoloService.save(articolo));
    }

    @GetMapping("/item")
    public ResponseEntity<Collection<ArticoloDto>> all() {
        return ResponseEntity.ok(articoloService.findAll());
    }

    @GetMapping("/item/{idItem}")
    public ResponseEntity<ArticoloDto> getById(@PathVariable("idItem") Long id) {
        return ResponseEntity.ok(articoloService.getById(id));
    }

    @DeleteMapping("/item/{idItem}")
    public CompletableFuture<HttpStatus> delete(@PathVariable("idItem") Long id, @RequestBody String sessionId) {
        return articoloService.delete(id, sessionId);
    }

    @GetMapping("/item/search/{searchValue}")
    public ResponseEntity<Collection<ArticoloDto>> getByTitoloContainingOrDescrizioneContaining(
            @PathVariable("searchValue") String searchValue) {
        return ResponseEntity
                .ok(articoloService.getByTitoloContainingOrDescrizioneContaining(searchValue, searchValue));
    }

}
