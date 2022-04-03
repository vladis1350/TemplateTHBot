package by.minilooth.telegrambot.bot.state.admin;

import by.minilooth.telegrambot.bot.api.BotState;
import by.minilooth.telegrambot.bot.context.admin.AdminBotContext;
import by.minilooth.telegrambot.bot.keyboard.InlineKeyboardMarkupSource;
import by.minilooth.telegrambot.bot.message.admin.AdminMessageService;
import by.minilooth.telegrambot.exception.AdminBotStateException;
import by.minilooth.telegrambot.model.*;
import by.minilooth.telegrambot.service.*;
import com.vdurmont.emoji.EmojiParser;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Document;

@Slf4j
public enum AdminBotState implements BotState<AdminBotState, AdminBotContext> {

    Start(false) {
        AdminBotState adminBotState = null;

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
            return MainMenu;
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
                case "Результаты":
                    adminBotState = SelectTopicForShowingResult;
                    break;
                case "Удалить тему":
                    adminBotState = SelectTopicForDelete;
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

            Admin admin = adminBotContext.getAdmin();
            Topic topic = topicService.createTopic(topicName);
            if (topic != null) {
                admin.setCurrentTopic(topic);
                adminService.save(admin);
                adminMessageService.sendTopicSuccessfullyAddedMessage(adminBotContext, topic);
            }
        }

        @Override
        public AdminBotState nextState() {
            return EnterTheory;
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
            Admin admin = adminBotContext.getAdmin();
            if (theoryText.equals("Загрузить файл")) {
                adminBotState = LoadTheoryFile;
            } else {
                Theory theory = theoryService.createTheory(admin.getCurrentTopic(), theoryText);
                if (theory != null) {
                    topicService.save(admin.getCurrentTopic());
                    adminBotState = TheorySuccessfullyAdded;
                }
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

    LoadTheoryFile(true) {
        AdminBotState adminBotState = null;

        @Override
        public void enter(AdminBotContext botContext) {
            adminMessageService.sendLoadTheoryFileMessage(botContext);
        }

        @Override
        public void handleText(AdminBotContext adminBotContext) {
            switch (EmojiParser.removeAllEmojis(adminBotContext.getUpdate().getMessage().getText())) {
                case "Отмена":
                    adminBotState = EnterTheory;
                    break;
                default:
                    adminBotState = MainMenu;
                    break;
            }
        }

        @Override
        public void handleDocument(AdminBotContext adminBotContext) {
            Admin admin = adminBotContext.getAdmin();
            Document document = adminBotContext.getUpdate().getMessage().getDocument();
            String caption = adminBotContext.getUpdate().getMessage().getCaption();

            Theory theory = theoryService.createTheory(admin.getCurrentTopic(), document, caption);
            if (theory != null) {
                topicService.save(admin.getCurrentTopic());
                adminBotState = TheorySuccessfullyAdded;
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

    TheorySuccessfullyAdded(true) {
        AdminBotState adminBotState = null;

        @Override
        public void enter(AdminBotContext adminBotContext) {
            adminMessageService.sendTheorySuccessfullyAddedMessage(adminBotContext);
        }

        @Override
        public void handleText(AdminBotContext adminBotContext) {
            switch (EmojiParser.removeAllEmojis(adminBotContext.getUpdate().getMessage().getText())) {
                case "Добавить практику":
                    adminBotState = EnterQuestion;
                    break;
                case "Главное меню":
                    adminBotState = MainMenu;
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

    EnterQuestion(true) {
        AdminBotState adminBotState = null;

        @Override
        public void enter(AdminBotContext adminBotContext) {
            adminMessageService.sendEnterQuestionMessage(adminBotContext);
        }

        @Override
        public void handleText(AdminBotContext adminBotContext) {
            String adminAnswer = adminBotContext.getUpdate().getMessage().getText();
            Admin admin = adminBotContext.getAdmin();
            Practice practice = practiceService.createPractice(admin.getCurrentTopic(), adminAnswer);
            if (practice != null) {
                admin.setCurrentPractice(practice);
                adminService.save(admin);
            }
        }

        @Override
        public AdminBotState nextState() {
            return EnterAnswer;
        }

        @Override
        public AdminBotState rootState() {
            return MainMenu;
        }

    },

    EnterAnswer(true) {
        AdminBotState adminBotState = null;

        @Override
        public void enter(AdminBotContext adminBotContext) {
            adminMessageService.sendEnterAnswerMessage(adminBotContext);
        }

        @Override
        public void handleText(AdminBotContext adminBotContext) {
            String adminAnswer = adminBotContext.getUpdate().getMessage().getText();
            Admin admin = adminBotContext.getAdmin();
            PracticeAnswer answer = practiceAnswerService.createPracticeAnswer(admin.getCurrentPractice(), adminAnswer);
            admin.setCurrentAnswer(answer);
            adminService.save(admin);
        }

        @Override
        public AdminBotState nextState() {
            return AnswerIsCorrect;
        }

        @Override
        public AdminBotState rootState() {
            return MainMenu;
        }

    },

    AnswerIsCorrect(true) {
        AdminBotState adminBotState = null;

        @Override
        public void enter(AdminBotContext adminBotContext) {
            adminMessageService.sendAnswerIsCorrectMessage(adminBotContext);
        }

        @Override
        public void handleCallbackQuery(AdminBotContext adminBotContext) {
            String adminAnswer = adminBotContext.getUpdate().getCallbackQuery().getData();
            Admin admin = adminBotContext.getAdmin();
            PracticeAnswer answer = admin.getCurrentAnswer();
            if (adminAnswer.equals("answerIsCorrect")) {
                answer.setIsCorrect(true);
            } else if (adminAnswer.equals("answerNotCorrect")) {
                answer.setIsCorrect(false);
            }
            practiceAnswerService.save(answer);
        }

        @Override
        public void handleText(AdminBotContext adminBotContext) {
            String adminAnswer = adminBotContext.getUpdate().getMessage().getText();
            Admin admin = adminBotContext.getAdmin();
            practiceAnswerService.createPracticeAnswer(admin.getCurrentPractice(), adminAnswer);
        }

        @Override
        public AdminBotState nextState() {
            return AnswerSuccessfullyAdded;
        }

        @Override
        public AdminBotState rootState() {
            return MainMenu;
        }

    },

    AnswerSuccessfullyAdded(true) {
        AdminBotState adminBotState = null;

        @Override
        public void enter(AdminBotContext adminBotContext) {
            adminMessageService.sendAnswerSuccessfullyAddedMessage(adminBotContext);
        }

        @Override
        public void handleText(AdminBotContext adminBotContext) {
            switch (EmojiParser.removeAllEmojis(adminBotContext.getUpdate().getMessage().getText())) {
                case "Добавить вариант ответа":
                    adminBotState = EnterAnswer;
                    break;
                case "Завершить добавление ответов":
                    adminBotContext.getAdmin().setCurrentAnswer(null);
                    adminBotState = PracticeMenu;
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

    PracticeMenu(true) {
        AdminBotState adminBotState = null;

        @Override
        public void enter(AdminBotContext adminBotContext) {
            adminMessageService.sendSelectProcessMessage(adminBotContext);
        }

        @Override
        public void handleText(AdminBotContext adminBotContext) {
            switch (EmojiParser.removeAllEmojis(adminBotContext.getUpdate().getMessage().getText())) {
                case "Добавить вопрос":
                    adminBotState = EnterQuestion;
                    break;
                case "Главное меню":
                    adminBotContext.getAdmin().setCurrentAnswer(null);
                    adminBotContext.getAdmin().setCurrentPractice(null);
                    adminBotContext.getAdmin().setCurrentTopic(null);
                    adminBotState = MainMenu;
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

    SelectTopicForShowingResult(true) {
        AdminBotState adminBotState = null;

        @Override
        public void enter(AdminBotContext botContext) {
            adminMessageService.sendSelectTopicForShowResultMessage(botContext);
        }

        @Override
        public void handleText(AdminBotContext adminBotContext) {
            adminBotState = MainMenu;
        }

        @Override
        public void handleCallbackQuery(AdminBotContext botContext) {
            String callbackData = botContext.getUpdate().getCallbackQuery().getData();
            Integer countOfTopics = topicService.getAll().size();
            Integer page = botContext.getAdmin().getUser().getCurrentPage();

            if (callbackData.equals("callback.previous")) {
                if (page > 1) {
                    page--;
                } else {
                    page = (int) Math.ceil((float) countOfTopics / (float) InlineKeyboardMarkupSource.ITEMS_PER_PAGE);
                }

                botContext.getAdmin().getUser().setCurrentPage(page);
                adminBotState = SelectTopicForShowingResult;
            } else if (callbackData.equals("callback.next")) {
                if (page * InlineKeyboardMarkupSource.ITEMS_PER_PAGE < countOfTopics) {
                    page++;
                } else {
                    page = 1;
                }

                botContext.getAdmin().getUser().setCurrentPage(page);
                adminBotState = SelectTopicForShowingResult;
            } else {
                botContext.getAdmin().getUser().setCurrentPage(UserService.DEFAULT_PAGE);
                try {
                    Topic topic = topicService.getTopicById(Long.parseLong(callbackData));
                    if (topic != null) {
                        Admin admin = botContext.getAdmin();
                        admin.setCurrentTopic(topic);
                        adminService.save(admin);
                        adminBotState = ResultPractice;
                    } else {
                        adminBotState = MainMenu;
                    }
                } catch (NumberFormatException ex) {
                    log.error("Error in state SelectTopicForShowingResult: " + ex.getMessage());
                    adminBotState = MainMenu;
                }
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

    ResultPractice(true) {
        AdminBotState adminBotState = null;

        @Override
        public void enter(AdminBotContext botContext) {
            adminMessageService.sendResultPracticeMessage(botContext);
        }

        @Override
        public void handleText(AdminBotContext adminBotContext) {
            adminBotState = MainMenu;

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

    GetTopicList(true) {
        AdminBotState adminBotState = null;

        @Override
        public void enter(AdminBotContext botContext) {
            adminMessageService.sendTopicList(botContext);
        }

        @Override
        public void handleCallbackQuery(AdminBotContext adminBotContext) {
            String callbackData = adminBotContext.getUpdate().getCallbackQuery().getData();
            Integer countOfTopics = topicService.getAll().size();
            Integer page = adminBotContext.getAdmin().getUser().getCurrentPage();

            if (callbackData.equals("callback.previous")) {
                if (page > 1) {
                    page--;
                } else {
                    page = (int) Math.ceil((float) countOfTopics / (float) InlineKeyboardMarkupSource.ITEMS_PER_PAGE);
                }

                adminBotContext.getAdmin().getUser().setCurrentPage(page);
                adminBotState = GetTopicList;
            } else if (callbackData.equals("callback.next")) {
                if (page * InlineKeyboardMarkupSource.ITEMS_PER_PAGE < countOfTopics) {
                    page++;
                } else {
                    page = 1;
                }

                adminBotContext.getAdmin().getUser().setCurrentPage(page);
                adminBotState = GetTopicList;
            } else {
                adminBotContext.getAdmin().getUser().setCurrentPage(UserService.DEFAULT_PAGE);
                try {
                    Topic topic = topicService.getTopicById(Long.parseLong(callbackData));
                    if (topic != null) {
                        Admin admin = adminBotContext.getAdmin();
                        admin.setCurrentTopic(topic);
                        adminService.save(admin);
                        adminBotState = GetTheory;
                    } else {
                        log.error("Topic is null");
                        adminBotState = MainMenu;
                    }
                } catch (NumberFormatException ex) {
                    log.error("Error: " + ex.getMessage());
                    adminBotState = MainMenu;
                }
            }
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
                case "Результаты":
                    adminBotState = SelectTopicForShowingResult;
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

    ShowPracticeForSelectedTopic(true) {
        AdminBotState adminBotState = null;

        @Override
        public void enter(AdminBotContext botContext) {
            adminMessageService.sendShowPracticeForSelectedTopicMessage(botContext);
        }

        @Override
        public void handleText(AdminBotContext adminBotContext) {
            adminBotState = MainMenu;
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

    SelectTopicForDelete(true) {
        AdminBotState adminBotState = null;

        @Override
        public void enter(AdminBotContext botContext) {
            adminMessageService.sendSelectTopicForDeleteMessage(botContext);
        }

        @Override
        public void handleText(AdminBotContext adminBotContext) {
            adminBotState = MainMenu;
        }

        @Override
        public void handleCallbackQuery(AdminBotContext botContext) {
            String callbackData = botContext.getUpdate().getCallbackQuery().getData();
            Integer countOfTopics = topicService.getAll().size();
            Integer page = botContext.getAdmin().getUser().getCurrentPage();

            if (callbackData.equals("callback.previous")) {
                if (page > 1) {
                    page--;
                } else {
                    page = (int) Math.ceil((float) countOfTopics / (float) InlineKeyboardMarkupSource.ITEMS_PER_PAGE);
                }

                botContext.getAdmin().getUser().setCurrentPage(page);
                adminBotState = SelectTopicForDelete;
            } else if (callbackData.equals("callback.next")) {
                if (page * InlineKeyboardMarkupSource.ITEMS_PER_PAGE < countOfTopics) {
                    page++;
                } else {
                    page = 1;
                }

                botContext.getAdmin().getUser().setCurrentPage(page);
                adminBotState = SelectTopicForDelete;
            } else {
                botContext.getAdmin().getUser().setCurrentPage(UserService.DEFAULT_PAGE);
                try {
                    Topic topic = topicService.getTopicById(Long.parseLong(callbackData));
                    if (topic != null) {
                        Admin admin = botContext.getAdmin();
                        admin.setCurrentTopic(topic);
                        adminService.save(admin);
                        adminBotState = ConfirmationDeleteTopic;
                    } else {
                        adminBotState = MainMenu;
                    }
                } catch (NumberFormatException ex) {
                    log.error("Error in state SelectTopicForShowingResult: " + ex.getMessage());
                    adminBotState = MainMenu;
                }
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

    ConfirmationDeleteTopic(true) {
        AdminBotState adminBotState = null;

        @Override
        public void enter(AdminBotContext adminBotContext) {
            adminMessageService.sendConfirmationDeleteTopicMessage(adminBotContext);
        }

        @Override
        public void handleCallbackQuery(AdminBotContext adminBotContext) {
            String adminAnswer = adminBotContext.getUpdate().getCallbackQuery().getData();
            Admin admin = adminBotContext.getAdmin();
            Topic topic = admin.getCurrentTopic();
            if (adminAnswer.equals("confirm")) {
                admin.setCurrentTopic(null);
                theoryService.deleteTheoryByTopic(topic);
                practiceService.deletePracticesByTopic(topic);
                topicService.delete(topic);
                adminBotState = TopicSuccessfullyDeleted;
            } else if (adminAnswer.equals("notConfirm")) {
                adminBotState = TopicCanceledDeleting;
            }
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
                case "Результаты":
                    adminBotState = SelectTopicForShowingResult;
                    break;
                case "Удалить тему":
                    adminBotState = SelectTopicForDelete;
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

    TopicCanceledDeleting(false) {
        AdminBotState adminBotState = null;

        @Override
        public void enter(AdminBotContext botContext) {
            adminMessageService.sendTopicCanceledDeletingMessage(botContext);
        }

        @Override
        public AdminBotState nextState() {
            return MainMenu;
        }

        @Override
        public AdminBotState rootState() {
            return MainMenu;
        }

    },

    TopicSuccessfullyDeleted(false) {
        AdminBotState adminBotState = null;

        @Override
        public void enter(AdminBotContext botContext) {
            adminMessageService.sendTopicSuccessfullyDeletedMessage(botContext);
        }

        @Override
        public AdminBotState nextState() {
            return MainMenu;
        }

        @Override
        public AdminBotState rootState() {
            return MainMenu;
        }
    },

    GetTheory(true) {
        AdminBotState adminBotState = null;

        @Override
        public void enter(AdminBotContext botContext) {
            adminMessageService.sendTheoryMessage(botContext);
        }

        @Override
        public void handleText(AdminBotContext adminBotContext) {
            switch (EmojiParser.removeAllEmojis(adminBotContext.getUpdate().getMessage().getText())) {
                case "Практика":
                    adminBotState = ShowPracticeForSelectedTopic;
                    break;
                case "Назад к списку тем":
                    adminBotContext.getAdmin().setCurrentTopic(null);
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
    public void handleCallbackQuery(AdminBotContext adminBotContext) {
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
    public void handleDocument(AdminBotContext adminBotContext) {
    }

    @Override
    public abstract void enter(AdminBotContext adminBotContext);

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

    @Setter
    private static AdminService adminService;

}
