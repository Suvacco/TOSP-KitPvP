package oldschoolproject.users;

import oldschoolproject.users.ranks.Rank;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.WeatherType;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import lombok.Getter;
import lombok.Setter;
import oldschoolproject.utils.kits.BaseKit;
import oldschoolproject.utils.warps.BaseWarp;

import java.util.UUID;

@Getter @Setter
public class User {

	UUID uuid;
	String userName;

	BaseKit kit;
	BaseWarp warp;

	UserState state;
	Rank rank;
	Integer kills, deaths, coins, duelsCount, duelsWins, duelsLosses;

	public User(UUID uuid, String userName) {
		this.userName = userName;
		this.uuid = uuid;
		this.rank = Rank.MEMBER;
		this.kills = this.deaths = this.coins = this.duelsCount = this.duelsWins = this.duelsLosses = 0;
	}
	
	public void reset() {
		Player player = this.getPlayer();

		player.setVisualFire(false);
		player.setVelocity(new Vector());
		player.setTotalExperience(0);
		player.setAbsorptionAmount(0);
		player.setAllowFlight(false);
		player.setArrowsInBody(0);
		player.setExhaustion(0);
		player.setExp(0);
		player.setFireTicks(0);
		player.setFlying(false);
		player.setFoodLevel(20);
		player.setFreezeTicks(0);
		player.setGameMode(GameMode.SURVIVAL);
		player.setGliding(false);
		player.setGravity(true);
		player.setHealth(20D);
		player.setInvisible(false);
		player.setInvulnerable(false);
		player.setLeashHolder(null);
		player.setLevel(0);
		player.setNoDamageTicks(0);
		player.setPlayerWeather(WeatherType.CLEAR);
		player.setSaturation(10F);
		player.getInventory().setArmorContents(null);
		player.getInventory().clear();
		player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(16);

		this.state = UserState.Protected;

		this.resetKit();
		this.teleportToWarp();
		this.setWarpItems();
	}

	public void resetKit() {
		if (this.hasKit()) {
			this.getKit().removeBossBar(this.getPlayer());

			if (this.getKit().isOnCooldown()) {
				this.getKit().cancelCooldown(this.getPlayer());
			}
		}

		this.kit = null;
	}

	public Player getPlayer() {
		return Bukkit.getPlayer(this.uuid);
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

	public void setRank(String rank) {
		this.rank = Rank.valueOf(rank);
	}
	
	public enum UserState {
		Playing, Protected;
	}

}
