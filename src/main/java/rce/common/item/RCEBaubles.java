package rce.common.item;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import rce.common.item.creativetabs.TabsLoader;
import rceutils.InGameExecute;

public class RCEBaubles extends RCEItem implements IBauble {
	
	protected int[] effect1 = null;
	protected int[] effect2 = null;
	protected int[] effect3 = null;
	protected boolean canEquip = true;
	protected boolean canUnequip = true;
	protected int type;
	public RCEBaubles(java.util.Map<String,String> data) {
		super(data);
		this.maxStackSize = 1;
		this.type = Integer.parseInt(data.get("Type"));
		if(data.containsKey("CanEquip") && Integer.parseInt(data.get("CanEquip")) != 1) canEquip = false;
		if(data.containsKey("CanUnequip") && Integer.parseInt(data.get("CanUnequip")) != 1) canUnequip = false;
		if(data.containsKey("EquipEffect")) effect1 = stringTo(data.get("EquipEffect"));
		if(data.containsKey("WornEffect")) effect2 = stringTo(data.get("WornEffect"));
		if(data.containsKey("UnequipEffect")) effect3 = stringTo(data.get("UnequipEffect"));
		this.setCreativeTab(TabsLoader.baublesTabs);
	}
	
	private static int[] stringTo(String str) {
		String num[] = str.split(" ");
		int[] n = new int[num.length];
		for(int x = 0 ; x < n.length ; x ++) {
			n[x] = Integer.parseInt(num[x]);
		}
		return n;
	}

	@Override
	public BaubleType getBaubleType(ItemStack i) {
		BaubleType t;
		int u = type;
		if(i.hasTagCompound() && i.getTagCompound().hasKey("rce") && i.getTagCompound().getCompoundTag("rce").hasKey("Type")) {
			u = i.getTagCompound().getCompoundTag("rce").getInteger("Type");
		}
		switch (u){
			case 2 : t = BaubleType.AMULET; break;
			case 3 : t = BaubleType.BELT; break;
			case 4 : t = BaubleType.HEAD; break;
			case 5 : t = BaubleType.BODY; break;
			case 6 : t = BaubleType.CHARM; break;
			case 7 : t = BaubleType.TRINKET; break;
			default : t = BaubleType.RING; break;
		}
		return t;

	}

	@Override
	public boolean canEquip(ItemStack i , EntityLivingBase e) {
		boolean foot = this.canEquip;
		if(i.hasTagCompound() && i.getTagCompound().hasKey("rce") && i.getTagCompound().getCompoundTag("rce").hasKey("CanEquip")) {
			foot = i.getTagCompound().getCompoundTag("rce").getByte("CanEquip") == 1 ? true : false;
		}
		return foot;
	}

	@Override
	public boolean canUnequip(ItemStack i , EntityLivingBase e) {
		boolean foot = this.canUnequip;
		if(i.hasTagCompound() && i.getTagCompound().hasKey("rce") && i.getTagCompound().getCompoundTag("rce").hasKey("CanUnequip")) {
			foot = i.getTagCompound().getCompoundTag("rce").getByte("CanUnequip") == 1 ? true : false;
		}
		return foot;
	}

	@Override
	public void onEquipped(ItemStack i , EntityLivingBase e) {
		if(i.hasTagCompound() && i.getTagCompound().hasKey("rce")) {
			NBTTagCompound nbt = i.getTagCompound().getCompoundTag("rce");
			if(nbt.hasKey("EquipEffect")) {
				if(nbt.hasKey("EquipEffect_random")) {
					InGameExecute.buffGiver(nbt.getIntArray("EquipEffect_random"), e, nbt.getIntArray("EquipEffect"));
				}else {
					InGameExecute.buffGiver(e, nbt.getIntArray("EquipEffect"));
				}
				if(nbt.hasKey("EquipEffect_sound")) {
					InGameExecute.soundPlayer(e, nbt.getString("EquipEffect_sound"), e.world);
				}
				if(e instanceof EntityPlayer && nbt.hasKey("EquipEffect_talk")) {
					InGameExecute.playerTalker((EntityPlayer)e, nbt.getTagList("EquipEffect_talk", 8));
				}
			}else {
				InGameExecute.buffGiver(e, effect1);
			}
		}else {
			InGameExecute.buffGiver(e, effect1);
		}
	}

	@Override
	public void onUnequipped(ItemStack i , EntityLivingBase e) {
		if(i.hasTagCompound() && i.getTagCompound().hasKey("rce")) {
			NBTTagCompound nbt = i.getTagCompound().getCompoundTag("rce");
			if(nbt.hasKey("UnequipEffect")) {
				if(nbt.hasKey("UnequipEffect_random")) {
					InGameExecute.buffGiver(nbt.getIntArray("UnequipEffect_random"), e, nbt.getIntArray("UnequipEffect"));
				}else {
					InGameExecute.buffGiver(e, nbt.getIntArray("UnequipEffect"));
				}
				if(nbt.hasKey("UnequipEffect_sound")) {
					InGameExecute.soundPlayer(e, nbt.getString("UnequipEffect_sound"), e.world);
				}
				if(e instanceof EntityPlayer && nbt.hasKey("UnequipEffect_talk")) {
					InGameExecute.playerTalker((EntityPlayer)e, nbt.getTagList("UnequipEffect_talk", 8));
				}
			}else {
				InGameExecute.buffGiver(e, effect3);
			}
		}else {
			InGameExecute.buffGiver(e, effect3);
		}
	}

	@Override
	public void onWornTick(ItemStack i , EntityLivingBase e) {
		if(i.hasTagCompound() && i.getTagCompound().hasKey("rce")) {
			NBTTagCompound nbt = i.getTagCompound().getCompoundTag("rce");
			if(nbt.hasKey("WornEffect")) {
				InGameExecute.buffGiver(e, nbt.getIntArray("WornEffect"));
			}else {
				InGameExecute.buffGiver(e, effect2);
			}
		}else {
			InGameExecute.buffGiver(e, effect2);
		}
	}
}
