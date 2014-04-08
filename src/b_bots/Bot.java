/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package b_bots;

import java.awt.Point;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 *
 * @author Brian
 */
public class Bot {
    final int BOTSIZE = 14;
    final int LIGHTSIZE = 20;
    final int WIDTH = 800+10;
    final int HEIGHT = 600+10;
    final double SPEED = 2;
    final int L = 10;
    public double x;
    public double y;
    public double theta;
    double k1;
    double k2;
    double k3;
    double k4;
    ArrayList lights = new ArrayList();
    
    
    /**
     * 
     * @param p - initial point of robot
     * @param theta - initial rotation of robot
     */
    public Bot(Point p, int theta, double k1, double k2, double k3, double k4){
        this.x = p.x;
        this.y = p.y;
        this.theta = theta;
        this.k1 = k1;
        this.k2 = k2;
        this.k3 = k3;
        this.k4 = k4;
    }
    
    /**
     * Move bot
     */
    public void updateBot(ArrayList lights){
        this.lights = lights;
        
        //locations for sensors 1 and 2
        double s1x = (x - 12 * Math.cos(Math.toRadians(theta + 90)));
        double s1y = (y - 12 * Math.sin(Math.toRadians(theta + 90)));
        double s2x = (x + 12 * Math.cos(Math.toRadians(theta + 90)));
        double s2y = (y + 12 * Math.sin(Math.toRadians(theta + 90)));
       
        //Intensity of light for sensor 1 and 2
        double s1Intensity = 0;
        double s2Intensity = 0;
        
        
        //get most intense light source for each sensor
        for (ListIterator<Light> iter = lights.listIterator(); iter.hasNext(); ){
                Light element = iter.next();
                double temp = 100 / Point.distance(s1x, s1y, element.x+LIGHTSIZE/2, element.y+LIGHTSIZE/2);

                if(temp > 10)
                    temp = 10;
                if(temp > s1Intensity)
                    s1Intensity = temp;
                
                temp = 100 / Point.distance(s2x, s2y, element.x+LIGHTSIZE/2, element.y+LIGHTSIZE/2);
                if(temp > 10)
                    temp = 10;
                if(temp > s2Intensity)
                    s2Intensity = temp;
        }
        
        
        //Determine theta, x, and y based off s1 intensity, s2 intensity, and k matrix
        double cl = (k1*s1Intensity + k2*s2Intensity);
        double cr = (k3*s1Intensity + k4*s2Intensity);
        double vl =  cl/3 + SPEED;
        double vr =  cr/3 + SPEED;
        
        if(vl != vr){
            double r = (L/2) * ((vl+vr)/(vr-vl));
            double w = (vr-vl)/L;           
            double iccx = x - r * Math.sin(Math.toRadians(theta));
            double iccy = y + r * Math.cos(Math.toRadians(theta));
            double oldx = x;
            double oldy = y;
            double oldtheta = Math.toRadians(theta);
            
            x = Math.cos(w) * (oldx - iccx) + -Math.sin(w) * (oldy - iccy) + iccx;
            y = Math.sin(w) * (oldx - iccx) + Math.cos(w) * (oldy - iccy) + iccy;
            theta = Math.toDegrees(oldtheta + w);
        }
        else{
            x = (x + SPEED * Math.cos(Math.toRadians(theta)));
            y = (y + SPEED * Math.sin(Math.toRadians(theta)));
        }

        //If out of bounds, move bot to according edge
        if(x+BOTSIZE < 0){
            x = WIDTH;
        }
        else if(x > WIDTH){
            x = 0 - BOTSIZE;
        }
        else if(y > HEIGHT){
            y = 0 - BOTSIZE;
        }
        else if(y + BOTSIZE < 0){
            y = HEIGHT;
        }
               
    }
    
}
