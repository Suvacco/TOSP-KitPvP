package oldschoolproject.databases;

import org.bukkit.entity.Player;

public interface DatabaseConnection {

    public void connect();

    public void registerPlayer(Player player);
}
