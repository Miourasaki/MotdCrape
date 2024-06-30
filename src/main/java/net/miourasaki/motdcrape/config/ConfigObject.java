package net.miourasaki.motdcrape.config;

import java.io.IOException;

public interface ConfigObject {

    void loadConfig() throws IOException;

    void initConfig();

}
