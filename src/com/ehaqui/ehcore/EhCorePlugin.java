package com.ehaqui.ehcore;

import org.bukkit.plugin.java.JavaPlugin;

import com.ehaqui.ehcore.Command.CommandCore;
import com.ehaqui.ehcore.Util.EhUtil;
import com.ehaqui.ehcore.Util.LogManager;
import com.ehaqui.ehcore.tps.TPS;


public class EhCorePlugin extends JavaPlugin
{
    public static LogManager log;
    public static EhUtil     util;

    @Override
    public void onEnable()
    {
        new LogManager ("[" + getName() + "]", true);
        
        setup();

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

        TPS.setup(this);
        
        getCommand("ehcore").setExecutor(new CommandCore(this));
    }

    @Override
    public void onDisable()
    {

    }

    private void setup()
    {
        new Settings(getDataFolder());
        
        log = new LogManager(Settings.pluginName, Settings.debug);
        util = new EhUtil(this, Settings.pluginPrefix, Settings.prefixPermission);
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
