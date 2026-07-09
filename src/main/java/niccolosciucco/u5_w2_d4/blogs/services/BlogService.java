package niccolosciucco.u5_w2_d4.blogs.services;

import niccolosciucco.u5_w2_d4.authors.entities.Author;
import niccolosciucco.u5_w2_d4.authors.repositories.AuthorRepository;
import niccolosciucco.u5_w2_d4.blogs.dto.BlogDTO;
import niccolosciucco.u5_w2_d4.blogs.entities.Blog;
import niccolosciucco.u5_w2_d4.blogs.repositories.BlogRepository;
import niccolosciucco.u5_w2_d4.exceptions.NotFound;
import niccolosciucco.u5_w2_d4.exceptions.NotValidAttribute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BlogService {
    private final BlogRepository blogRepository;
    private final AuthorRepository authorRepository;

    public BlogService(BlogRepository blogRepository, AuthorRepository authorRepository) {
        this.blogRepository = blogRepository;
        this.authorRepository = authorRepository;
    }

    public Blog post(BlogDTO blogDTO) {
        Author author = this.authorRepository.findById(blogDTO.authorId())
                .orElseThrow(() -> new NotFound("Impossibile creare il blog. Autore con id " + blogDTO.authorId() + " non trovato."));

        if (this.blogRepository.existsByTitle(blogDTO.title())) {
            throw new NotValidAttribute("Esiste già un articolo con il titolo: '" + blogDTO.title() + "'. Scegli un titolo diverso.");
        }

        if (blogDTO.readingTime() > 1000) {
            throw new NotValidAttribute("Il tempo di lettura inserito (" + blogDTO.readingTime() + " minuti) è troppo lungo. Massimo consentito: 1000 minuti.");
        }

        if (this.blogRepository.existsByAuthorAndTitle(author, blogDTO.title())) {
            throw new NotValidAttribute("Questo autore ha già pubblicato un articolo con questo esatto titolo.");
        }

        Blog newBlog = new Blog(blogDTO.category(), blogDTO.title(), blogDTO.plot(), blogDTO.readingTime(), author);

        return this.blogRepository.save(newBlog);
    }

    public Page<Blog> getAll(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return this.blogRepository.findAll(pageable);
    }

    public Blog findByIdAndUpdate(UUID id, BlogDTO blogDTO) {
        Blog found = this.blogRepository.findById(id)
                .orElseThrow(() -> new NotFound("Blog con id " + id + " non trovato."));

        Author author = this.authorRepository.findById(blogDTO.authorId())
                .orElseThrow(() -> new NotFound("Impossibile aggiornare il blog. Autore con id " + blogDTO.authorId() + " non trovato."));

        if (!found.getTitle().equals(blogDTO.title()) && this.blogRepository.existsByTitle(blogDTO.title())) {
            throw new NotValidAttribute("Esiste già un articolo con il titolo: '" + blogDTO.title() + "'. Scegli un titolo diverso.");
        }

        if (blogDTO.readingTime() > 1000) {
            throw new NotValidAttribute("Il tempo di lettura inserito (" + blogDTO.readingTime() + " minuti) è troppo lungo. Massimo consentito: 1000 minuti.");
        }

        found.setCategory(blogDTO.category());
        found.setTitle(blogDTO.title());
        found.setPlot(blogDTO.plot());
        found.setReadingTime(blogDTO.readingTime());
        found.setAuthor(author);

        return this.blogRepository.save(found);
    }

    public void findByIdAndDelete(UUID id) {
        Blog found = this.blogRepository.findById(id)
                .orElseThrow(() -> new NotFound("Impossibile eliminare. Blog con id " + id + " non trovato."));

        this.blogRepository.delete(found);
    }

}
