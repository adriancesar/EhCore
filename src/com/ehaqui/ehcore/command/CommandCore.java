package com.ehaqui.ehcore.command;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.ehaqui.ehcore.EhCorePlugin;
import com.ehaqui.ehcore.api.command.Command;
import com.ehaqui.ehcore.api.command.CommandContext;
import com.ehaqui.ehcore.api.command.Requirements;
import com.ehaqui.ehcore.api.util.Messaging;
import com.ehaqui.ehcore.api.util.StringHelper;
import com.ehaqui.ehcore.tps.TPS;


@Requirements
public class CommandCore
{
    
    public CommandCore(EhCorePlugin ehCorePlugin)
    {
    }
    
    @Command(aliases = { "lag" },
             permission = "ehcore.tps",
             max = 0,
             min = 0)
    public void sendTPSStats(CommandContext args, Player player)
    {
        Messaging.send(player, "&fThere are &c" + getEntities() + " &fentities, &a" + Bukkit.getServer().getOnlinePlayers().length + " &fplayers");
        
        // Memory
        long memUsed = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576;
        long memMax = Runtime.getRuntime().maxMemory() / 1048576;
        long memFree = memMax - memUsed;
        long percentageFree = (memUsed * 100) / memMax;
        Messaging.send(player, StringHelper.createBar((int) percentageFree) + "&7/Mem &fU=&c" + memUsed + "M &fF=&a" + memFree + "M &fT=&e" + memMax + "M &f" + percentageFree + "%");
        
        // TPS 1s
        Messaging.send(player, getLagMeterBar(TPS.getTPS()) + "&7/1sec TPS &a" + TPS.getTPS());
        // TPS 60s
        Messaging.send(player, getLagMeterBar(TPS.getAverageTPS()) + "&7/1min  TPS &a" + TPS.getAverageTPS() + " &favg");
        // TPS time
        float percentageTime = (100 * TPS.getTimeTPS()) / 50;
        Messaging.send(player, StringHelper.createBar((int) percentageTime) + "&7/Time : " + TPS.getTimeTPS() + "ms");
    }
    
    public int getEntities()
    {
        int totalEntities = 0;
        List<World> worlds = Bukkit.getServer().getWorlds();
        
        for (World world : worlds)
        {
            String worldName = world.getName();
            int i = Bukkit.getServer().getWorld(worldName).getEntities().size();
            totalEntities += i;
            //sendMessage(sender, 0, ChatColor.GOLD+"Entities in world \""+worldName+"\": "+i);
        }
        
        return totalEntities;
    }
    
    public String getLagMeterBar(float tps)
    {
        ChatColor wrapColour = ChatColor.WHITE;
        String lagMeter = "";
        
        ChatColor colour;
        if(tps >= 20)
        {
            colour = ChatColor.GREEN;
        }
        else if(tps >= 18)
        {
            colour = ChatColor.GREEN;
        }
        else if(tps >= 15)
        {
            colour = ChatColor.YELLOW;
        }
        else
        {
            colour = ChatColor.RED;
        }
        
        if(tps < 21)
        {
            int looped = 0;
            
            while (looped++ < tps)
            {
                lagMeter += "#";
            }
            
            lagMeter += ChatColor.WHITE;
            while (looped++ <= 20)
            {
                lagMeter += ChatColor.GRAY + "_";
            }
        }
        else
        {
            return "Please wait for polling.";
        }
        
        return wrapColour + "[" + colour + lagMeter + wrapColour + "]";
    }
}
