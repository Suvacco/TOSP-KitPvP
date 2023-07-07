package oldschoolproject.managers;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import oldschoolproject.entities.Kits;
import oldschoolproject.entities.State;
import oldschoolproject.entities.User;
import oldschoolproject.utils.builders.ItemBuilder;

public class KitManager {
	
	public static void setKit(User user, String kitName) {
		Player p = user.getPlayer();
		
		if (!kitExists(kitName)) {
			p.sendMessage("§cErro: Kit inexistente: " + kitName); 
			return;
		}
		
		if (!user.isProtected()) {
			p.sendMessage("§cErro: Você já recebeu um kit");
			return;
		}
		
		user.setKit(findKit(kitName));
		
		p.sendMessage("§eKit selecionado: " + kitName.substring(0, 1).toUpperCase() + kitName.substring(1).toLowerCase());
	}
	
	public static void giveKit(User user) {
		Player p = user.getPlayer();
		Inventory inv = p.getInventory();
		
		if (!user.isProtected()) {
			return;
		}
		
		if (!user.hasKit()) {
			user.setKit(Kits.PvP);
			p.sendMessage("§eKit PvP selecionado automaticamente");
		}
		
		inv.clear();
		
		inv.setItem(0, user.getKit() == Kits.PvP ? new ItemBuilder(Material.STONE_SWORD).setName("§aEspada").toItemStack() : new ItemBuilder(Material.WOODEN_SWORD).setName("§aEspada").toItemStack());
		
		if (user.getKit() != Kits.PvP) 
			inv.setItem(1, user.getKit().getSkillItem());
		
		for (int i = 0; i < inv.getSize(); i++) {
			p.getInventory().addItem(new ItemBuilder(Material.MUSHROOM_STEW).setName("§6Sopa").toItemStack());
		}
		
		user.setState(State.Ingame);
		
		p.sendMessage("§aVocê recebeu o kit: " + user.getKit().getName());
	}
	
	public static Kits findKit(String kitName) {
		return Arrays.stream(Kits.values()).filter(kit -> kit.name().equalsIgnoreCase(kitName)).findFirst().orElse(null);
	}
	
	public static boolean kitExists(String kitName) {
		return findKit(kitName) != null;
	}

}
