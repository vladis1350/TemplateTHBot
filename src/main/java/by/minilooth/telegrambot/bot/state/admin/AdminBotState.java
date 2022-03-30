package by.minilooth.telegrambot.bot.state.admin;

import by.minilooth.telegrambot.bot.api.BotState;
import by.minilooth.telegrambot.bot.context.admin.AdminBotContext;
import by.minilooth.telegrambot.bot.message.admin.AdminMessageService;
import by.minilooth.telegrambot.exception.AdminBotStateException;
import by.minilooth.telegrambot.model.Topic;
import by.minilooth.telegrambot.service.PracticeAnswerService;
import by.minilooth.telegrambot.service.PracticeService;
import by.minilooth.telegrambot.service.TheoryService;
import by.minilooth.telegrambot.service.TopicService;
import com.vdurmont.emoji.EmojiParser;
import lombok.Setter;

public enum AdminBotState implements BotState<AdminBotState, AdminBotContext> {
    Start(false) {
        @Override
        public void enter(AdminBotContext botContext) {
            adminMessageService.sendStartMessage(botContext);
        }

        @Override
        public AdminBotState nextState() {
            return MainMenu;
        }

        @Override
        public AdminBotState rootState() {
            return Start;
        }

    },

    MainMenu(true) {
        AdminBotState adminBotState = null;

        @Override
        public void enter(AdminBotContext botContext) {
            adminMessageService.sendMainMenuMessage(botContext);
        }

        @Override
        public void handleText(AdminBotContext adminBotContext) {
            switch (EmojiParser.removeAllEmojis(adminBotContext.getUpdate().getMessage().getText())) {
                case "Добавить тему":
                    adminBotState = AddNewTopic;
                    break;
                case "Список тем":
                    adminBotState = GetTopicList;
                    break;
                default:
                    adminBotState = MainMenu;
                    break;
            }
        }

        @Override
        public AdminBotState nextState() {
            return adminBotState;
        }

        @Override
        public AdminBotState rootState() {
            return MainMenu;
        }

    },

    AddNewTopic(true) {
        AdminBotState adminBotState = null;

        @Override
        public void enter(AdminBotContext botContext) {
            adminMessageService.sendEnterTopicNameMessage(botContext);
        }

        @Override
        public void handleText(AdminBotContext adminBotContext) {
            String topicName = adminBotContext.getUpdate().getMessage().getText();
            Topic topic = topicService.createTopic(topicName);
            if (topic != null) {
//                adminMessageService.sendTopicSuccessfullyAddedMessage(adminBotContext);
            }
        }

        @Override
        public AdminBotState nextState() {
            return GetTopicList;
        }

        @Override
        public AdminBotState rootState() {
            return MainMenu;
        }

    },

    EnterTheory(true) {
        AdminBotState adminBotState = null;

        @Override
        public void enter(AdminBotContext botContext) {
            adminMessageService.sendEnterTheoryMessage(botContext);
        }

        @Override
        public void handleText(AdminBotContext adminBotContext) {
            String theoryText = adminBotContext.getUpdate().getMessage().getText();
//            Topic topic = topicService.createTopic(topicName);
//            if (topic != null) {
//                adminMessageService.sendTopicSuccessfullyAddedMessage(adminBotContext);
//            }
        }

        @Override
        public AdminBotState nextState() {
            return GetTopicList;
        }

        @Override
        public AdminBotState rootState() {
            return MainMenu;
        }

    },

    GetTopicList(true) {
        AdminBotState adminBotState = null;

        @Override
        public void enter(AdminBotContext botContext) {
            adminMessageService.sendMainMenuMessage(botContext);
        }

        @Override
        public void handleText(AdminBotContext adminBotContext) {

        }

        @Override
        public AdminBotState nextState() {
            return GetTopicList;
        }

        @Override
        public AdminBotState rootState() {
            return Start;
        }

    },

    EnterTopicName(true) {
        @Override
        public void enter(AdminBotContext botContext) {
            adminMessageService.sendStartMessage(botContext);
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

    @Setter
    private static AdminMessageService adminMessageService;

    @Setter
    private static TopicService topicService;

    @Setter
    private static TheoryService theoryService;

    @Setter
    private static PracticeService practiceService;

    @Setter
    private static PracticeAnswerService practiceAnswerService;

//    @Setter
//    private AdminService adminService;

}
