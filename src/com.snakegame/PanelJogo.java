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

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
	
    private final int SEGMENTO_TAMANHO = 25;
    private final int LARGURA_TABULEIRO = 500;
    private final int ALTURA_TABULEIRO = 500;
    private final int SEGMENTO_MAXIMO = (LARGURA_TABULEIRO * ALTURA_TABULEIRO) / (SEGMENTO_TAMANHO * SEGMENTO_TAMANHO);

    private final int VELOCIDADE_NORMAL = 100;
    private final int VELOCIDADE_ACELERADA = 50;

    private final int[] x = new int[SEGMENTO_MAXIMO];
    private final int[] y = new int[SEGMENTO_MAXIMO];

    private int comprimentoCobra;
    private boolean moverEsquerda = false;
    private boolean moverDireita = true;
    private boolean moverBaixo = false;
    private boolean moverCima = false;
    private boolean emPausa = false;
    
    private int comidaX;
    private int comidaY;

    private int pontuacao;

    private Timer timer;

    public PanelJogo() {
        setPreferredSize(new Dimension(LARGURA_TABULEIRO, ALTURA_TABULEIRO));
        setBackground(Color.DARK_GRAY);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false); 
        addKeyListener(this);
        iniciarJogo();
    }

    private void iniciarJogo() {
        comprimentoCobra = 3;
        pontuacao = 0;

        for (int i = 0; i < comprimentoCobra; i++) {
            x[i] = 100 - i * SEGMENTO_TAMANHO;
            y[i] = 100;
        }

        timer = new Timer(VELOCIDADE_NORMAL, this);
        timer.start();
        gerarComida();
        requestFocusInWindow();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!emPausa) {
            moverCobra();
            repaint();
        }
    }

    private void moverCobra() {
        for (int i = comprimentoCobra - 1; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
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

        if (x[0] < 0 || x[0] >= LARGURA_TABULEIRO || y[0] < 0 || y[0] >= ALTURA_TABULEIRO) {
            gameOver();
        }

        for (int i = comprimentoCobra; i > 0; i--) {
            if (x[0] == x[i] && y[0]     == y[i]) {
                gameOver();
            }
        }

        if (x[0] == comidaX && y[0] == comidaY) {
            comprimentoCobra++;
            pontuacao += 100;
            gerarComida();
        }
        

        //System.out.println("Posição cabeça: (" + x[0] + ", " + y[0] + ")");
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        desenharCobra(g);
        desenharComida(g);
        desenharPontuacao(g);
    }
    
    private void desenharComida(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(comidaX, comidaY, SEGMENTO_TAMANHO, SEGMENTO_TAMANHO);
    }

    private void desenharCobra(Graphics g) {
        for (int i = 0; i < comprimentoCobra; i++) {
            if (i == 0) {
                g.setColor(Color.ORANGE);
            } else {
                g.setColor(Color.YELLOW);
            }
            g.fillRect(x[i], y[i], SEGMENTO_TAMANHO, SEGMENTO_TAMANHO);
        }
    }

    private void desenharPontuacao(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawString("Pontuação: " + pontuacao, 10, 10);  
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_SPACE) {
            timer.setDelay(VELOCIDADE_ACELERADA);
        }

        if (key == KeyEvent.VK_P) {
            emPausa = !emPausa;
            System.out.println(emPausa ? "Joso pausado" : "Jogo retomado");
        }
   
        if (!emPausa) {
           
            if (key == KeyEvent.VK_LEFT && !moverDireita) {
                moverEsquerda = true;
                moverDireita = false;
                moverCima = false;
                moverBaixo = false;
                //System.out.println("Movendo para a esquerda");
            } else if (key == KeyEvent.VK_RIGHT && !moverEsquerda) { 
                moverDireita = true;
                moverEsquerda = false;
                moverCima = false;
                moverBaixo = false;
                //System.out.println("Movendo para a direita");
            } else if (key == KeyEvent.VK_UP && !moverBaixo) { 
                moverCima = true;
                moverBaixo = false;
                moverEsquerda = false;
                moverDireita = false;
                //System.out.println("Movendo para cima");
            } else if (key == KeyEvent.VK_DOWN && !moverCima) { 
                moverBaixo = true;
                moverCima = false;
                moverEsquerda = false;
                moverDireita = false;
                //System.out.println("Movendo para baixo");
            }

            /*System.out.println("Tecla pressionada: " + KeyEvent.getKeyText(key));
            System.out.println("Direção: Esquerda=" + moverEsquerda + " Direita=" + moverDireita + " Baixo=" + moverBaixo + " Cima=" + moverCima);*/ 
        }

        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE) {
            timer.setDelay(VELOCIDADE_NORMAL);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    
    private void gerarComida() {
        int posicaoAleatoria = (int) (Math.random() * (LARGURA_TABULEIRO / SEGMENTO_TAMANHO)) * SEGMENTO_TAMANHO;
        comidaX = posicaoAleatoria;
        posicaoAleatoria = (int) (Math.random() * (ALTURA_TABULEIRO / SEGMENTO_TAMANHO)) * SEGMENTO_TAMANHO;
        comidaY = posicaoAleatoria;
    }
    
    private void gameOver() {
        
        timer.stop();
        System.out.println("Game Over!");
        
        System.out.println("Pressione 'R' para reiniciar o jogo.");

        addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_R) {
                    pontuacao = 0;
                    reiniciarJogo();
                    removeKeyListener(this);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }

            @Override
            public void keyTyped(KeyEvent e) {

            }
        });
    }


    private void reiniciarJogo() {
        comprimentoCobra = 3;

        for (int i = 0; i < comprimentoCobra; i++) {
            x[i] = 100 - i * SEGMENTO_TAMANHO;
            y[i] = 100;
        }

        moverEsquerda = false;
        moverDireita = true;
        moverBaixo = false;
        moverCima = false;

        timer.restart();
    }

}
