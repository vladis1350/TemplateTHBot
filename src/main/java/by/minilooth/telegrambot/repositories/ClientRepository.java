package by.minilooth.telegrambot.repositories;

import by.minilooth.telegrambot.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
    
}
