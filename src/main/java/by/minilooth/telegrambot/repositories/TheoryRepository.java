package by.minilooth.telegrambot.repositories;

import by.minilooth.telegrambot.model.Theory;
import by.minilooth.telegrambot.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheoryRepository extends JpaRepository<Theory, Long> {
    Theory getTheoriesByTopic(Topic topic);
    void deleteTheoryByTopic(Topic topic);
}
