package com.ehaqui.ehcore.api.message;

import org.bukkit.ChatColor;


public enum CustomColour
{
    
    RESET_COLOR('x'), RESET_FORMAT('x');
    
    private char ch;
    
    private CustomColour(char ch)
    {
        this.ch = ch;
    }
    
    @Override
    public String toString()
    {
        
        if(this == RESET_FORMAT)
            return ChatColor.RESET.toString() + RESET_COLOR.toString();
        
        return new String(new char[] { ChatColor.COLOR_CHAR, ch });
        
    }
    
    public static String process(String message)
    {
        
        ChatColor color = null;
        
        // Find default colour:
        char[] chmessage = message.toCharArray();
        for (int i = 1; i < chmessage.length; i++)
        {
            
            if(chmessage[i - 1] == ChatColor.COLOR_CHAR)
            {
                color = ChatColor.getByChar(chmessage[i]);
                
                if(color != null && color.isColor())
                    break;
                else
                    color = null;
                
            }
            
        }
        
        // Reset colours:
        if(color != null)
        {
            message = message.replace(RESET_COLOR.toString(), color.toString());
        }
        else
        {
            message = message.replace(RESET_COLOR.toString(), "");
        }
        
        return message;
        
    }
    
    
}