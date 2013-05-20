package com.ehaqui.ehcore.api.util.storage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import net.minecraft.server.v1_5_R3.NBTBase;
import net.minecraft.server.v1_5_R3.NBTTagCompound;
import net.minecraft.server.v1_5_R3.NBTTagList;

import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftInventoryCustom;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftItemStack;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;


public class ItemSerialization
{
    public static String toBase64(Inventory inventory)
    {
        if (inventory == null)
            return "";
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutput = new DataOutputStream(outputStream);
        NBTTagList itemList = new NBTTagList();
        
        for (int i = 0; i < inventory.getSize(); i++)
        {
            NBTTagCompound outputObject = new NBTTagCompound();
            net.minecraft.server.v1_5_R3.ItemStack craft = getCraftVersion(inventory.getItem(i));
            
            if (craft != null)
                craft.save(outputObject);
            itemList.add(outputObject);
        }
        
        NBTBase.a(itemList, dataOutput);
        
        return Base64Coder.encodeLines(outputStream.toByteArray());
    }
    
    public static Inventory fromBase64(String data)
    {
        if (data.equalsIgnoreCase("") || data.isEmpty())
            return null;
        
        ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
        NBTTagList itemList = (NBTTagList) NBTBase.b(new DataInputStream(inputStream));
        Inventory inventory = new CraftInventoryCustom(null, itemList.size());
        
        for (int i = 0; i < itemList.size(); i++)
        {
            NBTTagCompound inputObject = (NBTTagCompound) itemList.get(i);
            
            if (!inputObject.isEmpty())
            {
                inventory.setItem(i, CraftItemStack.asBukkitCopy(net.minecraft.server.v1_5_R3.ItemStack.createStack(inputObject)));
            }
        }
        
        return inventory;
    }
    
    private static net.minecraft.server.v1_5_R3.ItemStack getCraftVersion(ItemStack stack)
    {
        if (stack != null)
            return CraftItemStack.asNMSCopy(stack);
        
        return null;
    }
}