package com.nate.pong.state;

import java.awt.Graphics2D;

import com.nate.pong.Pong;

public abstract class GameState {

    public abstract void tick(Pong pong);
    public abstract void render(Graphics2D g);
}
