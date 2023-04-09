package by.minilooth.telegrambot;

import by.minilooth.telegrambot.service.bot.InitializationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.IOException;
import java.security.GeneralSecurityException;

@SpringBootApplication
@EnableAsync
public class TelegramBotApplication {

	public static void main(String[] args) throws GeneralSecurityException, IOException {
//		SpringApplication.run(TelegramBotApplication.class, args);
		ApplicationContext applicationContext = SpringApplication.run(TelegramBotApplication.class, args);
		InitializationService initializationService = applicationContext.getBean(InitializationService.class);
		initializationService.initialize();
	}

}
