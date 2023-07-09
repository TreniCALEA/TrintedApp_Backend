package it.unical.inf.ea.trintedapp.data.service;

import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import io.appwrite.Client;
import io.appwrite.ID;
import io.appwrite.coroutines.CoroutineCallback;
import io.appwrite.exceptions.AppwriteException;
import io.appwrite.services.Account;
import io.appwrite.services.Users;
import it.unical.inf.ea.trintedapp.config.AppwriteConfig;
import it.unical.inf.ea.trintedapp.data.dao.UtenteBannatoDao;
import it.unical.inf.ea.trintedapp.data.dao.UtenteDao;
import it.unical.inf.ea.trintedapp.data.entities.Utente;
import it.unical.inf.ea.trintedapp.data.entities.UtenteBannato;
import it.unical.inf.ea.trintedapp.dto.UtenteBasicDto;
import it.unical.inf.ea.trintedapp.dto.UtenteCompletionDto;
import it.unical.inf.ea.trintedapp.dto.UtenteDto;
import it.unical.inf.ea.trintedapp.dto.UtenteRegistrationDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UtenteServiceImpl implements UtenteService {

    private final UtenteBannatoDao utenteBannatoDao;
    private final UtenteDao utenteDao;
    private final ModelMapper modelMapper;
    private final String pfpImg = "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";

    @Override
    public void save(Utente utente) {
        utenteDao.save(utente);
    }

    @Override
    public HttpStatus isBanned(String email) {
        Optional<UtenteBannato> utenteBannato = utenteBannatoDao.findByEmailBannata(email);
        if (utenteBannato.isPresent()) return HttpStatus.OK;
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public UtenteRegistrationDto save(UtenteRegistrationDto utenteDto) {
        if (isBanned(utenteDto.getCredenzialiEmail()) == HttpStatus.FORBIDDEN) {
            throw new IllegalArgumentException("Utente bannato");
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        utenteDto.setCredenzialiPassword(passwordEncoder.encode(utenteDto.getCredenzialiPassword()));

        Client client = new Client(AppwriteConfig.ENDPOINT)
                .setProject(AppwriteConfig.PROJECT_ID)
                .setKey(AppwriteConfig.API_KEY);

        Users users = new Users(client);

        try {
            users.createBcryptUser(ID.Companion.unique(), utenteDto.getCredenzialiEmail(),
                    utenteDto.getCredenzialiPassword(),
                    utenteDto.getCredenzialiUsername(),
                    new CoroutineCallback<>((response, error) -> {
                        if (error != null) {
                            error.printStackTrace();
                        }
                        System.out.println(response);
                    }));
        } catch (AppwriteException e) {
        }
        Utente utente = modelMapper.map(utenteDto, Utente.class);
        utente.setImmagine(pfpImg);
        utente.setIsAdmin(false);
        utente.setIsOwner(false);

        Utente u = utenteDao.save(utente);
        return modelMapper.map(u, UtenteRegistrationDto.class);
    }

    @Override
    public Collection<UtenteBasicDto> findAll(Specification<Utente> spec) {
        return utenteDao.findAll(spec).stream()
                .map(u -> modelMapper.map(u, UtenteBasicDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UtenteDto getById(Long id) {
        Utente utente = utenteDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Non esiste un utente con id: [%s]", id)));
        return modelMapper.map(utente, UtenteDto.class);
    }

    @Override
    public Collection<UtenteBasicDto> findAll() {
        return utenteDao.findAll().stream()
                .map(u -> modelMapper.map(u, UtenteBasicDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public HttpStatus delete(Long id, String encodedJwt) {
        String jwt = new String(Base64.getDecoder().decode(encodedJwt));

        CompletableFuture<HttpStatus> res = new CompletableFuture<>();

        Client client = new Client(AppwriteConfig.ENDPOINT)
                .setProject(AppwriteConfig.PROJECT_ID)
                .setKey(AppwriteConfig.API_KEY)
                .setJWT(jwt);

        Account account = new Account(client);

        Users users = new Users(client);

        try {
            account.get(
                    new CoroutineCallback<>((response, error) -> {
                        Utente utente = utenteDao.findByCredenzialiEmail(response.getEmail()).get();
                        if (utente.getId() == id) {
                            utenteDao.deleteById(id);
                            try {
                                users.delete(response.getId(),
                                        new CoroutineCallback<>((resp, err) -> {
                                            System.out.println("Utente eliminato da Appwrite");
                                        }));
                            } catch (Exception e) {
                            }
                            res.complete(HttpStatus.OK);
                        } else
                            res.complete(HttpStatus.UNAUTHORIZED);
                    }));
        } catch (Exception e) {
            res.complete(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return res.join();
    }

    private static final int SIZE_FOR_PAGE = 20;

    @Override
    public Page<UtenteBasicDto> getAllPaged(int page) {
        Page<Utente> utenti = utenteDao.findAll(PageRequest.of(page, SIZE_FOR_PAGE));
        List<UtenteBasicDto> listUtenti = utenti.stream().map(u -> modelMapper.map(u, UtenteBasicDto.class))
                .collect(Collectors.toList());
        return new PageImpl<>(listUtenti);
    }

    @Override
    public Page<UtenteBasicDto> getAllByUsernameLike(String nome, int page) {
        PageRequest pageRequest = PageRequest.of(page, SIZE_FOR_PAGE, Sort.by("nome").ascending());
        List<UtenteBasicDto> list = utenteDao.getByUsernameLike(nome, pageRequest).stream()
                .map(u -> modelMapper.map(u, UtenteBasicDto.class))
                .collect(Collectors.toList());
        return new PageImpl<>(list);
    }

    @Override
    public UtenteDto getByCredenzialiEmail(String credenzialiEmail) {
        Utente utente = utenteDao.findByCredenzialiEmail(credenzialiEmail)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Non esiste un utente con email: [%s]", credenzialiEmail)));
        return modelMapper.map(utente, UtenteDto.class);
    }

    @Override
    public Collection<UtenteBasicDto> getAllByUsernameLike(String credenzialiUsername) {
        return utenteDao.getAllByCredenzialiUsernameLike(credenzialiUsername).stream()
                .map(u -> modelMapper.map(u, UtenteBasicDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public HttpStatus update(Long id, UtenteCompletionDto utenteCompletionDto, String encodedJwt) {
        String jwt = new String(Base64.getDecoder().decode(encodedJwt));

        CompletableFuture<HttpStatus> res = new CompletableFuture<>();

        Client client = new Client(AppwriteConfig.ENDPOINT)
                .setProject(AppwriteConfig.PROJECT_ID)
                .setJWT(jwt);

        Account account = new Account(client);

        try {
            account.get(
                    new CoroutineCallback<>((response, error) -> {
                        Utente utente = utenteDao.findByCredenzialiEmail(response.getEmail()).get();
                        if (utente.getId() == id) {
                            utente.setNome(
                                    utenteCompletionDto.getNome().isEmpty() ? null : utenteCompletionDto.getNome());

                            utente.setCognome(utenteCompletionDto.getCognome().isEmpty() ? null
                                    : utenteCompletionDto.getCognome());

                            if (utenteCompletionDto.getImmagine() == null)
                                utente.setImmagine(pfpImg);
                            else
                                utente.setImmagine(utenteCompletionDto.getImmagine());

                            if (utenteCompletionDto.getIndirizzo() != null)
                                utente.setIndirizzo(utenteCompletionDto.getIndirizzo());
                            else
                                utente.setIndirizzo(null);

                            utenteDao.save(utente);
                            res.complete(HttpStatus.OK);
                        } else
                            res.complete(HttpStatus.FORBIDDEN);
                    }));
        } catch (Exception e) {
            res.complete(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return res.join();
    }

    @Override
    public HttpStatus makeAdmin(Long id, String encodedJwt) {
        String jwt = new String(Base64.getDecoder().decode(encodedJwt));

        CompletableFuture<HttpStatus> res = new CompletableFuture<>();

        Client client = new Client(AppwriteConfig.ENDPOINT)
                .setProject(AppwriteConfig.PROJECT_ID)
                .setJWT(jwt);

        Account account = new Account(client);

        try {
            account.get(
                    new CoroutineCallback<>((response, error) -> {
                        Utente utente = utenteDao.findByCredenzialiEmail(response.getEmail()).get();
                        if (utente.getIsAdmin()) {
                            Utente utenteDaRendereAdmin = utenteDao.findById(id).get();
                            utenteDaRendereAdmin.setIsAdmin(true);
                            utenteDao.save(utenteDaRendereAdmin);
                            res.complete(HttpStatus.OK);
                        } else
                            res.complete(HttpStatus.UNAUTHORIZED);
                    }));
        } catch (Exception e) {
            res.complete(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return res.join();
    }

    @Override
    public HttpStatus revokeAdmin(Long id, String encodedJwt) {
        String jwt = new String(Base64.getDecoder().decode(encodedJwt));

        CompletableFuture<HttpStatus> res = new CompletableFuture<>();

        Client client = new Client(AppwriteConfig.ENDPOINT)
                .setProject(AppwriteConfig.PROJECT_ID)
                .setJWT(jwt);

        Account account = new Account(client);

        try {
            account.get(
                    new CoroutineCallback<>((response, error) -> {
                        Utente utente = utenteDao.findByCredenzialiEmail(response.getEmail()).get();
                        Utente utenteNonPiuAdmin = utenteDao.findById(id).get();
                        if (utenteNonPiuAdmin.getIsOwner() || !utente.getIsAdmin()) {
                            res.complete(HttpStatus.FORBIDDEN);
                        } else if (utente.getIsAdmin()) {
                            utenteNonPiuAdmin.setIsAdmin(false);
                            utenteDao.save(utenteNonPiuAdmin);
                            res.complete(HttpStatus.OK);
                        }
                    }));
        } catch (Exception e) {
            res.complete(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return res.join();
    }

    @Override
    public HttpStatus banUser(Long id, String encodedJwt) {
        String jwt = new String(Base64.getDecoder().decode(encodedJwt));

        CompletableFuture<HttpStatus> res = new CompletableFuture<>();

        Client client = new Client(AppwriteConfig.ENDPOINT)
                .setProject(AppwriteConfig.PROJECT_ID)
                .setJWT(jwt);

        Account account = new Account(client);

        try {
            account.get(
                    new CoroutineCallback<>((response, error) -> {
                        Utente utente = utenteDao.findByCredenzialiEmail(response.getEmail()).get();
                        Utente utenteDaBannare = utenteDao.findById(id).get();

                        if (utenteDaBannare.getIsOwner())
                            res.complete(HttpStatus.UNAUTHORIZED);
                        else if (utente.getIsAdmin()) {
                            UtenteBannato utenteBannato = new UtenteBannato();
                            utenteBannato.setEmailBannata(utenteDaBannare.getCredenziali().getEmail());
                            utenteBannatoDao.save(utenteBannato);
                            res.complete(HttpStatus.OK);
                        }

                    }));
        } catch (Exception e) {
            res.complete(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return res.join();
    }

}
