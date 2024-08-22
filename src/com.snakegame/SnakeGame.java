package com.snakegame;

import javax.swing.JFrame;

public class SnakeGame {

	public static void main(String[] args) {
	JFrame frame = new JFrame();
        frame.setTitle("Jogo da Cobrinha");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1600, 900); // Tamanho da janela
        frame.setVisible(true);  // Torna a janela visível
        frame.setLocationRelativeTo(null); // Centraliza a janela na tela
	}
	
}
