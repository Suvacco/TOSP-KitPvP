package oldschoolproject.utils.builders;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import oldschoolproject.Main;

public class FileBuilder {

	String fileName;
	File file;
	FileConfiguration customFile;
	
	public FileBuilder(String fileName) {
		this.fileName = fileName;
		setup();
	}
	
	public void setup() {
		file = new File(Main.getInstance().getDataFolder(), fileName + ".yml");
		
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		customFile = YamlConfiguration.loadConfiguration(file);
	}
	
	public <T> Object get(String path) {
		return customFile.get(path);
	}
	
	public void set(String path, Object value) {
		customFile.set(path, value);
		save();
	}
	
	public void save() {
		try {
			customFile.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void reload() {
		customFile = YamlConfiguration.loadConfiguration(file);
	}
}
