package com.nate.pong.state.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
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
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 80));
        FontMetrics m = g.getFontMetrics();

        for (int i = 0; i < MENU_TEXT.length; i++) {
            if (i == 1) g.setFont(new Font("Arial", Font.PLAIN, 30));
            int center = (Pong.SCREEN_WIDTH - g.getFontMetrics().stringWidth(MENU_TEXT[i])) / 2;
            g.drawString(MENU_TEXT[i], center, (i * 45) + 155);
        }
    }

    private final String[] MENU_TEXT = new String[]{
        "Pong by Nate",
        "",
        "",
        "Press enter to play",
        "Left player uses W and S",
        "Right player uses UP and DOWN"
    };
}
