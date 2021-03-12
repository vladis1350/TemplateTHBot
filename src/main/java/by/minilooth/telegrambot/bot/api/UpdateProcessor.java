package by.minilooth.telegrambot.bot.api;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateProcessor {
    
    public <E extends Exception> void processText(Update update) throws E;
    public <E extends Exception> void processContact(Update update) throws E;
    public <E extends Exception> void processPhoto(Update update) throws E;
    public <E extends Exception> void processCallbackQuery(Update update) throws E;
    public <E extends Exception> void processVoice(Update update) throws E;
    public <E extends Exception> void processVideo(Update update) throws E;
    public <E extends Exception> void processVideoNote(Update update) throws E;
    public <E extends Exception> void processDocument(Update update) throws E;
    
}
