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
    final int WIDTH = 800;
    final int HEIGHT = 600;
    final int SPEED = 3;
    public double x;
    public double y;
    public double theta;
    boolean tendency;
    //double agression;
    double speedLeft;
    double speedRight;
    ArrayList lights = new ArrayList();
    
    /**
     * 
     * @param p - initial point of robot
     * @param theta - initial rotation of robot
     */
    public Bot(Point p, int theta, boolean tendency, double speedLeft, double speedRight){
        this.x = p.x;
        this.y = p.y;
        this.theta = theta;
        this.tendency = tendency;
        this.speedLeft = speedLeft;
        this.speedRight = speedRight;
        //this.agression = agression;
    }
    
    /**
     * Move bot
     */
    public void updateBot(ArrayList lights){
        this.lights = lights;
        
        //locations for sensors 1 and 2
        double s1x = x;
        double s1y = y;
        double s2x = (x + 2 * Math.cos(Math.toRadians(theta + 90)));
        double s2y = (y + 2 * Math.sin(Math.toRadians(theta + 90)));
       
        //Inesity of light for sensor 1 and 2
        double s1Intensity = 0;
        double s2Intensity = 0;
        
        //get most intense light source for each sensor
        for (ListIterator<Light> iter = lights.listIterator(); iter.hasNext(); ){
                Light element = iter.next();
                double temp = 100 / Point.distance(s1x, s1y, element.x+LIGHTSIZE/2, element.y+LIGHTSIZE/2);

                if(temp > 100)
                    temp = 100;
                if(temp > s1Intensity)
                    s1Intensity = temp;
                
                temp = 100 / Point.distance(s2x, s2y, element.x+LIGHTSIZE/2, element.y+LIGHTSIZE/2);
                if(temp > 100)
                    temp = 100;
                if(temp > s2Intensity)
                    s2Intensity = temp;
        }
        
        //Determine theta based off s1 intensity and s2 intensity
        if(!tendency){
            //Move away from light
            //theta = theta + agression/4 * (s1Intensity - s2Intensity);
            theta = theta + (s1Intensity * speedLeft - s2Intensity * speedRight);
        }
        else{
            //Move toward light
            //theta = theta + agression/4 * (s2Intensity - s1Intensity);
            theta = theta + (s2Intensity * speedRight - s1Intensity * speedLeft);
        }
 
        //Move bots based on speed and angle
        x = (x + SPEED * Math.cos(Math.toRadians(theta)));
        y = (y + SPEED * Math.sin(Math.toRadians(theta)));
        
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
