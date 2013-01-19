package com.ehaqui.ehcore.api.trade;

import java.io.IOException;

import net.minecraft.server.v1_4_R1.MerchantRecipe;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ehaqui.ehcore.api.trade.exception.NullTradeOfferException;


/**
 * 
 * This software is part of VillagerTradingLib
 * 
 * @author Justin Michaud (jtjj222)
 */

public class VillagerTradeOffer
{
    
    /**
     * The ItemStack being offered
     */
    private ItemStack Offer = null;
    
    /**
     * The first ItemStack that the villager wants
     * (the leftmost one)
     */
    private ItemStack Cost1 = null;
    
    /**
     * The second ItemStack that the villager wants
     * (the rightmost one)
     */
    private ItemStack Cost2 = null;
    
    
    /**
     * 
     * @param cost
     *            - The item that the villager wants
     * @param offer
     *            - The item the villager is offering
     * 
     *            Note: looses enchantments and metadata
     */
    public VillagerTradeOffer(ItemStack cost, ItemStack offer)
    {
        this.Offer = offer;
        this.Cost1 = cost;
    }
    
    /**
     * 
     * @param cost1
     *            - The first item the villager wants
     * @param cost2
     *            - The second item the villager wants
     * @param offer
     *            - The item the villager is offering
     * 
     *            Note: looses enchantments and metadata
     */
    public VillagerTradeOffer(ItemStack cost1, ItemStack cost2, ItemStack offer)
    {
        this.Offer = offer;
        this.Cost1 = cost1;
        this.Cost2 = cost2;
    }
    
    /**
     * 
     * @return - A MerchantRecipie that can be used by minecraft's internal classes
     * @throws NullTradeOfferException
     *             - Thrown when the offer isn't valid; ie: one of the ItemStack's are null
     */
    public MerchantRecipe getMerchantRecipie() throws NullTradeOfferException
    {
        
        if(Cost1 == null || Offer == null)
            throw new NullTradeOfferException();
        
        if(Cost2 == null)
            return new MerchantRecipe(getNMSCost1(), getNMSOffer());
        else
            return new MerchantRecipe(getNMSCost1(), getNMSCost2(), getNMSOffer());
    }
    
    /**
     * 
     * @return - The first items the villager wants, in
     *         a netMinecraftServer itemstack
     */
    public net.minecraft.server.v1_4_R1.ItemStack getNMSCost1()
    {
        return convertItemStackToNMS(this.Cost1);
    }
    
    /**
     * 
     * @return - The second item the villager wants, in
     *         a netMinecraftServer itemstack
     */
    public net.minecraft.server.v1_4_R1.ItemStack getNMSCost2()
    {
        return convertItemStackToNMS(this.Cost2);
    }
    
    /**
     * 
     * @return - The item the villager is offering, in
     *         a netMinecraftServer itemstack
     */
    public net.minecraft.server.v1_4_R1.ItemStack getNMSOffer()
    {
        return convertItemStackToNMS(this.Offer);
    }
    
    /**
     * 
     * @param i
     *            - The bukkit itemstack to convert
     * @return - A netMinecraftServer itemstack
     * 
     *         Note: looses enchantments and metadata
     */
    private net.minecraft.server.v1_4_R1.ItemStack convertItemStackToNMS(ItemStack i)
    {
        int amount = i.getAmount();
        int id = i.getType().getId();
        short durability = i.getDurability();
        
        return new net.minecraft.server.v1_4_R1.ItemStack(id, amount, durability);
    }
    
    public void usage(Player player) throws NullTradeOfferException, IOException
    {
        VillagerTradingLib instance = new VillagerTradingLib();
        
        VillagerTradeOffer[] offers = {
                new VillagerTradeOffer(
                        new ItemStack(Material.EMERALD,1),
                        new ItemStack(Material.MUSHROOM_SOUP,1)
                )
        };
        
        instance.OpenTrade(player, offers);
    }
}
