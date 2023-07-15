package oldschoolproject.entities;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.WeatherType;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import oldschoolproject.utils.builders.ItemBuilder;

public class User {
	
	Player player;
	Kit kit;
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
		return this.state == State.Protected;
	}
	
	public void setKit(Kit kit) {
		this.kit = kit;
	}
	
	public Kit getKit() {
		return this.kit;
	}
	
	public void reset() {
		this.player.setVisualFire(false);
		this.player.setVelocity(new Vector());
		this.player.setTotalExperience(0);
		this.player.setAbsorptionAmount(0);
		this.player.setAllowFlight(false);
		this.player.setArrowsInBody(0);
		this.player.setExhaustion(0);
		this.player.setExp(0);
		this.player.setFireTicks(0);
		this.player.setFlying(false);
		this.player.setFoodLevel(20);
		this.player.setFreezeTicks(0);
		this.player.setGameMode(GameMode.SURVIVAL);
		this.player.setGliding(false);
		this.player.setGravity(true);
		this.player.setHealth(20D);
		this.player.setInvisible(false);
		this.player.setInvulnerable(false);
		this.player.setLeashHolder(null);
		this.player.setLevel(0);
		this.player.setNoDamageTicks(0);
		this.player.setPlayerWeather(WeatherType.CLEAR);
		this.player.setSaturation(10F);
		this.player.getInventory().setArmorContents(null);
		this.player.getInventory().clear();
		this.player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(16);
		this.state = State.Protected;
		this.kit = null;
		
		this.player.getInventory().setItem(4, new ItemBuilder(Material.CHEST).setName("§6Kit Selector").toItemStack());
	}
	
	public boolean hasKit() {
		return this.kit != null;
	}
}
