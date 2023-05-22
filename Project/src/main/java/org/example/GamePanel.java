package org.example;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends Panel implements Runnable {
    int width = 1000;
    int height = (int)(width*(0.555));
    Image image;
    Graphics graphics;
    Paddle p1,p2;
    Ball ball;
    Score score = new Score(width,height);
    Thread GameThread;
    Dimension screen = new Dimension(width,height);
    int Paddle_Height = 100;
    int Paddle_Width = 25;
    int ball_diameter=20;
    GamePanel(){
        setPreferredSize(screen);
        GameThread = new Thread(this);
        setFocusable(true);
        GameThread.start();
        addKeyListener(new AL());
        newPaddle();
        newBall();
    }

    private void newBall() {
        Random random= new Random();
        ball = new Ball(width/2, random.nextInt(height-ball_diameter),ball_diameter,ball_diameter);
    }

    private void newPaddle() {

        p1=new Paddle(0,(height-Paddle_Height)/2,Paddle_Width,Paddle_Height, 1);
        p2=new Paddle(width-Paddle_Width,(height-Paddle_Height)/2,Paddle_Width,Paddle_Height,2);
    }

    //paint
    @Override
    public void update(Graphics g) {
        super.paint(g);
        image=createImage(getWidth(),getHeight());
        graphics=image.getGraphics();
        draw(graphics);
        g.drawImage(image,0,0,this);
    }

//    @Override
//    public void update(Graphics g) {
//        super.update(g);
//    }

    private void draw(Graphics g) {
        p1.draw(g);
        p2.draw(g);
        ball.draw(g);
        score.draw(g);
    }

    @Override
    public void run() {
        long lastTime=System.nanoTime();
        double amountOfFlicks = 60;
        double ns = 1000000000/amountOfFlicks;
        double delta=0;
        while (true){
            long now=System.nanoTime();
            delta+=(now-lastTime)/ns;
            lastTime=now;
            if(delta>=1){
                repaint();
                move();
                checKCollision();
                delta--;
            }
        }
    }

    private void checKCollision() {
        if(ball.y<=0){
            ball.setYDirection(-ball.yVelocity);
        }
        if(ball.y>=height-ball_diameter){
            ball.setYDirection(-ball.yVelocity);
        }
        if(ball.intersects(p1)){
            ball.xVelocity=-ball.xVelocity;
            ball.xVelocity++;
            if(ball.yVelocity>0){
                ball.yVelocity++;
            }else{
                ball.yVelocity--;
            }
            ball.setYDirection(ball.yVelocity);
            ball.setXDirection(ball.xVelocity);
        }
        if(ball.intersects(p2)){
            ball.xVelocity=-ball.xVelocity;
            ball.xVelocity--;
            if(ball.yVelocity>0){
                ball.yVelocity++;
            }else{
                ball.yVelocity--;
            }
            ball.setYDirection(ball.yVelocity);
            ball.setXDirection(ball.xVelocity);
        }

        if(p1.y<=0){
            p1.y=0;
        }
        if(p1.y>=height-Paddle_Height){
            p1.y=height-Paddle_Height;
        }
        if(p2.y<=0){
            p2.y=0;
        }
        if(p2.y>=height-Paddle_Height){
            p2.y=height-Paddle_Height;
        }
        if(ball.x>=width-ball_diameter){
            newBall();
            newPaddle();
            score.player1++;
        }
        if(ball.x<=0){
            newPaddle();
            newBall();
            score.player2++;
        }
    }

    private void move() {
        p1.move();
        p2.move();
        ball.move();
    }

    public class AL extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            //super.keyPressed(e);
            p1.keyPressed(e);
            p2.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            //super.keyReleased(e);
            p1.keyReleased(e);
            p2.keyReleased(e);
        }
    }
}
