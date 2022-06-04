package by.minilooth.telegrambot.bot.state.client;

import by.minilooth.telegrambot.bot.api.BotState;
import by.minilooth.telegrambot.bot.context.client.ClientBotContext;
import by.minilooth.telegrambot.bot.keyboard.InlineKeyboardMarkupSource;
import by.minilooth.telegrambot.bot.message.client.ClientMessageService;
import by.minilooth.telegrambot.exception.ClientBotStateException;
import by.minilooth.telegrambot.model.Client;
import by.minilooth.telegrambot.model.Practice;
import by.minilooth.telegrambot.model.PracticeAnswer;
import by.minilooth.telegrambot.model.Topic;
import by.minilooth.telegrambot.service.*;
import com.vdurmont.emoji.EmojiParser;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public enum ClientBotState implements BotState<ClientBotState, ClientBotContext> {
    Start(false) {
        @Override
        public void enter(ClientBotContext botContext) {
            clientMessageService.sendStartMessage(botContext);
        }

        @Override
        public ClientBotState nextState() {
            return EnterFirstName;
        }

        @Override
        public ClientBotState rootState() {
            return Start;
        }

    },

    MainMenu(true) {
        private ClientBotState nextState = null;

        @Override
        public void enter(ClientBotContext botContext) {
            clientMessageService.sendMainMenu(botContext);
        }

        @Override
        public void handleText(ClientBotContext botContext) {
            switch (EmojiParser.removeAllEmojis(botContext.getUpdate().getMessage().getText())) {
                case "Список тем":
                    nextState = GetTopicList;
                    break;
                default:
                    nextState = MainMenu;
                    break;
            }
        }

        @Override
        public ClientBotState nextState() {
            return nextState;
        }

        @Override
        public ClientBotState rootState() {
            return MainMenu;
        }
    },

    GetTopicList(true) {
        private ClientBotState nextState = null;

        @Override
        public void enter(ClientBotContext botContext) {
            clientMessageService.sendTopicList(botContext);
        }

        @Override
        public void handleCallbackQuery(ClientBotContext botContext) {
            String callbackData = botContext.getUpdate().getCallbackQuery().getData();
            Integer countOfTopics = topicService.getAll().size();
            Integer page = botContext.getClient().getUser().getCurrentPage();

            if (callbackData.equals("callback.previous")) {
                if (page > 1) {
                    page--;
                } else {
                    page = (int) Math.ceil((float) countOfTopics / (float) InlineKeyboardMarkupSource.ITEMS_PER_PAGE);
                }

                botContext.getClient().getUser().setCurrentPage(page);
                nextState = GetTopicList;
            } else if (callbackData.equals("callback.next")) {
                if (page * InlineKeyboardMarkupSource.ITEMS_PER_PAGE < countOfTopics) {
                    page++;
                } else {
                    page = 1;
                }

                botContext.getClient().getUser().setCurrentPage(page);
                nextState = GetTopicList;
            } else {
                botContext.getClient().getUser().setCurrentPage(UserService.DEFAULT_PAGE);
                try {
                    Topic topic = topicService.getTopicById(Long.parseLong(callbackData));
                    if (topic != null) {
                        Client client = botContext.getClient();
                        client.setCurrentTopic(topic);
                        clientService.save(client);
                        nextState = GetTheory;
                    } else {
                        log.error("Topic is null");
                        nextState = MainMenu;
                    }
                } catch (NumberFormatException ex) {
                    log.error("Error: " + ex.getMessage());
                    nextState = MainMenu;
                }
            }

        }

        @Override
        public ClientBotState nextState() {
            return nextState;
        }

        @Override
        public ClientBotState rootState() {
            return MainMenu;
        }
    },

    GetTheory(true) {
        private ClientBotState nextState = null;

        @Override
        public void enter(ClientBotContext botContext) {
            clientMessageService.sendTheoryMessage(botContext);
        }

        @Override
        public void handleText(ClientBotContext botContext) {
            switch (EmojiParser.removeAllEmojis(botContext.getUpdate().getMessage().getText())) {
                case "Назад к списку тем":
                    nextState = GetTopicList;
                    break;
                case "Проверь себя":
                    nextState = AskQuestion;
                    break;
                default:
                    nextState = MainMenu;
                    break;
            }
        }

        @Override
        public ClientBotState nextState() {
            return nextState;
        }

        @Override
        public ClientBotState rootState() {
            return MainMenu;
        }
    },

    AskQuestion(true) {
        ClientBotState nextState = null;

        @Override
        public void enter(ClientBotContext botContext) {
            clientMessageService.sendFirstQuestionMessage(botContext);
        }

        @Override
        public void handleText(ClientBotContext botContext) {
            switch (EmojiParser.removeAllEmojis(botContext.getUpdate().getMessage().getText())) {
                case "Назад к списку тем":
                    nextState = GetTopicList;
                    break;
                case "Проверь себя":
                    nextState = AskQuestion;
                    break;
                default:
                    nextState = MainMenu;
                    break;
            }
        }

        @Override
        public void handleCallbackQuery(ClientBotContext botContext) {
            String userAnswer = botContext.getUpdate().getCallbackQuery().getData();
            Topic topic = botContext.getClient().getCurrentTopic();
            List<Practice> practices = practiceService.getAllByTopic(topic);
            Client client = botContext.getClient();
            Practice practice = botContext.getClient().getCurrentPractice();
            PracticeAnswer answer = null;
            if (practice != null) {
                answer = practiceAnswerService.getAnswerById(Long.parseLong(userAnswer));

                if (answer != null) {
                    practiceAnswerService.addAnswerToClient(client, answer);
                    if (client.getNumberQuestion() < practices.size()) {
                        int currentQuestionNumber = client.getNumberQuestion();
                        currentQuestionNumber++;
                        if (currentQuestionNumber < practices.size()) {
                            client.setNumberQuestion(currentQuestionNumber);
                            client.setCurrentPractice(practices.get(currentQuestionNumber));
                            clientService.save(client);
                            nextState = AskQuestion;
                        } else {
                            nextState = ResultTest;
                        }
                    } else {
                        nextState = ResultTest;
                    }
                }
            } else {
                if (client.getNumberQuestion() <= (practices.size() - 1)) {
                    int currentQuestionNumber = client.getNumberQuestion();
                    client.setNumberQuestion(currentQuestionNumber);
                    client.setCurrentPractice(practices.get(currentQuestionNumber));
                    clientService.save(client);
                    nextState = AskQuestion;
                } else {
                    nextState = ResultTest;
                }
            }
        }

        @Override
        public ClientBotState nextState() {
            return nextState;
        }

        @Override
        public ClientBotState rootState() {
            return Start;
        }
    },

    ResultTest(true) {
        ClientBotState nextState = null;

        @Override
        public void enter(ClientBotContext botContext) {
            clientMessageService.sendResultTest(botContext);
            botContext.getClient().setCurrentPractice(null);
            botContext.getClient().setNumberQuestion(0);
        }

        @Override
        public void handleText(ClientBotContext botContext) {
            switch (EmojiParser.removeAllEmojis(botContext.getUpdate().getMessage().getText())) {
                case "Список тем":
                    nextState = GetTopicList;
                    break;
                default:
                    nextState = MainMenu;
                    break;
            }
        }

        @Override
        public ClientBotState nextState() {
            return nextState;
        }

        @Override
        public ClientBotState rootState() {
            return Start;
        }
    },

    EnterFirstName(true) {
        private ClientBotState nextState = null;

        @Override
        public void enter(ClientBotContext botContext) {
            clientMessageService.sendFirstNameMessage(botContext);
        }

        @Override
        public void handleText(ClientBotContext botContext) {
            String inputText = botContext.getUpdate().getMessage().getText();
            Client client = botContext.getClient();
            client.setFirstName(inputText);
            clientService.save(client);
        }

        @Override
        public ClientBotState nextState() {
            return EnterLastName;
        }

        @Override
        public ClientBotState rootState() {
            return MainMenu;
        }
    },

    EnterLastName(true) {
        private ClientBotState nextState = null;

        @Override
        public void enter(ClientBotContext botContext) {
            clientMessageService.sendLastNameMessage(botContext);
        }

        @Override
        public void handleText(ClientBotContext botContext) {
            String inputText = botContext.getUpdate().getMessage().getText();
            Client client = botContext.getClient();
            client.setLastName(inputText);
            clientService.save(client);
        }

        @Override
        public ClientBotState nextState() {
            return MainMenu;
        }

        @Override
        public ClientBotState rootState() {
            return MainMenu;
        }
    },

    EnterPatronymic(true) {
        private ClientBotState nextState = null;

        @Override
        public void enter(ClientBotContext botContext) {

        }

        @Override
        public void handleText(ClientBotContext botContext) {

        }

        @Override
        public ClientBotState nextState() {
            return nextState;
        }

        @Override
        public ClientBotState rootState() {
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
    public void handleText(ClientBotContext clientBotContext) {
    }

    @Override
    public void handleCallbackQuery(ClientBotContext clientBotContext) {
    }

    @Override
    public void handleContact(ClientBotContext clientBotContext) throws ClientBotStateException {
    }

    @Override
    public void handlePhoto(ClientBotContext clientBotContext) throws ClientBotStateException {
    }

    @Override
    public void handleVoice(ClientBotContext clientBotContext) throws ClientBotStateException {
    }

    @Override
    public void handleVideo(ClientBotContext clientBotContext) throws ClientBotStateException {
    }

    @Override
    public void handleVideoNote(ClientBotContext clientBotContext) throws ClientBotStateException {
    }

    @Override
    public void handleDocument(ClientBotContext clientBotContext) throws ClientBotStateException {
    }

    @Override
    public abstract void enter(ClientBotContext clientBotContext) throws ClientBotStateException;

    @Override
    public abstract ClientBotState nextState();

    @Override
    public abstract ClientBotState rootState();

    @Setter
    private static ClientMessageService clientMessageService;

    @Setter
    private static ClientService clientService;

    @Setter
    private static TopicService topicService;

    @Setter
    private static PracticeService practiceService;

    @Setter
    private static PracticeAnswerService practiceAnswerService;

}
