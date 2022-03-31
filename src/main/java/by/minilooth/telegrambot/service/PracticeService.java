package by.minilooth.telegrambot.service;

import by.minilooth.telegrambot.model.Practice;
import by.minilooth.telegrambot.model.Topic;
import by.minilooth.telegrambot.repositories.PracticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PracticeService {

    @Autowired
    private PracticeRepository practiceRepository;

    @Transactional
    public void save(Practice practice) {
        practiceRepository.save(practice);
    }

    @Transactional
    public void delete(Practice practice) {
        practiceRepository.delete(practice);
    }

    @Transactional
    public List<Practice> getAll() {
        return practiceRepository.findAll();
    }

    @Transactional
    public List<Practice> getAllByTopic(Topic topic) {
        return practiceRepository.getAllByTopic(topic);
    }

    public Practice createPractice(Topic topic) {
        Practice practice = Practice.builder()
                .topic(topic)
                .build();

        save(practice);

        return practice;
    }
}
