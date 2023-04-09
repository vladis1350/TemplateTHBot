package by.minilooth.telegrambot.repositories.glusk;

import by.minilooth.telegrambot.model.glusk.WeeklyMilkGlusk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface WeeklyMilkGluskRepository extends JpaRepository<WeeklyMilkGlusk, Long> {
    WeeklyMilkGlusk getWeeklyMilkGluskByDate(LocalDate date);

}
