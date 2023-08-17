package oldschoolproject.managers;

import oldschoolproject.exceptions.OperationFailException;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import oldschoolproject.users.User;
import oldschoolproject.warps.BaseWarp;
import oldschoolproject.warps.WarpLoader;

public class WarpManager {
	
	public static void setWarp(User user, BaseWarp warp) {
		user.setWarp(warp);
		
		warp.addPlayer(user.getPlayer());
		
		user.reset();
	}
	
	public static void changeWarp(User user, String warpName) throws OperationFailException {
		Player p = user.getPlayer();
		
		if (!warpExists(warpName)) {
			throw new OperationFailException("Warp \"" + warpName + "\" not found");
		}
		
		if (!user.isProtected()) {
			throw new OperationFailException("You cannot go to another warp with a kit");
		}
		
		BaseWarp destination = findWarp(warpName);
		
		if (user.getWarp().getClass() == destination.getClass()) {
			throw new OperationFailException("You already are in the selected warp");
		}
		
		user.getWarp().removePlayer(user.getPlayer());
		
		setWarp(user, destination);

		p.playSound(p, Sound.ENTITY_ZOMBIE_VILLAGER_CONVERTED, 1.0F, 1.0F);
		p.sendTitle("§b§l" + user.getWarp().getName().toUpperCase(), "§fYou've been teleported to a warp!", 0, 45, 20);
	}
	
	public static BaseWarp findWarp(String warpName) {
		return WarpLoader.getWarpInstances().stream().filter(warp -> warp.getName().equalsIgnoreCase(warpName)).findFirst().orElse(null);
	}
	
	public static boolean warpExists(String warpName) {
		return findWarp(warpName) != null;
	}
	
}
