package rce.common.item.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class SwordTab extends CreativeTabs{
	public SwordTab()
    {
        super("SwordTab");
    }

    @Override
    public ItemStack getTabIconItem()
    {
        return new ItemStack(Items.DIAMOND_SWORD);
    }
}
