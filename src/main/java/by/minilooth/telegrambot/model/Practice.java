package by.minilooth.telegrambot.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Builder
@Table(name = "practice")
@AllArgsConstructor
@NoArgsConstructor
public class Practice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "topicId", nullable = false)
    private Topic topic;

    @Column(name = "question")
    private String question;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "practice", fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE, CascadeType.ALL })
    private Set<PracticeAnswer> practiceAnswers;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "currentPractice", cascade = CascadeType.PERSIST)
    private Set<Client> currentClients;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "currentPractice", cascade = CascadeType.PERSIST)
    private Set<Admin> currentAdmins;
}
