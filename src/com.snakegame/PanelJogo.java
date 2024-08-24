package com.snakegame;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

public class PanelJogo extends JPanel implements ActionListener, KeyListener {

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

    // Construtor e Inicialização
    public PanelJogo() {
        setPreferredSize(new Dimension(LARGURA_TABULEIRO, ALTURA_TABULEIRO));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
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
        requestFocusInWindow();
    }

    // Método de Atualização
    @Override
    public void actionPerformed(ActionEvent e) {
        moverCobra();
        repaint();
    }

    private void moverCobra() {
        // Atualiza a posição dos segmentos da cobra
        for (int i = comprimentoCobra - 1; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        // Atualiza a posição da cabeça da cobra com base na direção
        if (moverEsquerda) {
            x[0] -= SEGMENTO_TAMANHO;
        } else if (moverDireita) {
            x[0] += SEGMENTO_TAMANHO;
        } else if (moverCima) {
            y[0] -= SEGMENTO_TAMANHO; 
        } else if (moverBaixo) {
            y[0] += SEGMENTO_TAMANHO;
        }
        
        // Mensagem de depuração para verificar a posição da cobra
        System.out.println("Posição cabeça: (" + x[0] + ", " + y[0] + ")");
    }

    // Método de Desenho
    @Override
    protected void paintComponent(Graphics g) {
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

    // Métodos de Entrada
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        // Verificar as teclas e atualizar a direção da cobra
        if (key == KeyEvent.VK_A && !moverDireita) { 
            moverEsquerda = true;
            moverDireita = false;
            moverCima = false;
            moverBaixo = false;
            System.out.println("Movendo para a esquerda");
        } else if (key == KeyEvent.VK_D && !moverEsquerda) { 
            moverDireita = true;
            moverEsquerda = false;
            moverCima = false;
            moverBaixo = false;
            System.out.println("Movendo para a direita");
        } else if (key == KeyEvent.VK_W && !moverBaixo) { 
            moverCima = true;
            moverBaixo = false;
            moverEsquerda = false;
            moverDireita = false;
            System.out.println("Movendo para cima");
        } else if (key == KeyEvent.VK_S && !moverCima) {
            moverBaixo = true;
            moverCima = false;
            moverEsquerda = false;
            moverDireita = false;
            System.out.println("Movendo para baixo");
        }

        // Mensagem de depuração para verificar a tecla pressionada
        System.out.println("Tecla pressionada: " + KeyEvent.getKeyText(key));
        System.out.println("Direção: Esquerda=" + moverEsquerda + " Direita=" + moverDireita + " Baixo=" + moverBaixo + " Cima=" + moverCima);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
