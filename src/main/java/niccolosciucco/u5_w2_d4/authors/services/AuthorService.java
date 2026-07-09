package niccolosciucco.u5_w2_d4.authors.services;

import niccolosciucco.u5_w2_d4.authors.dto.AuthorDTO;
import niccolosciucco.u5_w2_d4.authors.entities.Author;
import niccolosciucco.u5_w2_d4.authors.repositories.AuthorRepository;
import niccolosciucco.u5_w2_d4.exceptions.NotFound;
import niccolosciucco.u5_w2_d4.exceptions.NotValidAttribute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author post(AuthorDTO authorDTO) {
        if (this.authorRepository.existsByEmail(authorDTO.email())) {
            throw new NotValidAttribute("L'email " + authorDTO.email() + " è già associata a un autore.");
        }

        if (authorDTO.dateOfBirth().isAfter(LocalDate.now().minusYears(18))) {
            throw new NotValidAttribute("L'autore deve essere maggiorenne.");
        }

        return this.authorRepository.save(new Author(authorDTO.name(), authorDTO.lastName(), authorDTO.email(), authorDTO.dateOfBirth()));
    }

    public Page<Author> getAll(int page) {
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, 10);
        return this.authorRepository.findAll(pageable);
    }

    public Author findByIdAndUpdate(UUID id, AuthorDTO authorDTO) {
        Author found = this.authorRepository.findById(id)
                .orElseThrow(() -> new NotFound("Autore con id " + id + " non trovato."));

        if (this.authorRepository.existsByEmail(authorDTO.email())) {
            throw new NotValidAttribute("L'email " + authorDTO.email() + " è già associata a un autore.");
        }

        if (authorDTO.dateOfBirth().isAfter(LocalDate.now().minusYears(18))) {
            throw new NotValidAttribute("L'autore deve essere maggiorenne.");
        }

        found.setName(authorDTO.name());
        found.setLastName(authorDTO.lastName());
        found.setEmail(authorDTO.email());
        found.setDateOfBirth(authorDTO.dateOfBirth());

        return this.authorRepository.save(found);
    }

    public void findByIdAndDelete(UUID id) {
        Author found = this.authorRepository.findById(id)
                .orElseThrow(() -> new NotFound("Impossibile eliminare. Autore con id " + id + " non trovato."));

        this.authorRepository.delete(found);
    }
}
