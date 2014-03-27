/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package b_bots;

import java.awt.Point;

/**
 *
 * @author Brian
 */
public class Bot {
    final int BOTSIZE = 14;
    final int SPEED = 1;
    final int WIDTH = 800;
    final int HEIGHT = 600;
    public double x;
    public double y;
    public double theta;
    
    /**
     * 
     * @param p - initial point of robot
     * @param theta - initial rotation of robot
     */
    public Bot(Point p, int theta){
        this.x = p.x;
        this.y = p.y;
        this.theta = theta;
    }
    
    /**
     * Move bot
     */
    public void updateBot(){
        //Move bots based on speed and angle
        x = (x + SPEED * Math.cos(theta * (Math.PI/180)));
        y = (y + SPEED * Math.sin(theta * (Math.PI/180)));
        
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
