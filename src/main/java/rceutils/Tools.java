package rceutils;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import rce.RCsEffect;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Random;

/**
 * @author RC_diamond_GH
 * Some tools convient to run a special function.
 */
public class Tools{
    /**
     * P(This function returns true) = p/100
     * @param probability
     * @return the result
     */
    public static boolean percent(int probability){
        Random randomBuilder = new Random();
        int randomNumber = randomBuilder.nextInt(100)+1;
        boolean b = randomNumber <= probability;
        return b;
    }

    /**
     * Output the debug information to the debug file.
     * @param info
     */
    public static void debugInfoOutput(String info){
        if (RCsEffect.debugFile != null){
            try {
                FileOutputStream outputExecutor = new FileOutputStream(RCsEffect.debugFile);
                try {
                    outputExecutor.write(info.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * return that what the time is it now
     */
    public static String getTime() {
        Calendar c=Calendar.getInstance();
        @SuppressWarnings("deprecation")
        String basicTime=""+c.get(Calendar.YEAR)+"."+(c.get(Calendar.MONTH)+1)+"."+c.get(Calendar.DAY_OF_MONTH)+" "+c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE);
        return "["+basicTime+"]";
    }
}
