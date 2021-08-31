package com.mr.modle;


import com.mr.view.BackgroundImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/*障碍类：用于封装石头和仙人掌，
它们会随着背景一起移动，
都有可以碰撞的区域        */
public class Obstacle {

    public int x, y; //横纵坐标
    public BufferedImage image;
    private BufferedImage stone; //石头图片
    private BufferedImage cacti; //仙人掌图片
    private int speed; //移动速度

    //构造方法中，Obstacle对象会随机成为石头或仙人掌
    public Obstacle(){
        try {
            stone = ImageIO.read(new File("image/stone.png"));
            cacti = ImageIO.read(new File("image/cacti.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Random r = new Random(); //创建随机对象
        if(r.nextInt(2) == 0) { //从0和1中取一值，若为0
            image = cacti; //采用仙人掌图片
        }
        else{
            image = stone; //否则采用石头图片
        }
        x = 800; //初始横坐标
        y = 200-image.getHeight(); //纵坐标
        speed = BackgroundImage.SPEED; //移动速度与背景同步
    }

    //移动
    public void move(){
        x -= speed; //横坐标递减
    }

    //消除: 障碍物移动出游戏画面，为减轻程序压力，删除
    //只有true时才会碰撞检测，否则从障碍集合中删除
    public boolean isLive(){
        if(x <= -image.getWidth()){ //如果移出了游戏界面
            return  false; //消亡
        }
        return true; //存活
    }

    //边界对象
    public Rectangle getBounds(){
        if(image == cacti){ //返回仙人掌的边界
            return new Rectangle(x+7, y, 15, image.getHeight());
        }
        else { //返回石头的边界
            return new Rectangle(x+5, y+4, 23, 21);
        }
    }

}
