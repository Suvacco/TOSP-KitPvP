package oldschoolproject.managers;

import org.bukkit.entity.Player;

import oldschoolproject.users.User;
import oldschoolproject.utils.warps.BaseWarp;
import oldschoolproject.utils.warps.WarpLoader;

public class WarpManager {
	
	public static void setWarp(User user, BaseWarp warp) {
		user.setWarp(warp);
		
		warp.addPlayer(user.getPlayer());
		
		user.reset();
	}
	
	public static void changeWarp(User user, String warpName) {
		Player p = user.getPlayer();
		
		if (!warpExists(warpName)) {
			p.sendMessage("§cErro: Warp inexistente: " + warpName);
			return;
		}
		
		if (!user.isProtected()) {
			p.sendMessage("§cErro: Você não pode ir para outra warp com um kit");
			return;
		}
		
		BaseWarp destination = findWarp(warpName);
		
		if (user.getWarp().getClass() == destination.getClass()) {
			p.sendMessage("§cErro: Você já está warp solicitada");
			return;
		}
		
		user.getWarp().removePlayer(user.getPlayer());
		
		setWarp(user, destination);
		
		p.sendMessage("§aVocê foi teleportado para a warp: " + warpName.substring(0, 1).toUpperCase() + warpName.substring(1).toLowerCase());
	}
	
	public static BaseWarp findWarp(String warpName) {
		return WarpLoader.getWarpInstances().stream().filter(warp -> warp.getName().equalsIgnoreCase(warpName)).findFirst().orElse(null);
	}
	
	public static boolean warpExists(String warpName) {
		return findWarp(warpName) != null;
	}
	
}
