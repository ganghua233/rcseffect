package rce.common.item;

import rce.common.item.creativetabs.TabsLoader;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;

import java.util.List;
import java.util.Map;

public class RCESword extends Item{

	public RCESword(java.util.Map<String,String> data) {
		//super(EnumHelper.addToolMaterial(i.getData().get("Name"), 0, Integer.parseInt(i.getData().get("Durability")), 8F, Integer.parseInt(i.getData().get("Damage")) - 4, (int)1.14514));
		this.setCreativeTab(TabsLoader.swordTabs);
		this.setRegistryName(data.get("Name"));
		this.setUnlocalizedName(data.get("Name"));
	}
	@Override
	public void addInformation(ItemStack item , World world , List<String> list , ITooltipFlag flagIn ) {
		ItemLoader.RCEItemToolBox.addInformation(item, world, list, flagIn);
	}
}
