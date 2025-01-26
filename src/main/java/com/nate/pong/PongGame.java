package com.nate.pong;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import com.nate.pong.entity.Ball;
import com.nate.pong.entity.Player;
import com.nate.pong.state.GameState;

public class PongGame extends GameState {

    private final float ballSpeedRatio = 1.1f;
    private Player[] players;
    private Ball ball;

    public PongGame() {
        ball = new Ball(getBallSpawn());
        players = new Player[2];
        players[0] = new Player(Player.WIDTH, Pong.SCREEN_HEIGHT / 2, KeyEvent.VK_W, KeyEvent.VK_S);
        players[1] = new Player(Pong.SCREEN_WIDTH - Player.WIDTH * 2, Pong.SCREEN_HEIGHT / 2, KeyEvent.VK_UP, KeyEvent.VK_DOWN);
    }

    @Override
    public void tick(Pong pong) {
        List<String> collisions = new ArrayList<>();
        Rectangle ballRect = ball.getCollisionBox();
        int ballx1 = ballRect.x;
        int ballx2 = ballRect.x + ballRect.width;
        int bally1 = ballRect.y;
        int bally2 = ballRect.y + ballRect.height;

        if (ballx2 < 0) {
            players[1].increaseScore();
            ball = new Ball(getBallSpawn());
        } else if (ballx1 > Pong.SCREEN_WIDTH) {
            players[0].increaseScore();
            ball = new Ball(getBallSpawn());
        } else if (bally1 < 0) {
            ball.setyDir(1);
        } else if (bally2 > Pong.SCREEN_HEIGHT) {
            ball.setyDir(-1);
        }

        for (int i = 0; i < players.length; i++) {
            players[i].tick(pong.getKeys());
            Rectangle playerRect = players[i].getCollisionBox();
            if (playerRect.intersects(ballRect)) {
                int playerx1 = playerRect.x;
                int playerx2 = playerRect.x + playerRect.width;
                int playery1 = playerRect.y;
                int playery2 = playerRect.y + playerRect.height;

                if (ballx1 < playerx1 && ballx2 >= playerx1) collisions.add("left");
                if (ballx2 > playerx2 && ballx1 <= playerx2) collisions.add("right");
                if (bally1 < playery1 && bally2 >= playery1) collisions.add("top");
                if (bally2 > playery2 && bally1 <= playery2) collisions.add("bottom");
            }
        }

        if (collisions.contains("top")) {
            ball.setyDir(-1);
        } else if (collisions.contains("bottom")) {
            ball.setyDir(1);
        } else if (collisions.contains("left")) {
            ball.setxDir(-1);
            ball.increaseSpeed(ballSpeedRatio);
        } else if (collisions.contains("right")) {
            ball.setxDir(1);
        }

        ball.tick();
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(new Color(1.0f, 1.0f, 1.0f, 0.5f));
        g.fillRect(Pong.SCREEN_WIDTH / 2 - 2, 0, 4, Pong.SCREEN_HEIGHT);

        ball.render(g);

        for (int i = 0; i < players.length; i++) {
            players[i].render(g);
        }
    }

    private float[] getBallSpawn() {
        return new float[] {Pong.SCREEN_WIDTH / 2, Pong.RANDOM.nextInt(Pong.SCREEN_HEIGHT), Pong.RANDOM.nextBoolean() ? 1 : -1, Pong.RANDOM.nextBoolean() ? 1 : -1};
    }
}
