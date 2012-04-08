package de.paeterick.dropblock;

public class Config {
	private static double _distance = 0.2D;
	private static boolean enabled = true;

	public static double getDistance() {
		return _distance;
	}

	public static void setDistance(double distance) {
		Config._distance = distance;
	}

	public static boolean isEnabled() {
		return enabled;
	}

	public static void setEnabled(boolean enabled) {
		Config.enabled = enabled;
	}
}
