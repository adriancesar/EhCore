/**
 * 
 * This software is part of the MerchantAPI
 * 
 * @author Seppe Volkaerts (Cybermaxke)
 * 
 * This api allows plugin developers to create on a easy way new
 * merchants they can open without villagers and adding as many 
 * offers they want.
 * 
 * MerchantAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or 
 * any later version.
 *  
 * MerchantAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MerchantAPI. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package com.ehaqui.ehcore.api.trade;

import net.minecraft.server.v1_5_R3.EntityHuman;
import net.minecraft.server.v1_5_R3.EntityPlayer;
import net.minecraft.server.v1_5_R3.IMerchant;
import net.minecraft.server.v1_5_R3.MerchantRecipe;
import net.minecraft.server.v1_5_R3.MerchantRecipeList;


public class NMSMerchant implements IMerchant
{
    private MerchantRecipeList o = new MerchantRecipeList();
    private EntityHuman        c;
    
    /**
     * Protected. (Only used by the api.)
     */
    protected NMSMerchant()
    {
        
    }
    
    /**
     * Adds a offer.
     * 
     * @param recipe
     *            The offer.
     */
    @SuppressWarnings("unchecked")
    @Override
    public void a(MerchantRecipe recipe)
    {
        this.o.add(recipe);
    }
    
    /**
     * Set the customer.
     * 
     * @param player
     *            The customer.
     */
    @Override
    public void a(EntityHuman player)
    {
        this.c = player;
    }
    
    /**
     * Returns all the offers.
     * 
     * @return The offers.
     */
    @Override
    public MerchantRecipeList getOffers(EntityHuman player)
    {
        return this.o;
    }
    
    /**
     * Returns the customer.
     * 
     * @return The customer.
     */
    @Override
    public EntityHuman m_()
    {
        return this.c;
    }
    
    /**
     * Sets the recipes with clearing the old ones.
     * 
     * @param recipes
     *            The offers list.
     */
    public void setRecipes(MerchantRecipeList recipes)
    {
        this.o = recipes;
    }
    
    /**
     * Open the player gui.
     * 
     * @param player
     *            The player which should open it.
     */
    public void openTrading(EntityPlayer player)
    {
        this.c = player;
        this.c.openTrade(this, "Trade");
    }
}