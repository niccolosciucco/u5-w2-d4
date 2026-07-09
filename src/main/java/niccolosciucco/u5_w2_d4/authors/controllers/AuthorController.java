package niccolosciucco.u5_w2_d4.authors.controllers;

import niccolosciucco.u5_w2_d4.authors.dto.AuthorDTO;
import niccolosciucco.u5_w2_d4.authors.entities.Author;
import niccolosciucco.u5_w2_d4.authors.services.AuthorService;
import niccolosciucco.u5_w2_d4.exceptions.BodyException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public Page<Author> getAll(@RequestParam(defaultValue = "0") int page) {
        return this.authorService.getAll(page);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Author post(@RequestBody @Validated AuthorDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            String errors = validation.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .collect(Collectors.joining(". "));

            throw new BodyException(errors);
        }

        return this.authorService.post(body);
    }

    @PutMapping("/{authorId}")
    public Author findByIdAndUpdate(@PathVariable UUID authorId, @RequestBody @Validated AuthorDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            String errors = validation.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .collect(Collectors.joining(". "));

            throw new BodyException(errors);
        }
        return this.authorService.findByIdAndUpdate(authorId, body);
    }

    @DeleteMapping("/{authorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID authorId) {
        this.authorService.findByIdAndDelete(authorId);
    }
}
