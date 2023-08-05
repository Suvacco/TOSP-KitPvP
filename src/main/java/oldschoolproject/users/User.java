package oldschoolproject.users;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.WeatherType;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import lombok.Getter;
import lombok.Setter;
import oldschoolproject.kits.BaseKit;
import oldschoolproject.warps.BaseWarp;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter @Setter
public class User {

	// Key properties
	UUID uuid;
	String userName;

	// Initialized values
	UserGuard userGuard = UserGuard.Protected;
	UserRank userRank = UserRank.MEMBER;

	// Modifiable integers
	Map<UserStats, Object> statsMap = new HashMap<>();

	// Player driven objects
	BaseKit kit;
	BaseWarp warp;

	public User(UUID uuid, String userName) {
		this.userName = userName;
		this.uuid = uuid;

		this.resetStats();
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

		this.setUserGuard(UserGuard.Protected);
		this.resetKit();
		this.teleportToWarp();
		this.setWarpItems();
	}

	public void resetKit() {
		if (!this.hasKit()) {
			return;
		}

		if (this.getKit().isOnCooldown()) {
			this.getKit().cancelCooldown(this.getPlayer());
		}

		this.getKit().removeBossBar(this.getPlayer());
		this.kit = null;
	}

	public void resetStats() {
		setStat(UserStats.KILLSTREAK, 0);

		for (UserStats userStats : UserStats.values()) {
			if (userStats.isAutoManageable()) {
				setStat(userStats, 0);
			}
		}
	}

	public void loadStats(Map<String, Object> values) {
		this.setUserRank((String)values.get("rank"));

		for (UserStats userStats : UserStats.values()) {
			if (userStats.isAutoManageable()) {
				setStat(userStats, values.get(userStats.name().toLowerCase()));
			}
		}
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
		return this.userGuard == UserGuard.Protected;
	}
	
	public boolean hasKit() {
		return this.kit != null;
	}

	public void setStat(UserStats stat, Object value) {
		this.statsMap.put(stat, value);
	}

	public Object getStat(UserStats stat) {
		return this.statsMap.get(stat);
	}

	public void setUserRank(String userRank) {
		this.userRank = UserRank.valueOf(userRank);
	}
}
