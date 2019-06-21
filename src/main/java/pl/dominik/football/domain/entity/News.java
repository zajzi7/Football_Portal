package pl.dominik.football.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class News {

    @Getter @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Getter @Setter
    private String title;

    @Getter @Setter
    @Lob
    private String content;

    @Getter @Setter
    private String mainImageSource;

    @Getter @Setter
    private LocalDateTime dateTime;

    @Getter @Setter
    private String author = "Dominik";

}