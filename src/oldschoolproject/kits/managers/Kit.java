package oldschoolproject.kits.managers;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import oldschoolproject.Main;

public abstract class Kit {
	
	private static Map<Kit, Long> cooldowned = new HashMap<>();

	String name;
	ItemStack skillItem, kitSelectorItem;
	Integer cooldownSeconds;
	BukkitTask cooldownTask;
	
	public Kit(String name, ItemStack skillItem, Integer cooldownSeconds) {
		this.name = name;
		this.skillItem = skillItem;
		this.kitSelectorItem = skillItem;
		this.cooldownSeconds = cooldownSeconds;
	}
	
	public Kit(String name, ItemStack skillItem, ItemStack kitSelectorItem, Integer cooldownSeconds) {
		this.name = name;
		this.skillItem = skillItem;
		this.kitSelectorItem = kitSelectorItem;
		this.cooldownSeconds = cooldownSeconds;
	}
	
	public void useSkill(PlayerInteractEvent e) {
		if (hasCooldown()) {
			
			e.getPlayer().setCooldown(getSkillItem().getType(), getCooldownSeconds() * 20);
	
			cooldowned.put(this, System.currentTimeMillis());
			
			this.cooldownTask = new BukkitRunnable() {
				
				public void run() {
					
					cooldowned.remove(Kit.this);
					
					e.getPlayer().sendMessage("§6Habilidade recarregada");
					
					this.cancel();
				}
			}.runTaskLater(Main.getInstance(), this.getCooldownSeconds() * 20);
		}
		
		this.activateSkill(e);
	}
	
	public void cancelCooldown(Player p) {
		this.cooldownTask.cancel();
		
		cooldowned.remove(Kit.this);
		
		p.setCooldown(getSkillItem().getType(), 0);
	}
	
	public abstract void activateSkill(PlayerInteractEvent e);
	
	public abstract Kit createInstance();

	public String getName() {
		return this.name;
	}
	
	public boolean hasCooldown() {
		return cooldownSeconds != null && cooldownSeconds != 0;
	}
	
	public ItemStack getKitSelectorItem() {
		return this.kitSelectorItem;
	}
	
	public ItemStack getSkillItem() {
		return this.skillItem;
	}
	
	public boolean isOnCooldown() {
		return cooldowned.containsKey(this);
	}
	
	public Integer getCooldownSeconds() {
		return this.cooldownSeconds;
	}
	
	public String getCooldownTimeFormatted() {
		return String.valueOf(new DecimalFormat("#.##").format((double)(cooldowned.get(this) / 1000.0 + this.getCooldownSeconds() - System.currentTimeMillis() / 1000.0)));
	}

}
