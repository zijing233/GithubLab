package cn.zijing.game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

/**
 * 游戏物体的父类
 * @author 子敬
 *
 *
 */

public class GameObject {
	 Image img;
	 double x,y;
	 int width,height;
	 int speed;
	
	public void drawSelf(Graphics g) {
		g.drawImage(img, (int)x, (int)y, null);
		
	}

	public GameObject(Image img, double x, double y, int width, int height, int speed) {
		super();
		this.img = img;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.speed = speed;
	}

	public GameObject(Image img, double x, double y) {
		super();
		this.img = img;
		this.x = x;
		this.y = y;
	}
	
public GameObject() {
	
}	
/**
 * 返回物体所在矩形
 * @return
 */

	public Rectangle getRect() {
		return new Rectangle((int)x, (int)y, width, height);
	}
	
}
