package system;

import java.awt.Image;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.text.Utilities;

import gui.Settings;
import gui.SimulationGUI;
import system.Pray.animation;
import utilities.Point;

public class Enviroment {
	public  int SIZE_X=600;
	public  int SIZE_Y=400;
	public double enviroment_speed=0.25d;
	public int selected_enviroment_speed=0;
	public  HashMap<Pray,JLabel> pray_table=new HashMap<Pray,JLabel>();
	public  HashMap<Predator,JLabel> predator_table=new HashMap<Predator,JLabel>();
	public  HashMap<Food,JLabel> food_table=new HashMap<Food,JLabel>();
	
	private double day_lenght=5.0d;
	private long day_start=0;
	private boolean day_end=false;
	private int current_day=1;
	private boolean stop=false;
	private boolean pause=false;
	
	public Enviroment() {}
	public Enviroment(int SIZE_X,int SIZE_Y) {
		this.SIZE_X=SIZE_X;
		this.SIZE_Y=SIZE_Y;
	}
	
	public void pause() {pause=true;}
	public void resume() {pause=false;}
	public boolean isPaused() {return pause;}
	public boolean isStopped() {return stop;}
	public void printSummary() {
		double speed_med=0.0d;
		double size_med=0.0d;
		double sense_radius_med=0.0d;
		for(Pray pray:pray_table.keySet()) {
			speed_med+=pray.getSpeed();
			size_med+=pray.getSize();
			sense_radius_med+=pray.getSenseRadius();
		}
		speed_med/=pray_table.keySet().size();
		size_med/=pray_table.keySet().size();
		sense_radius_med/=pray_table.keySet().size();
		System.out.println("***************************************");
		System.out.println("NUMBERO OF PRAYS = "+pray_table.keySet().size());
		System.out.println("Speed_med        = "+(float)speed_med);
		System.out.println("size_med         = "+(float)size_med);
		System.out.println("sense_radius_med = "+(float)sense_radius_med);
		System.out.println("***************************************");
	}
	
	//spawn pray
	public void start() {
		day_start=System.currentTimeMillis();
		for(Pray pray:this.pray_table.keySet()) {
			if(!pray.isMoving())
				movePrayRandomly(pray);
		}
		dayStatusCheck();
		stop=false;
	}
	public void stop() {
		Settings.start_stop.setEnabled(true);
		stop=true;
	}
	public void addPray(Pray pray) {
		new Thread() {
		    public void run() {
				//JLabel
				pray_table.put(pray, prayToJLabel(pray));
				
				double time=utilities.Utilities.module(pray.X, pray.Y)/pray.getSpeed()/12;
				double backup_X=pray.X,backup_Y=pray.Y;
				pray.X=0;pray.Y=0;
				SimulationGUI.setJLabelLocationGui(pray_table.get(pray),0,0);
				pray.move(backup_X, backup_Y, animation.bounce, time, false);
				System.out.println("Ho aggiunto una preda:\n\tX="+pray.X+"\n\tY="+pray.Y);
		    }
		}.start();
	}
	public void addPrayRandomly(int how_many) {
		Enviroment this_enviroment=this;
		for (int i=0;i<how_many;i++) {
			new Thread() {
			    public void run() {
					//Pray
					Point point=utilities.Utilities.randomPointOnRectangle(SIZE_X-40, SIZE_Y-40);
					Pray pray=new Pray(point.X+20, point.Y+20, 
							Math.random()*0.4+0.1,
							1.0d+Math.random()*0.0d,
							120.0,
							Pray.aggression.dove,this_enviroment);
					//JLabel
					pray_table.put(pray, prayToJLabel(pray));
					
					double time=utilities.Utilities.module(pray.X, pray.Y)/pray.getSpeed()/12;
					double backup_X=pray.X,backup_Y=pray.Y;
					pray.X=0;pray.Y=0;
					SimulationGUI.setJLabelLocationGui(pray_table.get(pray),0,0);
					pray.move(backup_X, backup_Y, animation.bounce, time, false);
					
					System.out.println("Ho aggiunto una preda:\n\tX="+pray.X+"\n\tY="+pray.Y);
				}
			}.start();
		}
	}
	public JLabel prayToJLabel(Pray pray) {
		int scale=(int)(pray.getSize()*30.0d);
		JLabel image=new JLabel(new ImageIcon(pray.pray_png_image_icon.getImage().getScaledInstance(scale, scale, Image.SCALE_DEFAULT)));
		image.setBounds(0, 0, SIZE_X, SIZE_Y);
		SimulationGUI.frame.getContentPane().add(image);
		SimulationGUI.setJLabelLocationGui(image,(int)pray.X,(int)pray.Y);
		return image;
	}
	public void removePray(Pray pray) {
		if(pray_table.keySet().contains(pray)) {
			System.out.println("***RIMUOVO UNA PREDA***"+pray_table.get(pray));
			SimulationGUI.frame.getContentPane().remove(pray_table.get(pray));
			try{SwingUtilities.updateComponentTreeUI(SimulationGUI.frame);}//aggiorno il frame
			catch(Exception e) {}
			pray_table.remove(pray,pray_table.get(pray));
		}
	}
	//spawn food	
	public void addFoodRandomly(int how_many) {
		for(int i=0;i<how_many;i++) {
			//Pray
			Food food=new Food(Math.random()*SIZE_X, Math.random()*SIZE_Y);
			//JLabel
			food_table.put(food, foodToJLabel(food));
			
			//System.out.println("Ho aggiunto un cibo:\n\tX="+food.X+"\n\tY="+food.Y);
		}
	}
	public JLabel foodToJLabel(Food food) {
		JLabel image=new JLabel(new ImageIcon(Food.food_png.getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
		image.setBounds(0, 0, SIZE_X, SIZE_Y);
		SimulationGUI.frame.getContentPane().add(image);
		SimulationGUI.setJLabelLocationGui(image,(int)food.X,(int)food.Y);
		return image;
	}
	//DINAMIC
	public void movePrayRandomly(Pray pray) {
		//Is the pray already moving?
		if(pray.isMoving())return;
			new Thread() {
			    public void run() {
			    	while(true) {                    
				    	double to_X=Math.random()*SIZE_X, to_Y=Math.random()*SIZE_Y;
				    	//System.out.println("[Enviroment] Muovo la preda");
				    	//SCELGO LA DURATA IN BASE ALLO SPOSTAMENTO E ALLA VELOCITA (t=s/v)
				    	double time=
				    			utilities.Utilities.module(pray.X-to_X, pray.Y-to_Y)
				    			/pray.getSpeed()/enviroment_speed;
				    	//SCELGO L'ANIMAZIONE
				    	Pray.animation anim=Pray.animation.bounce;
				    	//MUOVO LA PREDA
				    	//Has the pray enough energy to move?
				    	if(!pray.spendEnergy()) {
				    		restartAllPrayLocation(pray);
				    		break;
				    	};
				    	pray.move(to_X, to_Y, anim, time,true);
				    	if(isStopped())break;
			    	}
			    }  
			}.start();
	}
	private void dayStatusCheck() {
		new Thread() {
			public void run() {
				double paused_time=0;
				final int update_time=100;
				while(true) {
		    		//giorno finito
					if(isPaused())paused_time+=update_time;
					else {
			    		Settings.day_count.setText("Day "+ current_day+"    "+
			    		(float)(((System.currentTimeMillis()-day_start-paused_time)*enviroment_speed*100)/
			    				(day_lenght*1000.0d))+"%");
					}
		    		if((System.currentTimeMillis()-day_start-paused_time)*enviroment_speed>=day_lenght*1000.0d) {
		    			stop=true;
		    			try {Thread.sleep(300);} 
		    			catch (InterruptedException e) {}
		    			++current_day;
		    			Settings.day_count.setText("Day "+ current_day);
		    			Settings.start_stop.setText("START DAY");
		    			day_end=true;
		    			//endOfDay();
		    			restartAllPrayLocation(null);
		    			break;
		    		}
		    		try {Thread.sleep(update_time);}
		    		catch (InterruptedException e) {e.printStackTrace();}
				}
			}
		}.start();
	}
	private void restartAllPrayLocation(Pray my_pray) {
		if(my_pray==null) {
			boolean retry=false;
			do {
				try {
					System.out.println(this.pray_table.size());
					for(Pray pray:this.pray_table.keySet()) {
						new Thread() {
							public void run(){
								System.out.println("sono nel for");
								double to_X=pray.start_X;
								double to_Y=pray.start_Y;
								double time=utilities.Utilities.module(pray.X-to_X, pray.Y-to_Y)
										/pray.getSpeed()/enviroment_speed/5;
								Pray.animation anim=Pray.animation.pow;
								anim.pow_value=2.0d;
								pray.move(to_X, to_Y, anim, time,false);
								endOfDay(pray);
							}
						}.start();
					}
					retry=false;
				}catch(java.util.ConcurrentModificationException e) {
					System.out.println("***RIPROVO[restartAllPrayLocation]***");
					retry=true;
				}
			}while(retry);
		}
		else {
			if(my_pray.isMoving())return;
			double to_X=my_pray.start_X;
			double to_Y=my_pray.start_Y;
			double time=utilities.Utilities.module(my_pray.X-to_X, my_pray.Y-to_Y)
					/my_pray.getSpeed()/enviroment_speed;
			Pray.animation anim=Pray.animation.bounce;
			my_pray.move(to_X, to_Y, anim, time,false);
		}
		
	}
	private void endOfDay(Pray pray) {
		stop();
		switch(pray.eaten_food) {
			case 0:
				pray.die();
				break;
			case 1:
				pray.survive();
				break;
			case 2:
				pray.reproduce();
				break;
		}
	}
}
