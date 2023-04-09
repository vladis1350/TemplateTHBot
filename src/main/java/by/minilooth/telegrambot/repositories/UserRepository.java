package by.minilooth.telegrambot.repositories;

import java.util.Optional;

import by.minilooth.telegrambot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public Optional<User> findByTelegramId(String telegramId);
}
