package by.minilooth.telegrambot.bot.message.admin;

import by.minilooth.telegrambot.bot.api.MessageSender;
import by.minilooth.telegrambot.bot.api.enums.UpdateType;
import by.minilooth.telegrambot.bot.context.admin.AdminBotContext;
import by.minilooth.telegrambot.bot.context.client.ClientBotContext;
import by.minilooth.telegrambot.bot.keyboard.admin.AdminInlineKeyboardSource;
import by.minilooth.telegrambot.bot.keyboard.admin.AdminReplyKeyboardMarkupSource;
import by.minilooth.telegrambot.bot.keyboard.client.ClientReplyKeyboardMarkupSource;
import by.minilooth.telegrambot.bot.message.MessageService;
import by.minilooth.telegrambot.model.*;
import by.minilooth.telegrambot.service.*;
import by.minilooth.telegrambot.util.BotUtils;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

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
    @Autowired
    private PracticeAnswerService practiceAnswerService;
    @Autowired
    private AdminInlineKeyboardSource adminInlineKeyboardSource;
    @Autowired
    private TopicService topicService;
    @Autowired
    private TheoryService theoryService;
    @Autowired
    private PracticeService practiceService;
    @Autowired
    private ClientService clientService;

    @SneakyThrows
    private Boolean checkEditableMessage(AdminBotContext adminBotContext) {
        Admin admin = adminBotContext.getAdmin();
        return admin.getUser().hasLastBotMessage() && admin.getUser().getBotLastMessageEditable() &&
                !messageSender.isMessageExpired(admin.getUser().getBotLastMessageDate());
    }

    public void sendEnterTopicNameMessage(AdminBotContext adminBotContext) {
        Admin admin = adminBotContext.getAdmin();

        if (checkEditableMessage(adminBotContext)) {

        } else {
            try {
                Message message = messageSender.sendMessage(admin.getTelegramId(),
                        adminMessageSource.getMessage("enterTopicName"), new ReplyKeyboardRemove(true));

                updateLastBotMessageId(admin.getUser(), message);
            } catch (TelegramApiException ex) {
                LOGGER.error("Unable to send sendEnterTopicNameMessage to admin: {}, reason: {}", admin.getTelegramId(), ex.getLocalizedMessage());
            }
        }
    }

    public void sendEnterTheoryMessage(AdminBotContext adminBotContext) {
        Admin admin = adminBotContext.getAdmin();

        if (checkEditableMessage(adminBotContext)) {

        } else {
            try {
                Message message = messageSender.sendMessage(admin.getTelegramId().toString(),
                        adminMessageSource.getMessage("enterTheory"), adminReplyKeyboardMarkupSource.getLoadFileKeyboard());

                updateLastBotMessageId(admin.getUser(), message);
            } catch (TelegramApiException ex) {
                LOGGER.error("Unable to send sendEnterTheoryMessage to admin: {}, reason: {}", admin.getTelegramId(), ex.getLocalizedMessage());
            }
        }
    }

    public void sendTopicSuccessfullyAddedMessage(AdminBotContext adminBotContext, Topic topic) {
        Admin admin = adminBotContext.getAdmin();

        if (checkEditableMessage(adminBotContext)) {

        } else {
            try {
                Message message = messageSender.sendMessage(admin.getTelegramId().toString(),
                        String.format(adminMessageSource.getMessage("sendTopicSuccessfullyAddedMessage"), topic.getName()), null);

                updateLastBotMessageId(admin.getUser(), message);
            } catch (TelegramApiException ex) {
                LOGGER.error("Unable to send sendTopicSuccessfullyAddedMessage to admin: {}, reason: {}", admin.getTelegramId(), ex.getLocalizedMessage());
            }
        }
    }

    public void sendMainMenuMessage(AdminBotContext adminBotContext) {
        Admin admin = adminBotContext.getAdmin();

        if (checkEditableMessage(adminBotContext)) {
            messageSender.deleteBotLastMessage(admin.getUser());
        } else {
            try {
                Message message = messageSender.sendMessage(admin.getTelegramId().toString(),
                        adminMessageSource.getMessage("MainMenu"), adminReplyKeyboardMarkupSource.getMainMenuKeyboard());

                updateLastBotMessageId(admin.getUser(), message);
            } catch (TelegramApiException ex) {
                LOGGER.error("Unable to send sendMainMenuMessage to admin: {}, reason: {}", admin.getTelegramId(), ex.getLocalizedMessage());
            }
        }
    }

    public void sendTheorySuccessfullyAddedMessage(AdminBotContext adminBotContext) {
        Admin admin = adminBotContext.getAdmin();

        if (checkEditableMessage(adminBotContext)) {

        } else {
            try {
                Message message = messageSender.sendMessage(admin.getTelegramId().toString(),
                        String.format(adminMessageSource.getMessage("sendTheorySuccessfullyAddedMessage"), admin.getCurrentTopic().getName()),
                        adminReplyKeyboardMarkupSource.getAddPracticeKeyboard());

                updateLastBotMessageId(admin.getUser(), message);
            } catch (TelegramApiException ex) {
                LOGGER.error("Unable to send sendTheorySuccessfullyAddedMessage to admin: {}, reason: {}", admin.getTelegramId(), ex.getLocalizedMessage());
            }
        }
    }

    public void sendTheorySuccessfullyEditedMessage(AdminBotContext adminBotContext) {
        Admin admin = adminBotContext.getAdmin();

        if (checkEditableMessage(adminBotContext)) {

        } else {
            try {
                Message message = messageSender.sendMessage(admin.getTelegramId().toString(),
                        String.format(adminMessageSource.getMessage("sendTheorySuccessfullyEditedMessage"), admin.getCurrentTopic().getName()),
                        adminReplyKeyboardMarkupSource.getAddPracticeKeyboard());

                updateLastBotMessageId(admin.getUser(), message);
            } catch (TelegramApiException ex) {
                LOGGER.error("Unable to send sendTheorySuccessfullyAddedMessage to admin: {}, reason: {}", admin.getTelegramId(), ex.getLocalizedMessage());
            }
        }
    }

    public void sendEnterQuestionMessage(AdminBotContext adminBotContext) {
        Admin admin = adminBotContext.getAdmin();

        if (checkEditableMessage(adminBotContext)) {

        } else {
            try {
                Message message = messageSender.sendMessage(admin.getTelegramId().toString(),
                        adminMessageSource.getMessage("enterQuestion"), new ReplyKeyboardRemove(true));

                updateLastBotMessageId(admin.getUser(), message);
            } catch (TelegramApiException ex) {
                LOGGER.error("Unable to send sendEnterQuestionMessage to admin: {}, reason: {}", admin.getTelegramId(), ex.getLocalizedMessage());
            }
        }
    }

    public void sendEnterAnswerMessage(AdminBotContext adminBotContext) {
        Admin admin = adminBotContext.getAdmin();

        if (checkEditableMessage(adminBotContext)) {

        } else {
            try {
                List<PracticeAnswer> answers = practiceAnswerService.getAllByPractice(admin.getCurrentPractice());
                Message message = messageSender.sendMessage(admin.getTelegramId().toString(),
                        String.format(adminMessageSource.getMessage("enterAnswer"), answers.size() + 1), new ReplyKeyboardRemove(true));

                updateLastBotMessageId(admin.getUser(), message);
            } catch (TelegramApiException ex) {
                LOGGER.error("Unable to send sendEnterAnswerMessage to admin: {}, reason: {}", admin.getTelegramId(), ex.getLocalizedMessage());
            }
        }
    }

    public void sendAnswerSuccessfullyAddedMessage(AdminBotContext adminBotContext) {
        Admin admin = adminBotContext.getAdmin();

        if (checkEditableMessage(adminBotContext)) {
            messageSender.deleteBotLastMessage(admin.getUser());
        }
        try {
            Message message = messageSender.sendMessage(admin.getTelegramId(),
                    String.format(adminMessageSource.getMessage("sendAnswerSuccessfullyAddedMessage"),
                            admin.getCurrentPractice().getQuestion()), adminReplyKeyboardMarkupSource.getAddPracticeAnswerKeyboard());

            updateLastBotMessageId(admin.getUser(), message);
        } catch (TelegramApiException ex) {
            LOGGER.error("Unable to send sendAnswerSuccessfullyAddedMessage to admin: {}, reason: {}", admin.getTelegramId(), ex.getLocalizedMessage());
        }
    }

    public void sendAnswerIsCorrectMessage(AdminBotContext adminBotContext) {
        Admin admin = adminBotContext.getAdmin();

        if (checkEditableMessage(adminBotContext)) {

        } else {
            try {
                Message message = messageSender.sendMessage(admin.getTelegramId(),
                        String.format(adminMessageSource.getMessage("sendAnswerIsCorrectMessage"),
                                admin.getCurrentAnswer().getAnswer()), adminInlineKeyboardSource.getConfirmationInlineMarkup());

                updateLastBotMessageId(admin.getUser(), message);
            } catch (TelegramApiException ex) {
                LOGGER.error("Unable to send sendAnswerIsCorrectMessage to admin: {}, reason: {}", admin.getTelegramId(), ex.getLocalizedMessage());
            }
        }
    }

    public void sendSelectProcessMessage(AdminBotContext adminBotContext) {
        Admin admin = adminBotContext.getAdmin();

        if (checkEditableMessage(adminBotContext)) {

        } else {
            try {
                Message message = messageSender.sendMessage(admin.getTelegramId(),
                        adminMessageSource.getMessage("sendSelectProcessMessage"),
                        adminReplyKeyboardMarkupSource.getPracticeMenuKeyboard());

                updateLastBotMessageId(admin.getUser(), message);
            } catch (TelegramApiException ex) {
                LOGGER.error("Unable to send sendSelectProcessMessage to admin: {}, reason: {}", admin.getTelegramId(), ex.getLocalizedMessage());
            }
        }
    }

    public void sendTopicList(AdminBotContext adminBotContext) {
        Admin admin = adminBotContext.getAdmin();
        List<Topic> topicList = topicService.getAll();

        if (checkEditableMessage(adminBotContext)) {
            messageSender.deleteBotLastMessage(admin.getUser());
        }
        try {
            Message message = messageSender.sendMessage(admin.getTelegramId().toString(),
                    adminMessageSource.getMessage("topicList"),
                    adminInlineKeyboardSource.generateTopicsForSandboxPageableInlineMarkup(topicList, admin));

            updateLastBotMessageId(admin.getUser(), message);
        } catch (TelegramApiException ex) {
            LOGGER.error("Unable to send sendTopicList to admin: {}, reason: {}", admin.getTelegramId(), ex.getLocalizedMessage());
        }

    }

    public void sendTheoryMessage(AdminBotContext adminBotContext) {
        Admin admin = adminBotContext.getAdmin();
        Topic topic = admin.getCurrentTopic();
        Theory theory = topic.getTheory();

        if (checkEditableMessage(adminBotContext)) {
            messageSender.deleteBotLastMessage(admin.getUser());
        }
        try {
            Message message = null;
            if (theory != null) {
                switch (theory.getMediaType()) {
                    case TEXT:
                        message = messageSender.sendMessage(admin.getTelegramId(),
                                theory.getTheoryText(), adminReplyKeyboardMarkupSource.getPracticeKeyboard());
                        break;
                    case DOCUMENT:
                        message = messageSender.sendDocument(admin.getTelegramId(), theory.getCaption(),
                                new InputFile(botUtils.downloadTelegramFile(theory.getData()), theory.getFilename()),
                                adminReplyKeyboardMarkupSource.getPracticeKeyboard());
                }
            } else {
                message = messageSender.sendMessage(admin.getTelegramId(),
                        adminMessageSource.getMessage("theoryNotFound"), adminReplyKeyboardMarkupSource.getPracticeKeyboard());
            }
            updateLastBotMessageId(admin.getUser(), message);
        } catch (TelegramApiException ex) {
            LOGGER.error("Unable to send sendTheoryMessage to admin: {}, reason: {}", admin.getTelegramId(), ex.getLocalizedMessage());
        }
    }

    public void sendLoadTheoryFileMessage(AdminBotContext adminBotContext) {
        Admin admin = adminBotContext.getAdmin();

        if (checkEditableMessage(adminBotContext)) {
            messageSender.deleteBotLastMessage(admin.getUser());
        }
        try {
            Message message = messageSender.sendMessage(admin.getTelegramId(),
                    adminMessageSource.getMessage("loadTheoryFile"), adminReplyKeyboardMarkupSource.getCancelKeyboard());

            updateLastBotMessageId(admin.getUser(), message);
        } catch (TelegramApiException ex) {
            LOGGER.error("Unable to send sendLoadTheoryFileMessage to admin: {}, reason: {}", admin.getTelegramId(), ex.getLocalizedMessage());
        }
    }

    public void sendSelectTopicForShowResultMessage(AdminBotContext adminBotContext) {
        Admin admin = adminBotContext.getAdmin();
        List<Topic> topicList = topicService.getAll();

        if (checkEditableMessage(adminBotContext)) {
            messageSender.deleteBotLastMessage(admin.getUser());
        }
        try {
            Message message = messageSender.sendMessage(admin.getTelegramId().toString(),
                    adminMessageSource.getMessage("selectTopicForShowingResult"),
                    adminInlineKeyboardSource.generateTopicsForSandboxPageableInlineMarkup(topicList, admin));

            updateLastBotMessageId(admin.getUser(), message);
        } catch (TelegramApiException ex) {
            LOGGER.error("Unable to send sendTopicList to admin: {}, reason: {}", admin.getTelegramId(), ex.getLocalizedMessage());
        }
    }

    public void sendResultPracticeMessage(AdminBotContext adminBotContext) {
        Admin admin = adminBotContext.getAdmin();
        Topic topic = admin.getCurrentTopic();
        int i = 1;
        int countAnswers = 0;
        List<Practice> practices = practiceService.getAllByTopic(topic);
        StringBuilder resultTest = new StringBuilder();
        if (!practices.isEmpty()) {
            List<Client> clientList = clientService.getAll();
            for (Client client : clientList) {
                resultTest.append(client.getLastName()).append(" ").append(client.getFirstName()).append(":\n");
                for (Practice practice : practices) {
                    List<PracticeAnswer> answers = practiceAnswerService.getAllByClientAndPractice(client, practice);
                    if (!answers.isEmpty()) {
                        resultTest.append(i).append(". ").append(practice.getQuestion()).append("\n");
                        for (PracticeAnswer practiceAnswer : answers) {
                            if (practiceAnswer.getIsCorrect()) {
                                resultTest.append("Ответ: ").append(practiceAnswer.getAnswer())
                                        .append(" ").append("✅").append("\n");
                            } else {
                                resultTest.append("Ответ: ").append(practiceAnswer.getAnswer())
                                        .append(" ").append("❌").append("\n");
                            }
                        }
                        countAnswers++;
                        resultTest.append("\n");
                    }
                    i++;
                }
                if (countAnswers == 0) {
                    resultTest.append("Ответов нет.\n\n");
                } else {
                    countAnswers = 0;
                }
                i = 1;
            }
        }

        if (checkEditableMessage(adminBotContext)) {
            messageSender.deleteBotLastMessage(admin.getUser());
        }
        try {
            Message message = messageSender.sendMessage(admin.getTelegramId(),
                    resultTest.toString(), adminReplyKeyboardMarkupSource.getCancelKeyboard());

            updateLastBotMessageId(admin.getUser(), message);
        } catch (TelegramApiException ex) {
            LOGGER.error("Unable to send sendResultPracticeMessage to admin: {}, reason: {}", admin.getTelegramId(), ex.getLocalizedMessage());
        }
    }

    public void sendShowPracticeForSelectedTopicMessage(AdminBotContext botContext) {
        Admin admin = botContext.getAdmin();
        Topic topic = admin.getCurrentTopic();
        List<Practice> practices = practiceService.getAllByTopic(topic);
        int i = 1;
        StringBuilder resultText = new StringBuilder();
        if (!practices.isEmpty()) {
            for (Practice practice : practices) {
                List<PracticeAnswer> practiceAnswers = practiceAnswerService.getAllByPractice(practice);
                resultText.append(i).append(". ").append(practice.getQuestion()).append("\n");
                if (!practiceAnswers.isEmpty()) {
                    for (PracticeAnswer answer : practiceAnswers) {
                        resultText.append(" - ").append(answer.getAnswer()).append("\n");
                    }
                } else {
                    resultText.append("Вопросы не были добавлены.").append("\n");
                }
                i++;
            }
        }

        if (checkEditableMessage(botContext)) {
            messageSender.deleteBotLastMessage(admin.getUser());
        }
        try {
            Message message = messageSender.sendMessage(admin.getTelegramId(),
                    resultText.toString(), adminReplyKeyboardMarkupSource.getCancelPracticeKeyboard(practices));

            updateLastBotMessageId(admin.getUser(), message);
        } catch (TelegramApiException ex) {
            LOGGER.error("Unable to send sendShowPracticeForSelectedTopicMessage to admin: {}, reason: {}", admin.getTelegramId(), ex.getLocalizedMessage());
        }
    }

    public void sendSelectTopicForDeleteMessage(AdminBotContext adminBotContext) {
        Admin admin = adminBotContext.getAdmin();
        List<Topic> topicList = topicService.getAll();

        if (checkEditableMessage(adminBotContext)) {
            messageSender.deleteBotLastMessage(admin.getUser());
        }
        try {
            Message message = messageSender.sendMessage(admin.getTelegramId().toString(),
                    adminMessageSource.getMessage("sendSelectTopicForDeleteMessage"),
                    adminInlineKeyboardSource.generateTopicsForSandboxPageableInlineMarkup(topicList, admin));

            updateLastBotMessageId(admin.getUser(), message);
        } catch (TelegramApiException ex) {
            LOGGER.error("Unable to send sendSelectTopicForDeleteMessage to admin: {}, reason: {}", admin.getTelegramId(), ex.getLocalizedMessage());
        }
    }

    public void sendConfirmationDeleteTopicMessage(AdminBotContext adminBotContext) {
        Admin admin = adminBotContext.getAdmin();

        if (checkEditableMessage(adminBotContext)) {
            messageSender.deleteBotLastMessage(admin.getUser());
        }
        try {
            Message message = messageSender.sendMessage(admin.getTelegramId().toString(),
                    adminMessageSource.getMessage("confirmationDeleteTopic"),
                    adminInlineKeyboardSource.getConfirmationDeleteInlineMarkup());

            updateLastBotMessageId(admin.getUser(), message);
        } catch (TelegramApiException ex) {
            LOGGER.error("Unable to send sendConfirmationDeleteTopicMessage to admin: {}, reason: {}", admin.getTelegramId(), ex.getLocalizedMessage());
        }
    }

    public void sendConfirmationDeleteQuestionMessage(AdminBotContext adminBotContext) {
        Admin admin = adminBotContext.getAdmin();

        if (checkEditableMessage(adminBotContext)) {
            messageSender.deleteBotLastMessage(admin.getUser());
        }
        try {
            Message message = messageSender.sendMessage(admin.getTelegramId().toString(),
                    adminMessageSource.getMessage("confirmationDeleteQuestion"),
                    adminInlineKeyboardSource.getConfirmationDeleteInlineMarkup());

            updateLastBotMessageId(admin.getUser(), message);
        } catch (TelegramApiException ex) {
            LOGGER.error("Unable to send sendConfirmationDeleteQuestionMessage to admin: {}, reason: {}", admin.getTelegramId(), ex.getLocalizedMessage());
        }
    }

    public void sendTopicSuccessfullyDeletedMessage(AdminBotContext adminBotContext) {
        Admin admin = adminBotContext.getAdmin();

        if (checkEditableMessage(adminBotContext)) {
            messageSender.deleteBotLastMessage(admin.getUser());
        }
        try {
            Message message = messageSender.sendMessage(admin.getTelegramId().toString(),
                    adminMessageSource.getMessage("sendTopicSuccessfullyDeletedMessage"),
                    adminReplyKeyboardMarkupSource.getMainMenuKeyboard());

            updateLastBotMessageId(admin.getUser(), message);
        } catch (TelegramApiException ex) {
            LOGGER.error("Unable to send sendTopicSuccessfullyDeletedMessage to admin: {}, reason: {}", admin.getTelegramId(), ex.getLocalizedMessage());
        }
    }

    public void sendQuestionSuccessfullyDeletedMessage(AdminBotContext adminBotContext) {
        Admin admin = adminBotContext.getAdmin();

        if (checkEditableMessage(adminBotContext)) {
            messageSender.deleteBotLastMessage(admin.getUser());
        }
        try {
            Message message = messageSender.sendMessage(admin.getTelegramId().toString(),
                    adminMessageSource.getMessage("sendQuestionSuccessfullyDeletedMessage"),
                    adminReplyKeyboardMarkupSource.getMainMenuKeyboard());

            updateLastBotMessageId(admin.getUser(), message);
        } catch (TelegramApiException ex) {
            LOGGER.error("Unable to send sendQuestionSuccessfullyDeletedMessage to admin: {}, reason: {}", admin.getTelegramId(), ex.getLocalizedMessage());
        }
    }

    public void sendTopicCanceledDeletingMessage(AdminBotContext adminBotContext) {
        Admin admin = adminBotContext.getAdmin();

        if (checkEditableMessage(adminBotContext)) {
            messageSender.deleteBotLastMessage(admin.getUser());
        }
        try {
            Message message = messageSender.sendMessage(admin.getTelegramId().toString(),
                    adminMessageSource.getMessage("sendTopicCanceledDeletingMessage"),
                    adminReplyKeyboardMarkupSource.getMainMenuKeyboard());

            updateLastBotMessageId(admin.getUser(), message);
        } catch (TelegramApiException ex) {
            LOGGER.error("Unable to send sendTopicCanceledDeletingMessage to admin: {}, reason: {}", admin.getTelegramId(), ex.getLocalizedMessage());
        }
    }

    public void sendStartMessage(AdminBotContext adminBotContext) {
        Admin admin = adminBotContext.getAdmin();

        if (checkEditableMessage(adminBotContext)) {
            messageSender.deleteBotLastMessage(admin.getUser());
        }
        try {
            Message message = messageSender.sendMessage(admin.getTelegramId().toString(),
                    adminMessageSource.getMessage("startMessage"),
                    null);

            updateLastBotMessageId(admin.getUser(), message);
        } catch (TelegramApiException ex) {
            LOGGER.error("Unable to send sendStartMessage to admin: {}, reason: {}", admin.getTelegramId(), ex.getLocalizedMessage());
        }
    }

    public void sendEnterNumberQuestionForDeleteMessage(AdminBotContext adminBotContext) {
        Admin admin = adminBotContext.getAdmin();

        if (checkEditableMessage(adminBotContext)) {
            messageSender.deleteBotLastMessage(admin.getUser());
        }
        try {
            Message message = messageSender.sendMessage(admin.getTelegramId().toString(),
                    adminMessageSource.getMessage("enterQuestionNumberForDelete"),
                    adminReplyKeyboardMarkupSource.getCancelKeyboard());

            updateLastBotMessageId(admin.getUser(), message);
        } catch (TelegramApiException ex) {
            LOGGER.error("Unable to send sendStartMessage to admin: {}, reason: {}", admin.getTelegramId(), ex.getLocalizedMessage());
        }
    }

    public void sendErrorDeleteMessage(AdminBotContext adminBotContext) {
        Admin admin = adminBotContext.getAdmin();

        if (checkEditableMessage(adminBotContext)) {
            messageSender.deleteBotLastMessage(admin.getUser());
        }
        try {
            Message message = messageSender.sendMessage(admin.getTelegramId().toString(),
                    adminMessageSource.getMessage("sendErrorDeleteMessage"),
                    adminReplyKeyboardMarkupSource.getCancelKeyboard());

            updateLastBotMessageId(admin.getUser(), message);
        } catch (TelegramApiException ex) {
            LOGGER.error("Unable to send sendStartMessage to admin: {}, reason: {}", admin.getTelegramId(), ex.getLocalizedMessage());
        }
    }
}
