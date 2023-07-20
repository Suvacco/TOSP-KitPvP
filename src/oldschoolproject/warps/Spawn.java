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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handlePlayerQuit(Player player) {
		// TODO Auto-generated method stub
		
	}
	
	

}
