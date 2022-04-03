package by.minilooth.telegrambot.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Builder
@Table(name = "topic")
@AllArgsConstructor
@NoArgsConstructor
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToOne(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
    private Theory theory;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "topic", fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE, CascadeType.ALL })
    private Set<Practice> practice;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "currentTopic", cascade = CascadeType.PERSIST)
    private Set<Admin> currentAdmins;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "currentTopic", cascade = CascadeType.PERSIST)
    private Set<Client> currentClients;

    @PreRemove
    private void preRemove() {
        currentAdmins.forEach(m -> m.setCurrentTopic(null));
        currentClients.forEach(m -> m.setCurrentTopic(null));
        theory = null;
    }
}
