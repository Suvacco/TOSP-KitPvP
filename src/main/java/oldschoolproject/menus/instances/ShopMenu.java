package oldschoolproject.menus.instances;

import oldschoolproject.kits.BaseKit;
import oldschoolproject.kits.KitLoader;
import oldschoolproject.managers.KitManager;
import oldschoolproject.managers.UserManager;
import oldschoolproject.menus.PaginatedMenu;
import oldschoolproject.users.User;
import oldschoolproject.users.UserStats;
import oldschoolproject.utils.builders.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ShopMenu extends PaginatedMenu {

    User holder;
    public ShopMenu(User holder) {
        super(holder, 54, "Shop", 0, 8);
        this.holder = holder;
    }

    @Override
    public void setDefaultItems() {
        for (int i = 1; i < 8; i++) {
            this.getInventory().setItem(i, new ItemBuilder(Material.GLASS_PANE).toItemStack());
        }

        for (int i = 45; i < 54; i++) {
            this.getInventory().setItem(i, new ItemBuilder(Material.GLASS_PANE).toItemStack());
        }

        this.getInventory().setItem(49, new ItemBuilder(Material.EMERALD).setName("§7Suas Coins: §a" + this.holder.getStat(UserStats.COINS)).toItemStack());
    }

    @Override
    public void handleItemClick(InventoryClickEvent e) {
        e.setCancelled(true);

        new ShopConfirmMenu(this.holder, KitManager.findKit(e.getCurrentItem().getItemMeta().getDisplayName().substring(2))).open();
    }

    @Override
    public void fillMenu() {
        BaseKit[] allKits = KitLoader.getKitInstances().toArray(new BaseKit[0]);

        for (int i = 0; i < getMaxItemsPerPage(); i++) {

            index = getMaxItemsPerPage() * page + i;

            if (index >= allKits.length) {
                break;
            }

            if (allKits[index] != null) {

                if (!this.holder.getPlayer().hasPermission("perm.kit." + allKits[index].getName().toLowerCase()) &&
                        !this.holder.getPlayer().hasPermission("rank.kit." + allKits[index].getName().toLowerCase())) {

                    ItemStack item = allKits[index].getMenuItem().clone();

                    ItemMeta meta = item.getItemMeta();

                    meta.setDisplayName("§a" + allKits[index].getName());

                    meta.setLore(Arrays.asList("", "§7Preço: §a" + allKits[index].getShopPrice(), "", "§a§lCOMPRAR"));

                    item.setItemMeta(meta);

                    this.inventory.addItem(item);
                }
            }
        }
    }
}
