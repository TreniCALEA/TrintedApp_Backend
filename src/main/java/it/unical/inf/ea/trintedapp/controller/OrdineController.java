package it.unical.inf.ea.trintedapp.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import it.unical.inf.ea.trintedapp.data.entities.Indirizzo;
import it.unical.inf.ea.trintedapp.data.service.OrdineService;
import it.unical.inf.ea.trintedapp.dto.OrdineDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/order-api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class OrdineController {

    private final OrdineService ordineService;

    @GetMapping("/orders")
    public ResponseEntity<Collection<OrdineDto>> all(String jwt) {
        return ResponseEntity.ok(ordineService.findAll(jwt));
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrdineDto> getById(@PathVariable("orderId") Long id) {
        OrdineDto ordine = ordineService.getById(id);
        return (ordine != null) ? ResponseEntity.ok(ordine) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/orders/{orderId}")
    public HttpStatus delete(@PathVariable("orderId") Long id, @RequestParam String jwt) {
        return ordineService.delete(id, jwt);
    }

    @PostMapping("/orders/{acquirente}/{articoloId}")
    public HttpStatus add(@PathVariable("acquirente") Long acquirente, @PathVariable("articoloId") Long articoloId,
            @RequestBody Indirizzo indirizzo, @RequestParam String jwt) {
        return ordineService.confirmOrder(acquirente, articoloId, indirizzo, jwt);
    }

    @GetMapping("/seller")
    public ResponseEntity<List<OrdineDto>> getByVenditore(@RequestParam Long id, @RequestParam String jwt) {
        return ResponseEntity.ok(ordineService.getByVenditore(id, jwt));
    }

    @GetMapping("/buyer")
    public ResponseEntity<List<OrdineDto>> getByAcquirente(@RequestParam Long id, @RequestParam String jwt) {
        return ResponseEntity.ok(ordineService.getByAcquirente(id, jwt));
    }
}
