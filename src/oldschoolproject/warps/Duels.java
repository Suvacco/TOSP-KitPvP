package oldschoolproject.warps;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import oldschoolproject.utils.builders.ItemBuilder;
import oldschoolproject.utils.warps.BaseWarp;

public class Duels extends BaseWarp {

	public Duels() {
		super("Duels", new ItemBuilder(Material.BLAZE_ROD).toItemStack());
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
