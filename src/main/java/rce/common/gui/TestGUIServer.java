package rce.common.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class TestGUIServer extends Container {
    private boolean release = true;
    private Slot s;
    public TestGUIServer(EntityPlayer player){
        super();
        ItemStack i = player.getHeldItemMainhand();
        if(i == null)release = false;
        int sort = 0;
        for(; sort < 9; sort++){
            if(player.inventory.getStackInSlot(sort) == i)break;
        }
        this.s = new Slot(player.inventory, sort, 11, 12);
        this.addSlotToContainer(getS());
    }
    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return release;
    }

    public Slot getS() {
        return s;
    }
}
