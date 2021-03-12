package by.minilooth.telegrambot.bot.api;

public interface BotState<E extends Enum<E>, T> {
    
    public <X extends Exception> void handleText(T botContext) throws X;
    public <X extends Exception> void handleCallbackQuery(T botContext) throws X;
    public <X extends Exception> void handleContact(T botContext) throws X;
    public <X extends Exception> void handlePhoto(T botContext) throws X;
    public <X extends Exception> void handleVoice(T botContext) throws X;
    public <X extends Exception> void handleVideo(T botContext) throws X;
    public <X extends Exception> void handleVideoNote(T botContext) throws X;
    public <X extends Exception> void handleDocument(T botContext) throws X;

    public abstract <X extends Exception> void enter(T botState) throws X;
    public abstract E nextState();
    public abstract E rootState();

}
