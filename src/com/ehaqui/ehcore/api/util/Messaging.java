package com.ehaqui.ehcore.api.util;

import java.util.logging.Level;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.ehaqui.ehcore.Settings.Setting;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;


public class Messaging
{
    private static final Pattern  CHAT_NEWLINE          = Pattern.compile("<br>|<n>|\\n", Pattern.MULTILINE);
    private static final Splitter CHAT_NEWLINE_SPLITTER = Splitter.on(CHAT_NEWLINE);
    private static final Joiner   SPACE                 = Joiner.on(" ").useForNull("null");
    
    public static void debug(Object... msg)
    {
        if(Setting.DEBUG_MODE.asBoolean()) log(msg);
    }
    
    public static void log(Level level, Object... msg)
    {
        Bukkit.getLogger().log(level, "[Citizens] " + SPACE.join(msg));
    }
    
    public static void log(Object... msg)
    {
        log(Level.INFO, msg);
    }
    
    public static void send(CommandSender sender, Object... msg)
    {
        sendMessageTo(sender, SPACE.join(msg));
    }
    
    public static void sendError(CommandSender sender, Object... msg)
    {
        send(sender, ChatColor.RED.toString() + SPACE.join(msg));
    }
    
    private static void sendMessageTo(CommandSender sender, String rawMessage)
    {
        rawMessage = StringHelper.parseColors(rawMessage);
        for (String message : CHAT_NEWLINE_SPLITTER.split(rawMessage))
        {
            String trimmed = message.trim();
            String messageColour = StringHelper.parseColors(Setting.MESSAGE_COLOUR.asString());
            if(!trimmed.isEmpty())
            {
                if(trimmed.charAt(0) == ChatColor.COLOR_CHAR)
                {
                    ChatColor test = ChatColor.getByChar(trimmed.substring(1, 2));
                    if(test == null)
                    {
                        message = messageColour + message;
                    }
                    else
                        messageColour = test.toString();
                }
                else
                    message = messageColour + message;
            }
            message = message.replace("[[", StringHelper.parseColors(Setting.HIGHLIGHT_COLOUR.asString()));
            message = message.replace("]]", messageColour);
            sender.sendMessage(message);
        }
    }
    
    public static void severe(Object... messages)
    {
        log(Level.SEVERE, messages);
    }
    
    public static String tryTranslate(Object possible)
    {
        if(possible == null) return "";
        String message = possible.toString();
        
        return message;
    }
}