package com.ehaqui.ehcore.Util;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class LogManager
{

    public static Logger   log        = Logger.getLogger("Minecraft");

    private static String  pluginName = "Plugin";
    private static boolean debug      = false;

    public LogManager(String pl_name, boolean pl_debug)
    {
        pluginName = pl_name;
        debug = pl_debug;
    }

    /**
     * Log Info
     * 
     * @param text
     */
    public static void info(String text)
    {
        log.info(pluginName + " " + text);
    }

    public void info(String text, Object... txt)
    {
        log.info(pluginName + " " + String.format(text, txt));
    }

    /**
     * Log Aviso
     * 
     * @param text
     */
    public static void aviso(String text)
    {
        log.warning(pluginName + " " + text);
    }

    public void aviso(String text, Object... txt)
    {
        log.warning(pluginName + " " + String.format(text, txt));
    }

    /**
     * Log Severe
     * 
     * @param text
     */
    public static void severe(String text)
    {
        log.severe(pluginName + " " + text);
    }

    public void severe(String text, Object... txt)
    {
        log.severe(pluginName + " " + String.format(text, txt));
    }

    /**
     * Log Debug
     * 
     * @param text
     */
    public static void debug(String text)
    {
        if(debug)
        {
            log.info("[DEBUG]" + pluginName + " " + text);
        }
    }

    public static void log(String text)
    {
        log.info(pluginName + " " + text);
    }

    public static void sendError(Player player, String error) {
        player.sendMessage(ChatColor.RED + error);
    }

    public static void sendError(CommandSender sender, String error)
    {
        sender.sendMessage(ChatColor.RED + error);
    }
}
