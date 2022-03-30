package by.minilooth.telegrambot.bot.message.admin;

import by.minilooth.telegrambot.bot.api.MessageSender;
import by.minilooth.telegrambot.bot.api.enums.UpdateType;
import by.minilooth.telegrambot.bot.context.admin.AdminBotContext;
import by.minilooth.telegrambot.bot.context.client.ClientBotContext;
import by.minilooth.telegrambot.bot.keyboard.admin.AdminReplyKeyboardMarkupSource;
import by.minilooth.telegrambot.bot.keyboard.client.ClientReplyKeyboardMarkupSource;
import by.minilooth.telegrambot.bot.message.MessageService;
import by.minilooth.telegrambot.model.Admin;
import by.minilooth.telegrambot.model.Client;
import by.minilooth.telegrambot.util.BotUtils;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class AdminMessageService extends MessageService {

    private final static Logger LOGGER = LoggerFactory.getLogger(AdminMessageService.class);

    @Autowired
    private BotUtils botUtils;
    @Autowired
    private MessageSender messageSender;
    @Autowired
    private AdminMessageSource adminMessageSource;
    @Autowired
    private AdminReplyKeyboardMarkupSource adminReplyKeyboardMarkupSource;

    public void sendStartMessage(AdminBotContext adminBotContext) {
        Admin admin = adminBotContext.getAdmin();

        if (botUtils.getUpdateType(adminBotContext.getUpdate()).equals(UpdateType.CALLBACK_QUERY) &&
                !adminBotContext.getUpdate().getMessage().getReplyMarkup().equals(new InlineKeyboardMarkup())) {

        } else {
            try {
                Message message = messageSender.sendMessage(admin.getTelegramId().toString(),
                        adminMessageSource.getMessage("startMessage"), adminReplyKeyboardMarkupSource.getMainMenuKeyboard());

                updateLastBotMessageId(admin.getUser(), message);
            } catch (TelegramApiException ex) {
                LOGGER.error("Unable to send start message to user: {}, reason: {}", admin.getTelegramId(), ex.getLocalizedMessage());
            }
        }
    }

    public void sendEnterTopicNameMessage(AdminBotContext adminBotContext) {
        Admin admin = adminBotContext.getAdmin();

        if (botUtils.getUpdateType(adminBotContext.getUpdate()).equals(UpdateType.CALLBACK_QUERY) &&
                !adminBotContext.getUpdate().getMessage().getReplyMarkup().equals(new InlineKeyboardMarkup())) {

        } else {
            try {
                Message message = messageSender.sendMessage(admin.getTelegramId().toString(), adminMessageSource.getMessage("enterTopicName"), null);

                updateLastBotMessageId(admin.getUser(), message);
            } catch (TelegramApiException ex) {
                LOGGER.error("Unable to send start message to user: {}, reason: {}", admin.getTelegramId(), ex.getLocalizedMessage());
            }
        }
    }

    public void sendEnterTheoryMessage(AdminBotContext adminBotContext) {
        Admin admin = adminBotContext.getAdmin();

        if (botUtils.getUpdateType(adminBotContext.getUpdate()).equals(UpdateType.CALLBACK_QUERY) &&
                !adminBotContext.getUpdate().getMessage().getReplyMarkup().equals(new InlineKeyboardMarkup())) {

        } else {
            try {
                Message message = messageSender.sendMessage(admin.getTelegramId().toString(),
                        adminMessageSource.getMessage("enterTheory"), null);

                updateLastBotMessageId(admin.getUser(), message);
            } catch (TelegramApiException ex) {
                LOGGER.error("Unable to send start message to user: {}, reason: {}", admin.getTelegramId(), ex.getLocalizedMessage());
            }
        }
    }

    public void sendMainMenuMessage(AdminBotContext adminBotContext) {
        Admin admin = adminBotContext.getAdmin();

        if (botUtils.getUpdateType(adminBotContext.getUpdate()).equals(UpdateType.CALLBACK_QUERY) &&
                !adminBotContext.getUpdate().getMessage().getReplyMarkup().equals(new InlineKeyboardMarkup())) {

        } else {
            try {
                Message message = messageSender.sendMessage(admin.getTelegramId().toString(),
                        adminMessageSource.getMessage("MainMenu"), adminReplyKeyboardMarkupSource.getMainMenuKeyboard());

                updateLastBotMessageId(admin.getUser(), message);
            } catch (TelegramApiException ex) {
                LOGGER.error("Unable to send start message to user: {}, reason: {}", admin.getTelegramId(), ex.getLocalizedMessage());
            }
        }
    }
}
