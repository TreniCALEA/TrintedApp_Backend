package it.unical.inf.ea.trintedapp.data.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.appwrite.ID;
import io.appwrite.coroutines.CoroutineCallback;
import io.appwrite.exceptions.AppwriteException;
import it.unical.inf.ea.trintedapp.config.AppwriteConfig;
import it.unical.inf.ea.trintedapp.data.dao.UtenteDao;
import it.unical.inf.ea.trintedapp.data.entities.Utente;
import it.unical.inf.ea.trintedapp.dto.UtenteBasicDto;
import it.unical.inf.ea.trintedapp.dto.UtenteDto;
import it.unical.inf.ea.trintedapp.dto.UtenteRegistrationDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UtenteServiceImpl implements UtenteService {

    private final UtenteDao utenteDao;

    private final ModelMapper modelMapper;

    @Override
    public void save(Utente utente) {
        utenteDao.save(utente);
    }

    @Override
    public UtenteRegistrationDto save(UtenteRegistrationDto utenteDto) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        utenteDto.setCredenzialiPassword(passwordEncoder.encode(utenteDto.getCredenzialiPassword()));
        try {
            AppwriteConfig.users.createBcryptUser(ID.Companion.unique(), utenteDto.getCredenzialiEmail(),
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
    public void delete(Long id) {
        utenteDao.deleteById(id);
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
                .orElseThrow(() -> new EntityNotFoundException(String.format("Non esiste un utente con email: [%s]", credenzialiEmail)));
        return modelMapper.map(utente, UtenteDto.class);
    }

    @Override
    public Collection<UtenteBasicDto> getAllByUsernameLike(String credenzialiUsername) {
        return utenteDao.getAllByCredenzialiUsernameLike(credenzialiUsername).stream()
                .map(u -> modelMapper.map(u, UtenteBasicDto.class))
                .collect(Collectors.toList());
    }

}
