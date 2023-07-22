package oldschoolproject.warps;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import oldschoolproject.utils.builders.ItemBuilder;
import oldschoolproject.utils.warps.BaseWarp;

public class Spawn extends BaseWarp {

	public Spawn() {
		super("Spawn", new ItemBuilder(Material.COMPASS).toItemStack());
	}

	@Override
	public void handlePlayerJoin(Player player) {

	}

	@Override
	public void handlePlayerQuit(Player player) { }

	@Override
	public void setDefaultItems(Player player) {
		player.getInventory().setItem(4, new ItemBuilder(Material.CHEST).setName("§6Kits").toItemStack());
		
		player.getInventory().setItem(6, new ItemBuilder(Material.NETHER_STAR).setName("§bWarps").toItemStack());
	}
	
}
