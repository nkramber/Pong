package com.nate.pong.state.menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.nate.pong.Pong;
import com.nate.pong.PongGame;
import com.nate.pong.state.GameState;

public class MainMenu extends GameState {

    @Override
    public void tick(Pong pong) {
        if (pong.getKey(KeyEvent.VK_ENTER)) {
            pong.setState(new PongGame());
        } 
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.RED);
        g.drawRect(10, 10, 30, 30);
    }
}
