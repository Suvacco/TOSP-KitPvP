package oldschoolproject.managers;

import oldschoolproject.databases.DatabaseLoader;
import oldschoolproject.users.User;
import org.bukkit.entity.Player;

public class DatabaseManager {

    public static void registerPlayer(User user) {
        Player player = user.getPlayer();

        DatabaseLoader.getDatabaseConnection().registerPlayer(player);
    }

}
