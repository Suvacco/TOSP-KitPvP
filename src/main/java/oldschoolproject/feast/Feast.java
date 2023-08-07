package oldschoolproject.feast;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

public class Feast extends BukkitRunnable {
	
	private Location location;
	
	private static final int SECONDS_TO_SPAWN = 300;
	
	private int secondsToSpawn;
	
//	private static ItemStack[] defaultLoot = new ItemStack[] {
//		
//	};
	
	private Location[] feastLocs = new Location[] {
		createChestLoc(2, -2),
		createChestLoc(2, 0),
		createChestLoc(2, 2),
		createChestLoc(1, -1),
		createChestLoc(1, 1),
		createChestLoc(0, -2),
		createChestLoc(0, 0),
		createChestLoc(0, 2),
		createChestLoc(-1, -1),
		createChestLoc(-1, 1),
		createChestLoc(-2, -2),
		createChestLoc(-2, 0),
		createChestLoc(-2, 2),
	};
	
	public Feast(Location location) {
		this.location = location;
		
		this.secondsToSpawn = SECONDS_TO_SPAWN;
	}
	
	public void spawn() {
		Arrays.stream(feastLocs).forEach(location -> {
			
		});
	}
	
	public void destroy() {
		
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	private Location createChestLoc(int xOffset, int zOffset) {
		return new Location(Bukkit.getWorld("world"), this.location.getX() + xOffset, this.location.getY(), this.location.getZ() + zOffset);
	}

	@Override
	public void run() {
		if (secondsToSpawn < 1) {
			
		}
		
		secondsToSpawn--;
	}
}
