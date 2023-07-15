package oldschoolproject.entities;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class Skill {
	
	String name;
	ItemStack skillItem;
	ItemStack kitSelectorItem;
	Integer cooldownSeconds;
	
	public Skill(String name, ItemStack skillItem, Integer cooldownSeconds) { 
		this.name = name;
		this.skillItem = skillItem;
		this.kitSelectorItem = skillItem;
		this.cooldownSeconds = cooldownSeconds;
	}
	
	public Skill(String name, ItemStack skillItem, ItemStack kitSelectorItem, Integer cooldownSeconds) {
		this.name = name;
		this.skillItem = skillItem;
		this.kitSelectorItem = kitSelectorItem;
		this.cooldownSeconds = cooldownSeconds;
	}
	
	public abstract void activate(Player p);
}
