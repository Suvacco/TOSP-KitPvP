package oldschoolproject.users;

import oldschoolproject.users.data.RankType;
import org.bukkit.GameMode;
import org.bukkit.WeatherType;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import lombok.Getter;
import lombok.Setter;
import oldschoolproject.utils.kits.BaseKit;
import oldschoolproject.utils.warps.BaseWarp;

@Getter
@Setter
public class User {
	
	Player player;
	BaseKit kit;
	BaseWarp warp;
	@Getter @Setter
	Integer kills, deaths, coins, duelsCount, duelsWins, duelsLosses;
	@Getter @Setter
	RankType rank;
	UserState state;
	
	public User(Player player) {
		this.player = player;
		this.rank = RankType.MEMBER;
		this.kills = this.deaths = this.coins = this.duelsCount = this.duelsWins = this.duelsLosses = 0;
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
		
		this.teleportToWarp();
		this.setWarpItems();
	}
	
	public void setWarpItems() {
		this.getWarp().setDefaultItems(this.getPlayer());
	}
	
	public void teleportToWarp() {
		this.getPlayer().teleport(this.getWarp().getSpawnLocation());
	}
	
	public boolean isProtected() {
		return this.state == UserState.Protected;
	}
	
	public boolean hasKit() {
		return this.kit != null;
	}
	
	public enum UserState {
		Playing, Protected;
	}

}
