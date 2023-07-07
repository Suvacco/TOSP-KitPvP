package oldschoolproject.entities;

import org.bukkit.entity.Player;

public class User {
	
	Player player;
	Kit kit;
	
	public User(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public void setKit(Kit kit) {
		this.kit = kit;
	}
	
	public Kit getKit() {
		return this.kit;
	}
	
	public boolean hasKit() {
		return this.kit != null;
	}
}
