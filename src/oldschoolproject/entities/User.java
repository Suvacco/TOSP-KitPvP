package oldschoolproject.entities;

import org.bukkit.entity.Player;

public class User {
	
	Player player;
	Kits kit;
	State state;
	
	public User(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public State getState() {
		return this.state;
	}
	
	public boolean isProtected() {
		return this.state != State.Ingame;
	}
	
	public void setKit(Kits kit) {
		this.kit = kit;
	}
	
	public Kits getKit() {
		return this.kit;
	}
	
	public boolean hasKit() {
		return this.kit != null;
	}
}
