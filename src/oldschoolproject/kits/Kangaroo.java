package oldschoolproject.kits;

import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;

import oldschoolproject.kits.managers.Kit;
import oldschoolproject.utils.builders.ItemBuilder;

public class Kangaroo extends Kit {

	public Kangaroo() {
		super("Kangaroo", new ItemBuilder(Material.FIREWORK_ROCKET).toItemStack(), 2);
	}

	@Override
	public void activateSkill(PlayerInteractEvent e) {
		e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection().multiply(1.5D));
		
	}

	@Override
	public Kit createInstance() {
		return new Kangaroo();
	}

}
