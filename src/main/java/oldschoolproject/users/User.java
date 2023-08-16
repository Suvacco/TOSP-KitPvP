package oldschoolproject.users;

import oldschoolproject.Main;
import org.bukkit.GameMode;
import org.bukkit.WeatherType;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.util.Vector;

import lombok.Getter;
import lombok.Setter;
import oldschoolproject.kits.BaseKit;
import oldschoolproject.warps.BaseWarp;

import java.util.*;

@Getter @Setter
public class User {

	Player player;

	UUID uuid;
	String userName;

	UserGuard userGuard;
	UserRank userRank;

	PermissionAttachment permissionAttachment;

	Map<UserStats, Object> statsMap = new HashMap<>();

	BaseKit kit;
	BaseWarp warp;

	// Player online
	public User(Player player) {
		this.player = player;
		this.uuid = player.getUniqueId();
		this.userName = player.getName();

		this.buildUser();
	}

	// Player offline
	public User(UUID uuid, String userName) {
		this.userName = userName;
		this.uuid = uuid;

		this.buildUser();
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

	public void buildUser() {
		this.setUserRank(UserRank.MEMBER);
		this.userGuard = UserGuard.Protected;

		if (this.player != null) {
			this.permissionAttachment = player.addAttachment(Main.getInstance());
			// Rank permissions
			this.loadPermissionAttachment(this.getUserRank().getPermissions());
		}

//		setStat(UserStats.KILLSTREAK, 0);

		Arrays.stream(UserStats.values()).forEach(stat -> { if (stat.isNotControllable()) { setStat(stat, 0); }});
	}
	@SuppressWarnings("unchecked")
	public void loadDatabaseDataIntoUser(Map<String, Object> values) {
		this.setUserRank((String) values.get("rank"));

		if (this.player != null) {
			// User permissions
			this.loadPermissionAttachment((ArrayList<String>) values.get("permissions"));
		} else {
			this.setStat(UserStats.PERMISSIONS, values.get("permissions"));
		}

		Arrays.stream(UserStats.values()).forEach(stat -> { if (stat.isNotControllable()) { setStat(stat, values.get(stat.name().toLowerCase())); }});
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

	public void loadPermissionAttachment(List<String> permissions) {
		for (String permission : permissions) {
			addPermission(permission);
		}
	}

	public void refreshRankPermissions() {
		for (String permission : this.getUserRank().getPermissions()) {
			removePermission(permission);
		}

		this.loadPermissionAttachment(this.getUserRank().getPermissions());
	}

	public void addPermission(String permission) {
		getPermissionAttachment().setPermission(permission, true);

		for (PermissionAttachmentInfo paInfo : getPlayer().getEffectivePermissions()) {
			if (paInfo.getAttachment() != null && paInfo.getAttachment().getPlugin().equals(Main.getInstance())) {
				paInfo.getAttachment().setPermission(permission, true);
			}
		}
	}

	public void removePermission(String permission) {
		getPermissionAttachment().unsetPermission(permission);

		for (PermissionAttachmentInfo paInfo : getPlayer().getEffectivePermissions()) {
			if (paInfo.getAttachment() != null && paInfo.getAttachment().getPlugin().equals(Main.getInstance())) {
				paInfo.getAttachment().unsetPermission(permission);
			}
		}
	}

	public void setUserRank(String userRank) {
		this.userRank = UserRank.valueOf(userRank);
	}

	public void setUserRank(UserRank userRank) {
		this.userRank = userRank;
	}
}
