package by.minilooth.telegrambot.repositories;

import by.minilooth.telegrambot.model.Practice;
import by.minilooth.telegrambot.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PracticeRepository extends JpaRepository<Practice, Long> {
    public List<Practice> getAllByTopic(Topic topic);
    void deletePracticesByTopic(Topic topic);
}
