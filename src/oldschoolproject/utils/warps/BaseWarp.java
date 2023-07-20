package oldschoolproject.utils.warps;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import oldschoolproject.utils.builders.FileBuilder;

public abstract class BaseWarp {
	
	String warpName;
	FileBuilder fileBuilder;
	Location spawnLocation;
	ItemStack menuItem;
	List<Player> players;
	
	public BaseWarp(String warpName, ItemStack menuItem) {
		this.warpName = warpName;
		this.menuItem = menuItem;
		this.fileBuilder = new FileBuilder("warps/" + warpName);
		this.players = new ArrayList<>();
		
		loadSpawnLocation();
	}
	
	public void loadSpawnLocation() {
		Location defaultSpawn = Bukkit.getWorld("world").getSpawnLocation();
		
		if (fileBuilder.get("spawn") == null) {
			setSpawnLocation(defaultSpawn);
			setLocation("spawn", defaultSpawn);
			return;
		}
		
		this.spawnLocation = this.loadLocation("spawn");
	}
	
	public Location getSpawnLocation() {
		return this.spawnLocation;
	}
	
	public void setSpawnLocation(Location spawnLocation) {
		this.spawnLocation = spawnLocation;
	}
	
	public String getName() {
		return this.warpName;
	}
	
	public void addPlayer(Player player) {
		this.players.add(player);
		
		handlePlayerJoin(player);
	}
	
	public void removePlayer(Player player) {
		this.players.remove(player);
		
		handlePlayerQuit(player);
	}
	
	public List<Player> getPlayerList() {
		return this.players;
	}
	
	public ItemStack getMenuItem() {
		return this.menuItem;
	}
	
	public abstract void handlePlayerJoin(Player player);
	
	public abstract void handlePlayerQuit(Player player);
	
	public void setLocation(String path, Location location) {
		this.fileBuilder.set(path + ".x", location.getX());
		this.fileBuilder.set(path + ".y", location.getY());
		this.fileBuilder.set(path + ".z", location.getZ());
	}
	
	public Location loadLocation(String path) {
		double x = (double) this.fileBuilder.get(path + ".x");
		double y = (double) this.fileBuilder.get(path + ".y");
		double z = (double) this.fileBuilder.get(path + ".z");
		
		return new Location(Bukkit.getWorld("world"), x, y, z);
	}
}
