package by.minilooth.telegrambot.service;

import by.minilooth.telegrambot.model.Topic;
import by.minilooth.telegrambot.repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Transactional
    public void save(Topic topic) {
        topicRepository.save(topic);
    }

    @Transactional
    public void delete(Topic topic) {
        topicRepository.delete(topic);
    }

    @Transactional
    public List<Topic> getAll() {
        return topicRepository.findAll();
    }

    public Topic createTopic(String name) {
        Topic topic = Topic.builder()
                .name(name)
                .build();

        save(topic);

        return topic;
    }

    public Topic getTopicById(Long topicId) {
        return topicRepository.getById(topicId);
    }
}
