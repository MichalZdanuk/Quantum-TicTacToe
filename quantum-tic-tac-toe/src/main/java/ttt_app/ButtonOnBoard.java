package ttt_app;

import javax.swing.JButton;

public class ButtonOnBoard {
    private JButton jButton;
    private String text = "";

    ButtonOnBoard(JButton jButton, String text) {
        this.jButton = jButton;
        this.text = text;
    }

    public void addText(String addedText) {
        text += addedText;
        jButton.setText(text);
    }

    public String getText() {
        return text;
    }

    public JButton getJButton() {
        return jButton;
    }
}