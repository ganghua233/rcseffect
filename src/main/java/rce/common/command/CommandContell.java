package rce.common.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import rceutils.InGameExecute;
import rceutils.Tools;

public class CommandContell extends CommandBase{

    @Override
    public String getName() {
        return "contell";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "Read the intruction,please.";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] coms) throws CommandException {

        if(coms.length==0) {
            throw new WrongUsageException("commands.contell.usage");
        }else {
            EntityPlayerMP players= CommandBase.getPlayer(server, sender, coms[0]);

            String[] temps=coms;

            if(coms.length%2!=0) {
                throw new WrongUsageException("commands.contell.usage");
            }

            String[] texts=new String[coms.length/2];
            int[] timeQuantum=new int[(coms.length-2)/2];

            if(temps.length==1) {
                throw new WrongUsageException("commands.contell.usage");
            }else {

                for(int n = 0; n < timeQuantum.length ; n++) {
                    texts[n] = coms[2 * n + 1].replace('|', ' ');

                    int tempNum = 0;
                    try {
                        tempNum = Integer.parseInt(coms[2 * n + 2]);
                    }catch(NumberFormatException e) {
                        throw new WrongUsageException("commands.contell.usage");
                    }

                    if( n !=(coms.length -2)/2 -1) {
                        timeQuantum[n] = tempNum;
                    }
                }
                texts[texts.length - 1] = coms[coms.length - 1].replace('|', ' ');
                InGameExecute.messageHandler(players, texts[0]);
                if(texts.length > 1){
                    ConTellHelper helper = new ConTellHelper(players,texts,timeQuantum,sender,server);
                    new Thread(helper, "Loser").start();
                }
            }
        }
    }
}
class ConTellHelper implements Runnable{
    EntityPlayerMP players;
    String[] words;
    int[] deley;
    ICommandSender sender;
    MinecraftServer server;

    public ConTellHelper(EntityPlayerMP players,String[] words,int[] deley,ICommandSender sender,MinecraftServer server) {
        this.players=players;
        this.words=words;
        this.deley=deley;
        this.sender=sender;
        this.server=server;
    }

    @Override
    public void run() {
        for(int a = 1;  a < words.length; a++) {
            try {
                Thread.sleep(deley[a-1]);
                InGameExecute.messageHandle(players ,words[a], server);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
