package by.minilooth.telegrambot.bot.state.admin;

import by.minilooth.telegrambot.bot.api.BotState;
import by.minilooth.telegrambot.bot.context.admin.AdminBotContext;
import by.minilooth.telegrambot.bot.context.client.ClientBotContext;
import by.minilooth.telegrambot.bot.state.client.ClientBotState;
import by.minilooth.telegrambot.exception.AdminBotStateException;

public enum AdminBotState implements BotState<AdminBotState, AdminBotContext> {
    Start(false) {
        @Override
        public void enter(AdminBotContext botContext) {
//            clientMessageService.sendStartMessage(botContext);
        }

        @Override
        public AdminBotState nextState() {
            return null;
        }

        @Override
        public AdminBotState rootState() {
            return Start;
        }

    };

    private final Boolean isInputNeeded;

    AdminBotState(Boolean isInputNeeded) {
        this.isInputNeeded = isInputNeeded;
    }

    public Boolean getIsInputNeeded() {
        return isInputNeeded;
    }

    public static AdminBotState getInitialState() {
        return Start;
    }

    @Override
    public void handleText(AdminBotContext adminBotContext) throws AdminBotStateException {
    }

    @Override
    public void handleCallbackQuery(AdminBotContext adminBotContext) throws AdminBotStateException {
    }

    @Override
    public void handleContact(AdminBotContext adminBotContext) throws AdminBotStateException {
    }

    @Override
    public void handlePhoto(AdminBotContext adminBotContext) throws AdminBotStateException {
    }

    @Override
    public void handleVoice(AdminBotContext adminBotContext) throws AdminBotStateException {
    }

    @Override
    public void handleVideo(AdminBotContext adminBotContext) throws AdminBotStateException {
    }

    @Override
    public void handleVideoNote(AdminBotContext adminBotContext) throws AdminBotStateException {
    }

    @Override
    public void handleDocument(AdminBotContext adminBotContext) throws AdminBotStateException {
    }

    @Override
    public abstract void enter(AdminBotContext adminBotContext) throws AdminBotStateException;

    @Override
    public abstract AdminBotState nextState();

    @Override
    public abstract AdminBotState rootState();

}
