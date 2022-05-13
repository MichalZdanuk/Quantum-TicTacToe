package ttt_app;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GUI extends WindowAdapter implements ActionListener {

    private static Board gameBoard;
    private static JFrame frame;
    private static ArrayList<JButton> buttonList;
    private static ArrayList<JButton> buttonListInner;
    private String number;
    private static JLabel infoLabel = new JLabel("Player X Turn", SwingConstants.CENTER);
    private JLabel emptyLabel = new JLabel();
    private static Clip clip;
    private static Clip clickClip;
    private static Clip entanglementClip;

    private JPanel panel = new JPanel(new GridLayout(3, 3, 1, 1));

    private int chosenEntangledTile;
    private String chosenMark = "";
    private static String bigMark = "";

    private JMenuBar menuBar;
    private JMenu game, mode, theme, help;
    private JMenuItem restart, single, multi, blue, red, green, orange, rules;

    private static Color foreGround = new Color(255, 255, 255);
    private static Color backGround = new Color(0, 59, 54);
    private Color backGroundSecond = new Color(0, 102, 94);
    private static Color backGroundEntangled = new Color(0, 204, 187);
    private static Color backGroundColapsed = new Color(0, 71, 0);

    private static Bot bot;
    private static String gameMode = "multi";

    GUI(Board givenBoard) {
        restart = new JMenuItem("restart");
        single = new JMenuItem("single");
        multi = new JMenuItem("multi");
        blue = new JMenuItem("blue");
        red = new JMenuItem("red");
        green = new JMenuItem("green");
        orange = new JMenuItem("orange");
        rules = new JMenuItem("rules");

        restart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });

        single.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bot = new Bot();
                gameMode = "single";
                restartGame();
            }
        });

        multi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameMode = "multi";
                restartGame();
            }
        });

        red.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                foreGround = new Color(255, 255, 255);
                backGround = new Color(255, 0, 0);
                backGroundSecond = new Color(179, 0, 0);
                backGroundEntangled = new Color(255, 191, 191);
                backGroundColapsed = new Color(255, 128, 128);
                setThemeColor(foreGround, backGround, backGroundSecond, backGroundEntangled, backGroundColapsed);
            }
        });

        blue.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                foreGround = new Color(255, 255, 255);
                backGround = new Color(20, 93, 160);
                backGroundSecond = new Color(12, 45, 72);
                backGroundEntangled = new Color(46, 139, 192);
                backGroundColapsed = new Color(177, 212, 224);
                setThemeColor(foreGround, backGround, backGroundSecond, backGroundEntangled, backGroundColapsed);
            }
        });

        green.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                foreGround = new Color(0, 0, 0);
                backGround = new Color(0, 59, 54);
                backGroundSecond = new Color(0, 102, 94);
                backGroundEntangled = new Color(0, 204, 187);
                backGroundColapsed = new Color(0, 71, 0);
                setThemeColor(foreGround, backGround, backGroundSecond, backGroundEntangled, backGroundColapsed);
            }
        });

        orange.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                foreGround = new Color(0, 0, 0);
                backGround = new Color(255, 148, 0);
                backGroundSecond = new Color(179, 104, 0);
                backGroundEntangled = new Color(255, 250, 0);
                backGroundColapsed = new Color(179, 139, 0);
                setThemeColor(foreGround, backGround, backGroundSecond, backGroundEntangled, backGroundColapsed);
            }
        });

        rules.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame,
                        "Welcome to game Quantum Tic-Tac-Toe!\nThis is a bit more advanced version of classical tic-tac-toe game\nRules:\n1. Each round one player put small two marks, which are numbered same as round \ne.g. It's first round (time for player X), so he'll put two x1 marks\n2. You can't place two small marks during round in the same tile.\n3. When after one player's move occures entanglement then second player \nchoose tile and mark to be collapsed here.\n4. Collapsed tiles with chosen mark evolve to big marks.\n5. To win a game you have to place three big marks in horizontal/vertical/diagonal line \n(same as classical tic-tac-toe).\nGood luck!!!",
                        "Game Rules", JOptionPane.INFORMATION_MESSAGE);
                ;
            }
        });

        menuBar = new JMenuBar();
        game = new JMenu("GAME");
        mode = new JMenu("MODE");
        theme = new JMenu("THEME");
        help = new JMenu("HELP");
        game.add(restart);
        mode.add(single);
        mode.add(multi);
        theme.add(blue);
        theme.add(red);
        theme.add(green);
        theme.add(orange);
        help.add(rules);
        menuBar.add(game);
        menuBar.add(mode);
        menuBar.add(theme);
        menuBar.add(help);

        frame = new JFrame("Tic-Tac-Toe");
        frame.add(menuBar);
        frame.setJMenuBar(menuBar);
        frame.addWindowListener(this);
        infoLabel.setForeground(foreGround);
        frame.getContentPane().setBackground(backGroundSecond);
        panel.setBackground(backGroundSecond);
        buttonList = new ArrayList<JButton>();
        for (int i = 0; i < 9; i++) {
            buttonList.add(new JButton());
            buttonList.get(i).setBackground(backGround);
            buttonList.get(i).setForeground(foreGround);
            buttonList.get(i).setFont(new Font("", Font.BOLD, 14));
            buttonList.get(i).setFocusable(false);

            buttonList.get(i).addActionListener(this);
            frame.add(buttonList.get(i));
            if (i == 2) {
                frame.add(infoLabel);
            } else if (i == 5) {
                frame.add(panel);
            } else if (i == 8) {
                frame.add(emptyLabel);
            }

        }

        // inner buttons
        buttonListInner = new ArrayList<JButton>();
        for (int i = 0; i < 9; i++) {
            number = Integer.toString(i);
            buttonListInner.add(new JButton(number));
            buttonListInner.get(i).setBackground(backGroundEntangled);
            buttonListInner.get(i).setForeground(foreGround);
            buttonListInner.get(i).setVisible(false);
            buttonListInner.get(i).setFocusable(false);
            buttonListInner.get(i).addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    for (int i = 0; i < 9; i++) {
                        if (e.getSource() == buttonListInner.get(i)) {
                            chosenMark = buttonListInner.get(i).getText();
                            break;
                        }
                    }
                    gameBoard.resolveEntanglement(chosenMark, gameBoard.tileList.get(chosenEntangledTile));

                    colorColapsedTiles();

                    // unlock other tiles
                    for (int i = 0; i < 9; i++) {
                        if (!gameBoard.tileList.get(i).checkIfTileColapsed()) {
                            buttonList.get(i).setEnabled(true);
                        }
                    }

                    hideInnerButtons();

                    infoLabel.setText("Player " + gameBoard.whichPlayerTurn() + " turn");
                    resolvingEntanglementFlag = false;

                    entanglementClip.start();
                    entanglementClip.setMicrosecondPosition(0);
                    try {
                        TimeUnit.MILLISECONDS.sleep(600);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }

            });
            panel.add(buttonListInner.get(i));
        }

        Image icon = Toolkit.getDefaultToolkit().getImage(
                "C:\\Users\\Michal\\Desktop\\SEM_IV\\Projekt_indywidualny\\Quantum-TicTacToe\\quantum-tic-tac-toe\\graphics\\x.png");

        frame.setIconImage(icon);
        frame.setLayout(new GridLayout(3, 4, 5, 5));
        frame.setSize(800, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    ImageIcon closeIcon = new ImageIcon(
            "C:\\Users\\Michal\\Desktop\\SEM_IV\\Projekt_indywidualny\\Quantum-TicTacToe\\quantum-tic-tac-toe\\graphics\\quit.png");

    public void windowClosing(WindowEvent e) {
        int a = JOptionPane.showConfirmDialog(frame, "Are you sure to close game?", "Close game",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, closeIcon);

        if (a == JOptionPane.YES_OPTION) {
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }

    private static void restartGame() {
        gameBoard = new Board();
        mark = 'x';
        numberOfMove = 0;
        clip.loop(6);
        for (int i = 0; i < 9; i++) {
            buttonList.get(i).setText("");
            buttonList.get(i).setEnabled(true);
            buttonList.get(i).setBackground(backGround);
            buttonList.get(i).setForeground(foreGround);
            buttonList.get(i).setFont(new Font("", Font.PLAIN, 14));

            buttonListInner.get(i).setText("");
            buttonListInner.get(i).setEnabled(true);

        }
        infoLabel.setText("Player X Turn");
        hideInnerButtons();
    }

    private void setThemeColor(Color foreGround, Color backGround, Color backGroundSecond, Color backGroundEntangled,
            Color backGroundColapsed) {
        for (int i = 0; i < 9; i++) {
            if (gameBoard.tileList.get(i).checkIfTileColapsed()) {
                buttonList.get(i)
                        .setBackground(backGroundColapsed);
            } else if (gameBoard.tileList.get(i).checkIfTileEntangled()) {
                buttonList.get(i)
                        .setBackground(backGroundEntangled);
            } else {
                buttonList.get(i)
                        .setBackground(backGround);
            }
            buttonList.get(i).setForeground(foreGround);

            buttonListInner.get(i)
                    .setBackground(backGroundEntangled);
            buttonListInner.get(i).setForeground(foreGround);

            panel.setBackground(backGroundSecond);
            frame.getContentPane().setBackground(backGroundSecond);
        }
    }

    private static Character mark = 'x';
    private static int numberOfMove = 0;
    private static String text = "";
    private ArrayList<Integer> chosenTiles = new ArrayList<Integer>(0);
    private static boolean resolvingEntanglementFlag = false;

    public void actionPerformed(ActionEvent e) {
        clickClip.start();
        clickClip.setMicrosecondPosition(0);
        if (!resolvingEntanglementFlag) {
            text = mark + Integer.toString(gameBoard.getRoundNumber());

            for (int i = 0; i < buttonList.size(); i++) {
                if (e.getSource() == buttonList.get(i)) {
                    buttonList.get(i).setText(buttonList.get(i).getText() + " " + text);
                    buttonList.get(i).setEnabled(false);
                    chosenTiles.add(i);
                    numberOfMove++;
                    break;
                }
            }

            if (numberOfMove == 2) {
                gameBoard.makeMove(chosenTiles, "multi");
                for (int i = 0; i < chosenTiles.size(); i++) {
                    buttonList.get(chosenTiles.get(i)).setEnabled(true);
                }
                changeMark();
                gameBoard.changePlayer();
                numberOfMove = 0;

                if (gameBoard.player == false) {
                    infoLabel.setText("Player X Turn");
                    mark = 'x';
                } else if (gameBoard.player == true) {
                    infoLabel.setText("Player O Turn");
                    mark = 'o';
                }
                chosenTiles.clear();

                if (gameMode == "single") {
                    lockAllTiles();
                }
            }

            if (gameBoard.checkIfIsEntanglement()) {
                infoLabel.setText("<html>Entanglement occured: player " + mark + " move</html>");
                lockAllTiles();
                colorEntangledTiles();
                resolvingEntanglementFlag = true;
            }

        } else {
            for (int i = 0; i < buttonList.size(); i++) {
                if (e.getSource() == buttonList.get(i)) {
                    chosenEntangledTile = i;
                    break;
                }
            }
            hideInnerButtons();

            for (int i = 0; i < gameBoard.marksInEntanglementList.size(); i++) {
                for (int j = 0; j < gameBoard.tileList.get(chosenEntangledTile).marklist.size(); j++) {
                    if (gameBoard.marksInEntanglementList.get(i)
                            .markSyntax().equals(gameBoard.tileList.get(chosenEntangledTile).marklist.get(j)
                                    .markSyntax())) {
                        buttonListInner.get(i).setVisible(true);
                        text = gameBoard.marksInEntanglementList.get(i).markSyntax();
                        buttonListInner.get(i).setText(text);
                        break;
                    }
                }
            }

            // block other tile buttons
            for (int i = 0; i < buttonList.size(); i++) {
                if (!gameBoard.tileList.get(i).checkIfTileEntangled()) {
                    buttonList.get(i).setEnabled(false);
                }
            }

        }

    }

    private static void refreshBoard(int botEntngledTile) {
        infoLabel.setText("<html> ENTANGLEMENT: BOT CHOSEN " + botEntngledTile + " TILE </html>");
        bot.delay(1);
    }

    static ArrayList<Integer> botTiles;
    static int botEntngledTile;

    private void changeMark() {
        if (mark == 'x') {
            mark = 'o';
        } else if (mark == 'o') {
            mark = 'x';
        }
    }

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File("backgroundMusic.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        clip = AudioSystem.getClip();
        clip.open(audioStream);

        File fileClick = new File("clickSound.wav");
        AudioInputStream clickStream = AudioSystem.getAudioInputStream(fileClick);
        clickClip = AudioSystem.getClip();
        clickClip.open(clickStream);

        File fileEntanglement = new File("entanglementSound.wav");
        AudioInputStream entanglementStream = AudioSystem.getAudioInputStream(fileEntanglement);
        entanglementClip = AudioSystem.getClip();
        entanglementClip.open(entanglementStream);
        gameBoard = new Board();
        clip.start();
        clip.loop(6);
        new GUI(gameBoard);
        while (true) {
            playGame();
        }

    }

    private static void playGame() {
        while (!gameBoard.checkIfWinner() && !gameBoard.checkIfDraw()) {
            if (gameMode == "single" && mark == 'o') {
                lockAllTiles();
                System.out.println("inside bot");
                if (gameBoard.isEntanglement) {
                    botEntngledTile = bot.botEntangleMove(gameBoard);
                    refreshBoard(botEntngledTile);
                    buttonList.get(botEntngledTile).setBackground(new Color(70, 122, 38));
                    bot.delay(2);
                    colorColapsedTiles();
                    resolvingEntanglementFlag = false;
                    infoLabel.setText("Player O Turn");
                } else if (!gameBoard.isEntanglement) {
                    botTiles = bot.botMove(gameBoard);
                    gameBoard.changePlayer();
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < botTiles.size(); j++) {
                            if (i == botTiles.get(j)) {
                                buttonList.get(i)
                                        .setText(
                                                buttonList.get(i).getText() + " " + "o"
                                                        + (gameBoard.getRoundNumber() - 1));
                            }
                        }
                    }
                    infoLabel.setText("Player X Turn");
                    mark = 'x';
                }

                unlockAllTiles();
                gameBoard.checkIfIsEntanglement();
            } else if (gameMode == "single" && mark == 'x') {
                if (gameBoard.isEntanglement) {
                    lockAllTiles();
                    colorEntangledTiles();
                    gameBoard.isEntanglement = false;
                    resolvingEntanglementFlag = true;
                }
            }
        }

        if (gameBoard.checkIfWinner()) {
            informAboutWin();
        } else if (gameBoard.checkIfDraw()) {
            informAboutDraw();
        }
        restartGame();

    }

    private static void colorEntangledTiles() {
        for (int i = 0; i < gameBoard.entangledTilesList.size(); i++) {
            buttonList.get(gameBoard.entangledTilesList.get(i).getNumberOfTile())
                    .setBackground(backGroundEntangled);
            buttonList.get(gameBoard.entangledTilesList.get(i).getNumberOfTile())
                    .setForeground(foreGround);
            buttonList.get(gameBoard.entangledTilesList.get(i).getNumberOfTile()).setEnabled(true);
            if (gameMode == "single" && mark == 'o') {
                buttonList.get(gameBoard.entangledTilesList.get(i).getNumberOfTile()).setEnabled(false);
            }
            gameBoard.isEntanglement = true;
        }
    }

    private static void colorColapsedTiles() {
        for (int i = 0; i < gameBoard.tileList.size(); i++) {
            if (gameBoard.tileList.get(i).checkIfTileColapsed()) {
                bigMark = gameBoard.tileList.get(i).getBigMark().markSyntax();
                buttonList.get(gameBoard.tileList.get(i).getNumberOfTile()).setText(bigMark);
                buttonList.get(gameBoard.tileList.get(i).getNumberOfTile()).setEnabled(false);
                buttonList.get(gameBoard.tileList.get(i).getNumberOfTile())
                        .setBackground(backGroundColapsed);
                buttonList.get(gameBoard.tileList.get(i).getNumberOfTile())
                        .setForeground(foreGround);
                buttonList.get(gameBoard.tileList.get(i).getNumberOfTile())
                        .setFont(new Font("", Font.BOLD, 80));
            }
        }
    }

    private static void lockAllTiles() {
        for (int i = 0; i < buttonList.size(); i++) {
            buttonList.get(i).setEnabled(false);
        }
    }

    private static void unlockAllTiles() {
        for (int i = 0; i < buttonList.size(); i++) {
            if (!gameBoard.tileList.get(i).checkIfTileColapsed()) {
                buttonList.get(i).setEnabled(true);
            }
        }
    }

    private static void hideInnerButtons() {
        for (int i = 0; i < buttonListInner.size(); i++) {
            buttonListInner.get(i).setVisible(false);
        }
    }

    private static void informAboutDraw() {
        infoLabel.setText("Draw!!!Nobody wins");
        JOptionPane.showMessageDialog(frame,
                "Draw!!!Nobody wins",
                "DRAW",
                JOptionPane.INFORMATION_MESSAGE);
        for (int i = 0; i < 9; i++) {
            buttonList.get(i).setEnabled(false);
        }
    }

    private static void informAboutWin() {
        infoLabel.setText("<html>Congratulations to player " + gameBoard.whoIsWinner()
                + " who has won!!!</html>");
        JOptionPane.showMessageDialog(frame,
                "Congratulations to player " + gameBoard.whoIsWinner()
                        + " who has won!!!",
                "WINNER",
                JOptionPane.INFORMATION_MESSAGE);
        for (int i = 0; i < 9; i++) {
            buttonList.get(i).setEnabled(false);
        }
    }

}