package rce.common.item.creativetabs;

import rce.RCsEffect;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class TabsLoader {
	public static CreativeTabs foodTabs;
	public static CreativeTabs swordTabs;
	public static CreativeTabs itemTabs;
	public static CreativeTabs blockTabs;
	public static CreativeTabs armorTabs;
	public static CreativeTabs baublesTabs;

	public static void loadTabs(FMLPreInitializationEvent event){
		foodTabs = new FoodTab();
		swordTabs=new SwordTab();
		itemTabs=new ItemTab();
		armorTabs=new ArmorTab();
		if(RCsEffect.hasBaubles) {
			baublesTabs=new BaublesTab();
		}
	}
}
