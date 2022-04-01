package by.minilooth.telegrambot.service;

import by.minilooth.telegrambot.model.Client;
import by.minilooth.telegrambot.model.Practice;
import by.minilooth.telegrambot.model.PracticeAnswer;
import by.minilooth.telegrambot.repositories.PracticeAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
public class PracticeAnswerService {

    @Autowired
    private PracticeAnswerRepository practiceAnswerRepository;
    @Autowired
    private ClientService clientService;

    @Transactional
    public void save(PracticeAnswer practiceAnswer) {
        practiceAnswerRepository.save(practiceAnswer);
    }

    @Transactional
    public void delete(PracticeAnswer practiceAnswer) {
        practiceAnswerRepository.delete(practiceAnswer);
    }

    @Transactional
    public List<PracticeAnswer> getAll() {
        return practiceAnswerRepository.findAll();
    }


    public PracticeAnswer createPracticeAnswer(Practice practice, String answer) {
        PracticeAnswer practiceAnswer = PracticeAnswer.builder()
                .practice(practice)
                .answer(answer)
                .build();

        save(practiceAnswer);

        return practiceAnswer;
    }

    @Transactional
    public List<PracticeAnswer> getAllByPractice(Practice practice) {
        return practiceAnswerRepository.getAllByPractice(practice);
    }

    @Transactional
    public PracticeAnswer getAnswerById(Long answerId) {
        return practiceAnswerRepository.getById(answerId);
    }

    @Transactional
    public List<PracticeAnswer> getAllByClient(Client client) {
        return practiceAnswerRepository.findAllByClients(client);
    }

    @Transactional
    public void addAnswerToClient(Client client, PracticeAnswer answer) {
        List<PracticeAnswer> answerList = getAllByClient(client);

        answerList.add(answer);

        client.setAnswers(new HashSet<>(answerList));

        clientService.save(client);
    }

    @Transactional
    public void deleteClientAnswer(Client client) {
        List<PracticeAnswer> answerList = getAllByClient(client);

        if (!answerList.isEmpty()) {
            for (int i = 0; i < answerList.size(); i++) {
                if (answerList.get(i).getPractice().equals(client.getCurrentPractice())) {
                    answerList.remove(answerList.get(i));
                }
            }
            client.setAnswers(new HashSet<>(answerList));

            clientService.save(client);
        }
    }

    @Transactional
    public List<PracticeAnswer> getAllByClientAndPractice(Client client, Practice practice) {
        return practiceAnswerRepository.findAllByClientsAndPractice(client, practice);
    }

    @Transactional
    public void deleteAllByClientAndPractice(Client client, Practice practice) {
        practiceAnswerRepository.deleteAllByClientsAndPractice(client, practice);
    }
}
