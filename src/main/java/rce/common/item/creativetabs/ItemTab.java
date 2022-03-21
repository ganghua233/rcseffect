package rce.common.item.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ItemTab extends CreativeTabs{
	
	public ItemTab()
    {
        super("ItemTab");
    }

    @Override
    public ItemStack getTabIconItem()
    {
        return new ItemStack(Items.BOOK);
    }
	
}
