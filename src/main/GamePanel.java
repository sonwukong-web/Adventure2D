package main;

import main.Main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    //Screen settings
    final int originalTitleSize = 16; //16x16 tile
    final int scale = 3;

    final int tilesize = originalTitleSize*scale; //48x48 tile
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tilesize * maxScreenCol; //768 pixels
    final int screenHeight = tilesize * maxScreenRow; //576 px
    //FPS
    int FPS = 60;
    KeyHandler keyH = new KeyHandler();

    Thread gameThread;

    //Set player default pos
    int playerX = 100;
    int playerY = 100;
    int playerSpeed= 4;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);  //offscreen painting buffer
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread!=null){
           // long currentTime = System.nanoTime();
            //System.out.println("Current time : "+currentTime);
            update();

            repaint();


            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if(remainingTime<0){
                    remainingTime = 0;
                }

                Thread.sleep((long)remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void update(){

        if(keyH.upPressed){
            playerY-=playerSpeed;

        }
        else if(keyH.downPressed){
            playerY+=playerSpeed;
        }
        else if(keyH.leftPressed){
            playerX -= playerSpeed;
        }
        else if(keyH.rightPressed){
            playerX+=playerSpeed;
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.white);

        g2.fillRect(playerX,playerY,tilesize,tilesize);

        g2.dispose();  //save memory
    }
}
