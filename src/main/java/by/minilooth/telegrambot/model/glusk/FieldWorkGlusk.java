package by.minilooth.telegrambot.model.glusk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@Table(name = "fieldWork")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class FieldWorkGlusk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "removalOrganic")
    private int removalOrganic;  // вывезено органики всего

    @Column(name = "removalOrganicPerDay")
    private int removalOrganicPerDay; // вывезено органики за день

    @Column(name = "introductionOrganic")
    private int introductionOrganic; // внесено органики всего

    @Column(name = "introductionOrganicPerDay")
    private int introductionOrganicPerDay; // вснесено ораганики за день

    @Column(name = "potassium")
    private int potassium; // вснесено калия всего

    @Column(name = "potassiumPerDay")
    private int potassiumPerDay; // вснесено калия за день

    @Column(name = "phosphorus")
    private int phosphorus; // вснесено фосфора за день

    @Column(name = "phosphorusPerDay")
    private int phosphorusPerDay; // вснесено фосфора за день

    @Column(name = "nitrogen")
    private int nitrogen; // вснесено азота за день

    @Column(name = "nitrogenPerDay")
    private int nitrogenPerDay; // вснесено азота за день

    @Column(name = "plowing")
    private int plowing; // пахота

    @Column(name = "plowingPerDay")
    private int plowingPerDay; // пахота за день

    @Column(name = "dressingNitrogen")
    private int dressingNitrogen; // подкормка озимых озот

    @Column(name = "dressingNitrogenPerDay")
    private int dressingNitrogenPerDay; // подкормка озимых азот за день

    @Column(name = "dressingHerbPotassium")
    private int dressingHerbPotassium; // подкормка многолетних калий

    @Column(name = "dressingHerbPotassiumPerDay")
    private int dressingHerbPotassiumPerDay; // подкормка многолетних калий за день

    @Column(name = "dressingHerbAmmonium")
    private int dressingHerbAmmonium; // подкормка многолетних сульфат аммоний

    @Column(name = "dressingHerbAmmoniumPerDay")
    private int dressingHerbAmmoniumPerDay; // подкормка многолетних сульфат аммоний за день
}
