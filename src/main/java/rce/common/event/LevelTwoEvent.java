package rce.common.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rce.RCsEffect;
import rce.common.gui.GuiLoader;
import rceutils.InGameExecute;
import rceutils.Tools;

import java.util.Random;

public class LevelTwoEvent {
    public LevelTwoEvent() {
        FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);
    }
    /**
     * To realize the function of effect
     * @param entity
     * @param nbt the Function BLOCK compound
     */
    private static void effect(EntityLivingBase entity , NBTTagCompound nbt) {
        NBTTagList list = nbt.getTagList("effect", 11);
        if(nbt.hasKey("effect_mode")) {
            byte m = nbt.getByte("effect_mode");
            if(m != 1 && m != 2) {
                for(int y = 0; y < list.tagCount(); y++) {
                    InGameExecute.buffGiver(entity,list.getIntArrayAt(y));
                }
            }else if(m == 1) {
                int foot = new Random().nextInt(list.tagCount());
                InGameExecute.buffGiver(entity, list.getIntArrayAt(foot));
            }else if(m == 2 && nbt.hasKey("effect_random")) {
                int[] ps = nbt.getIntArray("effect_random");
                if(ps.length == list.tagCount()) {
                    for(int y = 0; y < list.tagCount(); y++) {
                        if(Tools.percent(ps[y])) {
                            InGameExecute.buffGiver(entity, list.getIntArrayAt(y));
                        }
                    }
                }
            }
        }else {
            for(int y=0;y<list.tagCount();y++) {
                InGameExecute.buffGiver(entity,list.getIntArrayAt(y));
            }
        }
    }

    /**
     * To realize the function of talk.
     * @param player
     * @param nbt
     */
    private static void talk(EntityPlayer player , NBTTagCompound nbt) {
        NBTTagList list=nbt.getTagList("talk", 8);
        if(nbt.hasKey("talk_mode")) {
            byte m=nbt.getByte("talk_mode");
            if(m != 1 && m != 2) {
                for(int y=0;y<list.tagCount();y++) {
                    InGameExecute.messageHandler(player, list.getStringTagAt(y));
                }
            }else if(m==1) {
                int foot=new Random().nextInt(list.tagCount());
                InGameExecute.messageHandler(player, list.getStringTagAt(foot));

            }else if(m == 2 && nbt.hasKey("talk_random")) {
                int[] ps=nbt.getIntArray("talk_random");
                for(int y=0;y<list.tagCount();y++) {
                    if(Tools.percent(ps[y])) {
                        InGameExecute.messageHandler(player, list.getStringTagAt(y));
                    }
                }
            }
        }else {
            for(int y=0;y<list.tagCount();y++) {
                InGameExecute.messageHandler(player, list.getStringTagAt(y));
            }
        }
    }

    /**
     * To realize the function item
     * @param player
     * @param nbt
     */
    private static void item(EntityPlayer player , NBTTagCompound nbt) {
        NBTTagList list=nbt.getTagList("item", 10);
        if(nbt.hasKey("item_mode")) {
            byte m=nbt.getByte("item_mode");
            if(m!=1&&m!=2) {
                for(int y=0;y<list.tagCount();y++) {
                    player.addItemStackToInventory(new ItemStack(list.getCompoundTagAt(y)));
                }
            }else if(m==1) {
                int foot=new Random().nextInt(list.tagCount());
                player.addItemStackToInventory(new ItemStack(list.getCompoundTagAt(foot)));
            }else if(m==2&&nbt.hasKey("item_random")) {
                int[] ps=nbt.getIntArray("item_random");
                for(int y=0;y<list.tagCount();y++) {
                    if(Tools.percent(ps[y])) {
                        player.addItemStackToInventory(new ItemStack(list.getCompoundTagAt(y)));
                    }
                }
            }
        }else {
            for(int y=0;y<list.tagCount();y++) {
                player.addItemStackToInventory(new ItemStack(list.getCompoundTagAt(y)));
            }
        }
    }

    /**
     * To realize the function damage.
     * @param item
     * @param player
     * @param nbt
     */
    private static void damage(ItemStack item , EntityPlayer player , NBTTagCompound nbt) {
        int x=nbt.getInteger("damage");
        if(x>0) {
            item.damageItem(x , player);
        }else {
            item.setItemDamage(item.getItemDamage()-x);
        }
    }

    /**
     *To realize the function of integral.
     * @param item
     * @param nbt
     */
    private static void integral(ItemStack item, NBTTagCompound nbt) {
        String str=nbt.getString("integral");
        String strs[]=str.split(",");
        if(strs.length%4==0) {
            NBTTagCompound n=item.getTagCompound().getCompoundTag("rce");
            int mode=0;
            String name=strs[1];
            if(strs[0].equals("byte")) {
                byte add=Byte.parseByte(strs[2]);
                byte max=Byte.parseByte(strs[3]);
                if(n.hasKey(strs[1])) {
                    byte bas=n.getByte(name);
                    if(add<max-bas) {
                        n.setByte(name,(byte) (bas+add));
                    }else {
                        n.setByte(name,max);
                    }
                }else {
                    n.setByte(name, add);
                }
            }else if(strs[0].equals("short")) {
                short add=Short.parseShort(strs[2]);
                short max=Short.parseShort(strs[3]);
                if(n.hasKey(strs[1])) {
                    short bas=n.getShort(name);
                    if(add<max-bas) {
                        n.setShort(name,(short) (bas+add));
                    }else {
                        n.setShort(name,max);
                    }
                }else {
                    n.setShort(name, add);
                }
            }else if(strs[0].equals("int")) {
                int add=Integer.parseInt(strs[2]);
                int max=Integer.parseInt(strs[3]);
                if(n.hasKey(strs[1])) {
                    int bas=n.getInteger(name);
                    if(add<max-bas) {
                        n.setInteger(name,bas+add);
                    }else {
                        n.setInteger(name,max);
                    }
                }else {
                    n.setInteger(name, add);
                }
            }else if(strs[0].equals("long")) {
                long add=Long.parseLong(strs[2]);
                long max=Long.parseLong(strs[3]);
                if(n.hasKey(strs[1])) {
                    long bas=n.getLong(name);
                    if(add<max-bas) {
                        n.setLong(name,bas+add);
                    }else {
                        n.setLong(name,max);
                    }
                }else {
                    n.setLong(name, add);
                }
            }else if(strs[0].equals("float")) {
                float add=Float.parseFloat(strs[2]);
                float max=Float.parseFloat(strs[3]);
                if(n.hasKey(strs[1])) {
                    float bas=n.getFloat(name);
                    if(add<max-bas) {
                        n.setFloat(name,bas+add);
                    }else {
                        n.setFloat(name,max);
                    }
                }else {
                    n.setFloat(name, add);
                }
            }else if(strs[0].equals("double")) {
                double add=Double.parseDouble(strs[2]);
                double max=Double.parseDouble(strs[3]);
                if(n.hasKey(strs[1])) {
                    double bas=n.getDouble(name);
                    if(add<max-bas) {
                        n.setDouble(name,bas+add);
                    }else {
                        n.setDouble(name,max);
                    }
                }else {
                    n.setDouble(name, add);
                }
            }
        }
    }

    /**
     * To realize the function of consume.
     * @param item
     * @param nbt
     */
    private static void consume(ItemStack item, NBTTagCompound nbt){
        int amount = nbt.getInteger("consume");
        int now = item.getCount();
        if(now <= amount){
            item.setCount(0);
        }else{
            item.setCount(now - amount);
        }
    }

    /**
     * To realize the function of sound.
     * @param entity
     * @param nbt
     */

    private static void sound(EntityLivingBase entity, NBTTagCompound nbt){
        String soundName = nbt.getString("sound");
        InGameExecute.soundPlayer(entity, soundName, entity.world);
    }

    /**
     * To realize the block`s function.
     * @param dataGiver  The ItemStack to offer the data.
     * @param reception The one to receive the block`s affect.
     * @param name Name of the function block.
     * @param part Judge the part.
     */
    private static void block(ItemStack dataGiver, EntityLivingBase reception, String name, byte part){
        if(dataGiver == null)return;
        if(!dataGiver.hasTagCompound())return;
        if(!dataGiver.getTagCompound().hasKey("rce"))return;
        if(!dataGiver.getTagCompound().getCompoundTag(("rce")).hasKey(name))return;
        NBTTagCompound block = dataGiver.getTagCompound().getCompoundTag("rce").getCompoundTag(name);
        if(part != block.getByte("part"))return;        //effect

        if(block.hasKey("effect"))effect(reception, block);
        if(block.hasKey("sound"))sound(reception, block);
        if(reception instanceof  EntityPlayer){
            EntityPlayer player = (EntityPlayer)reception;
            if(block.hasKey("talk")){
                talk(player, block);
            }
            if(block.hasKey("item")){
                item(player, block);
            }
            if(block.hasKey("damage")){
                damage(dataGiver, player, block);
            }
            if(block.hasKey("integral")){
                integral(dataGiver, block);
            }
            if(block.hasKey("consume")){
                consume(dataGiver, block);
            }
        }
    }

    @SubscribeEvent
    public void onHurt(LivingHurtEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        EntityLivingBase attacker = null;
        if (event.getSource().getTrueSource() instanceof EntityLivingBase) {
            attacker = (EntityLivingBase) event.getSource().getTrueSource();
            ItemStack stacks[] = {attacker.getHeldItemMainhand(), attacker.getHeldItemOffhand(), attacker.getItemStackFromSlot(EntityEquipmentSlot.HEAD), attacker.getItemStackFromSlot(EntityEquipmentSlot.CHEST), attacker.getItemStackFromSlot(EntityEquipmentSlot.LEGS), attacker.getItemStackFromSlot(EntityEquipmentSlot.FEET)};
            for (byte i = 1; i <= 6; i++) {
                if (stacks[i - 1] == null || !stacks[i - 1].hasTagCompound() || !stacks[i - 1].getTagCompound().hasKey("rce"))
                    continue;
                NBTTagCompound nbt = stacks[i - 1].getTagCompound().getCompoundTag("rce");
                if (nbt.hasKey("OnAttack_Wounded")) {
                    block(stacks[i - 1], entity, nbt.getString("OnAttack_Wounded"), i);
                }

                if (attacker != null && nbt.hasKey("OnAttack_Attacker")) {
                    block(stacks[i - 1], attacker, nbt.getString("OnAttack_Attacker"), i);
                }
            }
        }
        if (entity == null) return;
        ItemStack items[] = {entity.getHeldItemMainhand(), entity.getHeldItemOffhand(), entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD), entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST), entity.getItemStackFromSlot(EntityEquipmentSlot.LEGS), entity.getItemStackFromSlot(EntityEquipmentSlot.FEET)};
        for (byte i = 1; i <= 6; i++) {
            if (items[i - 1] == null || !items[i - 1].hasTagCompound() || !items[i - 1].getTagCompound().hasKey("rce"))
                continue;
            NBTTagCompound nbt = items[i - 1].getTagCompound().getCompoundTag("rce");
            if (nbt.hasKey("OnHurt_Wounded")) {
                block(items[i - 1], entity, nbt.getString("OnHurt_Wounded"), i);
            }

            if (attacker != null && nbt.hasKey("OnHurt_Attacker")) {
                block(items[i - 1], attacker, nbt.getString("OnHurt_Attacker"), i);
            }
        }
    }

    @SubscribeEvent
    public void onFall(LivingFallEvent event){
        EntityLivingBase entity = event.getEntityLiving();
        String KeyWord = "OnFall";
        if(entity == null)return;
        ItemStack items[] = {entity.getHeldItemMainhand(), entity.getHeldItemOffhand(), entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD), entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST), entity.getItemStackFromSlot(EntityEquipmentSlot.LEGS), entity.getItemStackFromSlot(EntityEquipmentSlot.FEET)};
        for (byte i = 1; i <= 6; i++) {
            if (items[i - 1] == null || !items[i - 1].hasTagCompound() || !items[i - 1].getTagCompound().hasKey("rce"))
                continue;
            NBTTagCompound nbt = items[i - 1].getTagCompound().getCompoundTag("rce");
            if (nbt.hasKey(KeyWord)) {
                block(items[i - 1], entity, nbt.getString(KeyWord), i);
            }
        }
    }

    @SubscribeEvent
    public void onArrowLoose(ArrowLooseEvent event){
        EntityPlayer entity = event.getEntityPlayer();
        String KeyWord = "OnArrowLoose";
        if(entity == null)return;
        ItemStack items[] = {entity.getHeldItemMainhand(), entity.getHeldItemOffhand(), entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD), entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST), entity.getItemStackFromSlot(EntityEquipmentSlot.LEGS), entity.getItemStackFromSlot(EntityEquipmentSlot.FEET)};
        for (byte i = 1; i <= 6; i++) {
            if (items[i - 1] == null || !items[i - 1].hasTagCompound() || !items[i - 1].getTagCompound().hasKey("rce"))
                continue;
            NBTTagCompound nbt = items[i - 1].getTagCompound().getCompoundTag("rce");
            if (nbt.hasKey(KeyWord)) {
                block(items[i - 1], entity, nbt.getString(KeyWord), i);
            }
        }
    }

    @SubscribeEvent
    public void onArrowNock(ArrowNockEvent event){
        EntityPlayer entity = event.getEntityPlayer();
        String KeyWord = "OnArrowNock";
        if(entity == null)return;
        ItemStack items[] = {entity.getHeldItemMainhand(), entity.getHeldItemOffhand(), entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD), entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST), entity.getItemStackFromSlot(EntityEquipmentSlot.LEGS), entity.getItemStackFromSlot(EntityEquipmentSlot.FEET)};
        for (byte i = 1; i <= 6; i++) {
            if (items[i - 1] == null || !items[i - 1].hasTagCompound() || !items[i - 1].getTagCompound().hasKey("rce"))
                continue;
            NBTTagCompound nbt = items[i - 1].getTagCompound().getCompoundTag("rce");
            if (nbt.hasKey(KeyWord)) {
                block(items[i - 1], entity, nbt.getString(KeyWord), i);
            }
        }
    }

    @SubscribeEvent
    public void onCriticalHit(CriticalHitEvent event){
        EntityPlayer entity = event.getEntityPlayer();
        String KeyWord = "OnCriticalHit";
        if(entity == null)return;
        ItemStack items[] = {entity.getHeldItemMainhand(), entity.getHeldItemOffhand(), entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD), entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST), entity.getItemStackFromSlot(EntityEquipmentSlot.LEGS), entity.getItemStackFromSlot(EntityEquipmentSlot.FEET)};
        for (byte i = 1; i <= 6; i++) {
            if (items[i - 1] == null || !items[i - 1].hasTagCompound() || !items[i - 1].getTagCompound().hasKey("rce"))
                continue;
            NBTTagCompound nbt = items[i - 1].getTagCompound().getCompoundTag("rce");
            if (nbt.hasKey(KeyWord)) {
                block(items[i - 1], entity, nbt.getString(KeyWord), i);
            }
        }
    }

    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.RightClickItem event){
        EntityPlayer entity = event.getEntityPlayer();
        String KeyWord = "OnRightClick";
        if(entity == null)return;
        ItemStack items[] = {entity.getHeldItemMainhand(), entity.getHeldItemOffhand(), entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD), entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST), entity.getItemStackFromSlot(EntityEquipmentSlot.LEGS), entity.getItemStackFromSlot(EntityEquipmentSlot.FEET)};
        for (byte i = 1; i <= 6; i++) {
            if (items[i - 1] == null || !items[i - 1].hasTagCompound() || !items[i - 1].getTagCompound().hasKey("rce"))
                continue;
            NBTTagCompound nbt = items[i - 1].getTagCompound().getCompoundTag("rce");
            if (nbt.hasKey(KeyWord)) {
                block(items[i - 1], entity, nbt.getString(KeyWord), i);
            }
        }
    }
    @SubscribeEvent
    public void rightClick(PlayerInteractEvent.RightClickItem event){
        EntityPlayer player = event.getEntityPlayer();
        if(player.isCreative() && player.isSneaking()){
            BlockPos pos = player.getPosition();
            player.openGui(RCsEffect.instance, GuiLoader.TEST_GUI, player.world, pos.getX(), pos.getY(), pos.getZ());

        }
    }
}
