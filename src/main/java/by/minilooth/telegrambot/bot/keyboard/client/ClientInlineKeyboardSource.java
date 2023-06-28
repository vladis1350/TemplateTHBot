package by.minilooth.telegrambot.bot.keyboard.client;

import by.minilooth.telegrambot.bot.keyboard.InlineKeyboardSource;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class ClientInlineKeyboardSource extends InlineKeyboardSource {

    public InlineKeyboardMarkup getSelectReportInlineMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton buttonMilk = new InlineKeyboardButton();
        InlineKeyboardButton buttonField = new InlineKeyboardButton();
        InlineKeyboardButton buttonFull = new InlineKeyboardButton();
        InlineKeyboardButton buttonWeekly = new InlineKeyboardButton();

        buttonMilk.setCallbackData("getReportMilk");
        buttonMilk.setText("По молоку");

        buttonField.setCallbackData("getReportField");
        buttonField.setText("Полевые работы");

        buttonFull.setCallbackData("getFullReport");
        buttonFull.setText("Полный ежедневный отчёт");

        buttonWeekly.setCallbackData("getWeeklyReport");
        buttonWeekly.setText("Еженедельный отчёт");

        List<InlineKeyboardButton> firstKeyboardButtonRow1 = new ArrayList<>();
        List<InlineKeyboardButton> firstKeyboardButtonRow2 = new ArrayList<>();
        List<InlineKeyboardButton> firstKeyboardButtonRow3 = new ArrayList<>();
        firstKeyboardButtonRow1.add(buttonMilk);
        firstKeyboardButtonRow1.add(buttonField);
        firstKeyboardButtonRow2.add(buttonFull);
        firstKeyboardButtonRow3.add(buttonWeekly);

        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();

        keyboardRows.add(firstKeyboardButtonRow1);
        keyboardRows.add(firstKeyboardButtonRow2);
        keyboardRows.add(firstKeyboardButtonRow3);

        inlineKeyboardMarkup.setKeyboard(keyboardRows);

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getSelectDistrictInlineMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton buttonGlusk = new InlineKeyboardButton();
        InlineKeyboardButton buttonBobr = new InlineKeyboardButton();
        InlineKeyboardButton buttonOsip = new InlineKeyboardButton();
        InlineKeyboardButton buttonPuh = new InlineKeyboardButton();

        buttonGlusk.setCallbackData("getGluskReport");
        buttonGlusk.setText("Глусский район");

        buttonBobr.setCallbackData("getBobrReport");
        buttonBobr.setText("Бобруйский район");

        buttonOsip.setCallbackData("getOsipReport");
        buttonOsip.setText("Осиповичский район");

        buttonPuh.setCallbackData("getPuhReport");
        buttonPuh.setText("Пуховичский район");

        List<InlineKeyboardButton> firstKeyboardButtonRow1 = new ArrayList<>();
        List<InlineKeyboardButton> firstKeyboardButtonRow2 = new ArrayList<>();
        List<InlineKeyboardButton> firstKeyboardButtonRow3 = new ArrayList<>();
        List<InlineKeyboardButton> firstKeyboardButtonRow4 = new ArrayList<>();
        firstKeyboardButtonRow1.add(buttonGlusk);
        firstKeyboardButtonRow2.add(buttonBobr);
        firstKeyboardButtonRow3.add(buttonOsip);
        firstKeyboardButtonRow4.add(buttonPuh);

        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();

        keyboardRows.add(firstKeyboardButtonRow1);
        keyboardRows.add(firstKeyboardButtonRow2);
        keyboardRows.add(firstKeyboardButtonRow3);
        keyboardRows.add(firstKeyboardButtonRow4);

        inlineKeyboardMarkup.setKeyboard(keyboardRows);

        return inlineKeyboardMarkup;
    }

}
