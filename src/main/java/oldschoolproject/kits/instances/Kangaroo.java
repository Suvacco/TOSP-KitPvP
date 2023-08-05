package oldschoolproject.kits.instances;

import oldschoolproject.kits.BaseKit;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;

import oldschoolproject.utils.builders.ItemBuilder;

public class Kangaroo extends BaseKit {

	public Kangaroo() {
		super("Kangaroo", new ItemBuilder(Material.FIREWORK_ROCKET).toItemStack(), 2);
	}

	@Override
	public boolean activateSkill(PlayerInteractEvent e) {
		e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection().multiply(1.5D));
		return true;
	}

	@Override
	public BaseKit createInstance() {
		return new Kangaroo();
	}

}
