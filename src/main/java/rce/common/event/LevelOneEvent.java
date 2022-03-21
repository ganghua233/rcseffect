package rce.common.event;

import java.awt.Event;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import rceutils.InGameExecute;
import rceutils.Tools;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import scala.Int;

public class LevelOneEvent {
    public LevelOneEvent() {
        FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent e){

        EntityLivingBase wounded = e.getEntityLiving();
        EntityLivingBase attacker = null;

        if(e.getSource() != null && e.getSource().getTrueSource() != null && e.getSource().getTrueSource() instanceof EntityLivingBase){
            attacker = (EntityLivingBase) e.getSource().getTrueSource();
            //AttackerEffect
            if(attacker != null){
                InGameExecute.giveAllEffect(attacker, attacker, "AttackerEffect");
            }
            //WoundedEffect
            if(wounded != null){
                InGameExecute.giveAllEffect(wounded, wounded, "WoundedEffect");
            }
            //AttackEffect and StrikeEffect
            if(attacker != null && wounded != null){
                InGameExecute.giveAllEffect(attacker, wounded, "AttackEffect");
                InGameExecute.giveAllEffect(wounded, attacker, "StrikeEffect");
            }
        }

        //BloodPool
        bloodPoolGet(attacker, e.getAmount());
        bloodPoolUse(wounded);
    }

    @SubscribeEvent
    public void onPlayerAttack(AttackEntityEvent e){

    }
    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent e){
        EntityLivingBase wounded = e.getEntityLiving();
        EntityLivingBase attacker = null;

        if(e.getSource() != null && e.getSource().getTrueSource() != null && e.getSource().getTrueSource() instanceof EntityLivingBase){
            attacker = (EntityLivingBase) e.getSource().getTrueSource();
            //KillerEffect
            if(attacker != null){
                InGameExecute.giveAllEffect(attacker, attacker, "KillerEffect");
            }
            //KillEffect
            if(attacker != null && wounded != null){
                InGameExecute.giveAllEffect(wounded, attacker, "KillEffect");
            }
        }
    }

    @SubscribeEvent
    public void onUpdating (LivingEvent.LivingUpdateEvent e){
        EntityLivingBase entity = e.getEntityLiving();
        if(entity == null) return;
        //For everyone.
        ItemStack[] equipments = {entity.getHeldItemMainhand(),entity.getHeldItemOffhand(),entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD),entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST),entity.getItemStackFromSlot(EntityEquipmentSlot.LEGS),entity.getItemStackFromSlot(EntityEquipmentSlot.FEET)};
        for(int i = 0; i < equipments.length; i++){
           if(equipments[i] == null || !equipments[i].hasTagCompound() || !equipments[i].getTagCompound().hasKey("rce"))continue;
           NBTTagCompound nbt = equipments[i].getTagCompound().getCompoundTag("rce");
           String exceptName = "HasEffect_" + (i + 1);
           if(nbt.hasKey(exceptName)){
               InGameExecute.buffGiver(entity, nbt.getIntArray(exceptName));
           }
        }

        //For the players.
        if(!(entity instanceof EntityPlayer))return;
        EntityPlayer player = (EntityPlayer) entity;
        //function 7
        for(int i = 0; i < 36; i++){
            ItemStack item = player.inventory.mainInventory.get(i);
            if(item == null || !item.hasTagCompound() || !item.getTagCompound().hasKey("rce")) continue;
            NBTTagCompound nbt = item.getTagCompound().getCompoundTag("rce");
            String tagName = "HasEffect_7";
            if(nbt.hasKey(tagName)){
                InGameExecute.buffGiver(player, nbt.getIntArray(tagName));
            }
        }
        //function 8
        for(int i =0; i < 27; i++){
            ItemStack item = player.getInventoryEnderChest().getStackInSlot(i);
            if(item == null || !item.hasTagCompound() || !item.getTagCompound().hasKey("rce")) continue;
            NBTTagCompound nbt = item.getTagCompound().getCompoundTag("rce");
            String tagName = "HasEffect_8";
            if(nbt.hasKey(tagName)){
                InGameExecute.buffGiver(player, nbt.getIntArray(tagName));
            }
        }
    }

    @SubscribeEvent
    public void suitEffect(LivingEvent.LivingUpdateEvent e){
        EntityLivingBase entity = e.getEntityLiving();
        if(entity == null)return;
        ItemStack[] items = {entity.getHeldItemMainhand(),entity.getHeldItemOffhand(),entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD),entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST),entity.getItemStackFromSlot(EntityEquipmentSlot.LEGS),entity.getItemStackFromSlot(EntityEquipmentSlot.FEET)};
        int[] marks = {-1, -1, -1, -1, -1, -1};
        for(int i = 0; i < 6; i++){
            if(!items[i].hasTagCompound() || !items[i].getTagCompound().hasKey("rce") || !items[i].getTagCompound().getCompoundTag("rce").hasKey("SuitMark"))continue;
            marks[i] = items[i].getTagCompound().getCompoundTag("rce").getInteger("SuitMark");
        }
        for(int i = 0; i < 6; i++){
            if(marks[i] == 0)continue;
            int temp = marks[i];
            int amount = 0;
            for(int j = i; j < 6; j++){
                if(marks[j] == temp)amount++;
            }
            for(int j = i; j < 6; j++){
                if(marks[j] == temp){
                    String key = "SuitEffect_"+amount;
                    NBTTagCompound nbt = items[i].getTagCompound().getCompoundTag("rce");
                    if(nbt.hasKey(key)){
                        InGameExecute.buffGiver(entity, nbt.getIntArray(key));
                    }
                }
            }
        }
    }
    private static void buffGiver(ItemStack item, EntityLivingBase entity, String name){
        NBTTagCompound nbt = item.getTagCompound().getCompoundTag("rce");
        if(nbt.hasKey(name)){
            int buffs[] = nbt.getIntArray(name);
            InGameExecute.buffGiver(entity, buffs);
        }
    }
    private static int getSuitNumber(ItemStack item){
        if(item == null || !item.hasTagCompound() || !item.getTagCompound().hasKey("rce") || !item.getTagCompound().getCompoundTag("rce").hasKey("SuitMark"))return -1;
        return item.getTagCompound().getCompoundTag("rce").getInteger("SuitMark");
    }

    /**
     * To get the blood pool.
     * @param entity
     */
    private static void bloodPoolGet(EntityLivingBase entity, double damage){
        if(entity == null)return;
        String mainName = "EnableBloodPool";

        //For the function 1 to 6
        ItemStack[] equipments = {entity.getHeldItemMainhand(),entity.getHeldItemOffhand(),entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD),entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST),entity.getItemStackFromSlot(EntityEquipmentSlot.LEGS),entity.getItemStackFromSlot(EntityEquipmentSlot.FEET)};
        for(int i = 0; i<equipments.length; i++){

            if(equipments[i] != null && equipments[i].hasTagCompound() && equipments[i].getTagCompound().hasKey("rce")){

                NBTTagCompound nbt = equipments[i].getTagCompound().getCompoundTag("rce");
                String exceptName = mainName + "_" + (i + 1);

                if(nbt.hasKey(exceptName)){

                    int[] data = nbt.getIntArray(exceptName);
                    if(Tools.percent(data[2])){

                        double exceptAddition =  (damage * data[0]) / 100;

                        if(nbt.hasKey("BloodPool")){

                            double bloodPool = nbt.getDouble("BloodPool");
                            double bloodAdded = bloodPool + exceptAddition;

                            if(bloodAdded > data[1]){

                                nbt.setDouble("BloodPool", data[1]);

                            }else{

                                nbt.setDouble("BloodPool", bloodAdded);

                            }
                        }else{

                            nbt.setDouble("BloodPool", exceptAddition);

                        }
                    }else{
                        continue;
                    }
                }else{
                    continue;
                }
            }else{
                continue;
            }
        }

        //Player`s World
        if(!(entity instanceof EntityPlayer)) return;
        EntityPlayer player = (EntityPlayer)entity;

        //For the function 7
        for(int i = 0; i < 35; i++) {
            ItemStack item = player.inventory.mainInventory.get(i);
            if (item == null || !item.hasTagCompound() || !item.getTagCompound().hasKey("rce")) continue;
            NBTTagCompound nbt = item.getTagCompound().getCompoundTag("rce");
            String exceptName = mainName + "_" + 7;

            if (nbt.hasKey(exceptName)) {

                int[] data = nbt.getIntArray(exceptName);
                if (Tools.percent(data[2])) {

                    double exceptAddition = (damage * data[0]) / 100;

                    if (nbt.hasKey("BloodPool")) {

                        double bloodPool = nbt.getDouble("BloodPool");
                        double bloodAdded = bloodPool + exceptAddition;

                        if (bloodAdded > data[1]) {

                            nbt.setDouble("BloodPool", data[1]);

                        } else {

                            nbt.setDouble("BloodPool", bloodAdded);

                        }
                    } else {

                        nbt.setDouble("BloodPool", exceptAddition);

                    }
                } else {
                    continue;
                }
            } else {
                continue;
            }
        }

        //For the function 8
        for(int i = 0; i < 27; i++) {
            ItemStack item = player.getInventoryEnderChest().getStackInSlot(i);
            if (item == null || !item.hasTagCompound() || !item.getTagCompound().hasKey("rce")) continue;
            NBTTagCompound nbt = item.getTagCompound().getCompoundTag("rce");
            String exceptName = mainName + "_" + 8;

            if (nbt.hasKey(exceptName)) {

                int[] data = nbt.getIntArray(exceptName);
                if (Tools.percent(data[2])) {

                    double exceptAddition = (damage * data[0]) / 100;

                    if (nbt.hasKey("BloodPool")) {

                        double bloodPool = nbt.getDouble("BloodPool");
                        double bloodAdded = bloodPool + exceptAddition;

                        if (bloodAdded > data[1]) {

                            nbt.setDouble("BloodPool", data[1]);

                        } else {

                            nbt.setDouble("BloodPool", bloodAdded);

                        }
                    } else {

                        nbt.setDouble("BloodPool", exceptAddition);

                    }
                } else {
                    continue;
                }
            } else {
                continue;
            }
        }
    }

    /**
     * To use the blood pool.
     * @param entity
     */
    private static void bloodPoolUse(EntityLivingBase entity) {
        if (entity == null) return;
        String mainName = "EnableBloodPool";

        //For the function 1 to 6
        ItemStack[] equipments = {entity.getHeldItemMainhand(), entity.getHeldItemOffhand(), entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD), entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST), entity.getItemStackFromSlot(EntityEquipmentSlot.LEGS), entity.getItemStackFromSlot(EntityEquipmentSlot.FEET)};
        for (int i = 0; i < equipments.length; i++) {

            if (equipments[i] != null && equipments[i].hasTagCompound() && equipments[i].getTagCompound().hasKey("rce")) {

                NBTTagCompound nbt = equipments[i].getTagCompound().getCompoundTag("rce");
                String exceptName = mainName + "_" + (i + 1);

                if (nbt.hasKey(exceptName)) {

                    int[] data = nbt.getIntArray(exceptName);

                    double proportion = (entity.getHealth() / entity.getMaxHealth()) * 100;

                    if (proportion <= data[3] && Tools.percent(data[4]) && nbt.hasKey("BloodPool")) {

                        double differenceValue = entity.getMaxHealth() - entity.getHealth();
                        double bloodPool = nbt.getDouble("BloodPool");
                        if (bloodPool >= differenceValue) {
                            entity.setHealth(entity.getMaxHealth());
                            nbt.setDouble("BloodPool", bloodPool - differenceValue);
                        } else {
                            entity.setHealth((float) (entity.getHealth() + bloodPool));
                            nbt.setDouble("BloodPool", 0);
                        }

                    } else {
                        continue;
                    }
                } else {
                    continue;
                }
            } else {
                continue;
            }
        }

        //Player`s World
        if (!(entity instanceof EntityPlayer)) return;
        EntityPlayer player = (EntityPlayer) entity;

        //For the function 7
        for (int i = 0; i < 35; i++) {
            ItemStack item = player.inventory.mainInventory.get(i);
            if (item != null && item.hasTagCompound() && item.getTagCompound().hasKey("rce")) {

                NBTTagCompound nbt = item.getTagCompound().getCompoundTag("rce");
                String exceptName = mainName + "_" + 7;

                if (nbt.hasKey(exceptName)) {

                    int[] data = nbt.getIntArray(exceptName);

                    double proportion = (player.getHealth() / player.getMaxHealth()) * 100;

                    if (proportion <= data[3] && Tools.percent(data[4]) && nbt.hasKey("BloodPool")) {

                        double differenceValue = player.getMaxHealth() - player.getHealth();
                        double bloodPool = nbt.getDouble("BloodPool");
                        if (bloodPool >= differenceValue) {
                            player.setHealth(player.getMaxHealth());
                            nbt.setDouble("BloodPool", bloodPool - differenceValue);
                        } else {
                            player.setHealth((float) (player.getHealth() + bloodPool));
                            nbt.setDouble("BloodPool", 0);
                        }

                    } else {
                        continue;
                    }
                } else {
                    continue;
                }
            } else {
                continue;
            }
        }

        //For the function 8
        for (int i = 0; i < 27; i++) {
            ItemStack item = player.getInventoryEnderChest().getStackInSlot(i);
            if (item != null && item.hasTagCompound() && item.getTagCompound().hasKey("rce")) {

                NBTTagCompound nbt = item.getTagCompound().getCompoundTag("rce");
                String exceptName = mainName + "_" + 7;

                if (nbt.hasKey(exceptName)) {

                    int[] data = nbt.getIntArray(exceptName);

                    double proportion = (player.getHealth() / player.getMaxHealth()) * 100;

                    if (proportion <= data[3] && Tools.percent(data[4]) && nbt.hasKey("BloodPool")) {

                        double differenceValue = player.getMaxHealth() - player.getHealth();
                        double bloodPool = nbt.getDouble("BloodPool");
                        if (bloodPool >= differenceValue) {
                            player.setHealth(player.getMaxHealth());
                            nbt.setDouble("BloodPool", bloodPool - differenceValue);
                        } else {
                            player.setHealth((float) (player.getHealth() + bloodPool));
                            nbt.setDouble("BloodPool", 0);
                        }

                    } else {
                        continue;
                    }
                } else {
                    continue;
                }
            } else {
                continue;
            }
        }
    }

}
