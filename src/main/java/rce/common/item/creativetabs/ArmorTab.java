package rce.common.item.creativetabs;

import java.util.Random;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ArmorTab extends CreativeTabs{
	public ArmorTab()
    {
        super("ArmorTab");
    }
    @Override
    public ItemStack getTabIconItem()
    {
        Random ran=new Random();
        int r = ran.nextInt(5);
        switch(r) {
        case 0 : return new ItemStack(Items.CHAINMAIL_HELMET);
        case 1 : return new ItemStack(Items.DIAMOND_HELMET);
        case 2 : return new ItemStack(Items.GOLDEN_HELMET);
        case 3 : return new ItemStack(Items.IRON_HELMET);
        case 4 : return new ItemStack(Items.LEATHER_HELMET);
        default : return null;
        }
    }
}
