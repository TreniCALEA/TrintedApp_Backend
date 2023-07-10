package it.unical.inf.ea.trintedapp.data.service;

import java.time.LocalDate;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import io.appwrite.Client;
import io.appwrite.coroutines.CoroutineCallback;
import io.appwrite.services.Account;
import it.unical.inf.ea.trintedapp.config.AppwriteConfig;
import it.unical.inf.ea.trintedapp.data.dao.ArticoloDao;
import it.unical.inf.ea.trintedapp.data.dao.OrdineDao;
import it.unical.inf.ea.trintedapp.data.dao.UtenteDao;
import it.unical.inf.ea.trintedapp.data.entities.Articolo;
import it.unical.inf.ea.trintedapp.data.entities.Indirizzo;
import it.unical.inf.ea.trintedapp.data.entities.Ordine;
import it.unical.inf.ea.trintedapp.data.entities.Utente;
import it.unical.inf.ea.trintedapp.dto.OrdineDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrdineServiceImpl implements OrdineService {

    private final OrdineDao ordineDao;
    private final ArticoloDao articoloDao;
    private final UtenteDao utenteDao;

    private final ModelMapper modelMapper;

    @Override
    public void save(Ordine ordine) {
        ordineDao.save(ordine);
    }

    @Override
    public OrdineDto save(OrdineDto ordineDto) {
        Ordine ordine = modelMapper.map(ordineDto, Ordine.class);
        return modelMapper.map(ordineDao.save(ordine), OrdineDto.class);
    }

    @Override
    public OrdineDto getById(Long id) {
        return modelMapper.map(
                ordineDao.findById(id).orElseThrow(
                        () -> new EntityNotFoundException(String.format("Non esiste ordine con id: [%s]", id))),
                OrdineDto.class);
    }

    @Override
    public List<OrdineDto> getByAcquirente(Long id, String encodedJwt) {
        String jwt = new String(Base64.getDecoder().decode(encodedJwt));

        CompletableFuture<List<OrdineDto>> vendite = new CompletableFuture<>();

        Client client = new Client(AppwriteConfig.ENDPOINT)
                .setProject(AppwriteConfig.PROJECT_ID)
                .setJWT(jwt);

        Account account = new Account(client);

        try {
            account.get(
                new CoroutineCallback<>((response, error) -> {
                    Utente utente = utenteDao.findByCredenzialiEmail(response.getEmail()).get();
                    if (utente.getId() == id || utente.getIsAdmin()) {
                        vendite.complete(ordineDao.findAllByAcquirenteId(id).stream()
                                .map(ordine -> modelMapper.map(ordine, OrdineDto.class))
                                .collect(Collectors.toList()));
                    } else {
                        vendite.completeExceptionally(new Exception("Non sei autorizzato a visualizzare questi ordini"));
                    }
                })
            );
        } catch (Exception e) {
            vendite.completeExceptionally(e);
            e.printStackTrace();
        }

        return vendite.join();
    }

    @Override
    public List<OrdineDto> getByVenditore(Long id, String encodedJwt) {
        String jwt = new String(Base64.getDecoder().decode(encodedJwt));

        CompletableFuture<List<OrdineDto>> acquisti = new CompletableFuture<>();

        Client client = new Client(AppwriteConfig.ENDPOINT)
                .setProject(AppwriteConfig.PROJECT_ID)
                .setJWT(jwt);

        Account account = new Account(client);

        try {
            account.get(
                new CoroutineCallback<>((response, error) -> {
                    Utente utente = utenteDao.findByCredenzialiEmail(response.getEmail()).get();
                    if (utente.getId() == id || utente.getIsAdmin()) {
                        acquisti.complete(ordineDao.findAllByVenditoreId(id).stream()
                                .map(ordine -> modelMapper.map(ordine, OrdineDto.class))
                                .collect(Collectors.toList()));
                    } else {
                        acquisti.completeExceptionally(new Exception("Non sei autorizzato a visualizzare questi ordini"));
                    }
                })
            );
        } catch (Exception e) {
            acquisti.completeExceptionally(e);
        }

        return acquisti.join();
    }

    @Override
    public Collection<OrdineDto> findAll(String encodedJwt) {
        String jwt = new String(Base64.getDecoder().decode(encodedJwt));

        CompletableFuture<List<OrdineDto>> ordini = new CompletableFuture<>();

        Client client = new Client(AppwriteConfig.ENDPOINT)
                .setProject(AppwriteConfig.PROJECT_ID)
                .setJWT(jwt);

        Account account = new Account(client);

        try {
            account.get(
                new CoroutineCallback<>((response, error) -> {
                    Utente utente = utenteDao.findByCredenzialiEmail(response.getEmail()).get();
                    if (utente.getIsAdmin()) {
                        ordini.complete(ordineDao.findAll().stream()
                                .map(ordine -> modelMapper.map(ordine, OrdineDto.class))
                                .collect(Collectors.toList()));
                    } else {
                        ordini.completeExceptionally(new Exception("Non sei autorizzato a visualizzare questi ordini"));
                    }
                })
            );
        } catch (Exception e) {
            ordini.completeExceptionally(e);
        }

        return ordini.join();
    }

    @Override
    public HttpStatus delete(Long id, String encodedJwt) {
        String jwt = new String(Base64.getDecoder().decode(encodedJwt));

        CompletableFuture<HttpStatus> status = new CompletableFuture<>();

        Client client = new Client(AppwriteConfig.ENDPOINT)
                .setProject(AppwriteConfig.PROJECT_ID)
                .setJWT(jwt);

        Account account = new Account(client);

        try {
            account.get(
                new CoroutineCallback<>((response, error) -> {
                    Utente utente = utenteDao.findByCredenzialiEmail(response.getEmail()).get();
                    if (utente.getIsAdmin()) {
                        ordineDao.deleteById(id);
                        status.complete(HttpStatus.OK);
                    } else {
                        status.complete(HttpStatus.UNAUTHORIZED);
                    }
                })
            );
        } catch (Exception e) {
            status.complete(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return status.join();
    }

    @Override
    @Transactional
    public HttpStatus confirmOrder(Long acquirente, Long articoloId, Indirizzo indirizzo, String encodedJwt) {
        String jwt = new String(Base64.getDecoder().decode(encodedJwt));

        CompletableFuture<HttpStatus> status = new CompletableFuture<>();

        Articolo _articolo = articoloDao.findById(articoloId).get();

        Utente _acquirente = utenteDao.findById(acquirente).get();
        Utente _venditore = utenteDao.findById(_articolo.getUtente().getId()).get();

        Client client = new Client(AppwriteConfig.ENDPOINT)
                .setProject(AppwriteConfig.PROJECT_ID)
                .setJWT(jwt);

        Account account = new Account(client);

        try {
            account.get(
                    new CoroutineCallback<>((response, error) -> {
                        if (response.getEmail().equals(_acquirente.getCredenziali().getEmail())) {
                            // create a new order in Ordine
                            Ordine nuovoOrdine = new Ordine();
                            nuovoOrdine.setAcquirente(_acquirente);
                            nuovoOrdine.setVenditore(_venditore);
                            nuovoOrdine.setArticolo(_articolo);
                            nuovoOrdine.setDataAcquisto(LocalDate.now());

                            nuovoOrdine.setIndirizzo(indirizzo);
                            ordineDao.save(nuovoOrdine);

                            // update Articolo
                            _articolo.setAcquistabile(false);
                            articoloDao.save(_articolo);
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
}
