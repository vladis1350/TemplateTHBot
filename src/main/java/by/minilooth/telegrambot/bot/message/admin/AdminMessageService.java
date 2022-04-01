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
import by.minilooth.telegrambot.service.PracticeAnswerService;
import by.minilooth.telegrambot.service.TheoryService;
import by.minilooth.telegrambot.service.TopicService;
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
                                theory.getTheoryText(), adminReplyKeyboardMarkupSource.getMainMenuKeyboard());
                        break;
                    case DOCUMENT:
                        message = messageSender.sendDocument(admin.getTelegramId(), theory.getCaption(),
                                new InputFile(botUtils.downloadTelegramFile(theory.getData()), theory.getFilename()),
                                adminReplyKeyboardMarkupSource.getMainMenuKeyboard());
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
}
