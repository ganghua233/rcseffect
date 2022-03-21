package rceutils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import rce.RCsEffect;

import java.util.Random;

public class InGameExecute {
    /**
     * To give the mob potion effect.
     * @param entity
     * @param buff
     */
    public static void buffGiver(EntityLivingBase entity, int[] buff){
        //Check the input
        if(buff.length % 3 != 0 || entity == null)return;
        //give the potion effect
        for(int times = 1; times <= buff.length / 3; times++){
            int potionID = buff[3*times - 3];
            int potionDuration = buff[3*times -2];
            int potionLevel = buff[3*times -1];
            PotionEffect potion = new PotionEffect(Potion.getPotionById(potionID), potionDuration, potionLevel);
            buffGiverController(entity, potion);
        }
    }

    /**
     * give the mob potion effect with random.
     * @param random
     * @param entity
     * @param buff
     */
    public static void buffGiver(int[] random, EntityLivingBase entity, int[] buff){
        //Check the input
        if(buff.length % 3 != 0 || entity == null)return;
        if(random.length != buff.length / 3) return;

        //give the potion effect
        for(int times = 1; times <= buff.length / 3; times++){
            if(Tools.percent(random[times -1])){
                int potionID = buff[3*times - 3];
                int potionDuration = buff[3*times -2];
                int potionLevel = buff[3*times -1];
                PotionEffect potion = new PotionEffect(Potion.getPotionById(potionID), potionDuration, potionLevel);
                buffGiverController(entity, potion);
            }else{
                continue;
            }
        }
    }
    /**
     * To give the mob the potion effect.
     * @param entity
     * @param ea
     */
    public static void buffGiverController(EntityLivingBase entity, PotionEffect ea) {
        if(entity.isPotionActive(ea.getPotion())&&entity!=null) {
            PotionEffect e=entity.getActivePotionEffect(ea.getPotion());
            if(e!=null){
                if(e.getDuration()<=4){
                    entity.addPotionEffect(ea);
                }else if(e.getAmplifier()<ea.getAmplifier()){
                    entity.addPotionEffect(ea);
                }
            }
        }else{
            entity.addPotionEffect(ea);
        }
    }

    /**
     * Send the message to the player.
     * @param player
     * @param list
     */
    public static void playerTalker(EntityPlayer player, NBTTagList list) {
        int v=list.tagCount();
        Random r=new Random();
        int cl=r.nextInt(v);
        String message = list.getStringTagAt(cl);
        player.sendMessage(new TextComponentString(message));
    }

    /**
     * Instantaneous integrated executor
     * @param dataGiver
     * @param recipient
     * @param name
     */
    public static void giveAllEffect(EntityLivingBase dataGiver, EntityLivingBase recipient, String name){
        if(dataGiver == null || recipient == null) return;

        //For the equipments on the non-player
        ItemStack[] dataGiverEquipments = {dataGiver.getHeldItemMainhand(),dataGiver.getHeldItemOffhand(),dataGiver.getItemStackFromSlot(EntityEquipmentSlot.HEAD),dataGiver.getItemStackFromSlot(EntityEquipmentSlot.CHEST),dataGiver.getItemStackFromSlot(EntityEquipmentSlot.LEGS),dataGiver.getItemStackFromSlot(EntityEquipmentSlot.FEET)};
        for(int i = 0; i < dataGiverEquipments.length; i++){
            if(dataGiverEquipments[i] == null || !dataGiverEquipments[i].hasTagCompound() || !dataGiverEquipments[i].getTagCompound().hasKey("rce")) continue;
            NBTTagCompound nbt = dataGiverEquipments[i].getTagCompound().getCompoundTag("rce");

            //the equipment will work in the correct place.
            String buffArrayName = name + "_" + (i + 1);
            String randomArrayName = buffArrayName + "_random";

            if(nbt.hasKey(buffArrayName)){

                int[] buff = nbt.getIntArray(buffArrayName);
                if(nbt.hasKey(randomArrayName)){

                    int[] random = nbt.getIntArray(randomArrayName);
                    buffGiver(random, recipient, buff);

                }else{

                    buffGiver(recipient, buff);

                }
            }


            //the equipment will work in any place.
            if(i == 0)continue;
            String buffArrayNameAll = name + "_" + 7;
            String randomArrayNameAll = buffArrayNameAll + "_random";

            if(nbt.hasKey(buffArrayNameAll)){

                int[] buff = nbt.getIntArray(buffArrayNameAll);
                if(nbt.hasKey(randomArrayNameAll)){

                    int[] random = nbt.getIntArray(randomArrayNameAll);
                    buffGiver(random, recipient, buff);

                }else{

                    buffGiver(recipient, buff);

                }
            }
        }

        //For the function 7 and 8
        if(dataGiver instanceof  EntityPlayer){
            EntityPlayer data = (EntityPlayer)dataGiver;
            for(int i = 0; i < 36; i++){
                ItemStack item = data.inventory.mainInventory.get(i);
                if(item == null || !item.hasTagCompound() || !item.getTagCompound().hasKey("rce"))continue;
                NBTTagCompound nbt = item.getTagCompound().getCompoundTag("rce");
                String buffName = name+"_7";
                String buffRandom = buffName + "_random";

                if(nbt.hasKey(buffName)){
                    int[] buff = nbt.getIntArray(buffName);
                    if(nbt.hasKey(buffRandom)){
                        int[] random = nbt.getIntArray(buffRandom);
                        buffGiver(random, recipient, buff);;
                    }else{
                        buffGiver(recipient, buff);
                    }
                }
            }
            for(int i = 0; i < 27; i++){
                ItemStack item = data.getInventoryEnderChest().getStackInSlot(i);
                if(item == null || !item.hasTagCompound() || !item.getTagCompound().hasKey("rce"))continue;
                NBTTagCompound nbt = item.getTagCompound().getCompoundTag("rce");
                String buffName = name+"_8";
                String buffRandom = buffName + "_random";

                if(nbt.hasKey(buffName)){
                    int[] buff = nbt.getIntArray(buffName);
                    if(nbt.hasKey(buffRandom)){
                        int[] random = nbt.getIntArray(buffRandom);
                        buffGiver(random, recipient, buff);;
                    }else{
                        buffGiver(recipient, buff);
                    }
                }
            }
        }
    }

    /**
     * continuous integrated executor
     * @param dataGiver
     * @param name
     */
    public static void giveAllEffect(EntityLivingBase dataGiver, String name){
        if(dataGiver == null) return;

        //For the equipments on the non-player
        ItemStack[] dataGiverEquipments = {dataGiver.getHeldItemMainhand(),dataGiver.getHeldItemOffhand(),dataGiver.getItemStackFromSlot(EntityEquipmentSlot.HEAD),dataGiver.getItemStackFromSlot(EntityEquipmentSlot.CHEST),dataGiver.getItemStackFromSlot(EntityEquipmentSlot.LEGS),dataGiver.getItemStackFromSlot(EntityEquipmentSlot.FEET)};
        for(int i = 0; i < dataGiverEquipments.length; i++){
            if(dataGiverEquipments[i] == null || !dataGiverEquipments[i].hasTagCompound() || !dataGiverEquipments[i].getTagCompound().hasKey("rce")) continue;
            NBTTagCompound nbt = dataGiverEquipments[i].getTagCompound().getCompoundTag("rce");

            //the equipment will work in the correct place.
            String buffArrayName = name + "_" + (i + 1);

            if(nbt.hasKey(buffArrayName)){

                int[] buff = nbt.getIntArray(buffArrayName);
                buffGiver(dataGiver, buff);

            }


            //the equipment will work in any place.
            if(i == 0)continue;
            String buffArrayNameAll = name + "_" + 7;
            String randomArrayNameAll = buffArrayNameAll + "_random";

            if(nbt.hasKey(buffArrayNameAll)){

                int[] buff = nbt.getIntArray(buffArrayNameAll);
                buffGiver(dataGiver, buff);

            }
        }

        //For the function 7 and 8
        if(dataGiver instanceof  EntityPlayer){
            EntityPlayer data = (EntityPlayer)dataGiver;
            for(int i = 0; i < 36; i++){
                ItemStack item = data.inventory.mainInventory.get(i);
                if(item == null || !item.hasTagCompound() || !item.getTagCompound().hasKey("rce"))continue;
                NBTTagCompound nbt = item.getTagCompound().getCompoundTag("rce");
                String buffName = name+"_7";

                if(nbt.hasKey(buffName)){

                    int[] buff = nbt.getIntArray(buffName);
                    buffGiver(dataGiver, buff);

                }
            }
            for(int i = 0; i < 27; i++){
                ItemStack item = data.getInventoryEnderChest().getStackInSlot(i);
                if(item == null || !item.hasTagCompound() || !item.getTagCompound().hasKey("rce"))continue;
                NBTTagCompound nbt = item.getTagCompound().getCompoundTag("rce");
                String buffName = name+"_8";

                if(nbt.hasKey(buffName)){
                    int[] buff = nbt.getIntArray(buffName);
                    buffGiver(dataGiver, buff);
                }
            }
        }
    }

    /**
     * To play sound.
     * @param e The entity that play sound.
     * @param sound the sound to play
     * @param world
     */
    public static void soundPlayer(EntityLivingBase e, String sound, World world) {
        String str[] = sound.split(" ");
        if(str.length != 3)return;
        try {
            world.playSound(null, e.posX, e.posY, e.posZ, new SoundEvent(new ResourceLocation(str[0])), SoundCategory.PLAYERS, Integer.parseInt(str[1]), Integer.parseInt(str[2]));
        }catch(Exception exception) {

        }
    }

    public static void messageHandler(EntityPlayer player, String message){
        if(new StringBuffer(message).indexOf("/") == 0){
            RCsEffect.server.commandManager.executeCommand(player, message);
        }else{
            player.sendMessage(new TextComponentString(message));
        }
    }

    public static void messageHandle(EntityPlayerMP player, String message, MinecraftServer server){
        if(new StringBuffer(message).indexOf("/") == 0){
            server.commandManager.executeCommand(player, message);
        }else{
            player.sendMessage(new TextComponentString(message));
        }
    }
}
