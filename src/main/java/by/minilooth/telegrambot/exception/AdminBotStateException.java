package by.minilooth.telegrambot.exception;

import by.minilooth.telegrambot.bot.state.admin.AdminBotState;
import by.minilooth.telegrambot.bot.state.client.ClientBotState;
import lombok.Getter;

public class AdminBotStateException extends Exception {

    private static final long serialVersionUID = 5771105261629045380L;

    @Getter private final AdminBotState exceptionState;

    public AdminBotStateException(String message, AdminBotState adminBotState) {
        super(message);

        this.exceptionState = adminBotState;
    }

}
