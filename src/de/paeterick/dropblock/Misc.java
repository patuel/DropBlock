package de.paeterick.dropblock;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.TreeType;

public class Misc {
	public static Location add(Location loc, int x, int y, int z) {
		return new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y,
				loc.getZ() + z);
	}

	public static Location substract(Location loc, int x, int y, int z) {
		return new Location(loc.getWorld(), loc.getX() - x, loc.getY() - y,
				loc.getZ() - z);
	}

	public static Location add(Location loc, double x, double y, double z) {
		return new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y,
				loc.getZ() + z);
	}

	public static Location substract(Location loc, double x, double y, double z) {
		return new Location(loc.getWorld(), loc.getX() - x, loc.getY() - y,
				loc.getZ() - z);
	}

	public static double distance(Location loc1, Location loc2) {
		return Math.sqrt(Math.pow(loc1.getBlockX() - loc2.getBlockX(), 2.0D)
				+ Math.pow(loc1.getBlockY() - loc2.getBlockY(), 2.0D)
				+ Math.pow(loc1.getBlockZ() - loc2.getBlockZ(), 2.0D));
	}

	public static boolean canPassThrough(int id) {
		return (id == 0) || (id == 8) || (id == 9) || (id == 10) || (id == 11)
				|| (id == 51) || (id == 78) || (id == 6) || (id == 37)
				|| (id == 38) || (id == 39) || (id == 40) || (id == 50)
				|| (id == 55) || (id == 63) || (id == 65) || (id == 66)
				|| (id == 68) || (id == 70) || (id == 72) || (id == 69)
				|| (id == 77) || (id == 75) || (id == 76) || (id == 90);
	}

	public static boolean isReplacableBlock(int id) {
		return (id == 0) || (id == 8) || (id == 9) || (id == 10) || (id == 11)
				|| (id == 51) || (id == 78);
	}

	public static boolean isPlacableBlock(int id) {
		return ((id < 92) && (id != 0)) || (id == 323) || (id == 331)
				|| (id == 354) || (id == 326) || (id == 327) || (id == 259)
				|| (id == 324) || (id == 330) || (id == 295) || (id == 338);
	}

	public static boolean isAttachable(int id) {
		return (id == 50) || (id == 63) || (id == 65) || (id == 68)
				|| (id == 69) || (id == 75) || (id == 76) || (id == 77)
				|| (id == 323);
	}
	
	public static TreeType randomTreeType() {
		int r = new Random().nextInt(100) + 1;

		if (r <= 65) {
			return TreeType.TREE;
		}
		if ((r > 65) && (r <= 90)) {
			return TreeType.BIRCH;
		}
		if ((r > 90) && (r <= 100)) {
			return TreeType.BIG_TREE;
		}
		return null;
	}
}
