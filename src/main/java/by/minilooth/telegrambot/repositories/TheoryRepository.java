package by.minilooth.telegrambot.repositories;

import by.minilooth.telegrambot.model.Theory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheoryRepository extends JpaRepository<Theory, Long> {
}