package by.minilooth.telegrambot.repositories;

import by.minilooth.telegrambot.model.PracticeAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PracticeAnswerRepository extends JpaRepository<PracticeAnswer, Long> {
}
