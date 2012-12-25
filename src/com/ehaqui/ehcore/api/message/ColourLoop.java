package com.ehaqui.ehcore.api.message;

import java.util.ArrayList;

import org.bukkit.ChatColor;


/**
 * Contains different colours.
 * 
 * @author andf
 * 
 */
public class ColourLoop
{
    // Colours:
    public static ChatColor veryPositive = ChatColor.DARK_GREEN;      // DO NOT OVERUSE.
                                                                       
    public static ChatColor positive     = ChatColor.GREEN;
    
    public static ChatColor negative     = ChatColor.RED;
    
    public static ChatColor veryNegative = ChatColor.DARK_RED;        // DO NOT OVERUSE.
                                                                       
    public static ChatColor unavailable  = ChatColor.DARK_GRAY;
    
    public static ChatColor announce     = ChatColor.AQUA;
    
    public static ChatColor normal1      = ChatColor.GOLD;
    
    public static ChatColor normal2      = ChatColor.YELLOW;
    
    public static ChatColor frame        = normal1;
    
    public static ChatColor frameTitle   = normal2;
    
    /**
     * All colours.
     */
    ArrayList<ChatColor>    colours      = new ArrayList<ChatColor>();
    
    /**
     * Current index.
     */
    private int             index        = 0;
    
    
    
    /**
     * Initialises the colour loop.
     * 
     */
    public ColourLoop()
    {
    }
    
    
    
    /**
     * Adds a colour to the circle.
     * 
     * @param colour
     *            colour
     * @return instance
     */
    public ColourLoop addColor(ChatColor colour)
    {
        colours.add(colour);
        return this;
    }
    
    /**
     * Gets the next colour in the loop.
     * 
     * @return next colour, {@link PlayerMessages#normal1} if none
     */
    public ChatColor nextColour()
    {
        
        
        if(colours.size() == 0)
            return normal1;
        
        index++;
        
        if(index >= colours.size())
        {
            index = 0;
        }
        
        return colours.get(index);
        
        
    }
    
    /**
     * Gets the first colour in the loop.
     * 
     * @return first colour, {@link PlayerMessages#normal1} if none
     */
    public ChatColor firstColour()
    {
        
        
        if(colours.size() == 0)
            return normal1;
        
        return colours.get(0);
        
        
    }
    
    /**
     * Resets the loop.
     * 
     */
    public void reset()
    {
        
        index = 0;
        
    }
    
    
}
