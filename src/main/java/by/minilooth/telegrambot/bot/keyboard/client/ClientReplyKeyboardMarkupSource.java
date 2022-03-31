package by.minilooth.telegrambot.bot.keyboard.client;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import by.minilooth.telegrambot.bot.keyboard.ReplyKeyboardMarkupSource;

public class ClientReplyKeyboardMarkupSource extends ReplyKeyboardMarkupSource {

    public ReplyKeyboardMarkup getMainMenuKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = this.createInstance(true, true, false);
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow firstKeyboardRow = new KeyboardRow();

        firstKeyboardRow.add(new KeyboardButton("Практика"));
        firstKeyboardRow.add(new KeyboardButton("Назад к списку тем"));

        keyboardRows.add(firstKeyboardRow);

        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup getTopicListKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = this.createInstance(true, true, false);
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow firstKeyboardRow = new KeyboardRow();

        firstKeyboardRow.add(new KeyboardButton("Список тем"));

        keyboardRows.add(firstKeyboardRow);

        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }
    
}
