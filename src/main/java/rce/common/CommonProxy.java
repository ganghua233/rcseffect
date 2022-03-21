package rce.common;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import rce.ConfigManager;
import rce.common.command.CommandLoader;
import rce.common.event.LevelOneEvent;
import rce.common.event.LevelTwoEvent;
import rce.common.gui.GuiLoader;
import rce.common.item.ItemLoader;
import rce.common.item.creativetabs.TabsLoader;
import rce.oldver.EventBase;
import rceutils.ItemFiles;

/**
 * @author RC_diamond_GH
 * Managed the contents of the server side.
 */
public class CommonProxy {
    //Load when the game is in pre init.
    public void preInit(FMLPreInitializationEvent event){
        new ConfigManager(event);
        TabsLoader.loadTabs(event);
        new ItemFiles(event);
        new ItemLoader();
    }
    //Load when the game is in init.
    public void init(FMLInitializationEvent event){
        new LevelOneEvent();
        new LevelTwoEvent();
        if(ConfigManager.enableOldVersion){
            new EventBase();
        }
        new GuiLoader();
    }
    //Load the game is in post init.
    public void postInit(FMLPostInitializationEvent event){

    }
    //Load when the server starting
    public void severStarting(FMLServerStartingEvent event){
        new CommandLoader(event);
    }
}
