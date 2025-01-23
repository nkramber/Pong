package com.nate.pong.entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.nate.pong.Pong;

public class Player extends Entity {

    private final int upKey, downKey;
    private final float speed = 5.5f;

    private int score = 0;

    public Player(float x, float y, int upKey, int downKey) {
        super(x, y);
        this.upKey = upKey;
        this.downKey = downKey;

        setWidth(4);
        setHeight(60);
    }

    @Override
    public void tick(Pong pong) {
        if (pong.getKey(upKey)) if (getY() - getHeight() / 2 - getWidth() > 0) setY(getY() - speed);
        if (pong.getKey(downKey)) if (getY() + getHeight() / 2 + getWidth() < Pong.SCREEN_HEIGHT) setY(getY() + speed);
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect((int) getX() - (getWidth() / 2), (int) getY() - (getHeight() / 2), getWidth(), getHeight());

        g.setFont(new Font("Arial", Font.BOLD, 45));
        if (getX() < Pong.SCREEN_WIDTH / 2) g.drawString(Integer.toString(score), Pong.SCREEN_WIDTH / 2 - getWidth() - 66, 50);
        if (getX() > Pong.SCREEN_WIDTH / 2) g.drawString(Integer.toString(score), Pong.SCREEN_WIDTH / 2 - getWidth() + 50, 50);
    }

    public int getScore() { return score; }

    public void setScore(int score) { this.score = score; }
}
