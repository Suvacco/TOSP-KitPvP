package oldschoolproject.users;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.WeatherType;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import oldschoolproject.kits.managers.Kit;
import oldschoolproject.kits.managers.KitEnum;
import oldschoolproject.users.managers.UserState;
import oldschoolproject.utils.builders.ItemBuilder;

public class User {
	
	Player player;
	Kit kit;
	UserState state;
	
	public User(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public void setState(UserState state) {
		this.state = state;
	}
	
	public UserState getState() {
		return this.state;
	}
	
	public boolean isProtected() {
		return this.state == UserState.Protected;
	}
	
	public void setKit(KitEnum kit) {
		this.kit = kit.instanceKit();
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
		this.state = UserState.Protected;
		this.kit = null;
		
		this.player.getInventory().setItem(4, new ItemBuilder(Material.CHEST).setName("§6Kits").toItemStack());
	}
	
	public boolean hasKit() {
		return this.kit != null;
	}
}
