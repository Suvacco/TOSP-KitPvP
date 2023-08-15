package oldschoolproject.managers;

import org.bukkit.Bukkit;

public class ChatManager {

    private static boolean chatStatus = true;

    public static void chatEnabled(boolean status) {
        chatStatus = status;
        Bukkit.broadcastMessage("§cA staff member " + (status ? "enabled" : "disabled") + " the chat!");
    }

    public static void wipeChat() {
        for (int i = 0; i < 100; i++) {
            Bukkit.broadcastMessage("");
        }

        Bukkit.broadcastMessage("§aA staff member wiped the chat!");
    }

    public static boolean isEnabled() {
        return chatStatus;
    }
}
