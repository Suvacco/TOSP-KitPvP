package oldschoolproject.managers;

import oldschoolproject.exceptions.OperationFailException;
import oldschoolproject.users.UserGuard;
import oldschoolproject.users.UserStats;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import oldschoolproject.kits.instances.PvP;
import oldschoolproject.users.User;
import oldschoolproject.utils.builders.ItemBuilder;
import oldschoolproject.kits.BaseKit;
import oldschoolproject.kits.KitLoader;
import oldschoolproject.warps.instances.Spawn;

public class KitManager {
	
	// Direct command
	public static void setKit(User user, String kitName) throws OperationFailException {
		Player p = user.getPlayer();
		
		if (!kitExists(kitName)) {
			throw new OperationFailException("Kit \"" + kitName + "\" not found");
		}
		
		if (!(user.getWarp() instanceof Spawn)) {
			throw new OperationFailException("It isn't allowed to select a kit outside of the spawn area");
		}
		
		if (!user.isProtected()) {
			throw new OperationFailException("Kit already received");
		}

		if (!user.getPlayer().hasPermission("perm.kit." + kitName.toLowerCase()) || !user.getPlayer().hasPermission("rank.kit." + kitName.toLowerCase())) {
			throw new OperationFailException("You don't have permission to use this kit");
		}
		
		user.setKit(findKit(kitName).createInstance());
		
		p.sendMessage("§eYou selected the kit: " + kitName.substring(0, 1).toUpperCase() + kitName.substring(1).toLowerCase());
	}
	
	// Steping on sponge
	public static void giveKit(User user) {
		Player p = user.getPlayer();

		// Doesn't make sense to send message every time unprotected player steps into sponge block
		if (!(user.getWarp() instanceof Spawn)) {
			return;
		}
		
		// This guard exists for one reason: Having a kit doesn't mean you're playing, you can have a kit while beign protected
		// Not beign protected means you're playing
		if (!user.isProtected()) {
			return;
		}
		
		// this doesn't matter for the upper guard conditions
		if (!user.hasKit()) {
			user.setKit(new PvP());
			p.sendMessage("§eKit PvP selecionado automaticamente");
		}
		
		setupInventory(user);
		
		user.setUserGuard(UserGuard.Playing);
		
		p.sendMessage("§aVocê recebeu o kit: " + user.getKit().getName());
	}
	
	public static BaseKit findKit(String kitName) {
		return KitLoader.getKitInstances().stream().filter(kit -> kit.getName().equalsIgnoreCase(kitName)).findFirst().orElse(null);
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

    public static void buyKit(User user, String kitName) throws OperationFailException {
		Player p = user.getPlayer();

		if (!kitExists(kitName)) {
			throw new OperationFailException("Kit inexistente: " + kitName);
		}

		if (user.getPlayer().hasPermission("rank.kit." + kitName.toLowerCase()) || user.getPlayer().hasPermission("perm.kit." + kitName.toLowerCase())) {
			throw new OperationFailException("Já possui o kit");
		}

		BaseKit kit = findKit(kitName);

		if ((Integer)user.getStat(UserStats.COINS) < kit.getShopPrice()) {
			throw new OperationFailException("Coins insuficientes");
		}

		user.getPermissionAttachment().setPermission("perm.kit." + kit.getName().toLowerCase(), true);
		user.setStat(UserStats.COINS, (Integer)user.getStat(UserStats.COINS) - kit.getShopPrice());

		p.sendMessage("§aKit \"" + kit.getName() + "\" comprado!");
	}
}
