package bfs;
import java.util.*;
import lejos.hardware.Button;
import lejos.hardware.Button;
import lejos.hardware.lcd.*;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.motor.*;
import bfs.Graph;

public class Project {
	
	public static void main(String[] args) throws Exception {
		 Graph g = new Graph(25);

	     g.addEdge(0, 1);
	     g.addEdge(0, 5);
	     g.addEdge(1, 6);
	     g.addEdge(2, 7);
	     g.addEdge(2, 3);
	     g.addEdge(3, 4);
	     g.addEdge(6, 7);
	     g.addEdge(7, 8);
	     g.addEdge(8, 9);
	     g.addEdge(9, 14);
	     g.addEdge(10, 11);
	     g.addEdge(11, 12);
	     g.addEdge(12, 13);
	     g.addEdge(13, 18);
	     g.addEdge(14, 19);
	     g.addEdge(15, 16);
	     g.addEdge(15, 20);
	     g.addEdge(16, 17);
	     g.addEdge(17, 22);
	     g.addEdge(18, 23);
	     g.addEdge(19, 24);
	     g.addEdge(20, 21);
	     g.addEdge(22, 23);
	     g.addEdge(23, 24);

	     System.out.println("Following is Breadth First Traversal "+
	             "(starting from vertex 2)");

	     int start = 4;
	     int end = 21;
	     int heading = 0;
		 int endheading = 180;


	     List<Integer> path = new ArrayList<>();
	     List<Integer> real_path = new ArrayList<>();

	     // implement endpoint term
	     // ination for BFS ?
	     int test[] = g.BFS(start);
	     System.out.println("Previous vertex:");

	     int i = end;

	     while((test[i] != -1) && (i != start)){
	         System.out.print(i + ",");

	         String command;
	         int next = test[i];
	         int difference = next - i;

	         i = next;

//	         switch(difference){
//	             case 1: command = "up";
//	                     break;
//	             case 5: command = "up";
//	                 break;
//	             case -1: command = "down";
//	                 break;
//	             case -5: command = "left";
//	                 break;
//	         }

	         path.add(difference);
	         System.out.print(path + " ");
	     }
	     System.out.println(i);

	     Collections.reverse(path);
	     for (int s : path) {
	         real_path.add(-s);
	     }

	     System.out.println("path: "+ real_path);
		
		/////////////////////////////////////////////////
		
		
		
		
		
		Motor.B.setSpeed(90);
		Motor.C.setSpeed(90);

		
		while (!Button.ENTER.isDown()) {
			
		}
		
		
		
		
		
		
		for (int comm : real_path){
			
			heading = gyroscope_rotate(heading, comm);
			
			//heading = changeheading(heading, comm);
			move();
			if (Button.ENTER.isDown()){
				return;
			}
		}
		
		heading = gyroscope_rotate(heading, -5);
		
		//heading = changeheading(heading, endheading);
		
	}
	public static void turn(int angle){
		Motor.B.rotate(-angle,true); 
		Motor.C.rotate(angle); 
	}
	public static void move(){
		int foot = 636;
		Motor.B.rotate(foot,true);
		Motor.C.rotate(foot); 	
	}
	public static int gyroscope_rotate(int heading, int dir){
		
		

		int desiredHeading = 0;
		
		switch(dir){
			case 1: desiredHeading = 90;
					break;
			case 5: desiredHeading = 0;
					break;
			case -1: desiredHeading = 270;
					break;
			case -5: desiredHeading = 180;
					break;
		}
		
		double angle = desiredHeading - heading;
		angle = angle/1.02;
		//int angle = 180;
		
		EV3GyroSensor tilt = new EV3GyroSensor(SensorPort.S3);
		
		int sampleSize = tilt.sampleSize();
		float[] tiltsample = new float[sampleSize];
		float[] ratesample = new float[sampleSize];
		if (angle ==0){

		}
		else if (angle>0){
			while((tiltsample[0]) <= angle ){//-4
				Motor.B.setSpeed(70);
				Motor.C.setSpeed(70);
				Motor.B.backward();
				Motor.C.forward();
				tilt.getAngleMode().fetchSample(tiltsample, 0);
				tilt.getRateMode().fetchSample(ratesample, 0);
				LCD.clear();
				System.out.println(tiltsample[0] + " " + ratesample[0]);
			}
		}
		else{
			while((tiltsample[0]) >= angle){ //+4
				Motor.B.setSpeed(70);
				Motor.C.setSpeed(70);
				Motor.B.forward();
				Motor.C.backward();
				tilt.getAngleMode().fetchSample(tiltsample, 0);
				tilt.getRateMode().fetchSample(ratesample, 0);
				LCD.clear();
				System.out.println(tiltsample[0] + " " + ratesample[0]);
			}
		}
		Motor.B.stop(true);
		Motor.C.stop(true);
		tilt.close();
		
		int newheading = 0;
		if (dir==5){
			newheading = 0;
		}
		else if (dir == 1){
			newheading = 90;
		}
		else if (dir == -5){
			newheading = 180;
		}
		else{
			newheading = 270;
		}
		
		
		return newheading;
	}

	
	
	
	
	
	
	
	public static int changeheading(int heading, int dir){
		//5 right
		//1 up
		//-5 left
		//-1 down
		int fullturn = 825;
		int offset = 5;
		int newheading = 0;
		if (dir==5){
			newheading = 0;
		}
		else if (dir == 1){
			newheading = 90;
		}
		else if (dir == -5){
			newheading = 180;
		}
		else{
			newheading = 270;
		}
		if (newheading- heading >180){
			turn((newheading-heading -360)*fullturn/360-offset);
		}
		else if (newheading- heading <-180){
			turn((newheading-heading +360)*fullturn/360 +offset);
		}
		else if (newheading- heading <0){
			turn((newheading-heading)*fullturn/360 -offset);
		}
		else if (newheading- heading >0){
			turn((newheading-heading)*fullturn/360 +offset);
		}
		return newheading;
	}
}