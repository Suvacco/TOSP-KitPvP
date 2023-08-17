package oldschoolproject.feast;

import java.util.Arrays;
import java.util.Random;

import lombok.Getter;
import lombok.Setter;
import oldschoolproject.Main;
import oldschoolproject.holograms.Hologram;
import oldschoolproject.utils.builders.FireworkBuilder;
import oldschoolproject.utils.builders.ItemBuilder;
import oldschoolproject.utils.formatters.ChatFormatter;
import org.bukkit.*;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class Feast {

	private static final Integer[] BROADCAST_MESSAGE_ANCHORS = new Integer[] {120, 60, 30, 15, 10, 5, 4, 3, 2, 1};

	@Getter @Setter
	private int secondsToSpawn; // 120

	@Getter @Setter
	private int secondsToDespawn; // 15

	@Getter @Setter
	private int secondsInCooldown; // 300

	private static ItemStack[] defaultLoot = new ItemStack[] {
			new ItemBuilder(Material.STONE_SWORD).setName("§bStone Sword").toItemStack(),
			new ItemBuilder(Material.FISHING_ROD).setName("§aFishing Rod").toItemStack(),
			new ItemBuilder(Material.FIRE_CHARGE).setName("§5Fireball").toItemStack(),
			new ItemBuilder(Material.IRON_SWORD).setName("§6Iron Sword").toItemStack(),
			new ItemBuilder(Material.ENDER_PEARL).setName("§bEnder Pearl").toItemStack(),
			new ItemBuilder(Material.LEATHER_HELMET).setName("§aLeather Helmet").toItemStack(),
			new ItemBuilder(Material.LEATHER_CHESTPLATE).setName("§aLeather Chestplate").toItemStack(),
			new ItemBuilder(Material.LEATHER_LEGGINGS).setName("§aLeather Leggins").toItemStack(),
			new ItemBuilder(Material.LEATHER_BOOTS).setName("§aLeather Boots").toItemStack(),
			new ItemBuilder(Material.CHAINMAIL_HELMET).setName("§bChainmail Helmet").toItemStack(),
			new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).setName("§bChainmail Chestplate").toItemStack(),
			new ItemBuilder(Material.CHAINMAIL_LEGGINGS).setName("§bChainmail Leggins").toItemStack(),
			new ItemBuilder(Material.CHAINMAIL_BOOTS).setName("§bChainmail Boots").toItemStack(),
			new ItemBuilder(Material.IRON_CHESTPLATE).setName("§dIron Chestplate").toItemStack(),
			new ItemBuilder(Material.TURTLE_HELMET).setName("§aTurtle Helmet").toItemStack(),
			new ItemBuilder(Material.TOTEM_OF_UNDYING).setName("§6Totem of Undying").toItemStack(),
			new ItemBuilder(Material.TNT).setName("§dTNT").toItemStack(),
			new ItemBuilder(Material.SNOWBALL, 4).setName("§aSnowball").toItemStack(),
			new ItemBuilder(Material.GOLDEN_APPLE).setName("§dGolden Apple").toItemStack(),
			new ItemBuilder(Material.POTION).setName("§dSprint Potion").setPotionEffect(PotionEffectType.SPEED, PotionType.SPEED, 30 * 20, 2, false).toItemStack(),
			new ItemBuilder(Material.POTION).setName("§dFire Resistance Potion").setPotionEffect(PotionEffectType.FIRE_RESISTANCE, PotionType.FIRE_RESISTANCE, 30 * 20, 1, false).toItemStack(),
			new ItemBuilder(Material.POTION).setName("§dRegeneration Potion").setPotionEffect(PotionEffectType.REGENERATION, PotionType.REGEN, 30 * 20, 2, false).toItemStack(),
			new ItemBuilder(Material.SPLASH_POTION).setName("§dSlowness Potion").setPotionEffect(PotionEffectType.SLOW, PotionType.SLOWNESS, 30 * 20, 4, false).toItemStack(),
			new ItemBuilder(Material.SPLASH_POTION).setName("§dHeal Potion").setPotionEffect(PotionEffectType.HEAL, PotionType.INSTANT_HEAL, 30 * 20, 2, false).toItemStack(),
			new ItemBuilder(Material.SPLASH_POTION).setName("§dDamage Potion").setPotionEffect(PotionEffectType.HARM, PotionType.INSTANT_DAMAGE, 30 * 20, 2, false).toItemStack(),
	};

	private Hologram hologram;

	private Location location;

	private BukkitTask feastTask;

	private String id;

	public Feast(String id, Location location, int secondsToSpawn, int secondsToDespawn, int secondsInCooldown) {
		this.location = location;

		this.secondsToSpawn = secondsToSpawn;

		this.secondsToDespawn = secondsToDespawn;

		this.secondsInCooldown = secondsInCooldown;

		this.id = id;

		this.hologram = new Hologram(this.location.clone().add(0, 1, 0), Arrays.asList("§6§lFEAST §7- §d" + id, "-"));

		this.hologram.spawn();

		this.destroy();

		this.beginSpawnCountdown();
	}

	public void delete() {
		this.destroy();

		this.hologram.destroy();

		this.feastTask.cancel();
	}

	private Location[] getChestLocs() {
		return new Location[] {
				getChestLoc(2, -2),
				getChestLoc(2, 0),
				getChestLoc(2, 2),
				getChestLoc(1, -1),
				getChestLoc(1, 1),
				getChestLoc(0, -2),
				getChestLoc(0, 0),
				getChestLoc(0, 2),
				getChestLoc(-1, -1),
				getChestLoc(-1, 1),
				getChestLoc(-2, -2),
				getChestLoc(-2, 0),
				getChestLoc(-2, 2)
		};
	}

	// 3 - 5 items per chest
	// 0 - 26 slots in the inventory

	public void spawn() {
		new FireworkBuilder(this.location, Color.YELLOW, Color.ORANGE).setType(FireworkEffect.Type.STAR).setExplosionSeconds(3);

		Arrays.stream(getChestLocs()).forEach(location -> {
			location.getBlock().setType(Material.CHEST);

			new FireworkBuilder(location, Color.PURPLE, Color.FUCHSIA).setType(FireworkEffect.Type.BALL).detonate();

			if (location.getBlock().getState() instanceof Chest) {

				Chest chest = (Chest)location.getBlock().getState();

				Random random = new Random();

				int randomNumberOfItems = random.nextInt(3) + 3;

				int i = 0;

				while (i < randomNumberOfItems) {

					int randomSlot = random.nextInt(27);

					ItemStack loot = defaultLoot[random.nextInt(defaultLoot.length)];

					if (chest.getInventory().getItem(randomSlot) != null) {
						continue;
					}

					if (chest.getInventory().contains(loot)) {
						continue;
					}

					chest.getInventory().setItem(randomSlot, loot);
					i++;
				}
			}
		});

		this.hologram.setLine(1, "§bChests spawned!");

		beginDespawnCountdown();
	}

	public void destroy() {
		Arrays.stream(getChestLocs()).forEach(location -> {
			location.getBlock().setType(Material.AIR);
			location.getWorld().spawnParticle(Particle.CLOUD, location, 5);
		});
	}
	
	public Location getLocation() {
		return this.location;
	}

	public static ItemStack[] getDefaultLoot() {
		return defaultLoot;
	}

	private Location getChestLoc(int xOffset, int zOffset) {
		return new Location(Bukkit.getWorld("world"), this.location.getX() + xOffset, this.location.getY(), this.location.getZ() + zOffset);
	}

	public void beginDespawnCountdown() {

		this.feastTask = new BukkitRunnable() {
			int seconds = Feast.this.secondsToDespawn;

			@Override
			public void run() {
				hologram.setLine(1, "§c" + ChatFormatter.formatSeconds(seconds));

				if (seconds < 1) {
					destroy();
					beginCooldown();
					cancel();
					Bukkit.broadcastMessage("§6§lFEAST §7- §d(" + Feast.this.id + ") §cDespawned! §eNow entering cooldown...");
					Feast.this.location.getWorld().playSound(Feast.this.location, Sound.ENTITY_CHICKEN_EGG, 1.0F, 1.0F);
				}

				seconds--;
			}
		}.runTaskTimer(Main.getInstance(), 20 * 5, 20);
	}

	public void beginSpawnCountdown() {
		this.feastTask = new BukkitRunnable() {
			int seconds = Feast.this.secondsToSpawn;
			@Override
			public void run() {
				Feast.this.hologram.setLine(1, "§a" + ChatFormatter.formatSeconds(seconds));

				if (Arrays.asList(BROADCAST_MESSAGE_ANCHORS).contains(seconds)) {
					Bukkit.broadcastMessage("§6§lFEAST §7- §d(" + Feast.this.id + ") §bwill spawn in §a" + (seconds < 60 ? seconds + "s" : ChatFormatter.formatSeconds(seconds)));
				}

				if (seconds < 1) {
					spawn();
					cancel();
					Bukkit.broadcastMessage("§6§lFEAST §7- §d(" + Feast.this.id + ") §bspawned! §cDespawning in " + Feast.this.secondsToDespawn + "s");
					Feast.this.location.getWorld().playSound(Feast.this.location, Sound.BLOCK_END_PORTAL_SPAWN, 1.2F, 1.0F);
				}

				seconds--;
			}
		}.runTaskTimer(Main.getInstance(), 0, 20);
	}

	public void beginCooldown() {
		hologram.setLine(1, "§eIn cooldown...");

		this.feastTask = new BukkitRunnable() {
			@Override
			public void run() {
				beginSpawnCountdown();
			}
		}.runTaskLater(Main.getInstance(), 20L * this.secondsInCooldown);
	}

    public void updateLocation(Location location) {

		deleteFeast();

		this.location = location;

		resetFeast();
    }

	private void deleteFeast() {
		this.destroy();

		this.feastTask.cancel();
	}

	private void resetFeast() {
		this.beginSpawnCountdown();

		this.hologram.updateLocation(location.clone().add(0, 1, 0));
	}

	public void updateFeast() {
		deleteFeast();
		resetFeast();
	}
}
