package com.nate.pong.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.nate.pong.Pong;

public abstract class Entity {

    private float x, y;
    private int width, height;

    public Entity(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public abstract void tick(Pong pong);
    public abstract void render(Graphics2D g);

    public float getX() { return x; }
    public float getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public Rectangle getCollisionBox() { return new Rectangle((int) getX() - getWidth() / 2, (int) getY() - getHeight() / 2, getWidth(), getHeight()); }

    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
    public void setWidth(int width) { this.width = width; }
    public void setHeight(int height) { this.height = height; }
}
