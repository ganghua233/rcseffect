package rce.oldver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Timer;

import rce.RCsEffect;

import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;

public class Tools {
	

	public static final String MODID="rcseffect:";
	public static File debugFile=new File("b:"+File.separator+"debugFile.txt");


	public static void writeInOutFile(String address,String info) {
		OutputStream writer=null;
		File file=new File(address);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			writer=new FileOutputStream(file);
		}catch(Exception e) {
			
		}
		try {
			writer.write(info.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public static void writeInDebugInfo(String info) {
		OutputStream writer=null;
		try {
			writer=new FileOutputStream(debugFile,true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			String str=getTime()+"\r\n"+info+"\r\n";
			writer.write(str.getBytes());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	

	public static void buffGiver(EntityLivingBase entity,PotionEffect ea) {
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

	public static void bufferGiverController(int[] ran,EntityLivingBase entity,int[] buff) {
		PotionEffect[] b=new PotionEffect[buff.length/3];
		int index=0;
		int a1=buff.length/3;
		int foot=0;
		for(int aa=0;aa<a1;aa++) {
			b[index]=new PotionEffect(Potion.getPotionById(buff[foot]),buff[foot+1],buff[foot+2]);
			foot+=3;
			index++;
		}
		
		//Percent		
		int a=0;
		for(PotionEffect e:b) {
			if(a>ran.length-1) {
				a++;
				buffGiver(entity,e);
				continue;
			}
			if(percenter(ran[a])) {
				buffGiver(entity,e);
				a++;
				continue;
			}else {
				a++;
				continue;
			}
		}
	}

	public static void bufferGiverController(EntityLivingBase entity,int[] buff) {
		PotionEffect[] b=new PotionEffect[buff.length/3];
		int index=0;
		int a=buff.length/3;
		int foot=0;
		for(int aa=0;aa<a;aa++) {
			b[index]=new PotionEffect(Potion.getPotionById(buff[foot]),buff[foot+1],buff[foot+2]);
			foot+=3;
			index++;
		}
		
		for(PotionEffect p:b) {
			buffGiver(entity,p);
		}
	}

	public static void playerTalker(EntityPlayer player,NBTTagList list) {
		int v=list.tagCount();
		Random r=new Random();
		int cl=r.nextInt(v);
		String message = list.getStringTagAt(cl);
		player.sendMessage(new TextComponentString(message));
	}
	
	public static String getTime() {
		Calendar c=Calendar.getInstance();
		@SuppressWarnings("deprecation")
		String basicTime=""+c.get(Calendar.YEAR)+"."+(c.get(Calendar.MONTH)+1)+"."+c.get(Calendar.DAY_OF_MONTH)+" "+c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE);
		return "["+basicTime+"]";
	}
	

	public static boolean percenter(double num){

		return percenter((int)num*100);
	}

	public static boolean percenter(int x) {
		boolean foot=false;
		if(x>=100) {
			return true;
		}
		
		if(x<=0) {
			return false;
		}
		
		Random randomer = new Random();
		int u=randomer.nextInt(100)+1;
		
		if(u <= x) {
			foot=true;
		}
		
		return foot;
	}

	public static void soundPlayer(EntityLivingBase e,String sound,World world) {
		String str[] = sound.split(" ");
		if(str.length != 3)return;
		try {
			world.playSound(null, e.posX, e.posY, e.posZ, new SoundEvent(new ResourceLocation(str[0])), SoundCategory.PLAYERS, Integer.parseInt(str[1]), Integer.parseInt(str[2]));
		}catch(Exception exception) {
			
		}
	}

	public static void soundPlayer(EntityLivingBase entity,NBTTagList list,World world) {
		int v=list.tagCount();
		Random r = new Random();
		int cl=r.nextInt(v);
		String sound = list.getStringTagAt(cl);
		soundPlayer(entity, sound, world);
	}

	public static EntityItem nbtToItemEntity(EntityLivingBase entity,NBTTagCompound nbt) {
		ItemStack item=new ItemStack(nbt);
		EntityItem ei=new EntityItem(entity.world , entity.posX , entity.posY , entity.posZ , item);
		return ei;
	}
}
