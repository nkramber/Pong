package com.nate.pong;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.nate.pong.state.GameState;
import com.nate.pong.state.menu.MainMenu;

public class Pong extends Canvas implements Runnable, KeyListener {

    private static JFrame frame;
    public static Random RANDOM;
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 500;
    private static final String TITLE = "Pong";
    private static final double TARGET_FPS = 60.0;
    private static final double TIME_BETWEEN_FRAMES = 1000000000 / TARGET_FPS;

    private boolean running;
    private BufferedImage display;
    private boolean keys[];

    private GameState currentState;

    private void init() {
        display = new BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
        RANDOM = new Random();
        keys = new boolean[256];

        currentState = new MainMenu();

        addKeyListener(this);
    }

    @Override
    public void run() {
        init();

        double lastUpdateTime = System.nanoTime();
        while(running) {
            double currentTime = System.nanoTime();
            while (currentTime - lastUpdateTime > TIME_BETWEEN_FRAMES) {
                tick();
                render();
                lastUpdateTime += TIME_BETWEEN_FRAMES;
            }
        }
        System.exit(0);
    }

    private void tick() {
        currentState.tick(this);
    }

    private void render() {
        Graphics2D g = (Graphics2D) display.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        currentState.render(g);

        getGraphics().drawImage(display, 0, 0, this);
    }

    private void start() {
        requestFocus();
        running = true;
        new Thread(this).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Pong pong = new Pong();
            pong.setMinimumSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
            pong.setMaximumSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
            pong.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));

            frame = new JFrame(TITLE);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            frame.add(pong);
            frame.pack();
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.setIgnoreRepaint(true);
            
            pong.start();
        });
    }

    public void setState(GameState state) { currentState = state; }
    public boolean[] getKeys() { return keys; }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyPressed(KeyEvent e) { keys[e.getKeyCode()] = true; }
    @Override public void keyReleased(KeyEvent e) { keys[e.getKeyCode()] = false; }
}