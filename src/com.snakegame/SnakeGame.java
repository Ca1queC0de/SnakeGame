package com.snakegame;

import javax.swing.JFrame;

public class SnakeGame {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("Jogo da Cobrinha");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720); 
        frame.setLocationRelativeTo(null); 
        
        PanelJogo panelJogo = new PanelJogo();
        frame.add(panelJogo); 
        
        frame.pack(); 
        frame.setVisible(true); 
    }
}
