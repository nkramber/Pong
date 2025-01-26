package com.nate.pong.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import com.nate.pong.Pong;

public class Player extends Entity {

    public static final int WIDTH = 12;
    public static final int HEIGHT = 78;
    public static final float SPEED = 5.5f;
    private static final int MIN_HEIGHT = 0;
    private static final int MAX_HEIGHT = Pong.SCREEN_HEIGHT - MIN_HEIGHT - 1;

    private final int upKey, downKey;

    private int score = 0;
    private boolean moving = false;

    public Player(float x, float y, int upKey, int downKey) {
        super(x, y, WIDTH, HEIGHT);
        this.upKey = upKey;
        this.downKey = downKey;
    }

    @Override
    public void tick(boolean[] keys) {
        moving = false;
        if (keys[upKey] && getY() > MIN_HEIGHT) {
            if (getY() - SPEED <= MIN_HEIGHT) setY(MIN_HEIGHT);
            else setY(getY() - SPEED);
            moving = true;
        }
        if (keys[downKey] && getY() + getHeight() <= MAX_HEIGHT) {
            if (getY() + SPEED >= MAX_HEIGHT - getHeight()) setY(MAX_HEIGHT - getHeight());
            else setY(getY() + SPEED);
            moving = true;
        }

        updateCollisionBox();
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect((int) getX(), (int) getY(), getWidth(), getHeight());
    }

    public void increaseScore() { this.score = score + 1; }

    public boolean isMoving() { return moving; }
    public int getScore() { return score; }
}
