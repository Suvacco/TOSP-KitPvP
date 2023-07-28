package oldschoolproject.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import oldschoolproject.Main;

public class AutoReloader {
	
	public AutoReloader() {
		logTimes();
		
		new BukkitRunnable() {
			public void run() {
				checkIfModified();
			}
		}.runTaskTimer(Main.getInstance(), 1L, 20L);
	}
	
	private final Map<String, Long> timeSinceLastChanged = new HashMap<>();
	
	private void checkIfModified() {
		File folder = new File("plugins/");
		if (folder.exists() && folder.isDirectory()) {
			File[] listOfFiles = folder.listFiles();
			for (File file : listOfFiles) {
				String fileName = file.getName();
				if (file.isFile() && fileName.endsWith(".jar")) {
					if (this.timeSinceLastChanged.containsKey(fileName)) {
						long time = (Long) this.timeSinceLastChanged.get(fileName);
						if (time < file.lastModified()) {
							Bukkit.reload();
							Bukkit.getServer().broadcastMessage("§aPlugin recarregado");
							this.timeSinceLastChanged.remove(fileName);
							this.timeSinceLastChanged.put(fileName, file.lastModified());
						}
					}
				}
			}
		}
	}

	private void logTimes() {
		File folder = new File("plugins/");
		File[] listOfFiles = folder.listFiles();
		for (File file : listOfFiles) {
			if (file.isFile() && file.getName().endsWith(".jar")) {
				this.timeSinceLastChanged.put(file.getName(), file.lastModified());
			}
		}
	}

}
