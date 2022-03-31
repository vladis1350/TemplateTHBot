package by.minilooth.telegrambot.bot.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import by.minilooth.telegrambot.model.User;
import by.minilooth.telegrambot.service.UserService;

@Service
public class MessageService {
    
    @Autowired private UserService userService;

    protected void updateLastBotMessageId(User user, Message message) {
        user.setBotLastMessageId(message.getMessageId());
        user.setBotLastMessageDate(message.getDate());
        user.setBotLastMessageEditable(message.getReplyMarkup() != null);
        userService.save(user);
    }

}
