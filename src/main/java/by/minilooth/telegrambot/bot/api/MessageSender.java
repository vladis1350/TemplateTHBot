package by.minilooth.telegrambot.bot.api;

import by.minilooth.telegrambot.bot.TelegramBot;
import by.minilooth.telegrambot.model.User;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.Serializable;
import java.time.Instant;

@Component
@Slf4j
public class MessageSender {
    private final static Long MESSAGE_ACTION_EXPIRATION = 172800L;
    private final static String DEFAULT_PARSE_MODE = "HTML";

    @Autowired private TelegramBot telegramBot;

    public Message sendMessage(String chatId, String text, ReplyKeyboard replyKeyboard) 
            throws TelegramApiException {
        SendMessage sendMessage = SendMessage.builder()
                                             .chatId(chatId)
                                             .parseMode(DEFAULT_PARSE_MODE)
                                             .replyMarkup(replyKeyboard)
                                             .text(text)
                                             .build();

        return this.sendMessage(sendMessage);
    }

    public Message sendPhoto(String chatId, String caption, InputFile photo, ReplyKeyboard replyKeyboard) 
            throws TelegramApiException {
        SendPhoto sendPhoto = SendPhoto.builder()
                                       .chatId(chatId)
                                       .caption(caption)
                                       .photo(photo)
                                       .replyMarkup(replyKeyboard)
                                       .parseMode(DEFAULT_PARSE_MODE)
                                       .build();

        return this.sendPhoto(sendPhoto);
    }

    public Message sendDocument(String chatId, String caption, InputFile document, ReplyKeyboard replyKeyboard) 
            throws TelegramApiException {
        SendDocument sendDocument = SendDocument.builder()
                                                .chatId(chatId)
                                                .caption(caption)
                                                .document(document)
                                                .replyMarkup(replyKeyboard)
                                                .parseMode(DEFAULT_PARSE_MODE)
                                                .build();
                                        
        return this.sendDocument(sendDocument);
    }

    public Message sendVoice(String chatId, String caption, InputFile voice, ReplyKeyboard replyKeyboard) 
            throws TelegramApiException {
        SendVoice sendVoice = SendVoice.builder()
                                       .chatId(chatId)
                                       .caption(caption)
                                       .voice(voice)
                                       .replyMarkup(replyKeyboard)
                                       .parseMode(DEFAULT_PARSE_MODE)
                                       .build();

        return this.sendVoice(sendVoice);
    }

    public Message sendVideo(String chatId, String caption, InputFile video, ReplyKeyboard replyKeyboard) 
            throws TelegramApiException {
        SendVideo sendVideo = SendVideo.builder()
                                       .chatId(chatId)
                                       .caption(caption)
                                       .video(video)
                                       .replyMarkup(replyKeyboard)
                                       .parseMode(DEFAULT_PARSE_MODE)
                                       .build();

        return this.sendVideo(sendVideo);
    }

    public Message sendVideoNote(String chatId, InputFile videoNote, ReplyKeyboard replyKeyboard) 
            throws TelegramApiException {
        SendVideoNote sendVideoNote = SendVideoNote.builder()
                                                   .chatId(chatId)
                                                   .videoNote(videoNote)
                                                   .replyMarkup(replyKeyboard)
                                                   .build();

        return this.sendVideoNote(sendVideoNote);
    }


    public Boolean editMessageText(String chatId, String text, Integer messageId, InlineKeyboardMarkup InlineKeyboardMarkup) 
            throws TelegramApiException {
        EditMessageText editMessageText = EditMessageText.builder()
                                                         .chatId(chatId)
                                                         .text(text)
                                                         .messageId(messageId)
                                                         .replyMarkup(InlineKeyboardMarkup)
                                                         .parseMode(DEFAULT_PARSE_MODE)
                                                         .build();

        return this.editMessageText(editMessageText);
    }

    public Boolean editMessageReplyMarkup(String chatId, Integer messageId, InlineKeyboardMarkup inlineKeyboardMarkup) 
            throws TelegramApiException {
        EditMessageReplyMarkup editMessageReplyMarkup = EditMessageReplyMarkup.builder()
                                                                              .chatId(chatId)
                                                                              .messageId(messageId)
                                                                              .replyMarkup(inlineKeyboardMarkup)
                                                                              .build();

        return this.editMessageReplyMarkup(editMessageReplyMarkup);
    }

    public Boolean deleteMessage(String chatId, Integer messageId) 
            throws TelegramApiException {
        DeleteMessage deleteMessage = DeleteMessage.builder()
                                                   .chatId(chatId)
                                                   .messageId(messageId)
                                                   .build();

        return this.deleteMessage(deleteMessage);
    }

    private Message sendMessage(SendMessage sendMessage) throws TelegramApiException {
        return telegramBot.execute(sendMessage);
    }

    private Message sendPhoto(SendPhoto sendPhoto) throws TelegramApiException {
        return telegramBot.execute(sendPhoto);
    }

    private Message sendDocument(SendDocument sendDocument) throws TelegramApiException {
        return telegramBot.execute(sendDocument);
    }

    private Message sendVoice(SendVoice sendVoice) throws TelegramApiException {
        return telegramBot.execute(sendVoice);
    }

    private Message sendVideo(SendVideo sendVideo) throws TelegramApiException {
        return telegramBot.execute(sendVideo);
    }

    private Message sendVideoNote(SendVideoNote sendVideoNote) throws TelegramApiException {
        return telegramBot.execute(sendVideoNote);
    }

    private Boolean editMessageText(EditMessageText editMessageText) throws TelegramApiException {
        Serializable result = telegramBot.execute(editMessageText);

        if (result instanceof Message) {
            return true;
        }

        return false;
    }

    private Boolean editMessageReplyMarkup(EditMessageReplyMarkup editMessageReplyMarkup) throws TelegramApiException {
        Serializable result = telegramBot.execute(editMessageReplyMarkup);

        if (result instanceof Message) {
            return true;
        }

        return false;
    }

    private Boolean deleteMessage(DeleteMessage deleteMessage) throws TelegramApiException {
        return telegramBot.execute(deleteMessage);
    }

    public Boolean isMessageExpired(Integer messageDate) {
        return Instant.now().getEpochSecond() - messageDate >= MESSAGE_ACTION_EXPIRATION;
    }

    public Boolean deleteMessage(DeleteMessage deleteMessage, Integer messageDate) throws TelegramApiException {
        if (Instant.now().getEpochSecond() - messageDate < MESSAGE_ACTION_EXPIRATION) {
            return telegramBot.execute(deleteMessage);
        }
        return false;
    }

    @SneakyThrows
    public void deleteBotLastMessage(User user) {
        if (!isMessageExpired(user.getBotLastMessageDate())) {
            DeleteMessage deleteMessage = new DeleteMessage();

            deleteMessage.setChatId(user.getTelegramId());
            deleteMessage.setMessageId(user.getBotLastMessageId());
            try {
                if (deleteMessage(deleteMessage, user.getBotLastMessageDate())) {
                    user.setBotLastMessageId(null);
                    user.setBotLastMessageDate(null);
                }
            } catch (TelegramApiRequestException e) {
                log.error("Error delete message: " + e.getApiResponse());
            }

        }
    }

}
