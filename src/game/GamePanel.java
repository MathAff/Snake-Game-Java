package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final Integer SCREEN_WIDTH = 600;
    static final Integer SCREEN_HEIGHT = 600;
    static final Integer UNIT_SIZE = 25;
    static final Integer GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final Integer delay = 100;
    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];
    Integer snakeBodyParts = 6;
    Integer points = 0;
    Integer appleX = 0;
    Integer appleY = 0;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel () {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame () {
        addApple();
        running = true;
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);

    }

    public void draw (Graphics g) {
        if (running) {
            for (int i=0;i<SCREEN_HEIGHT/UNIT_SIZE; i++){
                g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT); //abscissas
                g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE); //ordenadas
            }
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < snakeBodyParts; i++) {
                if (i==0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.red);
            g.setFont( new Font("Ink Free", Font.BOLD, 40) );
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: "+points, (SCREEN_WIDTH-metrics.stringWidth("Score: "+points))/2, g.getFont().getSize());
        } else {
            gameOver(g);
        }
    }

    public void move () {
        for (int i = snakeBodyParts; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
        }
    }

    public void checkApple () {
        if ((x[0] == appleX) && (y[0] == appleY)) {
            snakeBodyParts++;
            points++;
            addApple();
        }
    }

    public void checkCollisions () {
        for (int i = snakeBodyParts; i >0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                running = false;
                break;
            }
        }

        if (x[0]<0) {
            running = false;
        }

        if (x[0]>SCREEN_WIDTH) {
            running = false;
        }

        if (y[0] < 0) {
            running = false;
        }

        if (y[0]>SCREEN_HEIGHT) {
            running = false;
        }
    }

    public void gameOver (Graphics g) {
        g.setColor(Color.red);
        g.setFont( new Font("Ink Free", Font.BOLD, 40) );
        FontMetrics metricsScore = getFontMetrics(g.getFont());
        g.drawString("Score: "+points, (SCREEN_WIDTH-metricsScore.stringWidth("Score: "+points))/2, g.getFont().getSize());

        g.setColor(Color.red);
        g.setFont( new Font("Ink Free", Font.BOLD, 75) );
        FontMetrics metricsGameOver = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH-metricsGameOver.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
    }

    public void addApple() {
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }

    @Override
    public void actionPerformed (ActionEvent e) {
        if (running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }
    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}