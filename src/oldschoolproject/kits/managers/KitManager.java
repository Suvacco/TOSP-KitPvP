package oldschoolproject.kits.managers;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import oldschoolproject.kits.PvP;
import oldschoolproject.users.User;
import oldschoolproject.users.managers.UserState;
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
		
		if (!user.isProtected()) {
			return;
		}
		
		if (!user.hasKit()) {
			user.setKit(KitEnum.PvP);
			p.sendMessage("§eKit PvP selecionado automaticamente");
		}
		
		setupInventory(user);
		
		user.setState(UserState.Playing);
		
		p.sendMessage("§aVocê recebeu o kit: " + user.getKit().getName());
	}
	
	public static KitEnum findKit(String kitName) {
		return Arrays.stream(KitEnum.values()).filter(kit -> kit.name().equalsIgnoreCase(kitName)).findFirst().orElse(null);
	}
	
	public static boolean kitExists(String kitName) {
		return findKit(kitName) != null;
	}
	
	public static void setupInventory(User user) {
		Inventory inv = user.getPlayer().getInventory();
		
		inv.clear();
		
		inv.setItem(0, user.getKit() instanceof PvP ?
				new ItemBuilder(Material.STONE_SWORD).setName("§aEspada").toItemStack() 
				: 
				new ItemBuilder(Material.WOODEN_SWORD).setName("§aEspada").toItemStack()
		);
		
		inv.setItem(1, user.getKit().getSkillItem() == null ?
				new ItemBuilder(Material.AIR).toItemStack()
				:
				new ItemBuilder(user.getKit().getSkillItem()).setName("§b" + user.getKit().getName()).toItemStack()
		);
		
		for (int i = 0; i < inv.getSize(); i++) {
			inv.addItem(new ItemBuilder(Material.MUSHROOM_STEW).setName("§6Sopa").toItemStack());
		}
	}

}
