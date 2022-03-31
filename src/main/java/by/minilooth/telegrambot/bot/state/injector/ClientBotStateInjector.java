package by.minilooth.telegrambot.bot.state.injector;

import javax.annotation.PostConstruct;

import by.minilooth.telegrambot.bot.message.client.ClientMessageService;
import by.minilooth.telegrambot.service.ClientService;
import by.minilooth.telegrambot.service.PracticeAnswerService;
import by.minilooth.telegrambot.service.PracticeService;
import by.minilooth.telegrambot.service.TopicService;
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

    @Autowired
    private TopicService topicService;
    @Autowired
    private PracticeService practiceService;
    @Autowired
    private PracticeAnswerService practiceAnswerService;

    @PostConstruct
    @Override
    public void inject() {
        ClientBotState.setClientMessageService(clientMessageService);
        ClientBotState.setClientService(clientService);
        ClientBotState.setTopicService(topicService);
        ClientBotState.setPracticeAnswerService(practiceAnswerService);
        ClientBotState.setPracticeService(practiceService);
    }

}
