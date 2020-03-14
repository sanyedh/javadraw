package com.main;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.*;
import java.util.*;
public class Main{
    private MyFrame f;
    public Main() {
        f = new MyFrame();
        f.centerFrame();
        //new Thread(f).start();
    }
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        new Main();
    }

}
class MyFrame extends JFrame implements MouseListener,MouseMotionListener,ActionListener,Runnable{
    private int screenWidth;
    private int screenHeight;
    private Dimension screenSize;

    private int width = 800;
    private int height = 600;

    private int startX,startY;

    private boolean isClicked = false;

    private int cx,cy;

    private Graphics gra;

    private int pointFar;

    final JSplitPane jsPane;
    final JSplitPane leftJsPane;

    final JPanel allLeftPane;
    final JPanel leftPane;


    final JRightPanel rightPane;


    final int splitBarSize = 6;
    final int leftPaneSize = 200 + splitBarSize;

    final JButton jb1,jb2,jb3,jb4,jb5,jb6,jb7;

    private String drawFlag;


    private int lineEndX,lineEndY;

    private ArrayList<Point> pointArr = new ArrayList<Point>(1000);

    public Color scopeColor = Color.black;//初始化颜色
    private Graphics2D graphic;

    private BufferedImage b1;
    public MyFrame() {

        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = (int)screenSize.getWidth();
        screenHeight = (int)screenSize.getHeight();

        startX = (screenWidth - width) / 2;
        startY = (screenHeight - height) / 2;

        allLeftPane = new JPanel();
        leftJsPane = new JSplitPane();
        leftPane = new JPanel();
        rightPane = new JRightPanel();


        jsPane= new JSplitPane(JSplitPane.VERTICAL_SPLIT);





        allLeftPane.setLayout(new BorderLayout());

        allLeftPane.add(leftPane,BorderLayout.NORTH);

        leftPane.setLayout(new GridLayout(4,2,20,30));

        Dimension size = new Dimension(40,25);

        jb1 = new JButton("连续矩形");
        jb2 = new JButton("连续圆形");
        jb3 = new JButton("设置颜色");
        jb4 = new JButton("绘制直线");
        jb5 = new JButton("画图");
        jb6 = new JButton("矩形");
        jb7 = new JButton("圆形");
        //jb5 = new JButton("打印信息");
        jb1.setPreferredSize(size);
        leftPane.add(jb1);
        leftPane.add(jb2);
        leftPane.add(jb3);
        leftPane.add(jb4);
        leftPane.add(jb5);
        leftPane.add(jb6);
        leftPane.add(jb7);
        //leftPane.add(jb5);
        jb1.addActionListener(this);
        jb2.addActionListener(this);
        jb3.addActionListener(this);
        jb4.addActionListener(this);
        jb5.addActionListener(this);
        jb6.addActionListener(this);
        jb7.addActionListener(this);

        jsPane.setLeftComponent(new JPanel());
        jsPane.setRightComponent(leftJsPane);


        jsPane.setDividerSize(splitBarSize);

        jsPane.setContinuousLayout(true);


        jsPane.setDividerLocation(56);//
        leftJsPane.setDividerLocation(leftPaneSize);
        leftJsPane.setLeftComponent(allLeftPane);
        leftJsPane.setRightComponent(rightPane);


        this.add(jsPane);

        rightPane.addMouseListener(this);
        rightPane.addMouseMotionListener(this);

    }
    public void centerFrame() {
        this.setTitle("我的绘图程序");
        this.setSize(width,height);
        this.setLocation(startX - 50,startY);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //graphic = (Graphics2D)rightPane.getGraphics();
        b1 = new BufferedImage(800,600,BufferedImage.TYPE_INT_RGB);
        graphic = (Graphics2D)b1.createGraphics();
        b1 = graphic.getDeviceConfiguration().createCompatibleImage(800, 600, Transparency.TRANSLUCENT);
        graphic.dispose();
        graphic = b1.createGraphics();


        graphic.setColor(scopeColor);


        graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        graphic.setBackground(Color.white);

    }
    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        //Graphics g = this.getGraphics();
        //g.drawRect(10, 10, 100, 100);


    }
    @Override
    public void mousePressed(MouseEvent e) {
        cx = e.getX();
        cy = e.getY();

        isClicked = true;

    }
    @Override
    public void mouseReleased(MouseEvent e) {
        isClicked = false;

        if(drawFlag.equals("直线")) {
            graphic.drawLine(cx,cy,lineEndX,lineEndY);
            Point p = new Point(cx,cy,lineEndX,lineEndY,scopeColor,"直线");
            pointArr.add(p);
            rightPane.setG(pointArr);

            rightPane.getGraphics().drawImage(b1, 0,0,this);
//			rightPane.setImg(b1);

            //graphic.drawImage(b1,0,0,this);

            rightPane.tempPaint();
            rightPane.paint(graphic);
            //rightPane.getGraphics().drawImage(b1,0,0,this);
        }
        if(drawFlag.equals("矩形2")) {
            graphic.drawRect(cx,cy,e.getX()-cx,e.getY()-cy);
            Point p = new Point(cx,cy,e.getX()-cx,e.getY()-cy,scopeColor,"矩形");
            pointArr.add(p);
            rightPane.setG(pointArr);

            rightPane.getGraphics().drawImage(b1, 0,0,this);

            rightPane.tempPaint();
            rightPane.paint(graphic);
        }
        if(drawFlag.equals("圆形2")) {
            graphic.drawOval(cx,cy,e.getX()-cx,e.getY()-cy);
            Point p = new Point(cx,cy,e.getX()-cx,e.getY()-cy,scopeColor,"圆形");
            pointArr.add(p);
            rightPane.setG(pointArr);

            rightPane.getGraphics().drawImage(b1, 0,0,this);

            rightPane.tempPaint();
            rightPane.paint(graphic);
        }
    }
    @Override
    public void mouseEntered(MouseEvent e) {

    }
    @Override
    public void mouseExited(MouseEvent e) {

    }
    @Override
    public void mouseDragged(MouseEvent e) {

        if(isClicked) {
            int xFar = e.getX() - cx;
            int yFar = e.getY() - cy;
            pointFar = (int)Math.sqrt((int) Math.pow(xFar,2.0) + (int) Math.pow(yFar,2));
            if(drawFlag.equals("矩形") && pointFar >= 13) {
                graphic.drawRect(cx, cy, 10, 10);
                rightPane.getGraphics().drawImage(b1, 0, 0, this);

                Point p = new Point(cx,cy,10,10,scopeColor,"矩形");
                pointArr.add(p);
                rightPane.setG(pointArr);

                cx = e.getX();
                cy = e.getY();

            }
            if(drawFlag.equals("圆形") && pointFar >= 13) {
                graphic.drawOval(cx, cy, 10, 10);
                rightPane.getGraphics().drawImage(b1, 0, 0, this);

                Point p = new Point(cx,cy,10,10,scopeColor,"圆形");
                pointArr.add(p);
                rightPane.setG(pointArr);

                cx = e.getX();
                cy = e.getY();

            }

            if(drawFlag.equals("直线")){


                graphic.setColor(scopeColor);
                graphic.drawLine(cx, cy, e.getX(), e.getY());
                rightPane.getGraphics().drawImage(b1,0,0,this);

                graphic.clearRect(0, 0, 800, 600);

                //这里会有一个闪烁
                rightPane.tempPaint();
                rightPane.paint(graphic);

                lineEndX=e.getX();
                lineEndY=e.getY();

            }
            if(drawFlag.equals("矩形2")){


                graphic.setColor(scopeColor);
                graphic.drawRect(cx, cy,e.getX()-cx , e.getY()-cy);
                rightPane.getGraphics().drawImage(b1,0,0,this);

                graphic.clearRect(0, 0, 800, 600);

                //这里会有一个闪烁
                rightPane.tempPaint();
                rightPane.paint(graphic);

                lineEndX=e.getX();
                lineEndY=e.getY();

            }
            if(drawFlag.equals("圆形2")){


                graphic.setColor(scopeColor);
                graphic.drawOval(cx, cy,e.getX()-cx , e.getY()-cy);
                rightPane.getGraphics().drawImage(b1,0,0,this);

                graphic.clearRect(0, 0, 800, 600);

                //这里会有一个闪烁
                rightPane.tempPaint();
                rightPane.paint(graphic);

                lineEndX=e.getX();
                lineEndY=e.getY();

            }
            if(drawFlag.equals("画图")){
                System.out.println("画图");
                graphic.drawLine(cx, cy, e.getX(), e.getY());
                rightPane.getGraphics().drawImage(b1, 0, 0, this);

                Point p = new Point(cx,cy,e.getX(),e.getY(),scopeColor,"画图");
                pointArr.add(p);
                rightPane.setG(pointArr);

                cx = e.getX();
                cy = e.getY();
            }
        }
    }
    @Override
    public void mouseMoved(MouseEvent e) {

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==jb1) {
            drawFlag = "矩形";
        }else if(e.getSource()==jb2) {
            drawFlag = "圆形";
        }else if(e.getSource()==jb3) {
            //drawFlag = "颜色";

            Color color=JColorChooser.showDialog(this, "", Color.BLUE);
            graphic.setColor(color);
            scopeColor = color;
//			System.out.println(scopeColor);

        }else if(e.getSource()==jb4) {
            drawFlag = "直线";
        }else if(e.getSource()==jb5) {
            drawFlag = "画图";
        }else if(e.getSource()==jb6) {
            drawFlag = "矩形2";
        }else if(e.getSource()==jb7) {
            drawFlag = "圆形2";
        }
    }
    @Override
    public void run() {
        // TODO Auto-generated method stub
//		while(true) {
//			//rightPane.repaint();
////			try {
////				//graphic.clearRect(0, 0, 800, 600);
////				Thread.sleep(3);
////			} catch (InterruptedException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			}
////
////			graphic.clearRect(0, 0, 800, 600);
//			//System.out.println("ewe");
//		}
//		graphic.clearRect(0, 0, 800, 600);
    }
}
class Point{
    private int x1,x2,y1,y2;
    private Color color;
    private String name;
    public Point(int x1,int y1,int x2,int y2,Color color,String name) {
        this.x1=x1;
        this.y1=y1;
        this.x2=x2;
        this.y2=y2;
        this.color=color;
        this.name=name;
    }
    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }
    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}

class JRightPanel extends JPanel{
    private ArrayList<Point> pointArr = new ArrayList<Point>(1000);
    private BufferedImage b1;
    private Graphics2D g2;
    public JRightPanel() {
        //super();

        b1 = new BufferedImage(800,600,BufferedImage.TYPE_INT_RGB);
        g2 = b1.createGraphics();
        b1 = g2.getDeviceConfiguration().createCompatibleImage(800, 600, Transparency.TRANSLUCENT);
        g2.dispose();
        g2 = b1.createGraphics();
        g2.setColor(Color.BLACK);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        this.setBackground(Color.white);
    }
    public void setImg(BufferedImage b1) {
        this.b1 = b1;
    }
    public void setG(ArrayList<Point> pArr) {
        this.pointArr = pArr;
    }
    public void tempPaint() {
        for(Point p : pointArr) {

            if(p.getName().equals("直线")) {
                g2.setColor(p.getColor());
                g2.drawLine(p.getX1(), p.getY1(), p.getX2(), p.getY2());
            }else if(p.getName().equals("画图")) {
                g2.setColor(p.getColor());
                g2.drawLine(p.getX1(), p.getY1(), p.getX2(), p.getY2());
            }else if(p.getName().equals("矩形")) {
                g2.setColor(p.getColor());
                g2.drawRect(p.getX1(), p.getY1(), p.getX2(), p.getY2());
            }else if(p.getName().equals("圆形")) {
                g2.setColor(p.getColor());
                g2.drawOval(p.getX1(), p.getY1(), p.getX2(), p.getY2());
            }

        }
    }
    public void paint(Graphics2D g) {
        //g2 = (Graphics2D)g;
        g.drawImage(b1, 0, 0, this);
    }
}