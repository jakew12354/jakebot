package jakebot;
import robocode.*;
import static robocode.util.Utils.normalRelativeAngleDegrees;

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
			setTurnGunRight(gunTurnAmt);
            count++;
            if (count > 1) {
                gunTurnAmt = -20;
            }
            if (count > 2) {
                gunTurnAmt = 40;
            }
			if (count > 4) {
				trackName = null;
			}
		execute();
		}
	}

	
	public void onScannedRobot(ScannedRobotEvent e) {
		if (trackName != null && !e.getName().equals(trackName)) {
            return;
        }
        
        if (trackName == null) {
            trackName = e.getName();
            out.println("Tracking " + trackName);
        }
	count = 0;
	if (e.getDistance() > 100) {
		gunTurnAmt = normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getRadarHeading()));
	setTurnGunRight(gunTurnAmt);
	turnRight(e.getBearing());
	ahead(e.getDistance() - 90);
	return;
	}
	gunTurnAmt = normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getRadarHeading()));
setTurnGunRight(gunTurnAmt);
fire(3);
if (e.getDistance() < 50) {
		if (e.getBearing() > -50 && e.getBearing() <=50) {
			back(10);
		} else {
		ahead(10);
		}
	}
	scan();
}

public void onHitRobot(HitRobotEvent e) {
	if (trackName != null && !trackName.equals(e.getName())) {
		out.println("Tracking " + e.getName() + " due to collision");
	}
	trackName = e.getName();
	gunTurnAmt = normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getRadarHeading()));
	setTurnGunRight(gunTurnAmt);
	fire(2);
	back(30);
}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		// Replace the next line with any behavior you would like
		e.getDistance (); 
		if (e.getDistance() < 50 ) {
			if (e.getBearing() > -50 && e.getBearing() <=50) {
				back(20);
			}else {
				scan();
			}
		}
	}
	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		// Replace the next line with any behavior you would like
		back(20);
	}	
}
