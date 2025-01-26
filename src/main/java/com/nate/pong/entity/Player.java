package com.nate.pong.entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.nate.pong.Pong;

public class Player extends Entity {

    public static final int WIDTH = 40;
    public static final int HEIGHT = 60;
    private static final int MIN_HEIGHT = 5;
    private static final int MAX_HEIGHT = Pong.SCREEN_HEIGHT - MIN_HEIGHT - 1;

    private final int upKey, downKey;
    private final float speed = 5.5f;

    private int score = 0;

    public Player(float x, float y, int upKey, int downKey) {
        super(x, y, WIDTH, HEIGHT);
        this.upKey = upKey;
        this.downKey = downKey;
    }

    @Override
    public void tick(boolean[] keys) {
        if (keys[upKey] && getY() > MIN_HEIGHT) {
            if (getY() - speed <= MIN_HEIGHT) setY(MIN_HEIGHT);
            else setY(getY() - speed);
        }
        if (keys[downKey] && getY() + getHeight() <= MAX_HEIGHT) {
            if (getY() + speed >= MAX_HEIGHT - getHeight()) setY(MAX_HEIGHT - getHeight());
            else setY(getY() + speed);
        }

        updateCollisionBox();
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect((int) getX(), (int) getY(), getWidth(), getHeight());

        g.setFont(new Font("Arial", Font.BOLD, 45));
        if (getX() < Pong.SCREEN_WIDTH / 2) g.drawString(Integer.toString(score), Pong.SCREEN_WIDTH / 2 - getWidth() - 66, 50);
        if (getX() > Pong.SCREEN_WIDTH / 2) g.drawString(Integer.toString(score), Pong.SCREEN_WIDTH / 2 - getWidth() + 50, 50);
    }

    public void increaseScore() { this.score = score + 1; }
}
