package rce.oldver;

import java.awt.Event;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

public class EventBase {
	
	public EventBase() {
		FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);
    }
	
	@SubscribeEvent
	public void onLivingHurt(LivingHurtEvent e) {
		EntityLivingBase wounded=e.getEntityLiving();
		ItemStack woundedItems[] = {wounded.getHeldItemMainhand(),wounded.getHeldItemOffhand(),wounded.getItemStackFromSlot(EntityEquipmentSlot.HEAD),wounded.getItemStackFromSlot(EntityEquipmentSlot.CHEST),wounded.getItemStackFromSlot(EntityEquipmentSlot.LEGS),wounded.getItemStackFromSlot(EntityEquipmentSlot.FEET)};
		String part[] = {"_MainHand","_OffHand","_Head","_Chest","_Leg","_Feet"};
		
		//HurtEffect
		for(int i = 0;i < 6;i ++) {
			if(woundedItems[i] != null && woundedItems[i].hasTagCompound() && woundedItems[i].getTagCompound().hasKey("rcseffect")) {
				NBTTagCompound nbt = woundedItems[i].getTagCompound().getCompoundTag("rcseffect");
				if(nbt.hasKey("HurtEffect")) {
					if(nbt.hasKey("HurtEffect_random")) {
						Tools.bufferGiverController(nbt.getIntArray("HurtEffect_random"), wounded, nbt.getIntArray("HurtEffect"));
					}else {
						Tools.bufferGiverController(wounded, nbt.getIntArray("HurtEffect"));
					}
					
					if((wounded instanceof EntityPlayer) && nbt.hasKey("HurtEffect_talk")) {
						Tools.playerTalker((EntityPlayer)wounded, nbt.getTagList("HurtEffect_talk", 8));
					}
					if(nbt.hasKey("HurtEffect_sound")) {
						Tools.soundPlayer(wounded, nbt.getTagList("HurtEffect_sound", 8),wounded.world);
					}
				}
				
				if(nbt.hasKey("HurtEffect"+part[i])) {
					if(nbt.hasKey("HurtEffect" + part[i] + "_random")) {
						Tools.bufferGiverController(nbt.getIntArray("HurtEffect" + part[i] + "_random"), wounded, nbt.getIntArray("HurtEffect"+part[i]));
					}else {
						Tools.bufferGiverController(wounded, nbt.getIntArray("HurtEffect"+part[i]));
					}
					
					if((wounded instanceof EntityPlayer) && nbt.hasKey("HurtEffect"+ part[i] +"_talk")) {
						Tools.playerTalker((EntityPlayer)wounded, nbt.getTagList("HurtEffect"+ part[i] +"_talk", 8));
					}
					if(nbt.hasKey("HurtEffect"+ part[i] +"_sound")) {
						Tools.soundPlayer(wounded, nbt.getTagList("HurtEffect"+ part[i] +"_sound", 8),wounded.world);
					}
				}
			}
		}
		if(e.getSource() != null && e.getSource().getTrueSource() != null && e.getSource().getTrueSource() instanceof EntityLivingBase) {
			EntityLivingBase attacker=(EntityLivingBase) e.getSource().getTrueSource();
			ItemStack attackerItems[] = {attacker.getHeldItemMainhand(),attacker.getHeldItemOffhand(),attacker.getItemStackFromSlot(EntityEquipmentSlot.HEAD),attacker.getItemStackFromSlot(EntityEquipmentSlot.CHEST),attacker.getItemStackFromSlot(EntityEquipmentSlot.LEGS),attacker.getItemStackFromSlot(EntityEquipmentSlot.FEET)};
			for(int i=0;i<6;i++) {
				//HurtEnemy
				if(woundedItems[i] != null && woundedItems[i].hasTagCompound() && woundedItems[i].getTagCompound().hasKey("rcseffect")) {
					NBTTagCompound nbt = woundedItems[i].getTagCompound().getCompoundTag("rcseffect");
					
					if(nbt.hasKey("HurtEnemy")) {
						if(nbt.hasKey("HurtEnemy_random")) {
							Tools.bufferGiverController(nbt.getIntArray("HurtEnemy_random"), attacker, nbt.getIntArray("HurtEnemy"));
						}else {
							Tools.bufferGiverController(attacker, nbt.getIntArray("HurtEnemy"));
						}
						
						if((attacker instanceof EntityPlayer) && nbt.hasKey("HurtEnemy_talk")) {
							Tools.playerTalker((EntityPlayer)attacker, nbt.getTagList("HurtEnemy_talk", 8));
						}
						if(nbt.hasKey("HurtEnemy_sound")) {
							Tools.soundPlayer(attacker, nbt.getTagList("HurtEnemy_sound", 8),attacker.world);
						}
					}
					
					if(nbt.hasKey("HurtEnemy"+part[i])) {
						if(nbt.hasKey("HurtEnemy" + part[i] + "_random")) {
							Tools.bufferGiverController(nbt.getIntArray("HurtEnemy" + part[i] + "_random"), attacker, nbt.getIntArray("HurtEnemy"+part[i]));
						}else {
							Tools.bufferGiverController(attacker, nbt.getIntArray("HurtEnemy"+part[i]));
						}
						
						if((attacker instanceof EntityPlayer) && nbt.hasKey("HurtEnemy"+ part[i] +"_talk")) {
							Tools.playerTalker((EntityPlayer)attacker, nbt.getTagList("HurtEnemy"+ part[i] +"_talk", 8));
						}
						if(nbt.hasKey("HurtEnemy"+ part[i] +"_sound")) {
							Tools.soundPlayer(attacker, nbt.getTagList("HurtEnemy"+ part[i] +"_sound", 8),attacker.world);
						}
					}
				}
				
				//AttackEffect AttackEnemy
				if(attackerItems[i] !=null && attackerItems[i].hasTagCompound() && attackerItems[i].getTagCompound().hasKey("rcseffect")) {
					NBTTagCompound nbt = attackerItems[i].getTagCompound().getCompoundTag("rcseffect");
					
					if(nbt.hasKey("AttackEffect")) {
						if(nbt.hasKey("AttackEffect_random")) {
							Tools.bufferGiverController(nbt.getIntArray("AttackEffect_random"), attacker, nbt.getIntArray("AttackEffect"));
						}else {
							Tools.bufferGiverController(attacker, nbt.getIntArray("AttackEffect"));
						}
						
						if((attacker instanceof EntityPlayer) && nbt.hasKey("AttackEffect_talk")) {
							Tools.playerTalker((EntityPlayer)attacker, nbt.getTagList("AttackEffect_talk", 8));
						}
						if(nbt.hasKey("AttackEffect_sound")) {
							Tools.soundPlayer(attacker, nbt.getTagList("AttackEffect_sound", 8),attacker.world);
						}
					}
					
					if(nbt.hasKey("AttackEffect"+part[i])) {
						if(nbt.hasKey("AttackEffect" + part[i] + "_random")) {
							Tools.bufferGiverController(nbt.getIntArray("AttackEffect" + part[i] + "_random"), attacker, nbt.getIntArray("AttackEffect"+part[i]));
						}else {
							Tools.bufferGiverController(attacker, nbt.getIntArray("AttackEffect"+part[i]));
						}
						
						if((attacker instanceof EntityPlayer) && nbt.hasKey("AttackEffect"+ part[i] +"_talk")) {
							Tools.playerTalker((EntityPlayer)attacker, nbt.getTagList("AttackEffect"+ part[i] +"_talk", 8));
						}
						if(nbt.hasKey("AttackEffect"+ part[i] +"_sound")) {
							Tools.soundPlayer(attacker, nbt.getTagList("AttackEffect"+ part[i] +"_sound", 8),attacker.world);
						}
					}
					
					if(nbt.hasKey("AttackEnemy")) {
						if(nbt.hasKey("AttackEnemy_random")) {
							Tools.bufferGiverController(nbt.getIntArray("AttackEnemy_random"), wounded, nbt.getIntArray("AttackEnemy"));
						}else {
							Tools.bufferGiverController(wounded, nbt.getIntArray("AttackEnemy"));
						}
						
						if((wounded instanceof EntityPlayer) && nbt.hasKey("AttackEnemy_talk")) {
							Tools.playerTalker((EntityPlayer)wounded, nbt.getTagList("AttackEnemy_talk", 8));
						}
						if(nbt.hasKey("AttackEnemy_sound")) {
							Tools.soundPlayer(wounded, nbt.getTagList("AttackEnemy_sound", 8),wounded.world);
						}
					}
					
					if(nbt.hasKey("AttackEnemy"+part[i])) {
						if(nbt.hasKey("AttackEnemy" + part[i] + "_random")) {
							Tools.bufferGiverController(nbt.getIntArray("AttackEnemy" + part[i] + "_random"), wounded, nbt.getIntArray("AttackEnemy"+part[i]));
						}else {
							Tools.bufferGiverController(wounded, nbt.getIntArray("AttackEnemy"+part[i]));
						}
						
						if((wounded instanceof EntityPlayer) && nbt.hasKey("AttackEnemy"+ part[i] +"_talk")) {
							Tools.playerTalker((EntityPlayer)wounded, nbt.getTagList("AttackEnemy"+ part[i] +"_talk", 8));
						}
						if(nbt.hasKey("AttackEnemy"+ part[i] +"_sound")) {
							Tools.soundPlayer(wounded, nbt.getTagList("AttackEnemy"+ part[i] +"_sound", 8),wounded.world);
						}
					}
				}
				//Special Attack
				if(woundedItems[i] !=null && woundedItems[i].hasTagCompound() && woundedItems[i].getTagCompound().hasKey("rcseffect")) {
					if(woundedItems[i].getTagCompound().hasKey("SpecialMark")) {
						String mark=woundedItems[i].getTagCompound().getString("SpecialMark");
						for(int u = 0;u<6;u++) {
							if(attackerItems[u] != null && attackerItems[u].hasTagCompound() && attackerItems[u].getTagCompound().hasKey("rcseffect")) {
								NBTTagCompound nbt=attackerItems[u].getTagCompound();
								String code="A_Special_A_"+mark;
								if(nbt.hasKey(code)) {
									if(nbt.hasKey(code+"_random")) {
										Tools.bufferGiverController(nbt.getIntArray(code+"_random"), attacker, nbt.getIntArray(code));
									}else {
										Tools.bufferGiverController(attacker, nbt.getIntArray(code));
									}
									if(nbt.hasKey(code+"_sound")) {
										Tools.soundPlayer(attacker, nbt.getTagList(code+"_sound", 8), attacker.world);
									}
									
									if(nbt.hasKey(code + part[u] + "_random")) {
										Tools.bufferGiverController(nbt.getIntArray(code + part[u] + "_random"), attacker, nbt.getIntArray(code + part[u]));
									}else {
										Tools.bufferGiverController(attacker, nbt.getIntArray(code + part[u]));
									}
									if(nbt.hasKey(code + part[u] + "_sound")) {
										Tools.soundPlayer(attacker, nbt.getTagList(code + part[u] + "_sound", 8), attacker.world);
									}
								}
								
								String code2="A_Special_W_"+mark;
								if(nbt.hasKey(code2)) {
									if(nbt.hasKey(code2+"_random")) {
										Tools.bufferGiverController(nbt.getIntArray(code2+"_random"), wounded, nbt.getIntArray(code2));
									}else {
										Tools.bufferGiverController(wounded, nbt.getIntArray(code2));
									}
									if(nbt.hasKey(code2+"_sound")) {
										Tools.soundPlayer(wounded, nbt.getTagList(code2+"_sound", 8), wounded.world);
									}
									
									if(nbt.hasKey(code2 + part[u] + "_random")) {
										Tools.bufferGiverController(nbt.getIntArray(code2 + part[u] + "_random"), wounded, nbt.getIntArray(code2 + part[u]));
									}else {
										Tools.bufferGiverController(wounded, nbt.getIntArray(code2 + part[u]));
									}
									if(nbt.hasKey(code2 + part[u] + "_sound")) {
										Tools.soundPlayer(wounded, nbt.getTagList(code2 + part[u] + "_sound", 8), wounded.world);
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerAttack(AttackEntityEvent e) {
		EntityPlayer player=e.getEntityPlayer();
		if(player==null)return;
		ItemStack items[] = {player.getHeldItemMainhand(),player.getHeldItemOffhand(),player.getItemStackFromSlot(EntityEquipmentSlot.HEAD),player.getItemStackFromSlot(EntityEquipmentSlot.CHEST),player.getItemStackFromSlot(EntityEquipmentSlot.LEGS),player.getItemStackFromSlot(EntityEquipmentSlot.FEET)};
		String part[] = {"_MainHand","_OffHand","_Head","_Chest","_Leg","_Feet"};
		for(int i = 0;i < 6;i ++) {
			if(items[i] != null && items[i].hasTagCompound() && items[i].getTagCompound().hasKey("rcseffect")) {
				NBTTagCompound nbt = items[i].getTagCompound().getCompoundTag("rcseffect");
				if(nbt.hasKey("PlayerAttack")) {
					if(nbt.hasKey("PlayerAttack_random")) {
						Tools.bufferGiverController(nbt.getIntArray("PlayerAttack_random"), player, nbt.getIntArray("PlayerAttack"));
					}else {
						Tools.bufferGiverController(player, nbt.getIntArray("PlayerAttack"));
					}
					
					if((player instanceof EntityPlayer) && nbt.hasKey("PlayerAttack_talk")) {
						Tools.playerTalker((EntityPlayer)player, nbt.getTagList("PlayerAttack_talk", 8));
					}
					if(nbt.hasKey("PlayerAttack_sound")) {
						Tools.soundPlayer(player, nbt.getTagList("PlayerAttack_sound", 8),player.world);
					}
				}
				
				if(nbt.hasKey("PlayerAttack"+part[i])) {
					if(nbt.hasKey("PlayerAttack" + part[i] + "_random")) {
						Tools.bufferGiverController(nbt.getIntArray("PlayerAttack" + part[i] + "_random"), player, nbt.getIntArray("PlayerAttack"+part[i]));
					}else {
						Tools.bufferGiverController(player, nbt.getIntArray("PlayerAttack"+part[i]));
					}
					
					if((player instanceof EntityPlayer) && nbt.hasKey("PlayerAttack"+ part[i] +"_talk")) {
						Tools.playerTalker((EntityPlayer)player, nbt.getTagList("PlayerAttack"+ part[i] +"_talk", 8));
					}
					if(nbt.hasKey("PlayerAttack"+ part[i] +"_sound")) {
						Tools.soundPlayer(player, nbt.getTagList("PlayerAttack"+ part[i] +"_sound", 8),player.world);
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent e) {
		if(e.getSource()==null)return;
		if(e.getSource().getTrueSource()==null)return;
		if(e.getSource().getTrueSource() instanceof EntityLivingBase) {
			EntityLivingBase killer=(EntityLivingBase) e.getSource().getTrueSource();
			ItemStack items[] = {killer.getHeldItemMainhand(),killer.getHeldItemOffhand(),killer.getItemStackFromSlot(EntityEquipmentSlot.HEAD),killer.getItemStackFromSlot(EntityEquipmentSlot.CHEST),killer.getItemStackFromSlot(EntityEquipmentSlot.LEGS),killer.getItemStackFromSlot(EntityEquipmentSlot.FEET)};
			
			String part[] = {"_MainHand","_OffHand","_Head","_Chest","_Leg","_Feet"};
			//KilledEffect
			for(int i = 0;i < 6;i ++) {
				if(items[i] != null && items[i].hasTagCompound() && items[i].getTagCompound().hasKey("rcseffect")) {
					NBTTagCompound nbt = items[i].getTagCompound().getCompoundTag("rcseffect");
					if(nbt.hasKey("KilledEffect")) {
						if(nbt.hasKey("KilledEffect_random")) {
							Tools.bufferGiverController(nbt.getIntArray("KilledEffect_random"), killer, nbt.getIntArray("KilledEffect"));
						}else {
							Tools.bufferGiverController(killer, nbt.getIntArray("KilledEffect"));
						}
						
						if((killer instanceof EntityPlayer) && nbt.hasKey("KilledEffect_talk")) {
							Tools.playerTalker((EntityPlayer)killer, nbt.getTagList("KilledEffect_talk", 8));
						}
						if(nbt.hasKey("KilledEffect_sound")) {
							Tools.soundPlayer(killer, nbt.getTagList("KilledEffect_sound", 8),killer.world);
						}
					}
					
					if(nbt.hasKey("KilledEffect"+part[i])) {
						if(nbt.hasKey("KilledEffect" + part[i] + "_random")) {
							Tools.bufferGiverController(nbt.getIntArray("KilledEffect" + part[i] + "_random"), killer, nbt.getIntArray("KilledEffect"+part[i]));
						}else {
							Tools.bufferGiverController(killer, nbt.getIntArray("KilledEffect"+part[i]));
						}
						
						if((killer instanceof EntityPlayer) && nbt.hasKey("KilledEffect"+ part[i] +"_talk")) {
							Tools.playerTalker((EntityPlayer)killer, nbt.getTagList("KilledEffect"+ part[i] +"_talk", 8));
						}
						if(nbt.hasKey("KilledEffect"+ part[i] +"_sound")) {
							Tools.soundPlayer(killer, nbt.getTagList("KilledEffect"+ part[i] +"_sound", 8),killer.world);
						}
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onUpDate(LivingEvent.LivingUpdateEvent e) {
		if(e.getEntityLiving() == null)return;
		//WearEffect
		EntityLivingBase entity = e.getEntityLiving();
		ItemStack items[]  = {entity.getHeldItemMainhand(),entity.getHeldItemOffhand(),entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD),entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST),entity.getItemStackFromSlot(EntityEquipmentSlot.LEGS),entity.getItemStackFromSlot(EntityEquipmentSlot.FEET)};
		String part[] = {"_MainHand","_OffHand","_Head","_Chest","_Leg","_Feet"};
		for(int i = 0;i < 6;i ++) {
			if(items[i] != null && items[i].hasTagCompound() && items[i].getTagCompound().hasKey("rcseffect")) {
				NBTTagCompound nbt = items[i].getTagCompound().getCompoundTag("rcseffect");
				if(nbt.hasKey("WearEffect"))Tools.bufferGiverController(entity, nbt.getIntArray("WearEffect"));
				if(nbt.hasKey("WearEffect"+part[i]))Tools.bufferGiverController(entity, nbt.getIntArray("WearEffect"+part[i]));
			}
		}
		//HasEffect
		if(e.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) e.getEntityLiving();
			for(int i=0;i<36;i++) {
				ItemStack item = player.inventory.mainInventory.get(i);
				if(item != null && item.hasTagCompound() && item.getTagCompound().hasKey("rcseffect")) {
					NBTTagCompound nbt = item.getTagCompound().getCompoundTag("rcseffect");
					if(nbt.hasKey("HasEffect")) {
						Tools.bufferGiverController(player, nbt.getIntArray("HasEffect"));
					}
				}
			}
			//SuitEffect
			if(items[2] != null && items[3] != null && items[4] != null && items[5] != null) {
				int x=0;
				int y=0;
				if(items[2].hasTagCompound() && items[2].getTagCompound().hasKey("rcseffect")) {
					NBTTagCompound nbt = items[2].getTagCompound().getCompoundTag("rcseffect");
					if(nbt.hasKey("SuitMark")) {
						if(nbt.getInteger("SuitMark")!=0) {
							x=nbt.getInteger("SuitMark");
							y++;
						}
					}
				}
				for(int i=3;i<6;i++) {
					if(items[i].hasTagCompound() && items[i].getTagCompound().hasKey("rcseffect")) {
						NBTTagCompound nbt = items[i].getTagCompound().getCompoundTag("rcseffect");
						if(nbt.hasKey("SuitMark")) {
							if(nbt.getInteger("SuitMark") == x) {
								y++;
							}
						}
					}
				}
				if(y==4) {
					for(int i=2;i<6;i++) {
						if(items[i].getTagCompound().getCompoundTag("rcseffect").hasKey("SuitEffect")) {
							Tools.bufferGiverController(player, items[i].getTagCompound().getCompoundTag("rcseffect").getIntArray("SuitEffect"));
						}
					}
				}
			}
			//SuitEffectEX
			int marks[] = {0 , 0 , 0 , 0 , 0 , 0};
			int nums[] = {0 , 0 , 0 , 0 , 0 , 0};
			boolean accept[] = {false , false , false , false , false , false};
			for(int i=0;i<6;i++) {
				if(items[i] != null && items[i].hasTagCompound() && items[i].getTagCompound().hasKey("rcseffect")) {
					NBTTagCompound nbt = items[i].getTagCompound().getCompoundTag("rcseffect");
					if(nbt.hasKey("SuitMarkEX")) {
						marks[i]=nbt.getIntArray("SuitMarkEX")[0];
						nums[i]=nbt.getIntArray("SuitMarkEX")[1];
					}
				}
			}
			for(int i=0;i<6;i++) {
				if(marks[i]==0)continue;
				int a=-1;
				for(int v=0;v<6;v++) {
					if(marks[v]==marks[i]) {
						a++;
					}
				}
				if(a==nums[i]) {
					accept[i]=true;
				}
			}
			for(int i=0;i<6;i++) {
				if(accept[i]) {
					Tools.bufferGiverController(player, items[i].getTagCompound().getCompoundTag("rcseffect").getIntArray("SuitEffectEX"));
				}
			}
		}
	}
	
	@SubscribeEvent
	public void bloodPool(LivingHurtEvent e) {
		EntityLivingBase wounded=e.getEntityLiving();
		
		if(wounded == null) return;
		
		if(wounded.getHeldItemMainhand()!=null) {
			ItemStack item=wounded.getHeldItemMainhand();
			if(item.hasTagCompound() && item.getTagCompound().hasKey("rcseffect")) {
				NBTTagCompound nbt=item.getTagCompound().getCompoundTag("rcseffect");
				if(nbt.hasKey("EnableBloodPool")) {
					int[] bp=nbt.getIntArray("EnableBloodPool");
					if(bp.length!=5) {
						return;
					}else {
						if((wounded.getHealth()/wounded.getMaxHealth())*100<=bp[3]){
							if(Tools.percenter(bp[4])) {
								if(!nbt.hasKey("BloodPool")) {
									return;
								}
								if((wounded.getMaxHealth()-wounded.getHealth())>=nbt.getDouble("BloodPool")) {
									wounded.heal((float) nbt.getDouble("BloodPool"));
									nbt.setDouble("BloodPool",0);
								}else {
									nbt.setDouble("BloodPool",nbt.getDouble("BloodPool")-wounded.getMaxHealth()+wounded.getHealth());
									wounded.setHealth(wounded.getMaxHealth());
								}
							}
						}
					}
				}
			}
		}
		if(e.getSource() != null && e.getSource().getTrueSource() != null && e.getSource().getTrueSource() instanceof EntityLivingBase) {
			EntityLivingBase attacker=(EntityLivingBase) e.getSource().getTrueSource();
			if(attacker.getHeldItemMainhand()!=null) {
				ItemStack item=attacker.getHeldItemMainhand();
				if(item.hasTagCompound() && item.getTagCompound().hasKey("rcseffect")) {
					NBTTagCompound nbt=item.getTagCompound().getCompoundTag("rcseffect");
					if(nbt.hasKey("EnableBloodPool")) {
						int[] bp=nbt.getIntArray("EnableBloodPool");
						if(bp.length!=5) {
							return;
						}else {
							if(!nbt.hasKey("BloodPool")) {
								nbt.setDouble("BloodPool",0);
							}
							if(Tools.percenter(bp[2])) {
								
								float upper=(e.getAmount()/100)*bp[0];
								
								if(((float)bp[1]-nbt.getDouble("BloodPool"))<=upper) {
									nbt.setDouble("BloodPool",(float)bp[1]);
								}else {
									nbt.setDouble("BloodPool",nbt.getDouble("BloodPool")+upper);
								}
							}
						}
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onCriticalHit(CriticalHitEvent e) {
		EntityPlayer player=e.getEntityPlayer();
		ItemStack playerItems[] = {player.getHeldItemMainhand(),player.getHeldItemOffhand(),player.getItemStackFromSlot(EntityEquipmentSlot.HEAD),player.getItemStackFromSlot(EntityEquipmentSlot.CHEST),player.getItemStackFromSlot(EntityEquipmentSlot.LEGS),player.getItemStackFromSlot(EntityEquipmentSlot.FEET)};
		String part[] = {"_MainHand","_OffHand","_Head","_Chest","_Leg","_Feet"};
		for(int i = 0;i < 6;i++) {
			if(playerItems[i] != null && playerItems[i].hasTagCompound() && playerItems[i].getTagCompound().hasKey("rcseffect")) {
				NBTTagCompound nbt = playerItems[i].getTagCompound().getCompoundTag("rcseffect");
				if(nbt.hasKey("CriticalHitEffect")) {
					if(nbt.hasKey("CriticalHitEffect_random")) {
						Tools.bufferGiverController(nbt.getIntArray("CriticalHitEffect_random"), player, nbt.getIntArray("CriticalHitEffect"));
					}else {
						Tools.bufferGiverController(player, nbt.getIntArray("CriticalHitEffect"));
					}
					if(nbt.hasKey("CriticalHitEffect_sound")) {
						Tools.soundPlayer(player, nbt.getTagList("CriticalHitEffect_sound", 8), player.world);
					}
					if(nbt.hasKey("CriticalHitEffect_talk")) {
						Tools.playerTalker(player, nbt.getTagList("CriticalHitEffect_talk", 8));
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onHurt(LivingHurtEvent e) {
		EntityLivingBase wounded = e.getEntityLiving();
		if(wounded == null)return;
		if(e.getSource() == null)return;
		if(!(e.getSource().getTrueSource() instanceof EntityLivingBase))return;
		EntityLivingBase attacker = (EntityLivingBase) e.getSource().getTrueSource();
		if(attacker.getHeldItemMainhand() == null || !attacker.getHeldItemMainhand().hasTagCompound() || !attacker.getHeldItemMainhand().getTagCompound().hasKey("rcseffect") || !attacker.getHeldItemMainhand().getTagCompound().getCompoundTag("rcseffect").hasKey("FickleDamage_max") || !attacker.getHeldItemMainhand().getTagCompound().getCompoundTag("rcseffect").hasKey("FickleDamage_min"))return;
		float max = e.getAmount() + attacker.getHeldItemMainhand().getTagCompound().getCompoundTag("rcseffect").getFloat("FickleDamage_max");
		float min = e.getAmount() - attacker.getHeldItemMainhand().getTagCompound().getCompoundTag("rcseffect").getFloat("FickleDamage_min");
		float n = new Random().nextFloat();
		float finale = (1 - n) * max + n * min;
		e.setAmount(finale);
	}
}
