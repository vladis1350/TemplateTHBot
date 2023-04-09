package by.minilooth.telegrambot.model.glusk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@Table(name = "sowingGlusk")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class SowingGlusk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "barley")
    private int barley; // ячмень
    @Column(name = "barleyPerDay")
    private int barleyPerDay; // ячмень за день
    @Column(name = "wheat")
    private int wheat; // пшеница
    @Column(name = "wheatPerDay")
    private int wheatPerDay; // пшеница за день
    @Column(name = "triticale")
    private int triticale; // тритикале
    @Column(name = "triticalePerDay")
    private int triticalePerDay; // тритикале за день
    @Column(name = "oat")
    private int oat; // овёс
    @Column(name = "oatPerDay")
    private int oatPerDay; // овёс за день
    @Column(name = "peas")
    private int peas;// горох
    @Column(name = "peasPerDay")
    private int peasPerDay;// горох за день
    @Column(name = "annualHerbs")
    private int annualHerbs; // однолетние травы
    @Column(name = "annualHerbsPerDay")
    private int annualHerbsPerDay; // однолетние травы за день
}
