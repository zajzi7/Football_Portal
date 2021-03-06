//package pl.dominik.football.domain.entity;
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.ToString;
//import org.hibernate.annotations.Cache;
//import org.hibernate.annotations.CacheConcurrencyStrategy;
//
//import javax.persistence.Cacheable;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//
//@Entity
//@Cacheable
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
//@NoArgsConstructor
//@ToString
//public class Player {
//
//    @Id
//    @Getter @Setter
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//
//    @Getter @Setter
//    private String firstName;
//
//    @Getter @Setter
//    private String lastName;
//
//    @Getter @Setter
//    private int age;
//
//    @Getter @Setter
//    private int goals;
//
//    @Getter @Setter
//    private int assists;
//
//    @Getter @Setter
//    private int yellowCards;
//
//    @Getter @Setter
//    private int redCards;
//
//    public Player(String firstName, String lastName) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//    }
//
//    public String getFullName() {
//        return firstName + " " + lastName;
//    }
//
//    //TODO some stuff like add players to teams
//}
