package by.minilooth.telegrambot.bot.handler.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import by.minilooth.telegrambot.bot.api.UpdateHandler;
import by.minilooth.telegrambot.bot.context.client.ClientBotContext;
import by.minilooth.telegrambot.bot.state.client.ClientBotState;
import by.minilooth.telegrambot.exception.ClientBotStateException;
import by.minilooth.telegrambot.exception.UserNotFoundException;
import by.minilooth.telegrambot.model.Client;
import by.minilooth.telegrambot.model.User;
import by.minilooth.telegrambot.service.ClientService;
import by.minilooth.telegrambot.service.UserService;

@Component
public class ClientUpdateHandler extends UpdateHandler {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(ClientUpdateHandler.class);

    @Autowired private UserService userService;
    @Autowired private ClientService clientService;

    private void updateState(User user, ClientBotState clientBotState) {
        if (user != null && user.getClient() != null && clientBotState != null) {
            user.getClient().setClientBotState(clientBotState);
            userService.save(user);
        }
    }

    @Override
    public void processText(Update update) throws ClientBotStateException, UserNotFoundException {
        final String chatId = update.getMessage().getChatId().toString();
        ClientBotContext botContext = null;
        ClientBotState botState = null;

        User user = userService.getByTelegramId(chatId);
        Client client = user.getClient();

        try {
            if (client == null) {
                client = clientService.createClient(user);

                botContext = ClientBotContext.of(client, update);
                botState = client.getClientBotState();

                botState.enter(botContext);
                
                while(!botState.getIsInputNeeded()) {
                    if (botState.nextState() != null) {
                        botState = botState.nextState();
                        botState.enter(botContext);
                    }
                    else {
                        break;
                    }
                }
            }
            else {
                botContext = ClientBotContext.of(client, update);
                botState = client.getClientBotState();

                LOGGER.info("[{} | {}] Text: {}", chatId, botState, update.getMessage().getText());

                botState.handleText(botContext);

                do {
                    if (botState.nextState() != null) {
                        botState = botState.nextState();
                        botState.enter(botContext);
                    }
                    else {
                        break;
                    }
                } while (!botState.getIsInputNeeded());
            }
        }
        catch (ClientBotStateException ex) {
            botState = ex.getExceptionState().rootState();
            botState.enter(botContext);
        }
        finally {
            updateState(user, botState);
        }
    }

    @Override
    public void processContact(Update update) {
        // TODO Auto-generated method stub

    }

    @Override
    public void processPhoto(Update update) {
        // TODO Auto-generated method stub

    }

    @Override
    public void processCallbackQuery(Update update) throws ClientBotStateException {
        final String chatId = update.getCallbackQuery().getFrom().getId().toString();
        ClientBotContext botContext = null;
        ClientBotState botState = null;

        User user = userService.getByTelegramId(chatId);
        Client client = user.getClient();

        if (client == null) {
            client = clientService.createClient(user);

            botContext = ClientBotContext.of(client, update);
            botState = client.getClientBotState();

            botState.enter(botContext);

            while(!botState.getIsInputNeeded()) {
                if (botState.nextState() != null) {
                    botState = botState.nextState();
                    botState.enter(botContext);
                }
            }
        }
        else {
            botContext = ClientBotContext.of(client, update);
            botState = client.getClientBotState();

            LOGGER.info("CallbackQuery received from manager: " + chatId + ", in state: " + botState + ", with data: " + update.getCallbackQuery().getData());

            botState.handleCallbackQuery(botContext);

            do {
                if (botState.nextState() != null) {
                    botState = botState.nextState();
                    botState.enter(botContext);
                }
                else {
                    break;
                }
            } while (!botState.getIsInputNeeded());
        }

        if (user != null && user.getClient() != null) {
            user.getClient().setClientBotState(botState);
            userService.save(user);
        }

    }

    @Override
    public void processVoice(Update update) {
        // TODO Auto-generated method stub

    }

    @Override
    public void processVideo(Update update) {
        // TODO Auto-generated method stub

    }

    @Override
    public void processVideoNote(Update update) {
        // TODO Auto-generated method stub

    }

    @Override
    public void processDocument(Update update) {
        // TODO Auto-generated method stub

    }

    
}
