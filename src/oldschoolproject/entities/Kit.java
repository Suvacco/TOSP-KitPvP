package oldschoolproject.entities;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import oldschoolproject.Main;
import oldschoolproject.utils.builders.ItemBuilder;

public enum Kit {
	
	PvP("PvP", new ItemBuilder(Material.FIREWORK_ROCKET).toItemStack(), null, 10);
	
	String name;
	ItemStack skillItem;
	Integer cooldownSeconds;
	Skill skill;
	
	static Map<Integer, Long> cooldowned = new HashMap<>();
	
	Kit(String name, ItemStack skillItem, Skill skill, Integer cooldownSeconds) {
		this.name = name;
		this.skillItem = skillItem;
		this.skill = skill;
		this.cooldownSeconds = cooldownSeconds;
	}
	
	public void activateSkill(Player player) {
		this.skill.activate();
		
		cooldowned.put(this.hashCode(), System.currentTimeMillis());
		
		new BukkitRunnable() {
			public void run() {
				cooldowned.remove(this.hashCode());
				
				player.sendMessage("§6Habilidade recarregada");
			}
		}.runTaskLater(Main.getInstance(), this.cooldownSeconds * 20);
	}
	
	public ItemStack getSkillItem() {
		return this.skillItem;
	}
	
	public boolean isOnCooldown() {
		return cooldowned.containsKey(this.hashCode());
	}
	
	public String getCooldownTime() {
		return String.valueOf(new DecimalFormat("#.##").format((cooldowned.get(this.hashCode())) + Long.valueOf(cooldownSeconds) - System.currentTimeMillis()));
	}
}