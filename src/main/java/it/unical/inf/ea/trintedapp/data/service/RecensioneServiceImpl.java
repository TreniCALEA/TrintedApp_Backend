package it.unical.inf.ea.trintedapp.data.service;

import it.unical.inf.ea.trintedapp.config.AppwriteConfig;
import it.unical.inf.ea.trintedapp.data.dao.RecensioneDao;
import it.unical.inf.ea.trintedapp.data.dao.UtenteDao;
import it.unical.inf.ea.trintedapp.data.entities.Recensione;
import it.unical.inf.ea.trintedapp.data.entities.Utente;
import it.unical.inf.ea.trintedapp.dto.RecensioneDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import io.appwrite.Client;
import io.appwrite.coroutines.CoroutineCallback;
import io.appwrite.services.Account;

import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecensioneServiceImpl implements RecensioneService {

    private final RecensioneDao recensioneDao;
    private final ModelMapper modelMapper;
    private final UtenteDao utenteDao;

    @Override
    public void save(Recensione recensione) {
        recensioneDao.save(recensione);
    }

    @Override
    public List<Recensione> findAll(Long id) {
        Specification<Recensione> sp = recensioneDao.recensioneDestinatario(id);
        return recensioneDao.findAll(sp);
    }

    @Override
    public RecensioneDto getById(Long id) {
        Recensione recensione = recensioneDao.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(String.format("Non esiste una recensione con id: [%s]", id)));
        return modelMapper.map(recensione, RecensioneDto.class);
    }

    @Override
    public Collection<RecensioneDto> findAll() {
        return recensioneDao.findAll().stream()
                .map(recensione -> modelMapper.map(recensione, RecensioneDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public HttpStatus delete(Long id, String encodedJwt) {
        String jwt = new String(Base64.getDecoder().decode(encodedJwt));

        CompletableFuture<HttpStatus> status = new CompletableFuture<>();

        RecensioneDto recensione = getById(id);

        // Update destinatario's rating accordingly
        Utente destinatario = utenteDao.findByCredenzialiEmail(recensione.getDestinatarioCredenzialiEmail()).get();

        Client client = new Client(AppwriteConfig.ENDPOINT)
                .setProject(AppwriteConfig.PROJECT_ID)
                .setJWT(jwt);

        Account account = new Account(client);

        try {
            account.get(
                    new CoroutineCallback<>((response, error) -> {
                        Utente jwtUser = utenteDao.findByCredenzialiEmail(response.getEmail()).get();
                        if (destinatario.getId() == jwtUser.getId() || jwtUser.getIsAdmin()) {
                            recensioneDao.deleteById(id);
                            List<Recensione> recensioni = findAll(destinatario.getId());
                            if (!recensioni.isEmpty()) {
                                float sum = 0;
                                for (Recensione r : recensioni) {
                                    sum += r.getRating();
                                }
                                destinatario.setRatingGenerale(sum / recensioni.size());
                            } else {
                                destinatario.setRatingGenerale(null);
                            }

                            utenteDao.save(destinatario);

                            status.complete(HttpStatus.OK);

                        } else {
                            status.complete(HttpStatus.UNAUTHORIZED);
                        }
                    }));
        } catch (Exception e) {
            status.complete(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return status.join();
    }

    private static final int SIZE_FOR_PAGE = 20;

    @Override
    public Page<RecensioneDto> getAllPaged(int page) {
        Page<Recensione> recensioni = recensioneDao.findAll(PageRequest.of(page, SIZE_FOR_PAGE));
        List<RecensioneDto> listRecensioni = recensioni.stream()
                .map(recensione -> modelMapper.map(recensione, RecensioneDto.class))
                .collect(Collectors.toList());
        return new PageImpl<>(listRecensioni);
    }

    @Override
    public HttpStatus save(RecensioneDto recensioneDto, String encodedJwt) {
        String jwt = new String(Base64.getDecoder().decode(encodedJwt));

        CompletableFuture<HttpStatus> status = new CompletableFuture<>();

        Client client = new Client(AppwriteConfig.ENDPOINT)
                .setProject(AppwriteConfig.PROJECT_ID)
                .setJWT(jwt);

        Account account = new Account(client);

        Recensione recensione = modelMapper.map(recensioneDto, Recensione.class);

        // Get the Utente objects based on the email
        Utente autore = utenteDao.findByCredenzialiEmail(recensioneDto.getAutoreCredenzialiEmail()).get();
        Utente destinatario = utenteDao.findByCredenzialiEmail(recensioneDto.getDestinatarioCredenzialiEmail()).get();

        try {

            account.get(
                    new CoroutineCallback<>((response, error) -> {
                        if (response.getEmail().equals(autore.getCredenziali().getEmail())) {

                            // Set the Utente objects as the autore and destinatario
                            recensione.setAutore(autore);
                            recensione.setDestinatario(destinatario);

                            recensioneDao.save(recensione);

                            // Update rating accordingly
                            List<Recensione> recensioni = findAll(destinatario.getId());
                            if (!recensioni.isEmpty()) {
                                float sum = 0;
                                for (Recensione r : recensioni) {
                                    sum += r.getRating();
                                }
                                destinatario.setRatingGenerale(sum / recensioni.size());
                            } else {
                                destinatario.setRatingGenerale(recensione.getRating());
                            }
                            utenteDao.save(destinatario);
                            status.complete(HttpStatus.OK);
                        } else {
                            status.complete(HttpStatus.FORBIDDEN);
                        }
                    }));

        } catch (Exception e) {
            status.completeExceptionally(e);
        }

        return status.join();
    }

}
