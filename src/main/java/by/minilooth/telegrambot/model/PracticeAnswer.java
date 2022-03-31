package by.minilooth.telegrambot.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder
@Table(name = "practiceAnswer")
@AllArgsConstructor
@NoArgsConstructor
public class PracticeAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "answer")
    private String answer;

    @Column(name = "isCorrect", columnDefinition = "TINYINT(1)")
    private Boolean isCorrect;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "practiceId", nullable = false)
    private Practice practice;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "client_answer",
            joinColumns = @JoinColumn(name = "AnswerId"),
            inverseJoinColumns = @JoinColumn(name = "ClientId"))
    private Set<Client> clients = new HashSet<>();
}
