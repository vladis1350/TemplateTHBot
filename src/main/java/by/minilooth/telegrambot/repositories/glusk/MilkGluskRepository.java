package by.minilooth.telegrambot.repositories.glusk;

import by.minilooth.telegrambot.model.enums.Districts;
import by.minilooth.telegrambot.model.glusk.MilkGlusk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MilkGluskRepository extends JpaRepository<MilkGlusk, Long> {
    MilkGlusk findMilkByDateAndDistrict(LocalDate date, Districts districts);
    List<MilkGlusk> getAllByDateAfterAndDateBeforeAndDistrict(LocalDate after, LocalDate before, Districts districts);
}
