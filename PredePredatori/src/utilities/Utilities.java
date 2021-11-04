package utilities;

import java.io.BufferedInputStream;
import java.io.File;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import gui.Settings;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Utilities {
	public enum sounds_tag{
		food(new sounds[] {sounds.food_1,sounds.food_2}),
		click(new sounds[] {sounds.click_1,sounds.click_2});
		public sounds[] list;
		sounds_tag(sounds[] list){
			this.list=list;
		}
	}
	public enum sounds{
		food_1("food_1.wav"),
		food_2("food_2.wav"),
		click_1("click_1.wav"),
		click_2("click_2.wav");
		String file_name;
		sounds(String file_name){
			this.file_name=file_name;
		}
	}
	public static double module(double a,double b) {
		return Math.sqrt(Math.pow(a, 2)+Math.pow(b, 2));
	}
	public static synchronized void playSound(sounds sound) {
		  new Thread(new Runnable() {
		    public void run() {
		      try {  	  
		        Clip clip = AudioSystem.getClip();
		        
		        AudioInputStream inputStream = AudioSystem.getAudioInputStream(
		        		new BufferedInputStream(getClass().getResource("/"+sound.file_name).openStream()));
		        clip.open(inputStream);
		        clip.start(); 
		      } catch (Exception e) {
		        System.err.println(e);
		      }
		    }
		  }).start();
		}
	public static Point randomPointOnRectangle (double width,double height) {
		//height     width
		//-----------==================================
		double rnd=Math.random()*(height+width);
		int rnd2=(int)(Math.random()*2);
		if(rnd<=height) {
			if(rnd2==0)
				return new Point(0,Math.random()*height);
			if(rnd2==1)
				return new Point(width,Math.random()*height);
		}
		else {
			if(rnd2==0)
				return new Point(Math.random()*width,0);
			if(rnd2==1)
				return new Point(Math.random()*width,height);
		}
		return null;
	}
	public static BufferedImage rotate(BufferedImage image, double angle) {
		angle=angle/180*Math.PI;
	    double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
	    int w = image.getWidth(), h = image.getHeight();
	    int neww = (int)Math.floor(w*cos+h*sin), newh = (int) Math.floor(h * cos + w * sin);
	    GraphicsConfiguration gc = getDefaultConfiguration();
	    BufferedImage result = gc.createCompatibleImage(neww, newh, Transparency.TRANSLUCENT);
	    Graphics2D g = result.createGraphics();
	    g.translate((neww - w) / 2, (newh - h) / 2);
	    g.rotate(angle, w / 2, h / 2);
	    g.drawRenderedImage(image, null);
	    g.dispose();
	    return result;
	}
	private static GraphicsConfiguration getDefaultConfiguration() {
	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice gd = ge.getDefaultScreenDevice();
	    return gd.getDefaultConfiguration();
	}
}