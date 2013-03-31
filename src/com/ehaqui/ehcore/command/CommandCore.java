package com.ehaqui.ehcore.command;

import java.io.File;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
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
        Messaging.send(player, StringHelper.createBar((int) percentageFree, true) + "&7/Mem &fU=&c" + memUsed + "M &fF=&a" + memFree + "M &fT=&e" + memMax + "M &f" + percentageFree + "%");
        
        // Chunk
        int chunkloaded = 0;
        
        for (World w : Bukkit.getWorlds())
            chunkloaded += w.getLoadedChunks().length;
        
        int chunkPercentage = ((chunkloaded * 100) / (getChunkLimit() * Bukkit.getOnlinePlayers().length));
        Messaging.send(player, StringHelper.createBar(chunkPercentage, true) + "&7/Chunk loaded &a" + chunkloaded + " / " + getChunkLimit() * Bukkit.getOnlinePlayers().length + " = " + chunkPercentage + "%");
        
        // TPS 1s
        Messaging.send(player, getLagMeterBar(TPS.getTPS()) + "&7/1sec TPS &a" + TPS.getTPS());
        // TPS 60s
        Messaging.send(player, getLagMeterBar(TPS.getAverageTPS()) + "&7/1min  TPS &a" + TPS.getAverageTPS() + " &favg");
        // TPS time
        float percentageTime = (100 * TPS.getTimeTPS()) / 10;
        Messaging.send(player, StringHelper.createBar((int) percentageTime, true) + "&7/Time : " + TPS.getTimeTPS() + "ms");
    }
    
    private int getChunkLimit()
    {
        int limitByChunk = 0;
        
        switch (Bukkit.getViewDistance())
        {
            case 1:
                limitByChunk = 9;
                break;
            
            case 2:
                limitByChunk = 25;
                break;
            
            case 3:
                limitByChunk = 49;
                break;
            
            case 4:
                limitByChunk = 81;
                break;
            
            case 5:
                limitByChunk = 121;
                break;
            
            case 6:
                limitByChunk = 169;
                break;
            
            case 7:
                limitByChunk = 225;
                break;
            
            case 8:
                limitByChunk = 289;
                break;
            
            case 9:
                limitByChunk = 361;
                break;
            
            case 10:
                limitByChunk = 441;
                break;
            
            case 11:
                limitByChunk = 529;
                break;
            
            case 12:
                limitByChunk = 625;
                break;
            
            case 13:
                limitByChunk = 729;
                break;
            
            case 14:
                limitByChunk = 841;
                break;
            
            case 15:
                limitByChunk = 961;
                break;
        }
        
        return limitByChunk;
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
    
    @Command(aliases = { "ehcore" },
             modifiers = { "fix" },
             usage = "fix [playername]",
             permission = "ehcore.fix",
             max = 2,
             min = 2)
    public void deletePlayerDatFile(CommandContext args, CommandSender sender)
    {
        String pName = args.getString(2);
        
        File BaseFolder = new File(Bukkit.getServer().getWorld("world").getWorldFolder(), "players");
        File playerFile = new File(BaseFolder, pName + ".dat");
        
        if(playerFile.exists())
        {
            playerFile.delete();
            
            Messaging.send(sender, "Arquivo referente ao Player '"+ pName +"' apagado");
        }
        else
        {
            Messaging.sendError(sender, "Arquivo referente ao Player '"+ pName +"' NAO ENCONTRADO");
        }
    }
    
    
    
}
