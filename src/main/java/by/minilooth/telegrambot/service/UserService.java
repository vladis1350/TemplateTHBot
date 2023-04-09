package by.minilooth.telegrambot.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import by.minilooth.telegrambot.model.User;
import by.minilooth.telegrambot.model.enums.Role;
import by.minilooth.telegrambot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class UserService {
    public final static Integer DEFAULT_PAGE = 1;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Transactional
    public Optional<User> getByTelegramId(String telegramId) {
        return userRepository.findByTelegramId(telegramId);
    }

    @Transactional
    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User createUser(Update update, Role role) {
        User user = User.builder()
                .telegramId(update.getMessage().getFrom().getId().toString())
                .firstname(update.getMessage().getFrom().getFirstName())
                .lastname(update.getMessage().getFrom().getLastName())
                .username(update.getMessage().getFrom().getUserName())
                .botLastMessageDate(0)
                .lastBotMessageId(0)
                .botLastMessageEditable(false)
                .currentPage(DEFAULT_PAGE)
                .role(role)
                .build();

        this.save(user);

        return user;
    }

}
