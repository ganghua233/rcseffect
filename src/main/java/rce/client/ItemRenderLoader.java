package rce.client;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rce.RCsEffect;
import rce.common.item.ItemLoader;
import rce.common.item.RCEBaubles;

public class ItemRenderLoader {
    @SideOnly(Side.CLIENT)
    public static void registerModels() {
        if(ItemLoader.itemBase.size() != 0) {
            for(int x = 0; x < ItemLoader.itemBase.size(); x++) {
                ModelLoader.setCustomModelResourceLocation(ItemLoader.itemBase.get(x), 0,
                        new ModelResourceLocation(ItemLoader.itemBase.get(x).getRegistryName(), "inventory"));
            }
        }
        if(RCsEffect.hasBaubles) {
            for(RCEBaubles i : ItemLoader.baublesBase) {
                ModelLoader.setCustomModelResourceLocation(i, 0,
                        new ModelResourceLocation(i.getRegistryName()+ "#inventory"));
            }
        }
        if(ItemLoader.foodBase.size() != 0) {
            for(int x = 0; x < ItemLoader.foodBase.size(); x++) {
                ModelLoader.setCustomModelResourceLocation(ItemLoader.foodBase.get(x), 0,
                        new ModelResourceLocation(ItemLoader.foodBase.get(x).getRegistryName(), "inventory"));
            }
        }
        if(ItemLoader.swordBase.size() != 0) {
            for(int x = 0; x < ItemLoader.swordBase.size(); x++) {
                ModelLoader.setCustomModelResourceLocation(ItemLoader.swordBase.get(x), 0,
                        new ModelResourceLocation(ItemLoader.swordBase.get(x).getRegistryName(), "inventory"));
            }
        }
        if(ItemLoader.armorBase.size() != 0) {
            for(int x = 0; x < ItemLoader.armorBase.size(); x++) {
                ModelLoader.setCustomModelResourceLocation(ItemLoader.armorBase.get(x), 0,
                        new ModelResourceLocation(ItemLoader.armorBase.get(x).getRegistryName(), "inventory"));
            }
        }
    }
}
