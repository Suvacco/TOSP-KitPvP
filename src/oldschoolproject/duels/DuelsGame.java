package oldschoolproject.duels;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import oldschoolproject.utils.builders.ItemBuilder;
import oldschoolproject.utils.listeners.BaseListener;

public class DuelsGame implements BaseListener {
	
	private ItemStack requestRod;
	private Map<Player, Player> requestsMap;
	
	public DuelsGame() {
		this.requestRod = new ItemBuilder(Material.BLAZE_ROD).setName("§cConvidar Jogador").toItemStack();
		this.requestsMap = new HashMap<>();
	}
	
	public ItemStack getRequestRod() {
		return this.requestRod;
	}
	
	@EventHandler
	public void onClick(PlayerInteractAtEntityEvent e) {
		Player player = e.getPlayer();
		
		if (e.getRightClicked() instanceof Player) {
			Player clicked = (Player) e.getRightClicked();
			
			if (e.getHand().equals(EquipmentSlot.HAND)) {
			
				if (player.getInventory().getItemInMainHand().equals(requestRod)) {
					
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
