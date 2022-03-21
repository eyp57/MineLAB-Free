package tr.web.minelab.minelabfree.configurations;

import org.bukkit.configuration.file.YamlConfiguration;
import tr.web.minelab.minelabfree.MineLABFree;

import java.io.File;

public class LanguageConfiguration extends YamlConfiguration {

    File file;

    public LanguageConfiguration() {
        file = new File(MineLABFree.getInstance().getDataFolder(), "language.yml");
        if(!file.exists()) {
            file.getParentFile().mkdirs();
            MineLABFree.getInstance().saveResource("language.yml", false);
        }
        reload();
    }

    public void reload() {
        try {
            this.load(file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void save() {
        try {
            this.save(file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
