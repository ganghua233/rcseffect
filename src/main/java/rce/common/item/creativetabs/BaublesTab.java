package rce.common.item.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class BaublesTab extends CreativeTabs{
	public BaublesTab()
    {
        super("BaublesTab");
    }
    @Override
    public ItemStack getTabIconItem()
    {
        return new ItemStack(Items.NETHER_STAR);
    }
}
