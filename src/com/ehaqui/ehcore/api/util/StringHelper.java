package com.ehaqui.ehcore.api.util;

import org.bukkit.ChatColor;

import com.ehaqui.ehcore.Settings.Setting;


public class StringHelper
{
    public static String capitalize(Object string)
    {
        String capitalize = string.toString();
        return capitalize.replaceFirst(String.valueOf(capitalize.charAt(0)), String.valueOf(Character.toUpperCase(capitalize.charAt(0))));
    }
    
    public static int getLevenshteinDistance(String s, String t)
    {
        if(s == null || t == null)
            throw new IllegalArgumentException("Strings must not be null");
        
        int n = s.length(); // length of s
        int m = t.length(); // length of t
        
        if(n == 0)
            return m;
        else if(m == 0)
            return n;
        
        int p[] = new int[n + 1]; // 'previous' cost array, horizontally
        int d[] = new int[n + 1]; // cost array, horizontally
        int _d[]; // placeholder to assist in swapping p and d
        
        // indexes into strings s and t
        int i; // iterates through s
        int j; // iterates through t
        
        char t_j; // jth character of t
        
        int cost; // cost
        
        for (i = 0; i <= n; i++)
            p[i] = i;
        
        for (j = 1; j <= m; j++)
        {
            t_j = t.charAt(j - 1);
            d[0] = j;
            
            for (i = 1; i <= n; i++)
            {
                cost = s.charAt(i - 1) == t_j ? 0 : 1;
                // minimum of cell to the left+1, to the top+1, diagonally left
                // and up +cost
                d[i] = Math.min(Math.min(d[i - 1] + 1, p[i] + 1), p[i - 1] + cost);
            }
            
            // copy current distance counts to 'previous row' distance counts
            _d = p;
            p = d;
            d = _d;
        }
        
        // our last action in the above loop was to switch d and p, so p now
        // actually has the most recent cost counts
        return p[n];
    }
    
    public static String parseColors(Object string)
    {
        String parsed = string.toString();
        for (ChatColor color : ChatColor.values())
        {
            parsed = parsed.replace("<" + color.getChar() + ">", color.toString());
        }
        parsed = ChatColor.translateAlternateColorCodes('&', parsed);
        return parsed;
    }
    
    public static String wrap(Object string)
    {
        return wrap(string, parseColors(Setting.MESSAGE_COLOUR.asString()));
    }
    
    public static String wrap(Object string, ChatColor colour)
    {
        return wrap(string, colour.toString());
    }
    
    public static String wrap(Object string, String colour)
    {
        return parseColors(Setting.HIGHLIGHT_COLOUR.asString()) + string.toString() + colour;
    }
    
    public static String wrapHeader(Object string)
    {
        String highlight = Setting.HIGHLIGHT_COLOUR.asString();
        return highlight + "=====[ " + string.toString() + highlight + " ]=====";
    }
    
    public static String firstUpCase(String string)
    {
        return Character.toUpperCase(string.charAt(0)) + string.substring(1);
    }
    
    public static String join(String[] string)
    {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < string.length; i++)
        {
            result.append(string[i]);
            result.append(" ");
        }
        return result.toString();
    }
    
    public static String greatestCommonPrefix(String a, String b)
    {
        int minLength = Math.min(a.length(), b.length());
        for (int i = 0; i < minLength; i++)
        {
            if(a.charAt(i) != b.charAt(i))
            {
                return a.substring(0, i);
            }
        }
        return a.substring(0, minLength);
    }
    
    
    public static String createBar(int percetage)
    {
        ChatColor wrapColour = ChatColor.WHITE;
        String bar = "";
        
        ChatColor colour;
        if(percetage >= 20)
        {
            colour = ChatColor.GREEN;
        }
        else if(percetage >= 18)
        {
            colour = ChatColor.GREEN;
        }
        else if(percetage >= 15)
        {
            colour = ChatColor.YELLOW;
        }
        else
        {
            colour = ChatColor.RED;
        }
        
        int looped = 0;
        while (looped++ < (percetage / 5))
        {
            bar += '#';
        }
        
        bar += ChatColor.WHITE;
        while (looped++ <= 20)
        {
            bar += ChatColor.GRAY + "_";
        }
        
        return wrapColour + "[" + colour + bar + wrapColour + "]";
    }
    
    public static String createBar(int percetage, boolean inverse)
    {
        ChatColor wrapColour = ChatColor.WHITE;
        String bar = "";
        
        ChatColor colour;
        
        if(!inverse)
        {
            if(percetage >= 75)
            {
                colour = ChatColor.GREEN;
            }
            else if(percetage >= 40)
            {
                colour = ChatColor.GREEN;
            }
            else if(percetage >= 25)
            {
                colour = ChatColor.YELLOW;
            }
            else
            {
                colour = ChatColor.RED;
            }
        }
        else
        {
            if(percetage >= 75)
            {
                colour = ChatColor.RED;
            }
            else if(percetage >= 40)
            {
                colour = ChatColor.YELLOW;
            }
            else if(percetage >= 25)
            {
                colour = ChatColor.GREEN;
            }
            else
            {
                colour = ChatColor.GREEN;
            }
        }
        
        int looped = 0;
        while (looped++ < (percetage / 5))
        {
            bar += '#';
        }
        
        bar += ChatColor.WHITE;
        while (looped++ <= 20)
        {
            bar += ChatColor.GRAY + "_";
        }
        
        return wrapColour + "[" + colour + bar + wrapColour + "]";
    }
}