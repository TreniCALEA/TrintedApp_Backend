package it.unical.inf.ea.trintedapp.controller;

import java.util.Collection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unical.inf.ea.trintedapp.data.service.OrdineService;
import it.unical.inf.ea.trintedapp.dto.OrdineDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/order/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class OrdineController {

    private final OrdineService ordineService;

    @GetMapping("/orders")
    public ResponseEntity<Collection<OrdineDto>> all() {
        return ResponseEntity.ok(ordineService.findAll());
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrdineDto> getById(@PathVariable("orderId") Long id) {
        OrdineDto ordine = ordineService.getById(id);
        return (ordine == null) ? ResponseEntity.ok(ordine) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/orders/{orderId}")
    public HttpStatus delete(@PathVariable("orderId") Long id) {
        ordineService.delete(id);
        return HttpStatus.OK;
    }

}
