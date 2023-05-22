package org.example;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    GameFrame(){
        setTitle("Pong Game");
        //setLayout(null);
        //setSize(1000,500);
        getContentPane().setBackground(Color.BLACK);
        GamePanel panel = new GamePanel();
        add(panel);
        //setFocusable(true);
        pack();
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        GameFrame g = new GameFrame();
    }
}
