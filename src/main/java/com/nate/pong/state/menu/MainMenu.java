package com.nate.pong.state.menu;

import java.awt.Color;
import java.awt.Graphics2D;

import com.nate.pong.Pong;
import com.nate.pong.state.GameState;

public class MainMenu extends GameState {

    @Override
    public void tick(Pong pong) {

    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.RED);
        g.drawRect(10, 10, 30, 30);
    }
}
