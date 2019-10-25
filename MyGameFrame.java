package cn.zijing.game;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

import javax.swing.JFrame;

/**
 * 主窗口
 * @author zijing
 *
 */

public class MyGameFrame extends Frame{
	Image planeImg = GameUtil.getImage("Images/plane.png");
	Image bg = GameUtil.getImage("Images/bg.jpg");
	
	Plane plane = new Plane(planeImg,250,250 );
	Shell[] shells = new Shell[25];
	Explode bao;
	Date startTime = new Date();
	Date endTime;
	int period;//游戏时间
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(bg, 0, 0, null);
		plane.drawSelf(g);//画飞机
		for(int i = 0;i<shells.length;i++) {
		shells[i].draw(g);//画炮弹
	//飞机炮弹碰撞检测
		boolean peng = shells[i].getRect().intersects(plane.getRect());
		if(peng) {
			plane.live = false;
			if(bao == null) {
			bao = new Explode(plane.x,plane.y);
			endTime = new Date();
			period = (int)((endTime.getTime() - startTime.getTime())/1000);
			
			
			}
			g.setColor(Color.WHITE);
			g.drawString("时间："+period+"秒", (int)plane.x, (int)plane.y);
			bao.draw(g);
		}
		}
	}	
	
class PaintThread extends Thread{
	@Override
	public void run() {
	while(true) {
		repaint();  //重画
		try {
			Thread.sleep(40);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	}
}
//定义键盘内部类
	class KeyMonitor extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent e) {
			plane.addDirection(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			plane.minusDirection(e);
		}
		
	}
	/**
	 * 初始化窗口
	 */
	public void lauchJFame() {
		this.setTitle("子敬项目");
		this.setVisible(true);
		this.setSize(Constant.GAME_WITH,Constant.GAME_HEIGHT);
		this.setLocation(300,300);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
			System.exit(0);
			}
		});
		new PaintThread().start();//启动重画窗口线程
		addKeyListener(new KeyMonitor());//窗口增加键盘监听
		
		//初始化50个炮弹
		for(int i=0;i<shells.length;i++) {
			shells[i] =new Shell();
		}
		}

	public static void main(String[] args) {
		MyGameFrame f = new MyGameFrame();
		f.lauchJFame();
	}
	private Image offScreenImage = null;
	 
	public void update(Graphics g) {
	    if(offScreenImage == null)
	        offScreenImage = this.createImage(Constant.GAME_WITH,Constant.GAME_HEIGHT);//这是游戏窗口的宽度和高度
	     
	    Graphics gOff = offScreenImage.getGraphics();
	    paint(gOff);
	    g.drawImage(offScreenImage, 0, 0, null);
	}
}

