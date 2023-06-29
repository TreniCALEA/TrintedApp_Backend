package it.unical.inf.ea.trintedapp.data.service;

import it.unical.inf.ea.trintedapp.config.AppwriteConfig;
import it.unical.inf.ea.trintedapp.data.dao.ArticoloDao;
import it.unical.inf.ea.trintedapp.data.dao.UtenteDao;
import it.unical.inf.ea.trintedapp.data.entities.Articolo;
import it.unical.inf.ea.trintedapp.data.entities.Utente;
import it.unical.inf.ea.trintedapp.dto.ArticoloDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import io.appwrite.Client;
import io.appwrite.coroutines.CoroutineCallback;
import io.appwrite.exceptions.AppwriteException;
import io.appwrite.services.Account;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ArticoloServiceImpl implements ArticoloService {

    private final ArticoloDao articoloDao;
    private final UtenteDao utenteDao;
    private final ModelMapper modelMapper;

    @Override
    public void save(Articolo articolo) {
        articoloDao.save(articolo);
    }

    @Override
    public ArticoloDto save(ArticoloDto articoloDto, String jwt) {
        CompletableFuture<ArticoloDto> res = new CompletableFuture<>();

        Articolo articolo = modelMapper.map(articoloDto, Articolo.class);

        Utente venditore = utenteDao.findById(articoloDto.getUtenteId()).get();

        Client client = new Client(AppwriteConfig.ENDPOINT)
                .setProject(AppwriteConfig.PROJECT_ID)
                .setJWT(jwt);

        Account account = new Account(client);

        try {
            account.get(
                    new CoroutineCallback<>((response, error) -> {
                        if (response.getEmail().equals(venditore.getCredenziali().getEmail())) {
                            articoloDao.save(articolo);
                            res.complete(modelMapper.map(articolo, ArticoloDto.class));
                        }
                        else
                            res.complete(null);
                    }));
        } catch (Exception e) {
            res.completeExceptionally(e);
        }

        return res.join();
    }

    @Override
    public Collection<Articolo> findAll(Specification<Articolo> spec) {
        return articoloDao.findAll(spec);
    }

    @Override
    public ArticoloDto getById(Long id) {
        Articolo articolo = articoloDao.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Non esiste un articolo con id: [%s]", id)));
        return modelMapper.map(articolo, ArticoloDto.class);
    }

    @Override
    public Collection<ArticoloDto> findAll() {
        return articoloDao.findAll().stream()
                .map(u -> modelMapper.map(u, ArticoloDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CompletableFuture<HttpStatus> delete(Long id, String jwt) {
        CompletableFuture<HttpStatus> status = new CompletableFuture<>();

        Client client = new Client(AppwriteConfig.ENDPOINT)
                .setProject(AppwriteConfig.PROJECT_ID)
                .setJWT(jwt);

        Account account = new Account(client);

        System.out.println("jwt: " + jwt);

        Articolo articolo = articoloDao.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Non esiste un articolo con id: [%s]", id)));

        try {

            account.get(
                    new CoroutineCallback<>((response, error) -> {
                        System.out.println(response);
                        Utente utente = utenteDao.findByCredenzialiEmail(response.getEmail()).get();
                        Long userId = utente.getId(), articoloUserId = articolo.getUtente().getId();
                        System.out.println("userId: " + userId + ", articoloUserId: " + articoloUserId);
                        if (utente.getId() == articolo.getUtente().getId() || utente.getIsAdmin()) {
                            articoloDao.deleteById(id);
                            status.complete(HttpStatus.OK);
                        } else {
                            status.complete(HttpStatus.UNAUTHORIZED);
                        }
                    }));
        } catch (AppwriteException e) {
            status.complete(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return status;
    }

    @Override
    public Collection<ArticoloDto> getByTitoloContainingOrDescrizioneContaining(String titolo, String descrizione) {
        return articoloDao.findByTitoloContainingOrDescrizioneContaining(titolo, descrizione).stream()
                .map(u -> modelMapper.map(u, ArticoloDto.class))
                .collect(Collectors.toList());
    }

}
