package pl.dominik.football.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
public class Menu {

    @Getter @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Getter @Setter
    private String title;

    @Getter @Setter
    private int parentId = 0;

    public Menu(String title) {
        this.title = title;
    }

    public Menu(String title, int parentId) {
        this.title = title;
        this.parentId = parentId;
    }

}
