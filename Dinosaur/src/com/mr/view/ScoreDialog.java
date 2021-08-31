package com.mr.view;

import com.mr.service.ScoreRecorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//成绩对话框
public class ScoreDialog extends JDialog {

    public ScoreDialog(JFrame frame){
        //调用父类构造方法阻塞父窗体，以保证弹出成绩对话框之后，
        //主窗体内会停止全部功能且不可选中。
        //这样可以保证玩家单击“重新开始”之后，主窗体才会执行restart()方法
        super(frame, true);
        int scores[] = ScoreRecorder.getScores(); //获取当前的前三名成绩
        JPanel scoreP = new JPanel(new GridLayout(4,1)); //成绩面板，4行1列
        scoreP.setBackground(Color.WHITE); //白色背景
        JLabel title = new JLabel("得分排行榜", JLabel.CENTER); //标题标签，居中
        title.setFont(new Font("黑体", Font.BOLD, 20)); //设置字体
        title.setForeground(Color.RED); //红色前景字
        //第一名标签，居中显示
        JLabel first = new JLabel("第一名："+ scores[2], JLabel.CENTER);
        //第二名标签，居中显示
        JLabel second = new JLabel("第二名："+ scores[1], JLabel.CENTER);
        //第三名标签，居中显示
        JLabel third = new JLabel("第三名："+ scores[0], JLabel.CENTER);
        JButton restart = new JButton("重新开始"); //重新开始按钮
        restart.addActionListener(new ActionListener() { //按钮添加事件监听
            @Override
            public void actionPerformed(ActionEvent e) { //当点击时
                dispose(); //销毁对话框
            }
        });

        scoreP.add(title); //成绩面板添加标签
        scoreP.add(first);
        scoreP.add(second);
        scoreP.add(third);

        Container c = getContentPane(); //获取主容器
        c.setLayout(new BorderLayout()); //使用边界布局
        c.add(scoreP, BorderLayout.CENTER); //成绩面板放中间
        c.add(restart, BorderLayout.SOUTH); //按钮放底部

        setTitle("游戏结束"); //对话框标题
        int width, height;
        width = height = 200; //对话框宽高均为200
        //获得主窗体中居中位置的横纵坐标
        int x = frame.getX() + (frame.getWidth()-width)/2;
        int y = frame.getY()+(frame.getHeight()-height)/2;
        setBounds(x, y, width, height); //设置坐标和宽高
        setVisible(true); //显示对话框
    }
}
