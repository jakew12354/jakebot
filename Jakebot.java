package jakebot;

import robocode.*;
import static robocode.util.Utils.normalRelativeAngleDegrees;

public class Jakebot extends AdvancedRobot {
	int count = 0;
	double gunTurnAmt;
	String trackName;

	public void run() {
		trackName = null;
		setAdjustGunForRobotTurn(true);
		gunTurnAmt = 5;
		while (true) {
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
			setTurnRight(e.getBearing());
			setAhead(e.getDistance() - 90);
			return;
		}
		gunTurnAmt = normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getRadarHeading()));
		setTurnGunRight(gunTurnAmt);
		setFireBullet(3);
		if (e.getDistance() < 50) {
			if (e.getBearing() > -50 && e.getBearing() <= 50) {
				setAhead(-10);
			} else {
				setAhead(10);
				setFireBullet(3);
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
		setFireBullet(2);
		setAhead(-50);
	}

	public void onHitByBullet(HitByBulletEvent e) {
		if (trackName != null && !trackName.equals(e.getName())) {
			out.println("Tracking " + e.getName() + " due to being shot");
		}
		trackName = e.getName();
		gunTurnAmt = normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getRadarHeading()));
		setTurnGunRight(gunTurnAmt);
		execute();
		scan();
	}

	public void onHitWall(HitWallEvent e) {
		e.getBearing();
		if (e.getBearing() < 90) {
			setTurnRight(90);
			setAhead(50);
		} else {
			setTurnLeft(90);
			setAhead(50);
		}
		count = 0;
	}

	public void onBulletmissed(BulletMissedEvent e) {
		count = 0;
	}

	public void onWin(WinEvent e) {
		setAhead(10000);
		setTurnLeft(90);
		setTurnRight(90);
	}
}
