package org.example.service.logic;

import org.example.db.Registration;
import org.example.db.RegistrationsRepository;
import org.example.statemachine.State;
import org.example.statemachine.TransmittedData;
import org.example.util.NumberUtil;
import org.example.util.buttons.InlineButtonStorage;
import org.example.util.buttons.InlineKeyboardsStorage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class RegisterLogic {

    private RegistrationsRepository registrationsRepository;

    public RegisterLogic(){
        registrationsRepository = new RegistrationsRepository();
    }

    SendMessage processWaitingCommandStart(String textFromUsers, TransmittedData transmittedData) throws Exception{
        SendMessage messageToUsers = new SendMessage();
        messageToUsers.setChatId(transmittedData.getChatId());

        if (textFromUsers.equals("/start") == false){
            messageToUsers.setText("Что-то пошло не так, нажмите /start");
            return messageToUsers;
        }

        messageToUsers.setText("Начало регестрации. Введит название " +
                "комадны до 30 символов");
        transmittedData.setState(State.WaitingInputName);

        return messageToUsers;
    }
    SendMessage processWaitingInputName(String textFromUsers, TransmittedData transmittedData) throws Exception {
        SendMessage messageToUsers = new SendMessage();
        messageToUsers.setChatId(transmittedData.getChatId());

        if (textFromUsers.length() > 30){
            messageToUsers.setText("Название слишком длинное, ввидете название кароче.");
            return messageToUsers;
        }

        transmittedData.getDataStorage().add("Team Name", textFromUsers);// сохранить

        messageToUsers.setText("Всё четко");
        transmittedData.setState(State.WaitingInputNumberOfParticipants);

        return messageToUsers;
    }
    SendMessage processWaitingInputNumberOfParticipants(String textFromUsers, TransmittedData transmittedData) throws Exception {
        SendMessage messageToUsers = new SendMessage();
        messageToUsers.setChatId(transmittedData.getChatId());

        if (NumberUtil.isIntNumber(textFromUsers)== false){
            messageToUsers.setText("Вводи числа)");
            return messageToUsers;
        }

        int numberOfParticipants = NumberUtil.StringInInteger(textFromUsers);

        if (NumberUtil.isNumberInRange(numberOfParticipants, 1, 4) == false){
            messageToUsers.setText("Вводи числа от 1 до 4)");
            return messageToUsers;
        }

        transmittedData.getDataStorage().add("numberOfParticipants", numberOfParticipants);// сохранить

        messageToUsers.setText("Количество членов команды успешно записано. " +
                "Теперь номер выбранной задачи.\nЗадача" +
                " №1 - Разработать чат бота\nЗадача №2 -" +
                " Разработать мобильное приложение\nЗадача №3 - Разработать сайт");

        transmittedData.setState(State.WaitingInputSelectedTask);


        return messageToUsers;
    }
    SendMessage processWaitingInputSelectedTask(String textFromUsers, TransmittedData transmittedData) throws Exception {
        SendMessage messageToUsers = new SendMessage();
        messageToUsers.setChatId(transmittedData.getChatId());

        if (NumberUtil.isIntNumber(textFromUsers) == false){
            messageToUsers.setText("Вводи число");
            return messageToUsers;
        }

        int inputSelectedTask = NumberUtil.StringInInteger(textFromUsers);

        if (NumberUtil.isNumberInRange(inputSelectedTask, 1, 3) == false){
            messageToUsers.setText("Вводи числа от 1 до 3");
            return messageToUsers;
        }

        transmittedData.getDataStorage().add("inputSelectedTask", inputSelectedTask);

        String teamName = (String) transmittedData.getDataStorage().get("teamName");
        int numberOfParticipants = (int) transmittedData.getDataStorage().get("numberOfParticipants");

        messageToUsers.setText(String.format("Выбранная задача успешно записана. " +
                "Теперь проверьте корретность введённых данных и нажмите кнопку подтверждения или " +
                "отмены.\nНазвание команды: %s\nКоличество членов в команде: %d\nНомер выбранной " +
                "задачи: %d", teamName, numberOfParticipants, inputSelectedTask));

        messageToUsers.setReplyMarkup(InlineKeyboardsStorage.RegisterKeyboard());

        transmittedData.setState(State.WaitingApproveData);
        return messageToUsers;
    }
    SendMessage processWaitingApproveData(String textFromUsers, TransmittedData transmittedData) throws Exception {
        SendMessage messageToUsers = new SendMessage();
        messageToUsers.setChatId(transmittedData.getChatId());

        if (textFromUsers.equals(InlineButtonStorage.ApproveRegister.getCallBAckData()) == false
        && textFromUsers.equals(InlineButtonStorage.DisapproveRegister.getCallBAckData()) == false){
            messageToUsers.setText("Нажмите на кнопку");
            return messageToUsers;
        }

        if (textFromUsers.equals(InlineButtonStorage.ApproveRegister.getCallBAckData()) == true) {
            String teamName = (String) transmittedData.getDataStorage().get("teamName");
            int numberOfParticipants = (int) transmittedData.getDataStorage().get("numberOfParticipants");
            int selectedTask = (int) transmittedData.getDataStorage().get("selectedTask");

            Registration registration = new Registration(0, teamName, numberOfParticipants, selectedTask);

            registrationsRepository.addNew(registration);

            transmittedData.getDataStorage().clear();

            messageToUsers.setText("Данные о решестарции сохранены.");
        } else if (textFromUsers.equals(InlineButtonStorage.DisapproveRegister.getCallBAckData()) == true){
            transmittedData.getDataStorage().clear();

            messageToUsers.setText("Регестрация отменена.");
        }

        transmittedData.setState(State.WaitingCommandStart);

        return messageToUsers;
    }
}
