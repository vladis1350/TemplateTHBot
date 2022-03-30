package by.minilooth.telegrambot.exception;

import by.minilooth.telegrambot.bot.state.client.ClientBotState;
import lombok.Getter;

public class ClientBotStateException extends Exception {
    
    private static final long serialVersionUID = 5771105261629045380L;

    @Getter private final ClientBotState exceptionState;

    public ClientBotStateException(String message, ClientBotState clientBotState) {
        super(message);

        this.exceptionState = clientBotState;
    }

}
