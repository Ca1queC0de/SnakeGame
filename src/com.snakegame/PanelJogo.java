package com.snakegame;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.Timer;

public class PanelJogo extends JPanel implements ActionListener {

	private final int SEGMENTO_TAMANHO = 25;
	private final int LARGURA_TABULEIRO = 1600;
	private final int ALTURA_TABULEIRO = 900;
	private final int SEGMENTO_MAXIMO = (LARGURA_TABULEIRO * ALTURA_TABULEIRO) / (SEGMENTO_TAMANHO * SEGMENTO_TAMANHO);
	
	private final int[] x = new int[SEGMENTO_MAXIMO];
	private final int[] y = new int[SEGMENTO_MAXIMO];
	
	private int comprimentoCobra;
	private boolean moverEsquerda = false;
	private boolean moverDireita = true;
	private boolean moverBaixo = false;
	private boolean moverCima = false;
	
	private Timer timer;
	
	public PanelJogo() {
		setPreferredSize(new Dimension(LARGURA_TABULEIRO, ALTURA_TABULEIRO));
		setBackground(Color.BLACK);
		setFocusable(true);
		iniciarJogo();
	}

	private void iniciarJogo() {
		comprimentoCobra = 3;
		
		for (int i = 0; i < comprimentoCobra; i++) {
			x[i] = 100 - i * SEGMENTO_TAMANHO;
			y[i] = 100;
		}
		
		timer = new Timer(140, this);
		timer.start();
		
	}
	
	protected void paintComponent(java.awt.Graphics g) {
		super.paintComponent(g);
		desenharCobra(g);
	}
	
	private void desenharCobra(Graphics g) {
		for (int i = 0; i < comprimentoCobra; i++) {
			if (i == 0) {
				g.setColor(Color.GREEN);
			} else {
				g.setColor(Color.YELLOW);
			}
			g.fillRect(x[i], y[i], SEGMENTO_TAMANHO, SEGMENTO_TAMANHO);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		moverCobra();
		repaint();
	}
	
	private void moverCobra() {
		
		for (int i = comprimentoCobra - 1; i > 0; i--) {
			x[i] = x[i -1];
			y[i] = y[i -1];
		}
		
		if (moverEsquerda) {
			x[0] -= SEGMENTO_TAMANHO;
		} else if (moverDireita) {
			x[0] += SEGMENTO_TAMANHO;
		} else if (moverCima) {
			y[0] -= SEGMENTO_TAMANHO; 
		} else if (moverBaixo) {
			y[0] += SEGMENTO_TAMANHO;
		}
		
	}
	
}
