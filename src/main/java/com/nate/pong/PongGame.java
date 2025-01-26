package com.nate.pong;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import com.nate.pong.entity.Ball;
import com.nate.pong.entity.Player;
import com.nate.pong.state.GameState;

public class PongGame extends GameState {

    private static final int PLAYER_SIDE_BUFFER = 30;
    private static final float BALL_SPEED_RATIO = 1.1f;

    private Player[] players;
    private Ball ball;

    public PongGame() {
        ball = new Ball(getBallSpawn());
        players = new Player[2];
        players[0] = new Player(PLAYER_SIDE_BUFFER, Pong.SCREEN_HEIGHT / 2, KeyEvent.VK_W, KeyEvent.VK_S);
        players[1] = new Player(Pong.SCREEN_WIDTH - Player.WIDTH - PLAYER_SIDE_BUFFER, Pong.SCREEN_HEIGHT / 2, KeyEvent.VK_UP, KeyEvent.VK_DOWN);
    }

    @Override
    public void tick(Pong pong) {
        Rectangle ballRect = ball.getNewCollisionBox();
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

        for (Player player : players) {
            List<String> collisions = new ArrayList<>();
            player.tick(pong.getKeys());
            Rectangle playerRect = player.getCollisionBox();
            if (playerRect.intersects(ballRect)) {
                int playerx1 = playerRect.x;
                int playerx2 = playerRect.x + playerRect.width;
                int playery1 = playerRect.y;
                int playery2 = playerRect.y + playerRect.height;

                if (bally1 < playery1 && bally2 >= playery1) collisions.add("top");
                if (bally2 > playery2 && bally1 <= playery2) collisions.add("bottom");
                if (ballx1 < playerx1 && ballx2 >= playerx1) collisions.add("left");
                if (ballx2 > playerx2 && ballx1 <= playerx2) collisions.add("right");

                if (collisions.size() == 1) moveBall(player, collisions.get(0));
                else {
                    if (collisions.contains("left") && collisions.contains("top")) {
                        int topOverlap = ballx2 - playerx1;
                        int leftOverlap = bally2 - playery1;
                        moveBall(player, topOverlap >= leftOverlap ? "top" : "left");
                    } 
                    else if (collisions.contains("right") && collisions.contains("top")) {
                        int topOverlap = playerx2 - ballx1;
                        int rightOverlap = bally2 - playery1;
                        moveBall(player, topOverlap >= rightOverlap ? "top" : "right");
                    }
                    else if (collisions.contains("left") && collisions.contains("bottom")) {
                        int bottomOverlap = ballx2 - playerx1;
                        int leftOverlap = playery2 - bally1;
                        moveBall(player, bottomOverlap >= leftOverlap ? "bottom" : "left");
                    }
                    else if (collisions.contains("right") && collisions.contains("bottom")) {
                        int bottomOverlap = playerx2 - ballx1;
                        int rightOverlap = playery2 - bally1;
                        moveBall(player, bottomOverlap >= rightOverlap ? "bottom" : "right");
                    }
                }
            }
        }

        ball.tick();
    }

    private void moveBall(Player player, String direction) {
        if (direction == "top") {
            ball.setyDir(-1);
            if (player.isMoving()) {
                ball.setY(player.getY() - (ball.getHeight()));
                ball.setSpeed(Player.SPEED);
            }
        } else if (direction == "bottom") {
            ball.setyDir(1);
            if (player.isMoving()) {
                ball.setY(player.getY() + player.getHeight());
                ball.setSpeed(Player.SPEED);
            }
        } else if (direction == "left") {
            ball.setxDir(-1);
            ball.increaseSpeed(BALL_SPEED_RATIO);
        } else if (direction == "right") {
            ball.setxDir(1);
            ball.increaseSpeed(BALL_SPEED_RATIO);
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(new Color(1.0f, 1.0f, 1.0f, 0.5f));
        g.fillRect(Pong.SCREEN_WIDTH / 2 - 2, 0, 4, Pong.SCREEN_HEIGHT);

        ball.render(g);

        for (Player player : players) {
            player.render(g);
            g.setFont(new Font("Arial", Font.BOLD, 45));
            FontMetrics m = g.getFontMetrics();
            int center = (Pong.SCREEN_WIDTH - g.getFontMetrics().stringWidth(Integer.toString(player.getScore()))) / 2;
            if (player.getX() < Pong.SCREEN_WIDTH / 2) {
                g.drawString(Integer.toString(player.getScore()), center - 50, 50);
            }
            if (player.getX() > Pong.SCREEN_WIDTH / 2) {
                g.drawString(Integer.toString(player.getScore()), center + 50, 50);
            }
        }
    }

    private float[] getBallSpawn() {
        return new float[] {Pong.SCREEN_WIDTH / 2, Pong.RANDOM.nextInt(Pong.SCREEN_HEIGHT), Pong.RANDOM.nextBoolean() ? 1 : -1, Pong.RANDOM.nextBoolean() ? 1 : -1};
    }
}
