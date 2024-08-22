package com.snakegame;

import javax.swing.JPanel;

public class PanelJogo extends JPanel{

	public PanelJogo() {

	}

	protected void paintComponent(java.awt.Graphics g) {
		super.paintComponent(g);
		g.setColor(java.awt.Color.BLUE); //define a cor do fundo da tela
		g.fillRect(0, 0, getWidth(), getHeight()); //preenche com a cor selecionada
	}
	
}
