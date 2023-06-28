package by.minilooth.telegrambot.model.glusk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Table(name = "forageHarvesting")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ForageHarvesting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "totalBeleved")
    private Long totalBeleved;

    @Column(name = "belevedPerDay")
    private Long belevedPerDay;

    @Column(name = "totalPreparedHay")
    private Long totalPreparedHay;

    @Column(name = "preparedPerDayHay")
    private Long preparedPerDayHay;

    @Column(name = "totalPreparedHaylage")
    private Long totalPreparedHaylage;

    @Column(name = "preparedPerDayHaylage")
    private Long preparedPerDayHaylage;

    @Column(name = "totalPreparedSilage")
    private Long totalPreparedSilage;

    @Column(name = "preparedPerDaySilage")
    private Long preparedPerDaySilage;
}
