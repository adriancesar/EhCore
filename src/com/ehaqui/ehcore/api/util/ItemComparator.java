package com.ehaqui.ehcore.api.util;

import java.util.Comparator;
import org.bukkit.inventory.ItemStack;


class ItemComparator implements Comparator<ItemStack>
{
    public int compare(ItemStack item1, ItemStack item2)
    {
        if((item1 == null) && (item2 != null))
            return 1;
        if((item1 != null) && (item2 == null))
            return -1;
        if((item1 == null) && (item2 == null))
            return 0;
        if(item1.getTypeId() > item2.getTypeId())
            return 1;
        if(item1.getTypeId() < item2.getTypeId())
            return -1;
        if(item1.getTypeId() == item2.getTypeId())
        {
            if(item1.getDurability() > item2.getDurability())
                return 1;
            if(item1.getDurability() < item2.getDurability())
                return -1;
            if(item1.getDurability() == item2.getDurability())
            {
                return 0;
            }
            if(item1.getAmount() > item2.getAmount())
                return 1;
            if(item1.getAmount() < item2.getAmount())
            {
                return -1;
            }
        }
        return 0;
    }
}