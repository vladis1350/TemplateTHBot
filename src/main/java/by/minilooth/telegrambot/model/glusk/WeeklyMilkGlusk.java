package by.minilooth.telegrambot.model.glusk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@Table(name = "milk_weekly")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class WeeklyMilkGlusk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "date")
    private LocalDate date;
    @Column(name = "weeklyProduction")
    private int weeklyProduction;
    @Column(name = "lastWeeklyDifferenceProd")
    private int lastWeeklyDifferenceProd;
    @Column(name = "weeklyImplement")
    private int weeklyImplement;
    @Column(name = "lastWeeklyDifferenceImpl")
    private int lastWeeklyDifferenceImpl;
    @Column(name = "weeklyMarketability")
    private String weeklyMarketability;
    @Column(name = "weeklyMilkOnHead")
    private String weeklyMilkOnHead;
    @Column(name = "lastWeeklyMilkOnHead")
    private String lastWeeklyMilkOnHead;
    @Column(name = "isLastWeek")
    private Boolean isLastWeek;


}
