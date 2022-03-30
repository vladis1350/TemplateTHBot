package by.minilooth.telegrambot.bot.message.client;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import by.minilooth.telegrambot.bot.api.enums.UpdateType;
import by.minilooth.telegrambot.bot.context.client.ClientBotContext;
import by.minilooth.telegrambot.bot.keyboard.client.ClientReplyKeyboardMarkupSource;
import by.minilooth.telegrambot.bot.message.MessageService;
import by.minilooth.telegrambot.bot.api.MessageSender;
import by.minilooth.telegrambot.exception.ClientNotFoundException;
import by.minilooth.telegrambot.model.Client;
import by.minilooth.telegrambot.util.BotUtils;

@Service
public class ClientMessageService extends MessageService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ClientMessageService.class);

    @Autowired
    private BotUtils botUtils;
    @Autowired
    private MessageSender messageSender;
    @Autowired
    private ClientMessageSource clientMessageSource;
    @Autowired
    private ClientReplyKeyboardMarkupSource clientReplyKeyboardMarkupSource;

    public void sendStartMessage(ClientBotContext clientBotContext) {
        Client client = clientBotContext.getClient();

        if (botUtils.getUpdateType(clientBotContext.getUpdate()).equals(UpdateType.CALLBACK_QUERY) &&
                !clientBotContext.getUpdate().getMessage().getReplyMarkup().equals(new InlineKeyboardMarkup())) {

        } else {
            try {
                Message message = messageSender.sendMessage(client.getTelegramId().toString(),
                        String.format(clientMessageSource.getMessage("startMessage"), client.getUser().getFirstname()), null);

                updateLastBotMessageId(client.getUser(), message);
            } catch (TelegramApiException ex) {
                LOGGER.error("Unable to send start message to user: {}, reason: {}", client.getTelegramId(), ex.getLocalizedMessage());
            }
        }
    }

    public void sendFirstNameMessage(ClientBotContext clientBotContext) {
        Client client = clientBotContext.getClient();

        if (botUtils.getUpdateType(clientBotContext.getUpdate()).equals(UpdateType.CALLBACK_QUERY) &&
                !clientBotContext.getUpdate().getMessage().getReplyMarkup().equals(new InlineKeyboardMarkup())) {

        } else {
            try {
                Message message = messageSender.sendMessage(client.getTelegramId().toString(), clientMessageSource.getMessage("enterFirstName"), null);

                updateLastBotMessageId(client.getUser(), message);
            } catch (TelegramApiException ex) {
                LOGGER.error("Unable to send start message to user: {}, reason: {}", client.getTelegramId(), ex.getLocalizedMessage());
            }
        }
    }

    public void sendLastNameMessage(ClientBotContext clientBotContext) {
        Client client = clientBotContext.getClient();

        if (botUtils.getUpdateType(clientBotContext.getUpdate()).equals(UpdateType.CALLBACK_QUERY) &&
                !clientBotContext.getUpdate().getMessage().getReplyMarkup().equals(new InlineKeyboardMarkup())) {

        } else {
            try {
                Message message = messageSender.sendMessage(client.getTelegramId().toString(), clientMessageSource.getMessage("enterLastName"), null);

                updateLastBotMessageId(client.getUser(), message);
            } catch (TelegramApiException ex) {
                LOGGER.error("Unable to send start message to user: {}, reason: {}", client.getTelegramId(), ex.getLocalizedMessage());
            }
        }
    }

    @SneakyThrows
    public void sendMainMenu(ClientBotContext clientBotContext) {
        Client client = clientBotContext.getClient();

        if (botUtils.getUpdateType(clientBotContext.getUpdate()).equals(UpdateType.CALLBACK_QUERY) &&
                !clientBotContext.getUpdate().getMessage().getReplyMarkup().equals(new InlineKeyboardMarkup())) {

        } else {
            try {
                Message message = messageSender.sendMessage(client.getTelegramId().toString(),
                        clientMessageSource.getMessage("MainMenu"), clientReplyKeyboardMarkupSource.getTopicListKeyboard());

                updateLastBotMessageId(client.getUser(), message);
            } catch (TelegramApiException ex) {
                LOGGER.error("Unable to send start message to user: {}, reason: {}", client.getTelegramId(), ex.getLocalizedMessage());
            }
        }
    }
}
