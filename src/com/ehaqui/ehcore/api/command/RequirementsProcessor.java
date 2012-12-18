package com.ehaqui.ehcore.api.command;

import java.lang.annotation.Annotation;

import org.bukkit.command.CommandSender;

import com.ehaqui.ehcore.Settings;
import com.ehaqui.ehcore.api.command.exception.CommandException;
import com.ehaqui.ehcore.api.command.exception.RequirementMissingException;


public class RequirementsProcessor implements CommandAnnotationProcessor
{
    @Override
    public Class<? extends Annotation> getAnnotationClass()
    {
        return Requirements.class;
    }
    
    @Override
    public void process(CommandSender sender, CommandContext context, Annotation instance, Object[] methodArgs) throws CommandException
    {
        Requirements requirements = (Requirements) instance;
        // Requirements
        if(requirements.vip())
        {
            if(!hasPermission(sender, "vip"))
            {
                throw new RequirementMissingException("Você precisa ser VIP para usar este comando");
            }
        }
        
        if(requirements.admin())
        {
            if(!hasPermission(sender, "admin"))
            {
                throw new RequirementMissingException("Você precisa ser Admin para usar este comando");
            }
        }
        
    }
    
    // Returns whether a player has permission.
    private boolean hasPermission(CommandSender sender, String perm)
    {
        return sender.hasPermission(Settings.PREFIX_PERMISSION + "." + perm);
    }
    
}
