package oldschoolproject.managers;

import org.bukkit.entity.Player;

import oldschoolproject.users.User;
import oldschoolproject.utils.warps.BaseWarp;
import oldschoolproject.utils.warps.WarpLoader;

public class WarpManager {

	public static void setWarp(User user, String warpName) {
		Player p = user.getPlayer();
		
		if (!warpExists(warpName)) {
			p.sendMessage("§cErro: Warp inexistente: " + warpName);
			return;
		}
		
		if (!user.isProtected()) {
			p.sendMessage("§cErro: Você não pode ir para outra warp com um kit");
			return;
		}
		
		BaseWarp warp = findWarp(warpName);

		warp.addPlayer(p);
		
		user.setWarp(warp);
		
		user.getPlayer().teleport(user.getWarp().getSpawnLocation());
		
		p.sendMessage("§aVocê foi teleportado para a warp: " + warpName);
	}

	public static BaseWarp findWarp(String warpName) {
		return WarpLoader.getWarpInstances().stream().filter(warp -> warp.getName().equalsIgnoreCase(warpName)).findFirst().orElse(null);
	}
	
	public static boolean warpExists(String warpName) {
		return findWarp(warpName) != null;
	}
	
}
