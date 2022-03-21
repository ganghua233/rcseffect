package rce.common.item;

import java.util.List;
import java.util.Map;

import rce.RCsEffect;
import rce.common.item.creativetabs.TabsLoader;
import rceutils.ItemFiles;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RCEItem extends Item{
	public RCEItem(java.util.Map<String,String> data) {
		super();
		this.setUnlocalizedName(data.get("Name"));
		if(data.containsKey("MaxSize")) {
			this.maxStackSize = Integer.parseInt(data.get("MaxSize"));
		}else {
			this.maxStackSize = 64;
		}
		this.setRegistryName(data.get("Name"));
		this.setCreativeTab(TabsLoader.itemTabs);
	}
	
	@Override
	public void addInformation(ItemStack item , World world , List<String> list , ITooltipFlag flagIn ) {
		ItemLoader.RCEItemToolBox.addInformation(item, world, list, flagIn);
	}
}
