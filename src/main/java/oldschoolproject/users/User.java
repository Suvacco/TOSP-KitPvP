package oldschoolproject.users;

import oldschoolproject.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.WeatherType;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.util.Vector;

import lombok.Getter;
import lombok.Setter;
import oldschoolproject.kits.BaseKit;
import oldschoolproject.warps.BaseWarp;

import java.util.*;

@Getter @Setter
public class User {

	// Key properties
	Player player;
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

	// Permissions
	PermissionAttachment permissionAttachment;

	public User(Player player) {
		this.player = player;
		this.uuid = player.getUniqueId();
		this.userName = player.getName();

		this.resetStats();
	}

	public User(UUID uuid, String userName) {
		this.userName = userName;
		this.uuid = uuid;

		this.resetStats();
	}

	public void reset() {
		this.player.setVelocity(new Vector());
		this.player.setFallDistance(0);
		this.player.setVisualFire(false);
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
		if (getPlayer() != null) {
			this.permissionAttachment = getPlayer().addAttachment(Main.getInstance());
		}

		setStat(UserStats.KILLSTREAK, 0);

		for (UserStats userStats : UserStats.values()) {
			if (userStats.isAutoManageable()) {
				setStat(userStats, 0);
			}
		}
	}

	public void loadPermissions(ArrayList<String> permissions) {
		if (getPlayer() != null) {
			for (String permission : permissions) {
				this.permissionAttachment.setPermission(permission, true);
			}
		}
	}

	public void loadStats(Map<String, Object> values) {
		this.setUserRank((String) values.get("rank"));

		this.loadPermissions((ArrayList<String>) values.get("permissions"));

		for (UserStats userStats : UserStats.values()) {
			if (userStats.isAutoManageable()) {
				setStat(userStats, values.get(userStats.name().toLowerCase()));
			}
		}
	}

	public Player getPlayer() {
		return this.player;
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
