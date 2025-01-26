package com.nate.pong.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Ball extends Entity {

    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;
    private static final float INITIAL_SPEED = 2.5f;
    private static final float MAX_SPEED = Player.SPEED - 0.1f;

    private float speed = INITIAL_SPEED;
    private int xDir, yDir;

    public Ball(float[] ballSpawn) { //0 == x, 1 == y, 2 == xDir, 3 == yDir
        super(ballSpawn[0], ballSpawn[1], WIDTH, HEIGHT);
        this.xDir = (int) ballSpawn[2];
        this.yDir = (int) ballSpawn[3];
    }

    public void tick() {
        setX(getX() + getxDir() * speed);
        setY(getY() + getyDir() * speed);
        updateCollisionBox();
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect((int) getX(), (int) getY(), getWidth(), getHeight());
    }

    public Rectangle getNewCollisionBox() { return new Rectangle(getNewX(), getNewY(), getWidth(), getHeight()); }

    public int getNewX() { return (int) getX() + getxDir(); }
    public int getNewY() { return (int) getY() + getyDir(); }
    public int getxDir() { return xDir; }
    public int getyDir() { return yDir; }
    public float getSpeed() { return speed; }

    public void setxDir(int xDir) { this.xDir = xDir; }
    public void setyDir(int yDir) { this.yDir = yDir; }
    public void increaseSpeed(float speedIncreaseRatio) { speed = speed * speedIncreaseRatio <= MAX_SPEED ? speed * speedIncreaseRatio : speed; }
    public void setSpeed(float speed) { this.speed = speed; }
}
