package rce.common.item.creativetabs;

import java.util.Random;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class FoodTab extends CreativeTabs{
	public FoodTab()
    {
        super("FoodTab");
    }
    @Override
    public ItemStack getTabIconItem()
    {
    	Random ran=new Random();
        int r = ran.nextInt(5);
        switch(r) {
        case 0 : return new ItemStack(Items.BREAD);
        case 1 : return new ItemStack(Items.COOKED_BEEF);
        case 2 : return new ItemStack(Items.COOKED_PORKCHOP);
        case 3 : return new ItemStack(Items.COOKED_FISH);
        case 4 : return new ItemStack(Items.CAKE);
        default : return null;
        }
    }
}
