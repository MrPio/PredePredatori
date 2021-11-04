package system;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Set;
import java.util.Vector;

import javax.swing.ImageIcon;

import animation.Bounce_interpolator;
import animation.Pow_interpolator;
import animation.Sine_interpolator;
import gui.SimulationGUI;
import utilities.Point;

public class Pray {
	public static int STEP_MILLIS=17;
	public static final int SIZE_PNG=20;
	private static final int MAX_SONS=1;
	public double START_TOTAL_ENERGY=1.0d;
	
	public enum aggression {dove,hawk};
	public enum animation {
		sine(2.0d,0.0d,0.0d),
		bounce(7.0d,0.2d,0.0d),
		bounce_fast(8d,1.3d,0.0d),//4-12 |0.1-0.4
		pow(0.0d,0.0d,1.0),
		curve; 
		public double frequency=0.0d;
		public double amplitude=0.0d;
		public double pow_value=1.0d;
		animation(){}
		animation(double frequency,double amplitude,double pow_value){
	    	this.frequency=frequency;
	    	this.amplitude=amplitude;
	    	this.pow_value=pow_value;
		}
	};
	
	
	private aggression personality;
	private double speed=0.1d;
	private double size= 1.0d;
	private double sense_radius=10.0d;
	private boolean is_moving=false;
	private Enviroment my_enviroment;
	public final static int standard_scale=30;
	public static BufferedImage pray_png = new BufferedImage(SIZE_PNG,SIZE_PNG,BufferedImage.TYPE_INT_ARGB);
	public static BufferedImage pray_png_red = new BufferedImage(SIZE_PNG,SIZE_PNG,BufferedImage.TYPE_INT_ARGB);
	public static BufferedImage pray_png_green = new BufferedImage(SIZE_PNG,SIZE_PNG,BufferedImage.TYPE_INT_ARGB);
	public static BufferedImage pray_png_blue = new BufferedImage(SIZE_PNG,SIZE_PNG,BufferedImage.TYPE_INT_ARGB);
	public ImageIcon pray_png_image_icon=new ImageIcon(pray_png.getScaledInstance(standard_scale, standard_scale, Image.SCALE_DEFAULT));
	public double X=0.0d,Y=0.0d;
	public double start_X=0.0d,start_Y=0.0d;
	public double total_energy=START_TOTAL_ENERGY;
	public int eaten_food=0;
	
	public Pray() {};
	public Pray(double X,double Y,double speed,double size,double sense_radius,aggression personality,Enviroment my_enviroment) {
		this.X=X;
		this.Y=Y;
		this.start_X=X;
		this.start_Y=Y;
		this.speed=speed;
		this.size=size;
		this.sense_radius=sense_radius;
		this.personality=personality;
		this.my_enviroment=my_enviroment;
	}
	
	//Getter e Setter
	public aggression getPersonality() {
		return personality;
	}
	public double getSpeed() {
		return speed;
	}
	public double getSize() {
		return size;
	}
	public double getSenseRadius() {
		return sense_radius;
	}
	public Pray setPersonality(aggression personality) {
		this.personality=personality;
		return this;
	}
	public Pray setSpeed(double speed) {
		this.speed=speed;
		return this;
	}
	public Pray setSize(double size) {
		this.size=size;
		return this;
	}
	public Pray setSenseRadius(double sense_radius) {
		this.sense_radius=sense_radius;
		return this;
	}
	
	//METODI PRIVATI
	private void performStep(double from_X,double from_Y,double to_X, double to_Y,double time,
 animation anim) {
		switch(anim) {
		case sine:
			Sine_interpolator sine=new Sine_interpolator(anim.frequency);
			X=from_X+(to_X-from_X)*sine.interpolatorX(time);
			Y=from_Y+(to_Y-from_Y)*sine.interpolatorY(time);
			break;
		case bounce:
			Bounce_interpolator bounce=new Bounce_interpolator(anim.frequency,anim.amplitude);
			X=from_X+(to_X-from_X)*bounce.interpolatorX(time);
			Y=from_Y+(to_Y-from_Y)*bounce.interpolatorY(time);
			break;
		case bounce_fast:
			Bounce_interpolator bounce_fast=new Bounce_interpolator(anim.frequency,anim.amplitude);
			X=from_X+(to_X-from_X)*bounce_fast.interpolatorX(time);
			Y=from_Y+(to_Y-from_Y)*bounce_fast.interpolatorY(time);
			break;
		case pow:
			Pow_interpolator pow=new Pow_interpolator(anim.pow_value);
			X=from_X+(to_X-from_X)*pow.interpolatorX(time);
			Y=from_Y+(to_Y-from_Y)*pow.interpolatorY(time);
			break;
		}
		SimulationGUI.setJLabelLocationGui(my_enviroment.pray_table.get(this),(int)X,(int)Y);
	}
	private void whereAmI() {
		System.out.println("(X,Y) = ("+X+","+Y+")");
	}
	private Food isFoodNearby() {
		if(eaten_food==2)return null;//una pray non mangia più di 2 food
		Vector<Food> near_food=new Vector<Food>();
		for(Food food: my_enviroment.food_table.keySet())
			if(utilities.Utilities.module(food.X-this.X, food.Y-this.Y)<=sense_radius) 
				near_food.add(food);
		if(near_food.isEmpty())return null;
		return near_food.elementAt((int)(Math.random()*near_food.size()));
	}
	private boolean eatFood(Food food) {
		if(food==null)return false;
		double time=utilities.Utilities.module(X-food.X, Y-food.Y)
				/my_enviroment.enviroment_speed/getSpeed()/1.6;
		move(food.X,food.Y,animation.bounce,time,false);
		if(!food.isAvaible())return true;
		utilities.Utilities.playSound(utilities.Utilities.sounds_tag.food.list[
		        (int)(Math.random()*utilities.Utilities.sounds_tag.food.list.length)]);
		//System.out.println("***CIBO TROVATO***");
		SimulationGUI.frame.getContentPane().remove(my_enviroment.food_table.get(food));
		my_enviroment.food_table.remove(food,my_enviroment.food_table.get(food));
		total_energy+=food.gotEaten();
		++eaten_food;
		return true;
	}
	public boolean isMoving() {return is_moving;};
	
	//METODI PUBBLICI
	public boolean spendEnergy() {
		if(total_energy<=0)return false;
		total_energy-=size*size*size*speed*speed;
		return true;
	}
	public void move(double to_X, double to_Y,animation anim,double time,boolean seek_for_food) {
    	//System.out.println("FROM: (X,Y) = ("+X+","+Y+")\tTO: (X,Y) = ("+to_X+","+to_Y+")");
    	total_energy-=size*size*size*speed*speed;
    	double from_X=X,from_Y=Y;
    	double current_time=0;
    	
    	while(current_time/time<1) {
    		is_moving=true;
            performStep(from_X,from_Y,to_X, to_Y, current_time/time, anim);
            if(seek_for_food)
            	if(eatFood(isFoodNearby()))
            		return;
            
            try {Thread.sleep(STEP_MILLIS);} 
            catch (InterruptedException e) {e.printStackTrace();}
            current_time+=STEP_MILLIS;
            
            //IS ENVIROMENT PAUSED OR STOPPED?
            if(seek_for_food&& my_enviroment.isStopped())break;
            while(seek_for_food&&my_enviroment.isPaused()) {
            	if(my_enviroment.isStopped())break;
            	try{Thread.sleep(60);}catch(InterruptedException e){}
            }
    	}
    	
    	if(!my_enviroment.isStopped())
    		performStep(from_X,from_Y,to_X, to_Y, 1, anim);
    	//System.out.print("Sono arrivato alla posizione: ");
    	//whereAmI();
    	is_moving=false;
		return;
	}
	
	public void die() {
		my_enviroment.removePray(this);
	}
	public void reproduce() {
		survive();
		for(int i=0;i<1+(int)(Math.random()*MAX_SONS);i++) {
			Point point=utilities.Utilities.randomPointOnRectangle(my_enviroment.SIZE_X-40, my_enviroment.SIZE_Y-40);
			double child_speed=this.speed+Math.random()*0.3-0.15;
			if(child_speed<=0.01)child_speed=Math.random()*0.2+0.01;
			
			Pray pray=new Pray(point.X+20, point.Y+20, 
					this.speed+Math.random()*0.3-0.15,
					1.0,
					120.0,
					Pray.aggression.dove,my_enviroment);
			pray.pray_png_image_icon=this.pray_png_image_icon;
			System.out.println(this.total_energy);
			pray.total_energy=this.total_energy;
			my_enviroment.addPray(pray);
		}
	}
	public void survive() {
		eaten_food=0;
		total_energy=START_TOTAL_ENERGY;
	}
}
