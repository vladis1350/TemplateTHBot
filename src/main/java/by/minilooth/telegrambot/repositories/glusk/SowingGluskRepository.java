package by.minilooth.telegrambot.repositories.glusk;

import by.minilooth.telegrambot.model.glusk.MilkGlusk;
import by.minilooth.telegrambot.model.glusk.SowingGlusk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface SowingGluskRepository extends JpaRepository<SowingGlusk, Long> {
    SowingGlusk findSowingGluskByDate(LocalDate date);
}
