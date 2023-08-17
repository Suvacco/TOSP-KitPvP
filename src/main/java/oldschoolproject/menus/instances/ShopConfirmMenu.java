package oldschoolproject.menus.instances;

import oldschoolproject.kits.BaseKit;
import oldschoolproject.menus.BaseMenu;
import oldschoolproject.users.User;
import oldschoolproject.users.UserStats;
import oldschoolproject.utils.builders.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ShopConfirmMenu extends BaseMenu {

    BaseKit kit;
    User holder;
    public ShopConfirmMenu(User holder, BaseKit kit) {
        super(holder, 27, "Please, confirm your purchase:");
        this.kit = kit;
        this.holder = holder;
    }

    @Override
    public void setDefaultItems() {
        this.getInventory().setItem(10, new ItemBuilder(Material.RED_CONCRETE).setName("§c§lCANCEL").toItemStack());

        this.getInventory().setItem(13, new ItemBuilder(Material.PAPER).setName("§ePurchase Details")
                .setLore("", "§7Kit: §a" + this.kit.getName(),
                        "",
                        "§7Coins: §e" + this.holder.getStat(UserStats.COINS) + " §7> §c" + ((Integer)this.holder.getStat(UserStats.COINS) - kit.getShopPrice())).toItemStack());

        this.getInventory().setItem(16, new ItemBuilder(Material.LIME_CONCRETE).setName("§a§lCONFIRM").toItemStack());
    }

    @Override
    public void handleInteraction(InventoryClickEvent e) {
        e.setCancelled(true);

        if (e.getCurrentItem().getType() == Material.RED_CONCRETE) {
            this.holder.getPlayer().closeInventory();
            this.holder.getPlayer().sendMessage("§cPurchase canceled!");
            return;
        }

        if (e.getCurrentItem().getType() == Material.LIME_CONCRETE) {
            this.holder.getPlayer().closeInventory();
            this.holder.getPlayer().performCommand("buy " + kit.getName().toLowerCase());
        }
    }
}
