package oldschoolproject.kits;

import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;

import oldschoolproject.utils.builders.ItemBuilder;
import oldschoolproject.utils.kits.BaseKit;

public class PvP extends BaseKit {

	public PvP() {
		super("PvP", null, new ItemBuilder(Material.STONE_SWORD).toItemStack(), null);
	}

	@Override
	public void activateSkill(PlayerInteractEvent e) { }

	@Override
	public BaseKit createInstance() {
		return new PvP();
	}

}
