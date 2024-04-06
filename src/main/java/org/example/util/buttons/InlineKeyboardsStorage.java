package org.example.util.buttons;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class InlineKeyboardsStorage {
    public static InlineKeyboardMarkup RegisterKeyboard(){
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        List<InlineKeyboardButton> row = null;
        InlineKeyboardButton button = null;

        row = new ArrayList<>();// созадл арей лист
        button = new InlineKeyboardButton();// создал кнопку InlineKeyboardButton - класс тг
        button.setText(InlineButtonStorage.DisapproveRegister.getTitle());// залез в хранилище, чтобы достать что написано
        button.setCallbackData(InlineButtonStorage.DisapproveRegister.getCallBAckData());
        row.add(button);// добавили кпонку в элемент row - это элемент
        keyboard.add(row);// добавили кнопку в хранилище

        row = new ArrayList<>();
        button = new InlineKeyboardButton();
        button.setText(InlineButtonStorage.ApproveRegister.getTitle());
        button.setCallbackData(InlineButtonStorage.ApproveRegister.getCallBAckData());
        row.add(button);
        keyboard.add(row);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(); // разметка для тг
        inlineKeyboardMarkup.setKeyboard(keyboard);

        return inlineKeyboardMarkup;

    }
}
