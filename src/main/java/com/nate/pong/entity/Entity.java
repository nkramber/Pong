package com.nate.pong.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class Entity {

    private float x, y;
    private int width, height;
    private Rectangle collisionBox;

    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        collisionBox = new Rectangle((int) x, (int) y, width, height);
    }

    public void tick(boolean[] keys) { }
    public void render(Graphics2D g) { }

    public float getX() { return x; }
    public float getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public Rectangle getCollisionBox() { return collisionBox; }

    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
    public void updateCollisionBox() { collisionBox = new Rectangle((int) x, (int) y, width, height); }
}
