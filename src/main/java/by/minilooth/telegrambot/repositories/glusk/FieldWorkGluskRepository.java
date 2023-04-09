package by.minilooth.telegrambot.repositories.glusk;

import by.minilooth.telegrambot.model.glusk.FieldWorkGlusk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FieldWorkGluskRepository extends JpaRepository<FieldWorkGlusk, Long> {
    FieldWorkGlusk findFieldWorkByDate(LocalDate date);
    List<FieldWorkGlusk> getAllByDateAfterAndDateBefore(LocalDate after, LocalDate before);
}
