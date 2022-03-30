package by.minilooth.telegrambot.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Builder
@Table(name = "theory")
@AllArgsConstructor
@NoArgsConstructor
public class Theory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "theoryText", columnDefinition = "LONGTEXT")
    private String theoryText;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinTable( name = "theory_topic",
            joinColumns = {@JoinColumn(name = "theoryId", referencedColumnName = "Id")},
            inverseJoinColumns = {@JoinColumn(name = "topicId", referencedColumnName = "Id")})
    private Topic topic;

}
