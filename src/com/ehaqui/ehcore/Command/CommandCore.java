package com.ehaqui.ehcore.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.ehaqui.ehcore.EhCorePlugin;

public class CommandCore implements CommandExecutor
{

    public CommandCore(EhCorePlugin ehCorePlugin)
    {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {                
        return false;
    }

}
