package by.minilooth.telegrambot.bot.keyboard.admin;

import by.minilooth.telegrambot.bot.keyboard.ReplyKeyboardMarkupSource;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class AdminReplyKeyboardMarkupSource extends ReplyKeyboardMarkupSource {

    public ReplyKeyboardMarkup getMainMenuKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = this.createInstance(true, true, false);
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow firstKeyboardRow = new KeyboardRow();
        KeyboardRow firstKeyboardRowTwo = new KeyboardRow();
        KeyboardRow firstKeyboardRowThree = new KeyboardRow();

        firstKeyboardRow.add(new KeyboardButton("Добавить тему"));
        firstKeyboardRow.add(new KeyboardButton("Список тем"));
        firstKeyboardRowTwo.add(new KeyboardButton("Результаты"));
        firstKeyboardRowThree.add(new KeyboardButton("Удалить тему"));

        keyboardRows.add(firstKeyboardRow);
        keyboardRows.add(firstKeyboardRowTwo);
        keyboardRows.add(firstKeyboardRowThree);

        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup getPracticeKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = this.createInstance(true, true, false);
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow firstKeyboardRow = new KeyboardRow();

        firstKeyboardRow.add(new KeyboardButton("Практика"));
        firstKeyboardRow.add(new KeyboardButton("Назад к списку тем"));

        keyboardRows.add(firstKeyboardRow);

        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup getCancelKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = this.createInstance(true, true, false);
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow firstKeyboardRow = new KeyboardRow();

        firstKeyboardRow.add(new KeyboardButton("Отмена"));

        keyboardRows.add(firstKeyboardRow);

        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup getLoadFileKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = this.createInstance(true, true, false);
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow firstKeyboardRow = new KeyboardRow();

        firstKeyboardRow.add(new KeyboardButton("Загрузить файл"));

        keyboardRows.add(firstKeyboardRow);

        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup getAddPracticeKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = this.createInstance(true, true, false);
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow firstKeyboardRow = new KeyboardRow();

        firstKeyboardRow.add(new KeyboardButton("Добавить практику"));
        firstKeyboardRow.add(new KeyboardButton("Главное меню"));

        keyboardRows.add(firstKeyboardRow);

        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup getAddPracticeAnswerKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = this.createInstance(true, true, false);
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow firstKeyboardRowOne = new KeyboardRow();
        KeyboardRow firstKeyboardRowTwo = new KeyboardRow();

        firstKeyboardRowOne.add(new KeyboardButton("Добавить вариант ответа"));
        firstKeyboardRowTwo.add(new KeyboardButton("Завершить добавление ответов"));

        keyboardRows.add(firstKeyboardRowOne);
        keyboardRows.add(firstKeyboardRowTwo);

        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup getPracticeMenuKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = this.createInstance(true, true, false);
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow firstKeyboardRowOne = new KeyboardRow();
        KeyboardRow firstKeyboardRowTwo = new KeyboardRow();

        firstKeyboardRowOne.add(new KeyboardButton("Добавить вопрос"));
        firstKeyboardRowTwo.add(new KeyboardButton("Главное меню"));

        keyboardRows.add(firstKeyboardRowOne);
        keyboardRows.add(firstKeyboardRowTwo);

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
