package pl.dominik.football.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NoArgsConstructor
public class News {

    @Getter @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @NotNull(message = "{pl.news.validation.notNull.message}")
    @Getter @Setter
    private String title;

    @Getter @Setter
    @Lob
    private String content;

    @Getter @Setter
    @Lob
    private String mainImageSource;

    @Getter @Setter
    private String imageAlternateText;

    @Getter @Setter
    private LocalDateTime dateTime;

    @Getter @Setter
    private String author = "Dominik";

}