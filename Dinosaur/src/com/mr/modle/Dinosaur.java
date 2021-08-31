package com.mr.modle;
import com.mr.service.FreshThread;
import com.mr.service.Sound;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Dinosaur {

    public BufferedImage image; //主图片
    private BufferedImage image1, image2, image3; //跑步图片
    public int x, y; //坐标
    private int jumpValue = 0; //跳跃时的增变量
    private boolean jumpState = false; //跳跃状态
    private int stepTimer = 0; //踏步计时器
    private final int JUMP_HEIGHT = 100; //跳起最大高度
    private final int LOWEST_Y = 120; //落地最低坐标
    private final int FREASH = FreshThread.FREASH; //刷新时间

    public Dinosaur(){
        x = 50; //小恐龙的横坐标固定在50像素的位置
        y = LOWEST_Y; //纵坐标初始化为落地时的坐标
        try {
            image1 = ImageIO.read(new File("image/dinosaur1.png"));
            image2 = ImageIO.read(new File("image/dinosaur2.png"));
            image3 = ImageIO.read(new File("image/dinosaur3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //踏步
    private void step(){
        //每过250毫秒。更换一张图片。共有3张图片，故除以3取余，轮流展示这3张
        int tmp = stepTimer / 250 % 3;
        switch (tmp){
            case 1:
                image = image1;
                break;
            case 2:
                image = image2;
                break;
            case 3:
                image = image3;
                break;
        }
        stepTimer += FREASH; //计时器递增
    }

    //跳跃
    public void jump(){
        if(!jumpState){ //若没处于跳跃状态
            Sound.jump(); //播放跳跃音效
        }
        jumpState =true; //处于跳跃状态
    }

    //移动
    public void move(){
        step(); //不断踏步
        if(jumpState) { //如果正在跳跃
            if (y >= LOWEST_Y) { //如果纵坐标大于等于最低点
                jumpValue = -4; //增变量为负值，恐龙越来越高
            }
            if (y <= LOWEST_Y - JUMP_HEIGHT) { //如果跳过最高点
                jumpValue = 4; //增编量为正值，恐龙越来越低
            }
            y += jumpValue; //纵坐标发生变化
            if (y >= LOWEST_Y) { //如果再次落地
                jumpState = false; //停止跳跃
            }
        }
    }

    //边界对象，为恐龙的头部和脚步创建矩形边界对象，用于碰撞检测
    public Rectangle getFootBounds(){
        return new Rectangle(x+30, y+59, 29, 18);
    }

    public Rectangle getHeadBounds(){
        return new Rectangle(x+66, y+25, 32, 22);
    }

}
