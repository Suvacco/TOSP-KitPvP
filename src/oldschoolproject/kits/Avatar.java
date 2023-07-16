package oldschoolproject.kits;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockIterator;

import oldschoolproject.Main;
import oldschoolproject.kits.managers.Kit;
import oldschoolproject.users.User;
import oldschoolproject.users.managers.UserManager;
import oldschoolproject.utils.builders.ItemBuilder;

public class Avatar extends Kit {
	
	public Avatar() {
		super(
				"Avatar",
				new ItemBuilder(Material.BEACON).toItemStack(),
				5
				);
	}
	
	@Override
	public Kit createInstance() {
		return new Avatar();
	}
	
	int index = -1; // Neutral beacon item starting point
	
	public void swapBeam(User user) {
		index++;
		
		if (index == 4) index = 0;
		
		user.getKit().getSkillItem().setType(BeamType.values()[index].getMaterial());
		
		user.getPlayer().getInventory().setItemInMainHand(user.getKit().getSkillItem());
	}
	
	@Override
	public void activateSkill(PlayerInteractEvent e) {
		if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
			
			User user = UserManager.getUser(e.getPlayer());
			
			swapBeam(user);
			
			cancelCooldown(e.getPlayer());
			
			return;
		}
		
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			
			BlockIterator bi = new BlockIterator(e.getPlayer().getEyeLocation(), 0, 24);
			
			new BukkitRunnable() {
				public void run() {
					if (bi.hasNext()) {
						
						Location loc = bi.next().getLocation();
						loc.getWorld().playEffect(loc, Effect.STEP_SOUND, BeamType.values()[index].getMaterial());
						
						for (Entity nearby : loc.getWorld().getNearbyEntities(loc, 2.5D, 2.5D, 2.5D)) {
							if (nearby == e.getPlayer()) {
								continue;
							}
							
							if (!(nearby instanceof LivingEntity)) {
								continue;
							}
							
							((LivingEntity)nearby).setNoDamageTicks(0);
							((LivingEntity)nearby).damage(BeamType.values()[index].getDamage(), e.getPlayer());
							((LivingEntity)nearby).setFireTicks(nearby.getFireTicks() + BeamType.values()[index].getFireTicks());
							
							if (BeamType.values()[index].getPotionEffect() != null) {
								((LivingEntity)nearby).addPotionEffect(BeamType.values()[index].getPotionEffect());
							}
						}
					} else {
						this.cancel();
					}
				}
			}.runTaskTimer(Main.getInstance(), 0, 1);
		}
	}
	
	public enum BeamType {
		
		Air(Material.QUARTZ_BLOCK, 1D, 0, new PotionEffect(PotionEffectType.WEAKNESS, 100, 1)),
		Water(Material.LAPIS_BLOCK, 0.5D, 0, new PotionEffect(PotionEffectType.POISON, 100, 1)),
		Earth(Material.GRASS_BLOCK, 1D, 0, new PotionEffect(PotionEffectType.SLOW, 100, 2)),
		Fire(Material.REDSTONE_BLOCK, 0.5D, 60, new PotionEffect(PotionEffectType.CONFUSION, 100, 1));
		
		Material material;
		Double damage;
		Integer fireTicks;
		PotionEffect potionEffect;
		
		BeamType(Material material, Double damage, Integer fireTicks, PotionEffect potionEffect) {
			this.material = material;
			this.damage = damage;
			this.fireTicks = fireTicks;
			this.potionEffect = potionEffect;
		}
		
		Material getMaterial() {
			return material;
		}
	
		Double getDamage() {
			return damage;
		}
		
		Integer getFireTicks() {
			return fireTicks;
		}
		
		PotionEffect getPotionEffect() {
			return potionEffect;
		}
	}
}
