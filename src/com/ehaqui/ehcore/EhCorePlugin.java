package com.ehaqui.ehcore;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.ehaqui.ehcore.api.command.CommandManager;
import com.ehaqui.ehcore.api.command.Injector;
import com.ehaqui.ehcore.api.command.RequirementsProcessor;
import com.ehaqui.ehcore.api.util.Messages;
import com.ehaqui.ehcore.api.util.StringHelper;
import com.ehaqui.ehcore.command.CommandCore;
import com.ehaqui.ehcore.tps.TPS;
import com.ehaqui.ehcore.util.EhUtil;
import com.ehaqui.ehcore.util.LogManager;


public class EhCorePlugin extends JavaPlugin
{
    private final CommandManager commands = new CommandManager();
    
    public static LogManager     log;
    public static EhUtil         util;
    
    @Override
    public void onEnable()
    {
        new LogManager("[" + getName() + "]", true);
        
        setup();
        setupDependences();
        
        
        registerCommands();
    }
    
    @Override
    public void onDisable()
    {
        
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmdName, String[] args)
    {
        if(!commands.hasCommand(command, args))
        {
            return suggestClosestModifier(sender, command.getName().toLowerCase(), args);
        }
        
        Object[] methodArgs = { sender };
        
        return commands.executeSafe(command, args, sender, methodArgs);
    }
    
    private void registerCommands()
    {
        commands.setInjector(new Injector(this));
        commands.registerAnnotationProcessor(new RequirementsProcessor());
        
        // Register command classes
        commands.register(CommandCore.class);
        
    }
    
    private boolean suggestClosestModifier(CommandSender sender, String command, String[] args)
    {
        String closest = commands.getClosestCommandCommand(command, args);
        if(!closest.isEmpty())
        {
            sender.sendMessage(ChatColor.GRAY + Messages.UNKNOWN_COMMAND);
            sender.sendMessage(StringHelper.wrap(" /") + command + " " + StringHelper.wrap(closest.substring(2)));
            return true;
        }
        return false;
    }
    
    private void setup()
    {
        new Settings(getDataFolder());
        
        log = new LogManager(Settings.pluginName, Settings.debug);
        util = new EhUtil(this, Settings.pluginPrefix, Settings.prefixPermission);
        
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new TPS(), 0L, 40L);
    }
    
    private void setupDependences()
    {
        if(getServer().getPluginManager().getPlugin("VanishNoPacket") != null)
        {
            Settings.useVanishNoPacket = true;
            LogManager.info("VanishNoPacket not found. Disable Features!");
        }
        else
        {
            LogManager.info("VanishNoPacket found. Enable Features!");
        }
        
        if(getServer().getPluginManager().getPlugin("SimpleVanish") != null)
        {
            Settings.useSimpleVanish = true;
            LogManager.info("SimpleVanish not found. Disable Features!");
        }
        else
        {
            LogManager.info("SimpleVanish found. Enable Features!");
        }
        
        if(getServer().getPluginManager().getPlugin("xAuth") != null)
        {
            Settings.usexAuth = true;
            LogManager.info("xAuth not found. Disable Features!");
        }
        else
        {
            LogManager.info("xAuth found. Enable Features!");
        }
    }
    
    public static LogManager getlog()
    {
        return log;
    }
    
    public static EhUtil getUtil()
    {
        return util;
    }
    
    
    
}
