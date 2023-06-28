package by.minilooth.telegrambot.bot.state;

import by.minilooth.telegrambot.bot.context.client.ClientBotContext;
import by.minilooth.telegrambot.bot.message.client.ClientMessageService;
import by.minilooth.telegrambot.exception.ClientBotStateException;
import by.minilooth.telegrambot.exception.ClientNotFoundException;
import by.minilooth.telegrambot.bot.api.BotState;
import by.minilooth.telegrambot.model.enums.Districts;
import by.minilooth.telegrambot.model.enums.TypeReport;
import com.vdurmont.emoji.EmojiParser;
import lombok.Setter;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;

public enum ClientBotState implements BotState<ClientBotState, ClientBotContext> {
    Start(false) {

        @Override
        public void enter(ClientBotContext botContext) {
            clientMessageService.sendStartMessage(botContext);
        }

        @Override public ClientBotState nextState() {
            return MainMenu;
        }

        @Override public ClientBotState rootState() {
            return Start;
        }

    },

    MainMenu(true) {
        private ClientBotState nextState = null;

        @Override public void enter(ClientBotContext botContext) {
            clientMessageService.sendMainMenu(botContext);
        }

        @Override public void handleText(ClientBotContext botContext) throws ClientBotStateException {
            switch (EmojiParser.removeAllEmojis(botContext.getUpdate().getMessage().getText())) {
                case "Получить отчёт":
                    nextState = SelectDistrict;
                    break;
                default:
                    nextState = MainMenu;
                    break;
            }
        }

        @Override public ClientBotState nextState() {
            return nextState;
        }

        @Override public ClientBotState rootState() {
            return MainMenu;
        }
    },

    SelectDistrict(true) {
        private ClientBotState nextState = null;

        @Override public void enter(ClientBotContext botContext) {
            clientMessageService.sendSelectDistrict(botContext);
        }

        @Override
        public void handleCallbackQuery(ClientBotContext botContext) {
            String userAnswer = botContext.getUpdate().getCallbackQuery().getData();
            switch (EmojiParser.removeAllEmojis(userAnswer)) {
                case "getGluskReport":
                    botContext.getClient().setDistricts(Districts.GLUSK);
                    nextState = GetReport;
                    break;
                case "getBobrReport":
                    botContext.getClient().setDistricts(Districts.BOBRUISK);
                    nextState = GetReport;
                    break;
                case "getOsipReport":
                    botContext.getClient().setDistricts(Districts.OSIPOVICHI);
                    nextState = GetReport;
                    break;
                case "getPuhReport":
                    botContext.getClient().setDistricts(Districts.PUHOVICHI);
                    nextState = GetReport;
                    break;
                default:
                    nextState = MainMenu;
                    break;
            }
        }
        @Override public void handleText(ClientBotContext botContext) throws ClientBotStateException {
            switch (EmojiParser.removeAllEmojis(botContext.getUpdate().getMessage().getText())) {
                case "Получить отчёт":
                    nextState = SelectDistrict;
                    break;
                default:
                    nextState = MainMenu;
                    break;
            }
        }

        @Override public ClientBotState nextState() {
            return nextState;
        }

        @Override public ClientBotState rootState() {
            return MainMenu;
        }
    },

    GetReport(true) {
        private ClientBotState nextState = null;

        @Override public void enter(ClientBotContext botContext) {
            clientMessageService.sendSelectReport(botContext);
        }

        @Override public void handleText(ClientBotContext botContext) throws ClientBotStateException {
            switch (EmojiParser.removeAllEmojis(botContext.getUpdate().getMessage().getText())) {
                case "Получить отчёт":
                    nextState = SelectDistrict;
                    break;
                default:
                    nextState = MainMenu;
                    break;
            }
        }

        @Override
        public void handleCallbackQuery(ClientBotContext botContext) {
            String userAnswer = botContext.getUpdate().getCallbackQuery().getData();
            switch (EmojiParser.removeAllEmojis(userAnswer)) {
                case "getReportMilk":
                    botContext.getClient().setTypeReport(TypeReport.MILK);
                    nextState = GetReportMilk;
                    break;
                case "getReportField":
                    botContext.getClient().setTypeReport(TypeReport.FIELD);
                    nextState = GetReportField;
                    break;
                case "getFullReport":
                    botContext.getClient().setTypeReport(TypeReport.FULL);
                    nextState = GetFullReport;
                    break;
                case "getWeeklyReport":
                    botContext.getClient().setTypeReport(TypeReport.WEEKLY);
                    nextState = GetWeeklyReport;
                    break;
                default:
                    nextState = MainMenu;
                    break;
            }
        }

        @Override public ClientBotState nextState() {
            return nextState;
        }

        @Override public ClientBotState rootState() {
            return MainMenu;
        }
    },

    GetFullReport(true) {
        private ClientBotState nextState = null;

        @Override public void enter(ClientBotContext botContext) {
            try {
                clientMessageService.sendFullReport(botContext);
            } catch (GeneralSecurityException | IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        }
        @Override public void handleText(ClientBotContext botContext) throws ClientBotStateException {
            switch (EmojiParser.removeAllEmojis(botContext.getUpdate().getMessage().getText())) {
                case "Получить отчёт":
                    nextState = SelectDistrict;
                    break;
                default:
                    nextState = MainMenu;
                    break;
            }
        }

        @Override public ClientBotState nextState() {
            return nextState;
        }

        @Override public ClientBotState rootState() {
            return MainMenu;
        }
    },

    GetWeeklyReport(true) {
        private ClientBotState nextState = null;

        @Override public void enter(ClientBotContext botContext) {
            try {
                clientMessageService.sendWeeklyReport(botContext);
            } catch (GeneralSecurityException | IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        }

        @Override public void handleText(ClientBotContext botContext) throws ClientBotStateException {
            switch (EmojiParser.removeAllEmojis(botContext.getUpdate().getMessage().getText())) {
                case "Получить отчёт":
                    nextState = SelectDistrict;
                    break;
                default:
                    nextState = MainMenu;
                    break;
            }
        }

        @Override public ClientBotState nextState() {
            return nextState;
        }

        @Override public ClientBotState rootState() {
            return MainMenu;
        }
    },

    GetReportMilk(true) {
        private ClientBotState nextState = null;

        @Override public void enter(ClientBotContext botContext) {
            try {
//                clientMessageService.sendReportField(botContext);
                clientMessageService.sendReportMilk(botContext);
            } catch (GeneralSecurityException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        @Override public void handleText(ClientBotContext botContext) throws ClientBotStateException {
            switch (EmojiParser.removeAllEmojis(botContext.getUpdate().getMessage().getText())) {
                case "Получить отчёт":
                    nextState = SelectDistrict;
                    break;
                default:
                    nextState = MainMenu;
                    break;
            }
        }

        @Override public ClientBotState nextState() {
            return nextState;
        }

        @Override public ClientBotState rootState() {
            return MainMenu;
        }
    },

    GetReportField(true) {
        private ClientBotState nextState = null;

        @Override public void enter(ClientBotContext botContext) {
            try {
                clientMessageService.sendReportField(botContext);
//                clientMessageService.sendReportMilk(botContext);
            } catch (GeneralSecurityException | ParseException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        @Override public void handleText(ClientBotContext botContext) throws ClientBotStateException {
            switch (EmojiParser.removeAllEmojis(botContext.getUpdate().getMessage().getText())) {
                case "Получить отчёт":
                    nextState = SelectDistrict;
                    break;
                default:
                    nextState = MainMenu;
                    break;
            }
        }

        @Override public ClientBotState nextState() {
            return nextState;
        }

        @Override public ClientBotState rootState() {
            return MainMenu;
        }
    },

    EnterFirstname(true) {
        private ClientBotState nextState = null;

        @Override public void enter(ClientBotContext botContext) {

        }

        @Override public void handleText(ClientBotContext botContext) {

        }

        @Override public ClientBotState nextState() {
            return EnterSurname;
        }

        @Override public ClientBotState rootState() {
            return MainMenu;
        }
    },

    EnterSurname(true) {
        private ClientBotState nextState = null;

        @Override public void enter(ClientBotContext botContext) {

        }

        @Override public void handleText(ClientBotContext botContext) {

        }

        @Override public ClientBotState nextState() {
            return nextState;
        }

        @Override public ClientBotState rootState() {
            return MainMenu;
        }
    },

    EnterPatronymic(true) {
        private ClientBotState nextState = null;

        @Override public void enter(ClientBotContext botContext) {

        }

        @Override public void handleText(ClientBotContext botContext) {

        }

        @Override public ClientBotState nextState() {
            return nextState;
        }

        @Override public ClientBotState rootState() {
            return MainMenu;
        }
    };

    private final Boolean isInputNeeded;

    ClientBotState(Boolean isInputNeeded) {
        this.isInputNeeded = isInputNeeded;
    }

    public Boolean getIsInputNeeded() {
        return isInputNeeded;
    }

    public static ClientBotState getInitialState() {
        return Start;
    }

    @Override 
    public void handleText(ClientBotContext clientBotContext) throws ClientBotStateException {}
    
    @Override 
    public void handleCallbackQuery(ClientBotContext clientBotContext) throws ClientBotStateException {}
    
    @Override 
    public void handleContact(ClientBotContext clientBotContext) throws ClientBotStateException {}
    
    @Override 
    public void handlePhoto(ClientBotContext clientBotContext) throws ClientBotStateException {}
    
    @Override 
    public void handleVoice(ClientBotContext clientBotContext) throws ClientBotStateException {}
    
    @Override 
    public void handleVideo(ClientBotContext clientBotContext) throws ClientBotStateException {}
    
    @Override 
    public void handleVideoNote(ClientBotContext clientBotContext) throws ClientBotStateException {}
    
    @Override 
    public void handleDocument(ClientBotContext clientBotContext) throws ClientBotStateException {}

    @Override
    public abstract void enter(ClientBotContext clientBotContext) throws ClientBotStateException, ClientNotFoundException;
    
    @Override
    public abstract ClientBotState nextState();
    
    @Override
    public abstract ClientBotState rootState();

    @Setter
    private static ClientMessageService clientMessageService;
    
}
