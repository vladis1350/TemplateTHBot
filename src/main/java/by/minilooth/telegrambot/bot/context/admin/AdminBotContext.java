package by.minilooth.telegrambot.bot.context.admin;

import by.minilooth.telegrambot.model.Admin;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
public class AdminBotContext {

    private final Admin admin;
    private final Update update;

    private AdminBotContext(Admin admin, Update update) {
        this.admin = admin;
        this.update = update;
    }

    public static AdminBotContext of(Admin admin, Update update) {
        return new AdminBotContext(admin, update);
    }
    
}
