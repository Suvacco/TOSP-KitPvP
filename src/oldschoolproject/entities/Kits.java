package oldschoolproject.entities;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import oldschoolproject.Main;
import oldschoolproject.kits.SKangaroo;
import oldschoolproject.kits.SPvP;

public enum Kits {
	
	PvP(new SPvP()),
	Kangaroo(new SKangaroo());
	
	Skill skill;
	
	private static Map<Kits, Long> cooldowned = new HashMap<>();
	
	Kits(Skill skill) {
		this.skill = skill;
	}
	
	public String getName() {
		return this.skill.name;
	}
	
	public ItemStack getSkillItem() {
		return this.skill.skillItem;
	}
	
	public boolean isOnCooldown() {
		return cooldowned.containsKey(this);
	}
	
	public int getCooldownSeconds() {
		return this.skill.cooldownSeconds;
	}
	
	public String getCooldownTime() {
		return String.valueOf(new DecimalFormat("#.##").format((double)(cooldowned.get(this) / 1000.0 + this.getCooldownSeconds() - System.currentTimeMillis() / 1000.0)));
	}
	
	public void activateSkill(Player player) {
		this.skill.activate(player);
		
		cooldowned.put(this, System.currentTimeMillis());
		
		new BukkitRunnable() {
			
			public void run() {
				
				cooldowned.remove(Kits.this);
				
				player.sendMessage("§6Habilidade recarregada");
			}
		}.runTaskLater(Main.getInstance(), this.getCooldownSeconds() * 20);
	}
}