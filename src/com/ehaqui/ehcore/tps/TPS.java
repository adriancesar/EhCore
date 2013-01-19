package com.ehaqui.ehcore.tps;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;


public class TPS
{
    public static int  tps    = 0;
    public static long second = 0;
    
    public static float getServerTPS()
    {
        return tps;
    }
    
    public static void setup(Plugin plugin)
    {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            long sec;
            int  ticks;
            
            @Override
            public void run()
            {
                sec = System.currentTimeMillis() / 1000;
                if(second == sec)
                {
                    ticks++;
                }
                else
                {
                    second = sec;
                    tps = tps == 0 ? ticks : (tps + ticks) / 2;
                    ticks = 0;
                }
            }
        }, 20, 1);
    }
}
