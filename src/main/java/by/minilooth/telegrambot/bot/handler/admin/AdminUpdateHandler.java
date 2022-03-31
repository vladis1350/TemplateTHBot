package by.minilooth.telegrambot.bot.handler.admin;

import by.minilooth.telegrambot.bot.api.UpdateHandler;
import by.minilooth.telegrambot.bot.context.admin.AdminBotContext;
import by.minilooth.telegrambot.bot.state.admin.AdminBotState;
import by.minilooth.telegrambot.exception.AdminBotStateException;
import by.minilooth.telegrambot.exception.UserNotFoundException;
import by.minilooth.telegrambot.model.Admin;
import by.minilooth.telegrambot.model.User;
import by.minilooth.telegrambot.service.AdminService;
import by.minilooth.telegrambot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class AdminUpdateHandler extends UpdateHandler {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminUpdateHandler.class);

    @Autowired private UserService userService;
    @Autowired private AdminService adminService;

    private void updateState(User user, AdminBotState adminBotState) {
        if (user != null && user.getClient() != null && adminBotState != null) {
            user.getAdmin().setAdminBotState(adminBotState);
            userService.save(user);
        }
    }

    @Override
    public void processText(Update update) throws AdminBotStateException, UserNotFoundException {
        final String chatId = update.getMessage().getChatId().toString();
        AdminBotContext botContext = null;
        AdminBotState botState = null;

        User user = userService.getByTelegramId(chatId);
        Admin admin = user.getAdmin();

        try {
            if (admin == null) {
                admin = adminService.createAdmin(user);

                botContext = AdminBotContext.of(admin, update);
                botState = admin.getAdminBotState();

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
                botContext = AdminBotContext.of(admin, update);
                botState = admin.getAdminBotState();

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
        catch (AdminBotStateException ex) {
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
    public void processCallbackQuery(Update update) {
        // TODO Auto-generated method stub

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
