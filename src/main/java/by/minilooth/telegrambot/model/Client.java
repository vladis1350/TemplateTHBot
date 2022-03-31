package by.minilooth.telegrambot.model;

import javax.persistence.*;

import by.minilooth.telegrambot.bot.state.client.ClientBotState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@Table(name = "client")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "client_bot_state", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private ClientBotState clientBotState;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private User user;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "CurrentTopicId")
    private Topic currentTopic;

    @Column(name = "number_question")
    private Integer numberQuestion;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "CurrentPracticeId")
    private Practice currentPractice;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "client_answer",
            joinColumns = @JoinColumn(name = "ClientId"),
            inverseJoinColumns = @JoinColumn(name = "AnswerId"))
    private Set<PracticeAnswer> answers = new HashSet<>();

    public String getTelegramId() {
        if (this.user == null) {
            return null;
        }
        return this.user.getTelegramId();
    }

}
