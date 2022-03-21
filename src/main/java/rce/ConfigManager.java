package rce;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Logger;

public class ConfigManager {
    public static boolean enableOldVersion = false;
    public static Logger log;
    public static Configuration config;
    public ConfigManager(FMLPreInitializationEvent event){
        log = event.getModLog();
        config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        run();
    }
    public void run(){
        String eovComment = "Input 1 to enable the old version function.";
        int test = config.get(Configuration.CATEGORY_GENERAL, "Enable the function of old version", 640, eovComment).getInt();
        if(test == 1)enableOldVersion = true;
        config.save();
        log.info("Finished loading config. ");
    }
    public Logger logger(){
        return log;
    }
}
