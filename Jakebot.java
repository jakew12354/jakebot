package jakebot;
import robocode.*;
//import java.awt.Color;

// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

/**
 * Jakebot - a robot by (your name here)
 */
public class Jakebot extends AdvancedRobot
{
     int count = 0;
    double gunTurnAmt;
    String trackName;
	/**
	 * run: Jakebot's default behavior
	 */
	public void run() {
	trackName = null;
    setAdjustGunForRobotTurn(true);
    gunTurnAmt = 5;
		while(true) {
			// Replace the next 4 lines with any behavior you would like
			setTurnGunRight(gunTurnAmt);
            count++;
            if (count > 1){
                gunTurnAmt = -20;
            }
            if (count > 2) {
                gunTurnAmt = 40;
            }
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		// Replace the next line with any behavior you would like
		if (trackName != null && !e.getName().equals(trackName)) {
            return;
        }
        
        if (trackName == null) {
            trackName = e.getName();
            out.println("Tracking " + trackName);
        } 
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		// Replace the next line with any behavior you would like
		back(10);
	}
	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		// Replace the next line with any behavior you would like
		back(20);
	}	
}
