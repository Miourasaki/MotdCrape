package net.miourasaki.motdcrape.spigot;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class SpigotConfigObject {

    public FileConfiguration configuration;
    public File configFolder;
    public String configName;

    private File configFile;
    private String fileName;
    private JavaPlugin instance;

    public SpigotConfigObject(JavaPlugin instance, String configName, String fileName) throws IOException {
        if (configName.equals("default")) {
            this.configFolder = instance.getDataFolder();
        } else {
            this.configFolder = new File(instance.getDataFolder(), "configs");
        }
        this.configName = configName;
        this.fileName = fileName;
        this.instance = instance;

        loadConfig();
    }

    public void loadConfig() throws IOException {
        if (!configFolder.exists()) configFolder.mkdir();

        if (configName.equals("default")) {
            configFile = new File(configFolder, fileName);
        } else {
            configFile = new File(configFolder, configName + ".yml");
            fileName = "configs/" + configName + ".yml";
        }

        if (!configFile.exists()) {
            initConfig();
        }

        configuration = YamlConfiguration.loadConfiguration(configFile);
    }

    public void set(String path, Object value) {
        configuration.set(path, value);
        try {
            configuration.save(configFile);
        } catch (IOException e) {
            throw new RuntimeException("Error saving configuration file", e);
        }
    }

    public void initConfig() {
        if (fileName.equals("master.yml")) {
            try {
                new SpigotConfigObject(instance, "old", "configs/old.yml");
                new SpigotConfigObject(instance, "maintain", "configs/maintain.yml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try (InputStream inputStream = instance.getResource("config/" + fileName)) {
            Files.copy(inputStream, configFile.toPath());
        } catch (IOException e) {
            instance.getLogger().severe("Error saving config");
        }

        try (InputStream inputStream = instance.getResource("config/icons/" + configName + ".png")) {
            Files.copy(inputStream, new File(instance.getDataFolder(), "icons/" + configName + ".png").toPath());
        } catch (IOException e) {
            instance.getLogger().severe("Error saving favicon");
        }
    }
}
