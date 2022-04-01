package by.minilooth.telegrambot.service;

import by.minilooth.telegrambot.model.Theory;
import by.minilooth.telegrambot.model.Topic;
import by.minilooth.telegrambot.repositories.TheoryRepository;
import by.minilooth.telegrambot.util.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Document;

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

    public Theory createTheory(Topic topic, String theoryText) {
        Theory theory = Theory.builder()
                .topic(topic)
                .theoryText(theoryText)
                .mediaType(MediaType.TEXT)
                .build();

        topic.setTheory(theory);

        return theory;
    }

    public Theory createTheory(Topic topic, Document document, String caption) {
        Theory theory = Theory.builder()
                .topic(topic)
                .data(document.getFileId())
                .caption(caption)
                .filename(document.getFileName())
                .mediaType(MediaType.DOCUMENT)
                .build();

        topic.setTheory(theory);

        return theory;
    }
}
