package by.minilooth.telegrambot.repositories;

import by.minilooth.telegrambot.model.Client;
import by.minilooth.telegrambot.model.Practice;
import by.minilooth.telegrambot.model.PracticeAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PracticeAnswerRepository extends JpaRepository<PracticeAnswer, Long> {
    public List<PracticeAnswer> getAllByPractice(Practice practice);
    PracticeAnswer getById(Long answerId);

    List<PracticeAnswer> findAllByClients(Client client);
    List<PracticeAnswer> findAllByClientsAndPractice(Client client, Practice practice);

}
