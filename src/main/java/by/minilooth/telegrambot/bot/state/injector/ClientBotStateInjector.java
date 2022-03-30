package by.minilooth.telegrambot.bot.state.injector;

import javax.annotation.PostConstruct;

import by.minilooth.telegrambot.bot.message.client.ClientMessageService;
import by.minilooth.telegrambot.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import by.minilooth.telegrambot.bot.api.BotStateInjector;
import by.minilooth.telegrambot.bot.context.client.ClientBotContext;
import by.minilooth.telegrambot.bot.state.client.ClientBotState;

@Component
public class ClientBotStateInjector implements BotStateInjector<ClientBotState, ClientBotContext> {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientMessageService clientMessageService;

    @PostConstruct
    @Override
    public void inject() {
        ClientBotState.setClientMessageService(clientMessageService);
        ClientBotState.setClientService(clientService);
    }

}
