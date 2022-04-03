package by.minilooth.telegrambot.bot.keyboard.admin;

import by.minilooth.telegrambot.bot.keyboard.InlineKeyboardMarkupSource;
import by.minilooth.telegrambot.model.Admin;
import by.minilooth.telegrambot.model.Client;
import by.minilooth.telegrambot.model.PracticeAnswer;
import by.minilooth.telegrambot.model.Topic;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class AdminInlineKeyboardSource extends InlineKeyboardMarkupSource {

    public final InlineKeyboardMarkup generateTopicsForSandboxPageableInlineMarkup(List<Topic> topics, Admin admin) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();
        Integer page = admin.getUser().getCurrentPage();

        for (int i = ((page - 1) * ITEMS_PER_PAGE); i < page * ITEMS_PER_PAGE && i < topics.size(); i++) {
            List<InlineKeyboardButton> buttons = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton();

            button.setText(topics.get(i).getName());
            button.setCallbackData(topics.get(i).getId().toString());

            buttons.add(button);

            keyboardRows.add(buttons);
        }

        if (topics.size() > ITEMS_PER_PAGE) {
            keyboardRows.add(getNavigateInlineButtons(topics, page, admin.getAdminBotState()));
        }

        inlineKeyboardMarkup.setKeyboard(keyboardRows);

        return inlineKeyboardMarkup;
    }

    public final InlineKeyboardMarkup getConfirmationInlineMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();

        List<InlineKeyboardButton> buttons = new ArrayList<>();
        InlineKeyboardButton buttonYes = new InlineKeyboardButton();
        InlineKeyboardButton buttonNo = new InlineKeyboardButton();

        buttonYes.setText("Да");
        buttonYes.setCallbackData("answerIsCorrect");
        buttonNo.setText("Нет");
        buttonNo.setCallbackData("answerNotCorrect");

        buttons.add(buttonYes);
        buttons.add(buttonNo);

        keyboardRows.add(buttons);

        inlineKeyboardMarkup.setKeyboard(keyboardRows);

        return inlineKeyboardMarkup;
    }

    public final InlineKeyboardMarkup getConfirmationDeleteInlineMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();

        List<InlineKeyboardButton> buttons = new ArrayList<>();
        InlineKeyboardButton buttonYes = new InlineKeyboardButton();
        InlineKeyboardButton buttonNo = new InlineKeyboardButton();

        buttonYes.setText("Да");
        buttonYes.setCallbackData("confirm");
        buttonNo.setText("Нет");
        buttonNo.setCallbackData("notConfirm");

        buttons.add(buttonYes);
        buttons.add(buttonNo);

        keyboardRows.add(buttons);

        inlineKeyboardMarkup.setKeyboard(keyboardRows);

        return inlineKeyboardMarkup;
    }

    public final InlineKeyboardMarkup generateAnswersForSandboxPageableInlineMarkup(List<PracticeAnswer> practiceAnswers) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();

        for (int i = 0; i < practiceAnswers.size(); i++) {
            List<InlineKeyboardButton> buttons = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton();

            button.setText(i + 1 + "");
            button.setCallbackData(practiceAnswers.get(i).getId().toString());

            buttons.add(button);

            keyboardRows.add(buttons);
        }

        inlineKeyboardMarkup.setKeyboard(keyboardRows);

        return inlineKeyboardMarkup;
    }
}
