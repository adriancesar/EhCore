package com.ehaqui.ehcore.api.menu;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;



public class IconMenu implements Listener
{
    
    private String                  name;
    private int                     size;
    private OptionClickEventHandler handler;
    private Plugin                  plugin;
    
    private String[]                optionNames;
    private ItemStack[]             optionIcons;
    
    public IconMenu(Plugin plugin)
    {
        this.plugin = plugin;
    }
    
    public IconMenu(String name, int size, OptionClickEventHandler handler, Plugin plugin)
    {
        this.name = name;
        this.size = size;
        this.handler = handler;
        this.plugin = plugin;
        this.optionNames = new String[size];
        this.optionIcons = new ItemStack[size];
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    
    public IconMenu setOption(int position, ItemStack icon, String name, String... info)
    {
        optionNames[position] = name;
        optionIcons[position] = setItemNameAndLore(icon, name, Arrays.asList(info));
        return this;
    }
    
    public void open(Player player)
    {
        Inventory inventory = Bukkit.createInventory(player, size, name);
        for (int i = 0; i < optionIcons.length; i++)
        {
            if(optionIcons[i] != null)
            {
                inventory.setItem(i, optionIcons[i]);
            }
        }
        player.openInventory(inventory);
    }
    
    public void destroy()
    {
        HandlerList.unregisterAll(this);
        handler = null;
        plugin = null;
        optionNames = null;
        optionIcons = null;
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    void onInventoryClick(InventoryClickEvent event)
    {
        if(event.getInventory().getTitle().equals(name))
        {
            event.setCancelled(true);
            int slot = event.getRawSlot();
            if(slot >= 0 && slot < size && optionNames[slot] != null)
            {
                Plugin plugin = this.plugin;
                OptionClickEvent e = new OptionClickEvent((Player) event.getWhoClicked(), slot, optionNames[slot]);
                handler.onOptionClick(e);
                if(e.willClose())
                {
                    final Player p = (Player) event.getWhoClicked();
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        public void run()
                        {
                            p.closeInventory();
                        }
                    }, 1);
                }
                if(e.willDestroy())
                {
                    destroy();
                }
            }
        }
    }
    
    public interface OptionClickEventHandler
    {
        public void onOptionClick(OptionClickEvent event);
    }
    
    public class OptionClickEvent
    {
        private Player  player;
        private int     position;
        private String  name;
        private boolean close;
        private boolean destroy;
        
        public OptionClickEvent(Player player, int position, String name)
        {
            this.player = player;
            this.position = position;
            this.name = name;
            this.close = true;
            this.destroy = false;
        }
        
        public Player getPlayer()
        {
            return player;
        }
        
        public int getPosition()
        {
            return position;
        }
        
        public String getName()
        {
            return name;
        }
        
        public boolean willClose()
        {
            return close;
        }
        
        public boolean willDestroy()
        {
            return destroy;
        }
        
        public void setWillClose(boolean close)
        {
            this.close = close;
        }
        
        public void setWillDestroy(boolean destroy)
        {
            this.destroy = destroy;
        }
    }
    
    public static ItemStack setItemNameAndLore(ItemStack item, String name, List<String> info)
    {
        ItemMeta im = item.getItemMeta();
        
        im.setDisplayName(name);
        im.setLore(info);
       
        item.setItemMeta(im);

        return item;
    }
    
    
    public static ItemStack setItemNameAndLore(ItemStack item, String name, String[] info)
    {
        ItemMeta im = item.getItemMeta();
        
        im.setDisplayName(name);
        im.setLore(Arrays.asList(info));
       
        item.setItemMeta(im);

        return item;
    }
    
    public void usage(Player player)
    {
        IconMenu menu = new IconMenu("My Fancy Menu", 9, new IconMenu.OptionClickEventHandler() {
            @Override
            public void onOptionClick(IconMenu.OptionClickEvent event) {
                event.getPlayer().sendMessage("You have chosen " + event.getName());
                event.setWillClose(true);
            }
        }, plugin)
        .setOption(3, new ItemStack(Material.APPLE, 1),      "Food",   "The food is delicious")
        .setOption(4, new ItemStack(Material.IRON_SWORD, 1), "Weapon", "Weapons are for awesome people")
        .setOption(5, new ItemStack(Material.EMERALD, 1),    "Money",  "Money brings happiness");

        menu.open(player);
    }
    
}