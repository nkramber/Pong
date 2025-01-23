package com.nate.pong;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import com.nate.pong.entity.Ball;
import com.nate.pong.entity.Player;
import com.nate.pong.state.GameState;

public class PongGame extends GameState {

    private final float initialBallSpeed = 2.5f;
    private final float ballSpeedRatio = 1.1f;
    private Player[] players;
    private Ball ball;

    public PongGame() {
        ball = new Ball(Pong.SCREEN_WIDTH / 2, Pong.RANDOM.nextInt(Pong.SCREEN_HEIGHT), Pong.RANDOM.nextBoolean() ? 1 : -1, Pong.RANDOM.nextBoolean() ? 1 : -1, initialBallSpeed);
        players = new Player[2];
        players[0] = new Player(30.0f, Pong.SCREEN_HEIGHT / 2, KeyEvent.VK_W, KeyEvent.VK_S);
        players[1] = new Player(Pong.SCREEN_WIDTH - 30.0f, Pong.SCREEN_HEIGHT / 2, KeyEvent.VK_UP, KeyEvent.VK_DOWN);
    }

    @Override
    public void tick(Pong pong) {
        ball.tick(pong);

        if (ball.getX() < 0) {
            players[1].setScore(players[1].getScore() + 1);
            ball = new Ball(Pong.SCREEN_WIDTH / 2, Pong.RANDOM.nextInt(Pong.SCREEN_HEIGHT), Pong.RANDOM.nextBoolean() ? 1 : -1, Pong.RANDOM.nextBoolean() ? 1 : -1, initialBallSpeed);
        }

        if (ball.getX() > Pong.SCREEN_WIDTH) {
            players[0].setScore(players[0].getScore() + 1);
            ball = new Ball(Pong.SCREEN_WIDTH / 2, Pong.RANDOM.nextInt(Pong.SCREEN_HEIGHT), Pong.RANDOM.nextBoolean() ? 1 : -1, Pong.RANDOM.nextBoolean() ? 1 : -1, initialBallSpeed);
        }

        for (int i = 0; i < players.length; i++) {
            players[i].tick(pong);

            if (collides(ball, players[i]) && !ball.alreadyCollided()) {
                if (ball.getxDir() == 1) ball.setxDir(-1);
                else if (ball.getxDir() == -1) ball.setxDir(1);
                ball.setSpeed(ball.getSpeed() * ballSpeedRatio);
                ball.setAlreadyCollided(true);
            } else if (ball.alreadyCollided() && ball.getX() > 100 && ball.getX() < Pong.SCREEN_WIDTH - 100) {
                ball.setAlreadyCollided(false);
            }
        }
    }

    private boolean collides(Ball ball, Player player) {
        Rectangle ballRect = ball.getCollisionBox();
        Rectangle playerRect = player.getCollisionBox();

        return (ballRect.intersects(playerRect));
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
}
