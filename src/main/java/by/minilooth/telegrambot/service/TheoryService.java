package by.minilooth.telegrambot.service;

import by.minilooth.telegrambot.model.Theory;
import by.minilooth.telegrambot.model.Topic;
import by.minilooth.telegrambot.repositories.TheoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TheoryService {

    @Autowired
    private TheoryRepository topicRepository;

    @Transactional
    public void save(Theory theory) {
        topicRepository.save(theory);
    }

    @Transactional
    public void delete(Theory theory) {
        topicRepository.delete(theory);
    }

    @Transactional
    public List<Theory> getAll() {
        return topicRepository.findAll();
    }

    public Theory createTheory(Topic topic) {
        Theory theory = Theory.builder()
                .topic(topic)
                .build();

        save(theory);

        return theory;
    }
}
