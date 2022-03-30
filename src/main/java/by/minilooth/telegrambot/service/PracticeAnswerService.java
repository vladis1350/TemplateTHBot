package by.minilooth.telegrambot.service;

import by.minilooth.telegrambot.model.Practice;
import by.minilooth.telegrambot.model.PracticeAnswer;
import by.minilooth.telegrambot.repositories.PracticeAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PracticeAnswerService {

    @Autowired
    private PracticeAnswerRepository practiceAnswerRepository;

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

    public PracticeAnswer createPracticeAnswer(Practice practice) {
        PracticeAnswer practiceAnswer = PracticeAnswer.builder()
                .practice(practice)
                .build();

        save(practiceAnswer);

        return practiceAnswer;
    }
}
