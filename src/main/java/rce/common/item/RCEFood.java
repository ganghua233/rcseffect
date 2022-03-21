package rce.common.item;

import java.util.List;
import java.util.Map;

import rce.RCsEffect;
import rce.common.item.creativetabs.TabsLoader;
import rceutils.InGameExecute;
import rceutils.Tools;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;


public class RCEFood extends ItemFood{
	private int[] effect = null;
	public RCEFood(java.util.Map<String,String> data){
		super(Integer.parseInt(data.get("Amount")), Float.parseFloat(data.get("Saturation")),false);
		this.setUnlocalizedName(data.get("Name"));
		
		if(data.containsKey("MaxSize")) {
			this.maxStackSize = Integer.parseInt(data.get("MaxSize"));
		}else {
			this.maxStackSize = 64;
		}
		if(Integer.parseInt(data.get("IsAlwaysEatable")) == 1 ) {
			this.setAlwaysEdible();
		}
		this.setRegistryName(data.get("Name"));
		
		this.setCreativeTab(TabsLoader.foodTabs);
		if(data.containsKey("EatEffect")) {
			String num[] = data.get("EatEffect").split(" ");
			int[] n = new int[num.length];
			for(int x = 0 ; x < n.length ; x ++) {
				n[x] = Integer.parseInt(num[x]);
			}
			effect = n;
		}
		
	}
	
	@Override
	public void addInformation(ItemStack item , World world , List<String> list , ITooltipFlag flagIn ) {
		ItemLoader.RCEItemToolBox.addInformation(item, world, list, flagIn);
	}
	
	@Override
	public void onFoodEaten(ItemStack item , World world , EntityPlayer player) {
		if(item.hasTagCompound() && item.getTagCompound().hasKey("rce")) {
			NBTTagCompound nbt = item.getTagCompound().getCompoundTag("rce");
			if(nbt.hasKey("EatEffect")){
				InGameExecute.buffGiver(player, nbt.getIntArray("EatEffect"));
			}else if(effect != null) {
				InGameExecute.buffGiver(player, effect);
			}
		}else if(effect != null) {
			InGameExecute.buffGiver(player, effect);
		}
	}
}
