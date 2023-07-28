package oldschoolproject.utils.kits;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

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
	
	public abstract void activateSkill(PlayerInteractEvent e);
	
	public abstract BaseKit createInstance();
	
	public void useSkill(PlayerInteractEvent e) {
		if (hasCooldown()) {
			
			e.getPlayer().setCooldown(getSkillItem().getType(), getCooldownSeconds() * 20);
	
			cooldowned.put(this, System.currentTimeMillis());
			
			this.cooldownTask = new BukkitRunnable() {
				
				public void run() {
					
					cooldowned.remove(BaseKit.this);
					
					e.getPlayer().sendMessage("§6Habilidade recarregada");
					
					this.cancel();
				}
			}.runTaskLater(Main.getInstance(), this.getCooldownSeconds() * 20);
		}
		
		this.activateSkill(e);
	}
	
	public void cancelCooldown(Player p) {
		this.cooldownTask.cancel();
		
		cooldowned.remove(BaseKit.this);
		
		p.setCooldown(getSkillItem().getType(), 0);
	}
	
	public String getCooldownTimeFormatted() {
		return String.valueOf(new DecimalFormat("#.##").format((double)(cooldowned.get(this) / 1000.0 + this.getCooldownSeconds() - System.currentTimeMillis() / 1000.0)));
	}

	public boolean hasCooldown() {
		return cooldownSeconds != null && cooldownSeconds != 0;
	}
	
	public boolean isOnCooldown() {
		return cooldowned.containsKey(this);
	}
}
