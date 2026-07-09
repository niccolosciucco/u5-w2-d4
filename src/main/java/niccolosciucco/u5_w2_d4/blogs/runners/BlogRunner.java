package niccolosciucco.u5_w2_d4.blogs.runners;

import niccolosciucco.u5_w2_d4.blogs.dto.BlogDTO;
import niccolosciucco.u5_w2_d4.blogs.entities.Blog;
import niccolosciucco.u5_w2_d4.blogs.services.BlogService;
import niccolosciucco.u5_w2_d4.exceptions.BodyException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/blogs")
public class BlogRunner {
    private final BlogService blogService;

    public BlogRunner(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping
    public Page<Blog> getAll(@RequestParam(defaultValue = "0") int page) {
        return this.blogService.getAll(page);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Blog post(@RequestBody @Validated BlogDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            String errors = validation.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .collect(Collectors.joining(". "));

            throw new BodyException(errors);
        }

        return this.blogService.post(body);
    }

    @PutMapping("/{blogId}")
    public Blog findByIdAndUpdate(@PathVariable UUID blogId, @RequestBody @Validated BlogDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            String errors = validation.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .collect(Collectors.joining(". "));

            throw new BodyException(errors);
        }
        return this.blogService.findByIdAndUpdate(blogId, body);
    }

    @DeleteMapping("/{blogId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID blogId) {
        this.blogService.findByIdAndDelete(blogId);
    }


}
