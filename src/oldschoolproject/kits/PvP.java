package oldschoolproject.kits;

import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;

import oldschoolproject.kits.managers.Kit;
import oldschoolproject.utils.builders.ItemBuilder;

public class PvP extends Kit {

	public PvP() {
		super("PvP", null, new ItemBuilder(Material.STONE_SWORD).toItemStack(), null);
	}

	@Override
	public void activateSkill(PlayerInteractEvent e) { }

	@Override
	public Kit createInstance() {
		return new PvP();
	}

}
