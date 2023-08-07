package oldschoolproject.utils.builders;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import oldschoolproject.Main;

public class FileBuilder {

	private String fileName;
	private File file;
	private FileConfiguration customFile;
	
	public FileBuilder(String fileName) {
		this.fileName = fileName;
		setup();
	}
	
	private void setup() {
		file = new File(Main.getInstance().getDataFolder(), fileName);
		
		if (!file.exists()) {
			if (Main.getInstance().getResource(fileName) != null) {
				try {
					InputStream inputStream = Main.getInstance().getResource(fileName);
					OutputStream outputStream = new FileOutputStream(file);
					
					byte[] buffer = new byte[1024];
					int lenght;
					while ((lenght = inputStream.read(buffer)) > 0) {
						outputStream.write(buffer, 0, lenght);
					}
					
					outputStream.close();
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				file.getParentFile().mkdirs();
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

	public FileConfiguration getFileConfiguration() {
		return this.customFile;
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
