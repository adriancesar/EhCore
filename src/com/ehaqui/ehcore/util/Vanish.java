package com.ehaqui.ehcore.util;

import java.util.List;

import net.simpleplugins.simplevanish.SimpleVanish;

import org.bukkit.entity.Player;
import org.kitteh.vanish.staticaccess.VanishNoPacket;

import com.ehaqui.ehcore.Settings;

public class Vanish
{
    public static boolean isPlayerVanish(Player player) 
    {
        boolean hide = false;
        
        if(Settings.useVanishNoPacket)
        {
            try {
                hide = VanishNoPacket.isVanished(player.getName());
            } catch (Exception e) 
            {
                hide = false;
            }
        }
        else if (Settings.useSimpleVanish)
        {
            List<String> vanished = SimpleVanish.getVanishedPlayers();
            
            if(vanished.contains(player.getName()))
                hide = true;            
        }
        
        return hide;
    }
}
