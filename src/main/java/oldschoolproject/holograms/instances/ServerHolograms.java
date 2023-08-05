package oldschoolproject.holograms.instances;

import oldschoolproject.holograms.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.Arrays;

public enum ServerHolograms {

    SPAWN_HOLOGRAM(new Hologram(new Location(Bukkit.getWorld("world"), 0.5D, -56.0D, 4.5D), Arrays.asList("§6§lTHE §e§lOLD SCHOOL§6§l PROJECT", "§bBem-vindo jogador(a)!", "§bSuporte o projeto no GitHub :D", "§aE principalmente, divirta-se!")));

    Hologram hologram;

    ServerHolograms(Hologram hologram) {
        this.hologram = hologram;
    }

    public Hologram getInstance() {
        return hologram;
    }
}