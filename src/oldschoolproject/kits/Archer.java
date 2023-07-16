package oldschoolproject.kits;

import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;

import oldschoolproject.kits.managers.Kit;
import oldschoolproject.utils.builders.ItemBuilder;

public class Archer extends Kit {

	public Archer() {
		super("Archer", new ItemBuilder(Material.BOW).toItemStack(), null);
	}

	@Override
	public void activateSkill(PlayerInteractEvent e) {
		
	}

	@Override
	public Kit createInstance() {
		return new Archer();
	}

}
