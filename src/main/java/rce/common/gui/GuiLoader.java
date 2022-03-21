package rce.common.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import rce.RCsEffect;

import javax.annotation.Nullable;

public class GuiLoader implements IGuiHandler {
    public final static int TEST_GUI = 1;

    public GuiLoader(){
        NetworkRegistry.INSTANCE.registerGuiHandler(RCsEffect.instance, this);
    }

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case TEST_GUI:
                return new TestGUIServer(player);
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case TEST_GUI:
                return new TestGUI(new TestGUIServer(player));
            default:
                return null;
        }
    }
}
