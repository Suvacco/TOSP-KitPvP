package oldschoolproject.kits;

import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;

import oldschoolproject.utils.builders.ItemBuilder;
import oldschoolproject.utils.kits.BaseKit;

public class Kangaroo extends BaseKit {

	public Kangaroo() {
		super("Kangaroo", new ItemBuilder(Material.FIREWORK_ROCKET).toItemStack(), 2);
	}

	@Override
	public void activateSkill(PlayerInteractEvent e) {
		e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection().multiply(1.5D));
		
	}

	@Override
	public BaseKit createInstance() {
		return new Kangaroo();
	}

}
