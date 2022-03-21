package rce.common.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import rce.RCsEffect;
import rce.client.ItemRenderLoader;
import rceutils.ItemFiles;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class ItemLoader {
	public static List<RCEItem> itemBase = new ArrayList<RCEItem>();
	public static List<RCEBaubles> baublesBase = new ArrayList<RCEBaubles>();
	public static List<RCEFood> foodBase = new ArrayList<RCEFood>();
	public static List<RCEArmor> armorBase = new ArrayList<RCEArmor>();
	public static List<RCESword> swordBase = new ArrayList<RCESword>();
	
	public ItemLoader() {
		for(Map<String, String> i : ItemFiles.itemNormalData) {
			RCEItem x = new RCEItem(i);
			itemBase.add(x);
		}
		for(Map<String, String> i: ItemFiles.itemFoodData) {
			RCEFood x = new RCEFood(i);
			foodBase.add(x);
		}
		if(RCsEffect.hasBaubles) {
			for(Map<String, String> i : ItemFiles.itemBaublesData) {
				RCEBaubles x = new RCEBaubles(i);
				baublesBase.add(x);
			}
		}
		for(Map<String, String> i : ItemFiles.itemSwordData) {
			RCESword x = new RCESword(i);
			swordBase.add(x);
		}
		for(Map<String, String> data : ItemFiles.itemArmorData) {
			String name = data.get("Name");
			int durability = data.containsKey("Durability") ? Integer.parseInt(data.get("Durability")) : 100;
			String texture = data.get("Texture");
			int defenses =  data.containsKey("Defenses") ? Integer.parseInt(data.get("Defenses")) : 4;
			int type = data.containsKey("Type") ? Integer.parseInt(data.get("Type")) : 1;
			float toughness = data.containsKey("Toughness") ? Float.parseFloat(data.get("Toughness")) : 1;
			int soundType = data.containsKey("Sound") ? Integer.parseInt(data.get("Sound")) : 3;

			SoundEvent sound = null;
			switch(soundType) {
				case 1: sound = SoundEvents.ITEM_ARMOR_EQUIP_LEATHER; break;
				case 2: sound = SoundEvents.ITEM_ARMOR_EQUIP_CHAIN; break;
				case 4: sound = SoundEvents.ITEM_ARMOR_EQUIP_GOLD; break;
				case 5: sound = SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND; break;
				case 6: sound = SoundEvents.ITEM_ARMOR_EQIIP_ELYTRA; break;
				case 7: sound = SoundEvents.ITEM_ARMOR_EQUIP_GENERIC; break;
				default: sound = SoundEvents.ITEM_ARMOR_EQUIP_IRON; break;
			}

			int in[] = {defenses, defenses, defenses, defenses};
			EntityEquipmentSlot slot = null;
			switch(type) {
				case 2: slot = EntityEquipmentSlot.CHEST; break;
				case 3: slot = EntityEquipmentSlot.LEGS; break;
				case 4: slot = EntityEquipmentSlot.FEET; break;
				default: slot = EntityEquipmentSlot.HEAD; break;
			}

			RCEArmor x = new RCEArmor(EnumHelper.addArmorMaterial(name, texture, durability, in , 1, sound , toughness), name, slot, texture);
			armorBase.add(x);
		}
	}
	
	@SubscribeEvent
	public static void onRegister(RegistryEvent.Register<Item> event) {
		for(RCEItem i : itemBase) {
			event.getRegistry().register(i);
		}
		if(RCsEffect.hasBaubles) {
			for(RCEBaubles i : baublesBase) {
				event.getRegistry().register(i);
			}
		}
		for(RCEFood i : foodBase) {
			event.getRegistry().register(i);
		}
		for(RCESword i : swordBase) {
			event.getRegistry().register(i);
		}
		for(RCEArmor i : armorBase) {
			event.getRegistry().register(i);
		}
		if(RCsEffect.isClient) {
			ItemRenderLoader.registerModels();
		}
	}


	/**
	 * @author :RC_diamond_GH
	 * To offer some functions for the RCE Items
	 */
	static class RCEItemToolBox{
		public static void addInformation(ItemStack item , World world , List<String> list , ITooltipFlag flagIn ) {
			if(!world.isRemote)return;
			if((item.hasTagCompound() && item.getTagCompound().hasKey("rce")) && item.getTagCompound().getCompoundTag("rce").hasKey("RCELore")) {
				NBTTagCompound nbt = item.getTagCompound().getCompoundTag("rce");
				int u = nbt.getTagList("RCELore", 8).tagCount();
				for(int x= 0; x < u; x++) {
					String str = nbt.getTagList("RCELore", 8).getStringTagAt(x);
					int f  = str.indexOf("@[");
					int g = str.indexOf("]@");
					if((f >= 0 && g >= 0) && (g - f) >= 0) {
						String s = str.substring(f + 2, g);
						String strs[] = s.split(":");
						String type = strs[0];
						String name = strs[1];

						if(nbt.hasKey(name)){
							if(type.equals("byte")) {
								byte flag = nbt.getByte(name);
								String temp = str.substring(0, f);
								temp += flag;
								str = temp + str.substring(g + 2);

							}else if(type.equals("short")) {
								short flag = nbt.getShort(name);
								String temp = str.substring(0, f);
								temp += flag;
								str = temp + str.substring(g + 2);

							}else if(type.equals("int")) {
								int flag = nbt.getInteger(name);
								String temp = str.substring(0, f);
								temp += flag;
								str = temp + str.substring(g + 2);

							}else if(type.equals("long")) {
								long flag = nbt.getLong(name);
								String temp = str.substring(0, f);
								temp += flag;
								str = temp + str.substring(g + 2);

							}else if(type.equals("float")) {
								float flag = nbt.getFloat(name);
								String temp = str.substring(0, f);
								temp += new java.text.DecimalFormat("#.00").format(flag);
								str = temp + str.substring(g + 2);

							}else if(type.equals("double")) {
								double flag = nbt.getDouble(name);
								String temp = str.substring(0, f);
								temp += new java.text.DecimalFormat("#.00").format(flag);
								str =  temp + str.substring(g + 2);

							}
						}
					}
					list.add(str);
				}
			}
		}
	}
}
