package oldschoolproject.utils.loaders;

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
			for (int i = 0; i < listOfFiles.length; i++) {
				String fileName = listOfFiles[i].getName();
				if (listOfFiles[i].isFile() && fileName.endsWith(".jar")) {
					if (this.timeSinceLastChanged.containsKey(fileName)) {
						long time = ((Long) this.timeSinceLastChanged.get(fileName)).longValue();
						if (time < listOfFiles[i].lastModified()) {
							Bukkit.reload();
							Bukkit.getServer().broadcastMessage("§aPlugin recarregado");
							this.timeSinceLastChanged.remove(fileName);
							this.timeSinceLastChanged.put(fileName, Long.valueOf(listOfFiles[i].lastModified()));
						}
					}
				}
			}
		}
	}

	private void logTimes() {
		File folder = new File("plugins/");
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile() && listOfFiles[i].getName().endsWith(".jar")) {
				this.timeSinceLastChanged.put(listOfFiles[i].getName(), Long.valueOf(listOfFiles[i].lastModified()));
			}
		}
	}

}
