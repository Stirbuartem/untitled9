package org.example.util.buttons;

public class InlineButton {
    private String title; // что написано на кнопке
    private String callBAckData; // какой будет текс при нажатии на кнопку

    public InlineButton(String title, String callBAckData) {
        this.title = title;
        this.callBAckData = callBAckData;
    }

    public String getTitle() {
        return title;
    }

    public String getCallBAckData() {
        return callBAckData;
    }
}
