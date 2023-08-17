package oldschoolproject.users;

import oldschoolproject.permissions.PermissionStorage;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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

	PermissionStorage permissionsStorage = new PermissionStorage();

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

		Arrays.stream(UserStats.values()).forEach(stat -> { if (stat.isServerControlled()) { setStat(stat, 0); }});
	}
	@SuppressWarnings("unchecked")
	public void loadDatabaseDataIntoUser(Map<String, Object> values) {
		this.setUserRank((String) values.get("rank"));

		this.permissionsStorage.addPermissions((List<String>) values.get("permissions"));

		Arrays.stream(UserStats.values()).forEach(stat -> { if (stat.isServerControlled()) { setStat(stat, values.get(stat.name().toLowerCase())); }});
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

	public PermissionStorage getPermissionStorage() {
		return this.permissionsStorage;
	}

	public void refreshRankPermissions() {
		this.permissionsStorage.getPermissions().removeIf(perm -> perm.startsWith("rank."));

		this.permissionsStorage.addPermissions(this.getUserRank().getPermissions());
	}

	public void setUserRank(String userRank) {
		this.userRank = UserRank.valueOf(userRank);

		refreshRankPermissions();
	}

	public void setUserRank(UserRank userRank) {
		this.userRank = userRank;

		refreshRankPermissions();
	}

	public void dieEffect() {
		int option = new Random().nextInt(3) + 1;

		switch (option) {
			case 1:
				this.getPlayer().getWorld().playEffect(this.getPlayer().getLocation().clone().add(0, 1, 0), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
				break;
			case 2:
				this.getPlayer().getWorld().spawnParticle(Particle.ITEM_CRACK, this.getPlayer().getLocation().clone().add(0, 1, 0), 50, new ItemStack(Material.REDSTONE_BLOCK));
				break;
			case 3:
				this.getPlayer().getWorld().spawnParticle(Particle.BLOCK_CRACK, this.getPlayer().getLocation().clone().add(0, 1, 0), 50, Material.REDSTONE_BLOCK.createBlockData());
				break;
		}
	}
}
