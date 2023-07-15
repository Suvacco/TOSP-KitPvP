package oldschoolproject.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import oldschoolproject.entities.Skill;
import oldschoolproject.utils.builders.ItemBuilder;

public class SPvP extends Skill {

	public SPvP() {
		super(
				"PvP",
				null,
				new ItemBuilder(Material.STONE_SWORD).toItemStack(),
				null
				);
	}

	public void activate(Player p) { }

}
