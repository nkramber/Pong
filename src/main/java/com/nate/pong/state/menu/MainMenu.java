package com.nate.pong.state.menu;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.nate.pong.Pong;
import com.nate.pong.PongGame;
import com.nate.pong.state.GameState;

public class MainMenu extends GameState {

    @Override
    public void tick(Pong pong) {
        if (pong.getKeys()[KeyEvent.VK_ENTER]) {
            pong.setState(new PongGame());
        }
    }

    @Override
    public void render(Graphics2D g) {

    }
}
