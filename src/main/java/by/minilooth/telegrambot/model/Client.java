package by.minilooth.telegrambot.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import by.minilooth.telegrambot.bot.state.ClientBotState;
import by.minilooth.telegrambot.model.enums.Districts;
import by.minilooth.telegrambot.model.enums.TypeReport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private User user;

    @Column(name = "district", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Districts districts;

    @Column(name = "typeReport", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private TypeReport typeReport;

    public String getTelegramId() {
        if (this.user == null) {
            return null;
        }
        return this.user.getTelegramId();
    }

}
