package oldschoolproject.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import oldschoolproject.entities.Skill;
import oldschoolproject.utils.builders.ItemBuilder;

public class SKangaroo extends Skill {

	public SKangaroo() {
		super(
				"Kangaroo", 
				new ItemBuilder(Material.FIREWORK_ROCKET).toItemStack(), 
				2
				);
	}

	@Override
	public void activate(Player p) {
		p.setVelocity(p.getLocation().getDirection().multiply(1.5D));
	}

}
