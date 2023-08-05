package oldschoolproject.kits;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import lombok.Getter;
import oldschoolproject.Main;

public abstract class BaseKit {
	
	private static Map<BaseKit, Long> cooldowned = new HashMap<>();

	@Getter
	String name;
	
	@Getter
	ItemStack skillItem, menuItem;
	
	@Getter
	Integer cooldownSeconds;
	
	BukkitTask cooldownTask;

	BossBar bossBar = Bukkit.getServer().createBossBar("", BarColor.RED, BarStyle.SOLID);

	public BaseKit(String name, ItemStack skillItem, Integer cooldownSeconds) {
		this.name = name;
		this.skillItem = skillItem;
		this.menuItem = skillItem;
		this.cooldownSeconds = cooldownSeconds;
	}
	
	public BaseKit(String name, ItemStack skillItem, ItemStack menuItem, Integer cooldownSeconds) {
		this.name = name;
		this.skillItem = skillItem;
		this.menuItem = menuItem;
		this.cooldownSeconds = cooldownSeconds;
	}
	
	public abstract boolean activateSkill(PlayerInteractEvent e);
	
	public abstract BaseKit createInstance();
	
	public void useSkill(PlayerInteractEvent e) {
		if (this.activateSkill(e)) {
			applyCooldown(e.getPlayer());
		}
	}

	public void applyCooldown(Player player) {
		player.setCooldown(getSkillItem().getType(), getCooldownSeconds() * 20);

		cooldowned.put(this, System.currentTimeMillis());

		this.bossBar.setColor(BarColor.RED);
		this.bossBar.addPlayer(player);

		this.cooldownTask = new BukkitRunnable() {

			public void run() {

				double progress = Math.max(0.0, Math.min(1.0, getCooldownTime() / getCooldownSeconds())); // Ensure progress is between 0.0 and 1.0

				BaseKit.this.bossBar.setTitle("§c§l!! COOLDOWN " + getCooldownTimeFormatted() + "s !!");
				BaseKit.this.bossBar.setProgress(progress);

				if (getCooldownTime() <= 0) {
					cancelCooldown(player);

					player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(""));

					BaseKit.this.bossBar.setTitle("§a§l!! SKILL READY !!");
					BaseKit.this.bossBar.setColor(BarColor.GREEN);
				}
			}
		}.runTaskTimer(Main.getInstance(), 0, 1);
	}

	public void removeBossBar(Player player) {
		this.bossBar.removePlayer(player);
	}
	
	public void cancelCooldown(Player player) {
		this.cooldownTask.cancel();

		cooldowned.remove(BaseKit.this);

		player.setCooldown(getSkillItem().getType(), 0);
	}

	public double getCooldownTime() {
		return (double)(cooldowned.get(this) / 1000.0 + this.getCooldownSeconds() - System.currentTimeMillis() / 1000.0);
	}
	
	public String getCooldownTimeFormatted() {
		return String.valueOf(new DecimalFormat("0.0").format(getCooldownTime()));
	}

	public boolean isOnCooldown() {
		return cooldowned.containsKey(this);
	}
}
