package oldschoolproject.duels;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

import oldschoolproject.utils.listeners.BaseListener;

public class Duels implements BaseListener {
	
	Map<Player, Player> requestsMap = new HashMap<>();
	
	@EventHandler
	public void onClick(PlayerInteractAtEntityEvent e) {
		Player player = e.getPlayer();
		
		if (e.getRightClicked() instanceof Player) {
			Player clicked = (Player) e.getRightClicked();
			
			if (e.getHand().equals(EquipmentSlot.HAND)) {
			
				if (player.getInventory().getItemInMainHand().getType().equals(Material.BLAZE_ROD)) {
					
					// If player clicking was clicked before by the player he is clicking
					if (requestsMap.get(clicked) == player) { 
						
						// start match
						
						clicked.sendMessage("§amatch started");
						player.sendMessage("§amatch started");
						
						requestsMap.remove(clicked);
						requestsMap.remove(player);
						
						return;
					}
					
					player.sendMessage("you clicked" + clicked.getName());
					
					clicked.sendMessage("you got clicked by " + player.getName());
					
					requestsMap.put(player, clicked);
				}
			}
		}
	}

	
}
