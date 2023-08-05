package oldschoolproject.kits.instances;

import oldschoolproject.kits.BaseKit;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;

import oldschoolproject.utils.builders.ItemBuilder;

public class PvP extends BaseKit {

	public PvP() {
		super("PvP", null, new ItemBuilder(Material.STONE_SWORD).toItemStack(), null);
	}

	@Override
	public boolean activateSkill(PlayerInteractEvent e) {
		return false;
	}

	@Override
	public BaseKit createInstance() {
		return new PvP();
	}

}
