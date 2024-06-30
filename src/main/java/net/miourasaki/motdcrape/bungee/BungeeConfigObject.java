package net.miourasaki.motdcrape.bungee;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.miourasaki.motdcrape.BungeePlugin;
import net.miourasaki.motdcrape.config.ConfigObject;
import net.miourasaki.motdcrape.console.Out;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class BungeeConfigObject implements ConfigObject {

    public Configuration configuration;
    public File configFolder;
    public String configName;

    File configFile;
    String fileName;
    BungeePlugin instance;

    public BungeeConfigObject(BungeePlugin instance, String configName, String fileName) throws IOException {
        if (configName.equals("default")) {
            this.configFolder = instance.getDataFolder();
        }else {
            this.configFolder = new File(instance.getDataFolder(), "configs");
        }
        this.configName = configName;
        this.fileName = fileName;
        this.instance = instance;

        loadConfig();
    }

    @Override
    public void loadConfig() throws IOException {

        if (!configFolder.exists()) configFolder.mkdir(); // 创建插件数据文件夹
        File configsFolder = new File(instance.getDataFolder(), "icons");
        if (!configsFolder.exists()) configsFolder.mkdir(); // 创建插件数据文件夹

        if (configName.equals("default")) {
            configFile = new File(configFolder, fileName);
        }else {
            configFile = new File(configFolder, String.format("%s.yml", configName));
            fileName = String.format("configs/%s.yml", configName);
        }



        // 如果配置文件不存在，则保存默认配置
        if (!configFile.exists()) {
            initConfig();
        }

        // 读取配置文件
        configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(instance.getDataFolder(), fileName));

    }

    public void set(String path, Object value) {
        configuration.set(path, value);
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, configFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initConfig() {

        if (fileName.equals("master.yml")) {
            try {
                new BungeeConfigObject(BungeePlugin.instance, "old","configs/old.yml");
                new BungeeConfigObject(BungeePlugin.instance, "maintain","configs/maintain.yml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        // 从资源文件中复制默认配置
        try (InputStream inputStream = instance.getResourceAsStream("config/" + fileName)) {
            Files.copy(inputStream, configFile.toPath());
        } catch (IOException e) {
            Out.send("Error save config");
        }
        try (InputStream inputStream = instance.getResourceAsStream(String.format("config/icons/%s.png", configName))) {
            Files.copy(inputStream, new File(instance.getDataFolder(), String.format("icons/%s.png", configName)).toPath());
        } catch (IOException e) {
            Out.send("Error save favicon");
        }
    }

}
