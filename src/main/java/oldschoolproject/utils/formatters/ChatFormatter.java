package oldschoolproject.utils.formatters;

import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.List;

public class ChatFormatter {

    public static String format(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static String format(List<String> list) {
        StringBuilder str = new StringBuilder();
        for (String content : list) {
            if (str.toString().equals("")) {
                str = new StringBuilder(content);
            } else {
                str.append(", ").append(content);
            }
        }
        return str.toString();
    }

    public static String formatSeconds(int seconds) {
        int minutes = seconds / 60;
        seconds %= 60;
        return String.format("%d:%02d", minutes, seconds);
    }

    public static String convertIntoStringWithNewLines(List<String> list) {
        StringBuilder str = new StringBuilder();
        for (String content : list) {
            if (str.toString().equals("")) {
                str = new StringBuilder(content);
            } else {
                str.append("\n").append(content);
            }
        }
        return str.toString();
    }

    public static String format(Location location) {
        return location.getBlockX() + "/" + location.getBlockY() + "/" + location.getBlockZ();
    }
}
