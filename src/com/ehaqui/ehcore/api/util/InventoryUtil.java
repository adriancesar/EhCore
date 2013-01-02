package com.ehaqui.ehcore.api.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;


public class InventoryUtil
{
    
    
    /**
     * Gets item count.
     * 
     * @param material
     *            item material
     * @param items
     *            inventory contents
     * @return item count
     */
    public static Integer getItemCount(Material material, ItemStack[] items)
    {
        
        int count = 0;
        for (int i = 0; i < items.length; i++)
        {
            if(items[i] != null && items[i].getType().equals(material) && items[i].getDurability() == 0 && items[i].getEnchantments().size() == 0)
            {
                count += items[i].getAmount();
            }
        }
        return count;
        
    }
    
    /**
     * Adds an item to an inventory, drops it if there is no room.
     * 
     * @param itemStack
     *            item stack
     * @param inventory
     *            inventory
     * @param dropLocation
     *            drop location
     * @return true if the item was dropped
     */
    public static boolean addItem(ItemStack itemStack, Inventory inventory, Location dropLocation)
    {
        
        
        ItemStack remaining = inventory.addItem(itemStack).get(0);
        
        if(remaining != null)
        {
            dropLocation.getWorld().dropItemNaturally(dropLocation, remaining);
            return true;
        }
        return false;
        
        
    }
    
    /**
     * Removes an item from an inventory.
     * 
     * @param itemStack
     *            item stack
     * @param inventory
     *            inventory
     */
    public static void removeItem(ItemStack itemStack, Inventory inventory)
    {
        
        ItemStack remaining = inventory.removeItem(itemStack).get(0);
        
        if(remaining != null)
        {
            //SagaLogger.warning(InventoryUtil.class, "failed to remove " + itemStack + " from the inventory");
        }
        
    }
    
    /**
     * Removes an item.
     * 
     * @param material
     *            material
     * @param toRemove
     *            amount to remove
     * @param removeEnch
     *            if true, then enchanted items will also be removed
     * @param player
     *            player
     */
    public static void removeItem(Material material, Integer toRemove, boolean removeEnch, Player player)
    {
        
        
        Inventory inventory = player.getInventory();
        Integer toDelete = toRemove;
        
        HashMap<Integer, ? extends ItemStack> all = inventory.all(material);
        Set<Integer> slots = all.keySet();
        
        for (Integer first : slots)
        {
            
            ItemStack itemStack = inventory.getItem(first);
            
            // Ignore enchanted:
            if(!removeEnch && itemStack.getEnchantments().size() > 0)
                continue;
            
            int amount = itemStack.getAmount();
            
            if(amount <= toDelete)
            {
                toDelete -= amount;
                // clear the slot, all used up
                inventory.clear(first);
            }
            else
            {
                // split the stack and store
                itemStack.setAmount(amount - toDelete);
                inventory.setItem(first, itemStack);
                toDelete = 0;
            }
            
            // Bail when done
            if(toDelete <= 0)
            {
                break;
            }
            
        }
        
        if(toDelete > 0)
        {
            //SagaLogger.severe("Failed to remove " + toDelete + " " + material + " from players " + player.getName() + " inventory");
        }
        
        
    }
    
    /**
     * 
     * @param items
     * @param first
     * @param last
     * @return
     */
    public static ItemStack[] stackItems(ItemStack[] items, int first, int last)
    {
        for (int i = first; i < last; i++)
        {
            ItemStack item1 = items[i];
            if((item1 != null) && (item1.getAmount() > 0) && (item1.getMaxStackSize() != 1))
            {
                if(item1.getAmount() < item1.getMaxStackSize())
                {
                    int needed = item1.getMaxStackSize() - item1.getAmount();
                    for (int j = i + 1; j < last; j++)
                    {
                        ItemStack item2 = items[j];
                        if((item2 != null) && (item2.getAmount() > 0) && (item1.getMaxStackSize() != 1))
                        {
                            if((item2.getTypeId() == item1.getTypeId()) && (item1.getDurability() == item2.getDurability()) && (item1.getEnchantments().equals(item2.getEnchantments())))
                            {
                                if(item2.getAmount() > needed)
                                {
                                    item1.setAmount(item1.getMaxStackSize());
                                    item2.setAmount(item2.getAmount() - needed);
                                    break;
                                }
                                items[j] = null;
                                item1.setAmount(item1.getAmount() + item2.getAmount());
                                needed = item1.getMaxStackSize() - item1.getAmount();
                            }
                        }
                    }
                }
            }
        }
        return items;
    }
    
    /**
     * 
     * @param items
     * @param first
     * @param last
     * @return
     */
    public static ItemStack[] sortItems(ItemStack[] items, int first, int last)
    {
        items = stackItems(items, first, last);
        Arrays.sort(items, first, last, new ItemComparator());
        return items;
    }
    
    /**
     * 
     * @param item
     *            item to uncraft
     * @return list of items
     */
    public static List<ItemStack> uncraft(ItemStack item)
    {
        List<ItemStack> returns = new ArrayList<ItemStack>();
        List<Recipe> recipes = Bukkit.getRecipesFor(item);
        
        if(recipes.size() <= 0)
            return returns;
        
        Recipe recipe = null;
        
        for (Recipe r : recipes)
        {
            if(r.getResult().isSimilar(item))
            {
                if(r.getResult().getAmount() != item.getAmount())
                    continue;
                
                recipe = r;
                break;
            }
        }
        
        if(recipe == null)
            return returns;
        
        if(recipe instanceof ShapedRecipe)
        {
            ShapedRecipe sr = (ShapedRecipe) recipe;
            Map<Character, ItemStack> chart = sr.getIngredientMap();
                        
            for (ItemStack is : chart.values())
            {
                if(is != null)
                    returns.add(is);
            }
        }
        else if(recipe instanceof ShapelessRecipe)
        {
            ShapelessRecipe sr = (ShapelessRecipe) recipe;
            List<ItemStack> x = sr.getIngredientList();
            
            for (ItemStack is : x)
            {
                if(is != null)
                    returns.add(is);
            }
        }
        
        return returns;
    }
    
}
