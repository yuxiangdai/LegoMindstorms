import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.lang.Math;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.*;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;

public class bfsbot {

	public static void main(String[] args) throws Exception {
		int kp=4;
		int pspeed = 50; 
		long tStart;

		//Motor.C.setSpeed(250);
		//Motor.B.setSpeed(250);
		
		
		//Motor.B.rotate(970,true);
		//Motor.C.rotate(-970);
	
		
		EV3UltrasonicSensor sonic = new EV3UltrasonicSensor(SensorPort.S4);
		while (!Button.ENTER.isDown()) {
			int sampleSize = sonic.sampleSize();
			double desired=30;
			double x = 0;
			double actual=0;
			double error=0;
			int correction;
			double prev = 0;
			
			
			desired = 20;
			float[] sonicsample = new float[sampleSize];
			sonic.fetchSample(sonicsample, 0);
			actual=sonicsample[0]*100;
			
			error = desired - actual;
			
			correction = (int)(kp * error);
			System.out.println(x);
			Motor.C.setSpeed(pspeed + correction); //slow the right hand wheel
			Motor.B.setSpeed(pspeed - correction); //speed up the left hand wheel
			
			Motor.C.forward();
			Motor.B.forward();
			x =(power(pspeed,2)-power(power(actual-prev,2),0.5))+x;
			prev=actual;
			
			sonic.close();
		}
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


/**
 * A simple undirected and unweighted graph implementation.
 * 
 * @param <T> The type that would be used as vertex.
 */
public class Graph<T> {
    final private HashMap<T, Set<T>> adjacencyList;
    
    /**
     * Create new Graph object.
     */
    public Graph() {
        this.adjacencyList = new HashMap<>();
    }
    
    /**
     * Add new vertex to the graph.
     * 
     * @param v The vertex object. 
     */
    public void addVertex(T v) {
        if (this.adjacencyList.containsKey(v)) {
            throw new IllegalArgumentException("Vertex already exists.");
        }
        this.adjacencyList.put(v, new HashSet<T>());
    }
    
    /**
     * Remove the vertex v from the graph.
     * 
     * @param v The vertex that will be removed.
     */
    public void removeVertex(T v) {
        if (!this.adjacencyList.containsKey(v)) {
            throw new IllegalArgumentException("Vertex doesn't exist.");
        }
        
        this.adjacencyList.remove(v);
        
        for (T u: this.getAllVertices()) {
            this.adjacencyList.get(u).remove(v);
        }
    }
    
    /**
     * Add new edge between vertex. Adding new edge from u to v will
     * automatically add new edge from v to u since the graph is undirected.
     * 
     * @param v Start vertex.
     * @param u Destination vertex.
     */
    public void addEdge(T v, T u) {
        if (!this.adjacencyList.containsKey(v) || !this.adjacencyList.containsKey(u)) {
            throw new IllegalArgumentException();
        }
        
        this.adjacencyList.get(v).add(u);
        this.adjacencyList.get(u).add(v);
    }
    
    /**
     * Remove the edge between vertex. Removing the edge from u to v will 
     * automatically remove the edge from v to u since the graph is undirected.
     * 
     * @param v Start vertex.
     * @param u Destination vertex.
     */
    public void removeEdge(T v, T u) {
        if (!this.adjacencyList.containsKey(v) || !this.adjacencyList.containsKey(u)) {
            throw new IllegalArgumentException();
        }
        
        this.adjacencyList.get(v).remove(u);
        this.adjacencyList.get(u).remove(v);
    }
    
    /**
     * Check adjacency between 2 vertices in the graph.
     * 
     * @param v Start vertex.
     * @param u Destination vertex.
     * @return <tt>true</tt> if the vertex v and u are connected.
     */
    public boolean isAdjacent(T v, T u) {
        return this.adjacencyList.get(v).contains(u);
    }
    
    /**
     * Get connected vertices of a vertex.
     * 
     * @param v The vertex.
     * @return An iterable for connected vertices.
     */
    public Iterable<T> getNeighbors(T v) {
        return this.adjacencyList.get(v);
    }
    
    /**
     * Get all vertices in the graph.
     * 
     * @return An Iterable for all vertices in the graph.
     */
    public Iterable<T> getAllVertices() {
        return this.adjacencyList.keySet();
    }
}