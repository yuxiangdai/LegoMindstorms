package bfsbot;


import java.lang.Math;

import bfsbot.Graph;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.*;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;

public class bfsbot {

	public static void main(String[] args) throws Exception {
		Graph<int[]> graph = new Graph<int[]>();
	
		
		
		for(int i = 0 ; i< 5; i++){
			for(int j = 0 ; j< 5; j++){
				int[] arr = {i,j};
				graph.addVertex(arr);
			}
		}
		
		int[] arr = ;
		
		graph.addVertex(arr);
		
		isAdjacent(T v, T u);
	}
	
	public static double power (double n,double p) {
        return java.lang.Math.pow(n,p);
    }
	
	public static void pivot (){
		//leftMot();
		//rightMot();
	}
	
	public static void forward(int distance){
		int angle=360*distance/12;
		Motor.B.rotate(angle,true);
		Motor.C.rotate(angle);
	}
	
}

