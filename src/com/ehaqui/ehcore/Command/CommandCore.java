package com.ehaqui.ehcore.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ehaqui.ehcore.EhCorePlugin;
import com.ehaqui.ehcore.Util.Vanish;

public class CommandCore implements CommandExecutor
{

    public CommandCore(EhCorePlugin ehCorePlugin)
    {
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {

        Vanish.isPlayerVanish((Player) sender);
        
        return false;
    }

}
