package org.example.engine;

import org.example.statemachine.ChatsRouter;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.glassfish.grizzly.ProcessorExecutor.execute;

public class TelegramBotHandler extends TelegramLongPollingBot {
    private String botUsername;
    private String botToken;
    private ChatsRouter chatsRouter;


    public TelegramBotHandler() {
        chatsRouter = new ChatsRouter();

        botUsername = "first33457567665bot";
        botToken = "6718011164:AAGSu3WnsgB9xMX5TyA6SV7Zazz5cBn0_ik";
    }

    @Override
    public void onUpdateReceived(Update update) {
        long chatId = 0;
        int messageId = 0;
        String textFromUsers = "";

        try {
            if (update.hasMessage()) {
                chatId = update.getMessage().getChatId();
                messageId = update.getMessage().getMessageId();
                textFromUsers = update.getMessage().getText();
            } else if (update.hasCallbackQuery()) {
                chatId = update.getCallbackQuery().getMessage().getChatId();
                messageId = update.getCallbackQuery().getMessage().getMessageId();
                textFromUsers = update.getCallbackQuery().getData();
            }
            SendMessage messageToUsers = chatsRouter.route(chatId, textFromUsers);

            execute(messageToUsers);
        } catch (Exception e) {
            e.printStackTrace();

            DeleteMessage deleteMessageToUsers = new DeleteMessage();
            deleteMessageToUsers.setChatId(chatId);
            deleteMessageToUsers.setMessageId(messageId);

            try {
                execute(deleteMessageToUsers);
            } catch (TelegramApiException ee) {
                ee.printStackTrace();
            }

        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
