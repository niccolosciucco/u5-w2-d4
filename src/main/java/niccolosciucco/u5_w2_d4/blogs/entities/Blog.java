package niccolosciucco.u5_w2_d4.blogs.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import niccolosciucco.u5_w2_d4.authors.entities.Author;

import java.util.UUID;

@Entity
@Table(name = "blogs")
@NoArgsConstructor
@Getter
@Setter
public class Blog {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String cover;
    @Column(nullable = false)
    private String plot;
    @Column(name = "reading_time", nullable = false)
    private int readingTime;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    public Blog(String category, String title, String plot, int readingTime, Author author) {
        this.category = category;
        this.title = title;
        this.cover = "https://fastly.picsum.photos/id/93/200/300.jpg?hmac=_9xpriBgFwY9RX_KtR53oeWtaQaNroWVr-4a9aJ0g_4";
        this.plot = plot;
        this.readingTime = readingTime;
        this.author = author;
    }
}