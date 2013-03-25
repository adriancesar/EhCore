package com.ehaqui.ehcore.util;

import org.bukkit.entity.Player;

import com.cypherx.xauth.PlayerManager;
import com.cypherx.xauth.xAuth;
import com.cypherx.xauth.xAuthPlayer;
import com.ehaqui.ehcore.Settings;


public class ManagerxAuth
{
    public static boolean checkIsLogin(Player player)
    {
        if(!Settings.usexAuth)
            return true;
        
        PlayerManager plm = xAuth.getPlugin().getPlayerManager();
        
        xAuthPlayer p = plm.getPlayer(player);
        
        if(p.getStatus() == xAuthPlayer.Status.Authenticated)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
