package by.minilooth.telegrambot.bot.message.client;

import by.minilooth.telegrambot.bot.keyboard.client.ClientInlineKeyboardSource;
import by.minilooth.telegrambot.model.*;
import by.minilooth.telegrambot.service.*;
import by.minilooth.telegrambot.util.enums.Emoji;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import by.minilooth.telegrambot.bot.api.enums.UpdateType;
import by.minilooth.telegrambot.bot.context.client.ClientBotContext;
import by.minilooth.telegrambot.bot.keyboard.client.ClientReplyKeyboardMarkupSource;
import by.minilooth.telegrambot.bot.message.MessageService;
import by.minilooth.telegrambot.bot.api.MessageSender;
import by.minilooth.telegrambot.exception.ClientNotFoundException;
import by.minilooth.telegrambot.util.BotUtils;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    @Autowired
    private ClientInlineKeyboardSource clientInlineKeyboardSource;
    @Autowired
    private TopicService topicService;
    @Autowired
    private PracticeService practiceService;
    @Autowired
    private TheoryService theoryService;
    @Autowired
    private PracticeAnswerService practiceAnswerService;
    @Autowired
    private ClientService clientService;

    @SneakyThrows
    private Boolean checkEditableMessage(ClientBotContext clientBotContext) {
        Client client = clientBotContext.getClient();
        return client.getUser().hasLastBotMessage() && client.getUser().getBotLastMessageEditable() &&
                !messageSender.isMessageExpired(client.getUser().getBotLastMessageDate());
    }

    public void sendStartMessage(ClientBotContext clientBotContext) {
        Client client = clientBotContext.getClient();

        if (checkEditableMessage(clientBotContext)) {

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

        if (checkEditableMessage(clientBotContext)) {

        } else {
            try {
                Message message = messageSender.sendMessage(client.getTelegramId().toString(), clientMessageSource.getMessage("enterFirstName"), null);

                updateLastBotMessageId(client.getUser(), message);
            } catch (TelegramApiException ex) {
                LOGGER.error("Unable to send sendFirstNameMessage to user: {}, reason: {}", client.getTelegramId(), ex.getLocalizedMessage());
            }
        }
    }

    public void sendLastNameMessage(ClientBotContext clientBotContext) {
        Client client = clientBotContext.getClient();

        if (checkEditableMessage(clientBotContext)) {

        } else {
            try {
                Message message = messageSender.sendMessage(client.getTelegramId().toString(), clientMessageSource.getMessage("enterLastName"), null);

                updateLastBotMessageId(client.getUser(), message);
            } catch (TelegramApiException ex) {
                LOGGER.error("Unable to send sendLastNameMessage to user: {}, reason: {}", client.getTelegramId(), ex.getLocalizedMessage());
            }
        }
    }

    @SneakyThrows
    public void sendMainMenu(ClientBotContext clientBotContext) {
        Client client = clientBotContext.getClient();

        if (checkEditableMessage(clientBotContext)) {

        } else {
            try {
                Message message = messageSender.sendMessage(client.getTelegramId().toString(),
                        clientMessageSource.getMessage("MainMenu"), clientReplyKeyboardMarkupSource.getTopicListKeyboard());

                updateLastBotMessageId(client.getUser(), message);
            } catch (TelegramApiException ex) {
                LOGGER.error("Unable to send sendMainMenu to user: {}, reason: {}", client.getTelegramId(), ex.getLocalizedMessage());
            }
        }
    }

    @SneakyThrows
    public void sendTopicList(ClientBotContext clientBotContext) {
        Client client = clientBotContext.getClient();
        List<Topic> topicList = topicService.getAll();

        if (checkEditableMessage(clientBotContext)) {
            messageSender.deleteBotLastMessage(client.getUser());
        }
        try {
            Message message = messageSender.sendMessage(client.getTelegramId().toString(),
                    clientMessageSource.getMessage("topicList"), clientInlineKeyboardSource.generateTopicsForSandboxPageableInlineMarkup(topicList, client));

            updateLastBotMessageId(client.getUser(), message);
        } catch (TelegramApiException ex) {
            LOGGER.error("Unable to send sendTopicList to user: {}, reason: {}", client.getTelegramId(), ex.getLocalizedMessage());
        }

    }

    public void sendFirstQuestionMessage(ClientBotContext clientBotContext) {
        Client client = clientBotContext.getClient();
        Practice practice = client.getCurrentPractice();

        if (checkEditableMessage(clientBotContext)) {
            messageSender.deleteBotLastMessage(client.getUser());
        }
        try {
            Message message = null;
            StringBuilder answerText = new StringBuilder();
            if (practice != null) {
                practiceAnswerService.deleteClientAnswer(client);
                answerText.append(practice.getQuestion());
                answerText.append(": \n");
                List<PracticeAnswer> practiceAnswers = practiceAnswerService.getAllByPractice(practice);
                if (!practiceAnswers.isEmpty()) {
                    for (int i = 0; i < practiceAnswers.size(); i++) {
                        answerText.append(i + 1).append(". ");
                        answerText.append(practiceAnswers.get(i).getAnswer());
                        answerText.append(". \n");
                    }
                    message = messageSender.sendMessage(client.getTelegramId(),
                            answerText.toString(), clientInlineKeyboardSource.generateAnswersForSandboxPageableInlineMarkup(practiceAnswers));
                }
            } else {
                message = messageSender.sendMessage(client.getTelegramId(),
                        clientMessageSource.getMessage("practiceNotFound"), clientReplyKeyboardMarkupSource.getMainMenuKeyboard());
            }
            updateLastBotMessageId(client.getUser(), message);
        } catch (TelegramApiException ex) {
            LOGGER.error("Unable to send sendFirstQuestionMessage to user: {}, reason: {}", client.getTelegramId(), ex.getLocalizedMessage());
        }
    }
    @SneakyThrows
    public void sendTheoryMessage(ClientBotContext clientBotContext) {
        Client client = clientBotContext.getClient();
        Topic topic = client.getCurrentTopic();
        Theory theory = topic.getTheory();
        List<Practice> practices = practiceService.getAllByTopic(topic);
        if (!practices.isEmpty()) {
            client.setCurrentPractice(practices.get(0));
            clientService.save(client);
        }

        if (checkEditableMessage(clientBotContext)) {
            messageSender.deleteBotLastMessage(client.getUser());
        }
        try {
            Message message = null;
            if (theory != null) {
                switch (theory.getMediaType()) {
                    case TEXT:
                        message = messageSender.sendMessage(client.getTelegramId(),
                                theory.getTheoryText(), clientReplyKeyboardMarkupSource.getMainMenuKeyboard());
                        break;
                    case DOCUMENT:
                        message = messageSender.sendDocument(client.getTelegramId(), theory.getCaption(),
                                new InputFile(botUtils.downloadTelegramFile(theory.getData()), theory.getFilename()), clientReplyKeyboardMarkupSource.getMainMenuKeyboard());
                }
            } else {
                message = messageSender.sendMessage(client.getTelegramId(),
                        clientMessageSource.getMessage("theoryNotFound"), clientReplyKeyboardMarkupSource.getMainMenuKeyboard());
            }
            updateLastBotMessageId(client.getUser(), message);
        } catch (TelegramApiException ex) {
            LOGGER.error("Unable to send sendTheoryMessage to user: {}, reason: {}", client.getTelegramId(), ex.getLocalizedMessage());
        }
    }

//    @SneakyThrows
//    public void sendAskQuestion(ClientBotContext clientBotContext) {
//        Client client = clientBotContext.getClient();
//
//        if (client == null) throw new ClientNotFoundException();
//
//        Practice practice = client.getCurrentPractice();
//        if (checkEditableMessage(clientBotContext)) {
//            messageSender.deleteBotLastMessage(client.getUser());
//        }
//        List<PracticeAnswer> answerList = practiceAnswerService.getAllByPractice(practice);
//        String messageText = "";
//        try {
//            Message message = null;
//            if (practice != null) {
//                StringBuilder answerText = new StringBuilder();
//                answerText.append(practice.getQuestion());
//                answerText.append(": \n");
//                List<PracticeAnswer> practiceAnswers = practiceAnswerService.getAllByPractice(practice);
//                if (!practiceAnswers.isEmpty()) {
//                    for (int i = 0; i < practiceAnswers.size(); i++) {
//                        answerText.append(i+1).append(". ");
//                        answerText.append(practiceAnswers.get(i).getAnswer());
//                        answerText.append(". \n");
//                    }
//                    message = messageSender.sendMessage(client.getTelegramId(),
//                            answerText.toString(), clientInlineKeyboardSource.generateAnswersForSandboxPageableInlineMarkup(practiceAnswers));
//                }
//                updateLastBotMessageId(client.getUser(), message);
//            } else {
//                messageText = "Вопрос не найден, возможно был удален администратором.";
//                messageSender.sendMessage(client.getTelegramId(),
//                        messageText,
//                        clientReplyKeyboardMarkupSource.getMainMenuKeyboard());
//            }
//        } catch (TelegramApiException ex) {
//            LOGGER.error("Unable to send sendAskQuestion to user: " + client.getTelegramId() + ", reason: " + ex.getLocalizedMessage());
//        }
//
//    }

    public void sendResultTest(ClientBotContext clientBotContext) {
        Client client = clientBotContext.getClient();
        Topic topic = client.getCurrentTopic();
        List<Practice> practices = practiceService.getAllByTopic(topic);
        StringBuilder resultTest = new StringBuilder();
        if (!practices.isEmpty()) {
            for (Practice practice : practices) {
                List<PracticeAnswer> answers = practiceAnswerService.getAllByClientAndPractice(client, practice);
                if (!answers.isEmpty()) {
                    resultTest.append("Вопрос: \n").append(practice.getQuestion()).append("\n");
                    for (PracticeAnswer practiceAnswer : answers) {
                        if (practiceAnswer.getIsCorrect()) {
                            resultTest.append("Ответ: \n").append(practiceAnswer.getAnswer())
                                    .append(" ").append("✅").append("\n");
                        } else {
                            resultTest.append("Ответ: ").append(practiceAnswer.getAnswer())
                                    .append(" ").append("❌").append("\n");
                        }
                    }
                    resultTest.append("\n");
                }
            }
        }

        if (checkEditableMessage(clientBotContext)) {
            messageSender.deleteBotLastMessage(client.getUser());
        }
        try {
            Message message = messageSender.sendMessage(client.getTelegramId().toString(),
                    resultTest.toString(), clientReplyKeyboardMarkupSource.getTopicListKeyboard());

            updateLastBotMessageId(client.getUser(), message);
        } catch (TelegramApiException ex) {
            LOGGER.error("Unable to send sendResultTest to user: {}, reason: {}", client.getTelegramId(), ex.getLocalizedMessage());
        }
    }
}
