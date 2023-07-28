package oldschoolproject.utils.warps;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import oldschoolproject.utils.builders.FileBuilder;

public abstract class BaseWarp {
	
	protected String warpName;
	protected FileBuilder fileBuilder;
	protected ItemStack menuItem;
	
	public BaseWarp(String warpName, ItemStack menuItem) {
		this.warpName = warpName;
		this.menuItem = menuItem;
		this.fileBuilder = new FileBuilder("warps/" + warpName + ".yml");
		
		loadSpawnLocation();
	}
	
	public void loadSpawnLocation() {
		Location defaultSpawn = Bukkit.getWorld("world").getSpawnLocation();
		
		if (fileBuilder.get("spawn") == null) {
			setLocation("spawn", defaultSpawn);
		}
	}
	
	public void teleportToLocation(Player player, String path) {
		Location loc = getLocation(path);
		
		if (loc == null) {
			player.teleport(getSpawnLocation());
			player.sendMessage("§cLocalização ainda não setada");
			return;
		}
		
		player.teleport(loc);
	}
	
	public Location getSpawnLocation() {
		return this.getLocation("spawn");
	}
	
	public String getName() {
		return this.warpName;
	}
	
	public void addPlayer(Player player) {
		this.handlePlayerJoin(player);
		
		this.setDefaultItems(player);
	}
	
	public void removePlayer(Player player) {
		this.handlePlayerQuit(player);
	}
	
	public ItemStack getMenuItem() {
		return this.menuItem;
	}
	
	public abstract void setDefaultItems(Player player);
	
	public abstract void handlePlayerJoin(Player player);
	
	public abstract void handlePlayerQuit(Player player);
	
	public void setLocation(String path, Location location) {
		this.fileBuilder.set(path + ".x", location.getX());
		this.fileBuilder.set(path + ".y", location.getY());
		this.fileBuilder.set(path + ".z", location.getZ());
		this.fileBuilder.set(path + ".yaw", location.getYaw());
		this.fileBuilder.set(path + ".pitch", location.getPitch());
	}
	
	public Location getLocation(String path) {
		if (this.fileBuilder.get(path + ".x") == null) {
			return null;
		}
		
		double x = (double) this.fileBuilder.get(path + ".x");
		double y = (double) this.fileBuilder.get(path + ".y");
		double z = (double) this.fileBuilder.get(path + ".z");
		double yaw = (double) this.fileBuilder.get(path + ".yaw");
		double pitch = (double) this.fileBuilder.get(path + ".pitch");
		
		return new Location(Bukkit.getWorld("world"), x, y, z, (float)yaw, (float)pitch);
	}
}
