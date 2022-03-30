package by.minilooth.telegrambot.model;

import by.minilooth.telegrambot.bot.state.admin.AdminBotState;
import lombok.*;

import javax.persistence.*;

@Table(name = "admin")
@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "admin_bot_state", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private AdminBotState adminBotState;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToOne(mappedBy = "admin", cascade = CascadeType.ALL)
    private User user;

    public Long getTelegramId() {
        if (this.user == null) {
            return null;
        }
        return this.user.getTelegramId();
    }
}
