package by.minilooth.telegrambot.bot;

import by.minilooth.telegrambot.bot.handler.admin.AdminUpdateHandler;
import by.minilooth.telegrambot.bot.handler.client.ClientUpdateHandler;
import by.minilooth.telegrambot.model.User;
import by.minilooth.telegrambot.model.enums.Role;
import by.minilooth.telegrambot.service.UserService;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Configuration
public class TelegramBot extends TelegramLongPollingBot {
    private static final Logger LOGGER = LogManager.getLogger(TelegramBot.class);

    @Getter
    @Value("${telegram.botUsername}")
    private String botUsername;

    @Getter
    @Value("${telegram.botToken}")
    private String botToken;

    public static final String ADMIN_DEEP_LINK = "start_admin";

    @Autowired
    private UserService userService;
    @Autowired
    private ClientUpdateHandler clientUpdateHandler;
    @Autowired
    private AdminUpdateHandler adminUpdateHandler;

    @Override
    public void onUpdateReceived(Update update) {
        final String chatId = getTelegramId(update);

        User user = userService.getByTelegramId(chatId);

        if (user != null) {
            checkRoleAndHandlerCall(user, update);
        } else {
            register(chatId, update);
        }
    }

    private String getTelegramId(Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getChatId().toString();
        } else if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getFrom().getId().toString();
        }
        return null;
    }

    private void checkRoleAndHandlerCall(User user, Update update) {
        try {
            switch (user.getRole()) {
                case CLIENT:
                    clientUpdateHandler.handle(update);
                    break;
                case ADMIN:
                    adminUpdateHandler.handle(update);
                    break;
                default:
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void register(String chatId, Update update) {
        User user = userService.createUser(update);

        if (update.getMessage().getText().endsWith(ADMIN_DEEP_LINK)) {
            user.setRole(Role.ADMIN);
            LOGGER.info("New admin registered: " + chatId);
        } else {
            LOGGER.info("New user registered: " + chatId);
            user.setRole(Role.CLIENT);
        }

        userService.save(user);
        checkRoleAndHandlerCall(user, update);
    }
}
