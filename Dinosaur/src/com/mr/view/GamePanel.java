package com.mr.view;

import com.mr.modle.Dinosaur;
import com.mr.modle.Obstacle;
import com.mr.service.FreshThread;
import com.mr.service.ScoreRecorder;
import com.mr.service.Sound;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

//游戏面板
//采用双缓冲机制防止页面闪烁，image对象就是缓冲图片对象，
//也可以成为主图片对象，所有的游戏画面都绘制在image对象中，
//然后再讲image对象绘制到游戏面板中。
public class GamePanel extends JPanel implements KeyListener {

    private BufferedImage image; //主图片
    private BackgroundImage background; //背景图片
    private Dinosaur golden; //恐龙
    private Graphics2D g2; //主图片绘图对象
    private int addObstacleTimer = 0; //添加障碍计时器
    private boolean finish = false; //添加结束标志
    private List<Obstacle> list = new ArrayList<Obstacle>(); //障碍集合
    private final int FREASH = FreshThread.FREASH; //刷新时间
    int score = 0; //得分
    int scoreTimer = 0; //分数计时器

    public GamePanel(){
        //主图片采用宽800，高300的彩色图片
        image = new BufferedImage(800, 300, BufferedImage.TYPE_INT_BGR);
        g2 = image.createGraphics(); //获取主图片绘图对象
        background = new BackgroundImage(); //初始化滚动背景
        golden = new Dinosaur(); //初始化小恐龙
        list.add(new Obstacle()); //添加第一个障碍
        FreshThread t = new FreshThread(this); //刷新新线程
        t.start(); //启动线程
    }

    //让每个游戏元素都执行各自的运动方法
    private void paintImage(){
        background.roll(); //背景图片开始滚动
        golden.move(); //恐龙开始移动
        g2.drawImage(background.image, 0, 0, this); //绘制滚动背景
        if(addObstacleTimer == 1300) { //每过1300毫秒
            if (Math.random() * 100 > 40) { //60%概率出现障碍
                list.add(new Obstacle());
            }
            addObstacleTimer = 0; //重新计时
        }
        for(int i = 0; i < list.size(); i++){ //遍历障碍集合
            Obstacle o = list.get(i); //获取障碍对象
            if(o.isLive()){ //如果是有效对象
                o.move(); //障碍移动
                g2.drawImage(o.image, o.x, o.y, this); //绘制障碍
                //如果恐龙头脚碰到障碍
                if(o.getBounds().intersects(golden.getFootBounds()) || o.getBounds().intersects(golden.getHeadBounds())){
                    Sound.hit(); //播放撞击声音
                    gameOver(); //游戏结束
                }
            }
            else { //如果不是有效障碍
                list.remove(i); //删除此障碍
                i--; //循环变量前移
            }
        }
        g2.drawImage(golden.image, golden.x, golden.y, this); //绘制恐龙
        if(scoreTimer >= 500){ //每过500毫秒
            score += 10; //加十分
            scoreTimer = 0; //重新计时
        }
        g2.setColor(Color.BLACK); //使用黑色
        g2.setFont(new Font("黑体", Font.BOLD,24)); //设置字体
        g2.drawString(String.format("%06d", score), 700, 30); //绘制分数
        addObstacleTimer += FREASH; //障碍计时器递增
        scoreTimer += FREASH; //分数计时器递增
    }

    public void paint(Graphics g){
        paintImage(); //绘制主图片内容
        g.drawImage(image, 0, 0, this);
    }

    public void gameOver(){
        ScoreRecorder.addNewScore(score); //记录当前分数
        finish = true; //程序停止刷新帧功能
    }

    //判断游戏是否结束，FreshThread线程会根据游戏的运行状态绘制不同的内容
    public boolean isFinish(){
        return finish;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); //获取按下的空格键
        if(code == KeyEvent.VK_SPACE){ //如果是空格
            golden.jump(); //恐龙跳跃
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }
}
