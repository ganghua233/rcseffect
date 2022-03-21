package rce.common.item;

import java.util.List;
import java.util.Map;

import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;
import rce.common.item.creativetabs.TabsLoader;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class RCEArmor extends ItemArmor{
	private String texture = null;
	public RCEArmor(ArmorMaterial material, String name, EntityEquipmentSlot slot, String texture) {
		super(material, 0, slot);
		this.setCreativeTab(TabsLoader.armorTabs);
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		this.texture = texture;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type){
		if((this.armorType.equals(EntityEquipmentSlot.LEGS))){
			return "rcseffect:textures/armor/"+this.texture+"_2.png";
		}else{
			return "rcseffect:textures/armor/"+this.texture+"_1.png";
		}
	}
	@Override
	public void addInformation(ItemStack item , World world , List<String> list , ITooltipFlag flagIn ) {
		ItemLoader.RCEItemToolBox.addInformation(item, world, list, flagIn);
	}
}
