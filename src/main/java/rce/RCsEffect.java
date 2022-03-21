package rce;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;

import org.apache.logging.log4j.Logger;
import rce.client.ClientProxy;
import rce.common.CommonProxy;

/**
 * @author  RC_diamond_GH
 * This is the main class of the mod.
 * Managed every thing in the mod.
 */
@Mod(modid = RCsEffect.MODID, name = RCsEffect.NAME, version = RCsEffect.VERSION, acceptedMinecraftVersions = "1.12.2", dependencies="after:Baubles")
public class RCsEffect {
    //Some information of the mod.
    public static final String MODID = "rcseffect";
    public static final String NAME = "RC`s Effect";
    public static final String VERSION = "release-2.0";

    public static final double ACCURACY=0.001;

    //Proxies

    public static CommonProxy serverProxy = new CommonProxy();
    public static ClientProxy clientProxy = new ClientProxy();

    //config
    private static Logger config;
    public static boolean hasBaubles = false;
    public static File debugFile = null;
    public static boolean isClient = false;
    public static MinecraftServer server = null;

    @Mod.Instance(RCsEffect.MODID)
    public static RCsEffect instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
        //read the config file
        config = event.getModLog();

        //Check that is the Baubles mod loaded.
        hasBaubles = Loader.isModLoaded("Baubles");

        //Load the path of the debug File
        debugFile = new File(event.getModConfigurationDirectory().getParentFile(), "Debug.txt");

        if(event.getSide().equals(Side.CLIENT)){
            clientProxy.preInit(event);
            isClient = true;
        }else {
            serverProxy.preInit(event);
        }
    }

    @EventHandler
    public void init(FMLInitializationEvent event){
        if(event.getSide().equals(Side.CLIENT)){
            clientProxy.init(event);
            isClient = true;
        }else {
            serverProxy.init(event);
        }
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event){
        if(event.getSide().equals(Side.CLIENT)){
            clientProxy.postInit(event);
            isClient = true;
        }else {
            serverProxy.postInit(event);
        }
    }

    @EventHandler
    public void severStarting(FMLServerStartingEvent event){
        server = event.getServer();

        if(event.getSide().equals(Side.CLIENT)){
            clientProxy.severStarting(event);
            isClient = true;
        }else {
            serverProxy.severStarting(event);
        }
    }
}
