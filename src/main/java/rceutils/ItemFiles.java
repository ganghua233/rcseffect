package rceutils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import rce.RCsEffect;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * 
 * @author RC_diamond_GH
 *
 */

@SuppressWarnings("unused")
public class ItemFiles {
	
	public static List<Map<String, String>> itemSwordData = new ArrayList<Map<String, String>>();
	public static List<Map<String, String>> itemNormalData = new ArrayList<Map<String, String>>();
	public static List<Map<String, String>> itemFoodData = new ArrayList<Map<String, String>>();
	public static List<Map<String, String>> itemBlockData = new ArrayList<Map<String, String>>();
	public static List<Map<String, String>> itemArmorData = new ArrayList<Map<String, String>>();
	public static List<Map<String, String>> itemBaublesData = new ArrayList<Map<String, String>>();
	public static List<Map<String, String>> itemGunData = new ArrayList<Map<String, String>>();

	public static File rceDir;
	
	public ItemFiles(FMLPreInitializationEvent event){
		
		rceDir=new File(event.getModConfigurationDirectory().getParentFile(), "/RCS/");
		
		if(!rceDir.exists())rceDir.mkdirs();
		if(!rceDir.isDirectory()) {
			return;
		}

		File[] dirs=rceDir.listFiles();

		if(dirs.length==0) {
			return;
		}
		
		for(File f : dirs) {
			if(!f.isDirectory() || f.listFiles().length == 0) {
				continue;
			}

			if(f.getName().equals("sword")) {
				for(File fil : f.listFiles()) {
					itemSwordData.add(reader(fil));
				}
			}else if(f.getName().equals("item")) {
				for(File fil : f.listFiles()) {
					itemNormalData.add(reader(fil));
				}
			}else if(f.getName().equals("food")) {
				for(File fil : f.listFiles()) {
					itemFoodData.add(reader(fil));
				}
			}else if(f.getName().equals("block")) {
				for(File fil : f.listFiles()) {
					itemBlockData.add(reader(fil));
				}
			}else if(f.getName().equals("armor")) {
				for(File fil : f.listFiles()) {
					itemArmorData.add(reader(fil));
				}
			}else if(f.getName().equals("baubles")) {
				for(File fil : f.listFiles()) {
					itemBaublesData.add(reader(fil));
				}
			}
		}
	}

	//To save the data into the map.
	public static Map<String, String> reader(File file) {
		if(file.isDirectory()) {
			return null;
		}
		Map<String, String> item = new HashMap<String, String>();
		try{
			InputStream read = new FileInputStream(file);
			Properties prop = new Properties();
			prop.load(read);
			for (String key : prop.stringPropertyNames()) {
				String value = prop.getProperty(key);
				item.put(key, value);
			}
		}catch(Exception e){

		}
		return item;
	}
}
