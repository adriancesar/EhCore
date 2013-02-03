package com.ehaqui.ehcore.tps;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;


public class TPS
{
    public static int        tps    = 0;
    public static long       second = 0;
    
    public static BukkitTask task;
    
    public static float getServerTPS()
    {
        return tps;
    }
    
    public static long getSeconds()
    {
        return second;
    }
    
    public static void setup(Plugin plugin)
    {
        task = new BukkitRunnable() {
            long sec;
            int  ticks;
            
            @Override
            public void run()
            {
                sec = (System.currentTimeMillis() / 1000);
                
                if(second == sec)
                {
                    ticks++;
                }
                else
                {
                    second = sec;
                    tps = (tps == 0 ? ticks : ((tps + ticks) / 2));
                    ticks = 0;
                    
                    System.out.print("TPS = " + tps);
                }
            }
        }.runTaskTimer(plugin, 20, 1);
    }
    
    public static void cancel()
    {
        task.cancel();
    }
    
    public static BukkitTask getTask()
    {
        return task;
    }
    
    
}
