package by.minilooth.telegrambot.bot.state.injector;

import by.minilooth.telegrambot.bot.api.BotStateInjector;
import by.minilooth.telegrambot.bot.context.client.ClientBotContext;
import by.minilooth.telegrambot.bot.message.admin.AdminMessageService;
import by.minilooth.telegrambot.bot.message.client.ClientMessageService;
import by.minilooth.telegrambot.bot.state.admin.AdminBotState;
import by.minilooth.telegrambot.bot.state.client.ClientBotState;
import by.minilooth.telegrambot.model.Practice;
import by.minilooth.telegrambot.model.PracticeAnswer;
import by.minilooth.telegrambot.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
public class AdminBotStateInjector implements BotStateInjector<AdminBotState, AdminBotStateInjector> {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminMessageService adminMessageService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private TheoryService theoryService;

    @Autowired
    private PracticeService practiceService;

    @Autowired
    private PracticeAnswerService practiceAnswerService;

    @PostConstruct
    @Override
    public void inject() {
        AdminBotState.setAdminMessageService(adminMessageService);
//        AdminBotState.setAdminService(adminService);
        AdminBotState.setTopicService(topicService);
        AdminBotState.setTheoryService(theoryService);
        AdminBotState.setPracticeService(practiceService);
        AdminBotState.setPracticeAnswerService(practiceAnswerService);
    }

}
