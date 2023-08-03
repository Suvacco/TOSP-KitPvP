package oldschoolproject.users;

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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Getter @Setter
public class User {

	// Key properties
	UUID uuid;
	String userName;

	// Static values
	UserGuard userGuard = UserGuard.Protected;
	UserRank userRank = UserRank.MEMBER;

	// Modifiable integer stats
	Map<UserStats, Object> statsMap = new HashMap<>();

	// Objects
	BaseKit kit;
	BaseWarp warp;

	public User(UUID uuid, String userName) {
		this.userName = userName;
		this.uuid = uuid;

		this.resetStats();
	}

	public void resetStats() {
		for (UserStats userStats : UserStats.values()) {
			if (userStats.isModifiable()) {
				setStat(userStats, 0);
			}
		}
	}

	public void load(Map<String, Object> values) {
		this.setUserRank((String)values.get("rank"));

		// The only way is using this for each, using a map.entry will cause in
		// reading the name, _id and rank fields, which are not valid in the stats hash
		for (UserStats userStats : UserStats.values()) {
			if (userStats.isModifiable()) {
				setStat(userStats, values.get(userStats.name().toLowerCase()));
			}
		}
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
