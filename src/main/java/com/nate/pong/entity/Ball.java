package com.nate.pong.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import com.nate.pong.Pong;

public class Ball extends Entity {

    private float speed;
    private int xDir, yDir;
    private boolean alreadyCollided = false;

    public Ball(float x, float y, int xDir, int yDir, float speed) {
        super(x, y);
        this.xDir = xDir;
        this.yDir = yDir;
        this.speed = speed;

        setWidth(8);
        setHeight(8);
    }

    @Override
    public void tick(Pong pong) {
        int tempY = (int) (getY() + speed * yDir);

        if (tempY < 0 + getHeight() / 2) yDir = 1;
        if (tempY > Pong.SCREEN_HEIGHT - getWidth() / 2) yDir = -1;

        setX(getX() + speed * xDir);
        setY(getY() + speed * yDir);
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect((int) getX() - getWidth() / 2, (int) getY() - getHeight() / 2, getWidth(), getHeight());
    }

    public int getxDir() { return xDir; }
    public int getyDir() { return yDir; }
    public float getSpeed() { return speed; }
    public boolean alreadyCollided() { return alreadyCollided; }

    public void setxDir(int xDir) { this.xDir = xDir; }
    public void setYDir(int yDir) { this.yDir = yDir; }
    public void setSpeed(float speed) { this.speed = speed; }
    public void setAlreadyCollided(boolean alreadyCollided) { this.alreadyCollided = alreadyCollided; }
}
