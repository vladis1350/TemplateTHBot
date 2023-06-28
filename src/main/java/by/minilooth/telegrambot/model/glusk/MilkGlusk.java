package by.minilooth.telegrambot.model.glusk;

import by.minilooth.telegrambot.model.enums.Districts;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@Table(name = "milk")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MilkGlusk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "date")
    private LocalDate date;
    @Column(name = "production")
    private int production;
    @Column(name = "differenceProd")
    private int differenceProd;
    @Column(name = "impl")
    private int implement;
    @Column(name = "differenceImpl")
    private int differenceImpl;
    @Column(name = "marketability")
    private String marketability;
    @Column(name = "milkOnHead")
    private String milkOnHead;
    @Column(name = "milkOnHeadBeginningMonth")
    private String milkOnHeadBeginningMonth;
    @Column(name = "productionBeginningMonth")
    private String productionBeginningMonth;
    @Column(name = "district")
    private Districts district;

}
