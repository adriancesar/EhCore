package com.ehaqui.ehcore.Player;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import net.minecraft.server.v1_4_5.EntityPlayer;

import org.bukkit.Achievement;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.InventoryView.Property;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.map.MapView;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class EhPlayer implements Player
{
    private Player p;
    
    public EhPlayer(Player player)
    {
        p = player;
    }
    
    public EntityPlayer getHandle() 
    {
        return (EntityPlayer) p;
    }
    
    @Override
    public String getName()
    {
        return p.getName();
    }

    @Override
    public PlayerInventory getInventory()
    {
        return p.getInventory();
    }

    @Override
    public Inventory getEnderChest()
    {
        return p.getInventory();
    }

    @Override
    public boolean setWindowProperty(Property prop, int value)
    {
        return false;
    }

    @Override
    public InventoryView getOpenInventory()
    {
        return p.getOpenInventory();
    }

    @Override
    public InventoryView openInventory(Inventory inventory)
    {
        return p.openInventory(inventory);
    }

    @Override
    public InventoryView openWorkbench(Location location, boolean force)
    {
        return p.openWorkbench(location, force);
    }

    @Override
    public InventoryView openEnchanting(Location location, boolean force)
    {
        return p.openEnchanting(location, force);
    }

    @Override
    public void openInventory(InventoryView inventory)
    {
        p.openInventory(inventory);
    }

    @Override
    public void closeInventory()
    {
        p.closeInventory();
    }

    @Override
    public ItemStack getItemInHand()
    {
        return p.getItemInHand();
    }

    @Override
    public void setItemInHand(ItemStack item)
    {
        p.setItemInHand(item);
    }

    @Override
    public ItemStack getItemOnCursor()
    {
        return p.getItemOnCursor();
    }

    @Override
    public void setItemOnCursor(ItemStack item)
    {
        // TODO Auto-generated method stub
        p.setItemOnCursor(item);
    }

    @Override
    public boolean isSleeping()
    {
        return p.isSleeping();
    }

    @Override
    public int getSleepTicks()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public GameMode getGameMode()
    {
        return p.getGameMode();
    }

    @Override
    public void setGameMode(GameMode mode)
    {
        p.setGameMode(mode);
    }

    @Override
    public boolean isBlocking()
    {
        return p.isBlocking();
    }

    @Override
    public int getExpToLevel()
    {
        return p.getExpToLevel();
    }

    @Override
    public int getHealth()
    {
        return p.getHealth();
    }

    @Override
    public void setHealth(int health)
    {
        p.setHealth(health);
    }

    @Override
    public int getMaxHealth()
    {
        return p.getMaxHealth();
    }

    @Override
    public double getEyeHeight()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getEyeHeight(boolean ignoreSneaking)
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Location getEyeLocation()
    {
        return p.getEyeLocation();
    }

    @Override
    public List<Block> getLineOfSight(HashSet<Byte> transparent, int maxDistance)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Block getTargetBlock(HashSet<Byte> transparent, int maxDistance)
    {
        return p.getTargetBlock(transparent, maxDistance);
    }

    @Override
    public List<Block> getLastTwoTargetBlocks(HashSet<Byte> transparent, int maxDistance)
    {
        return p.getLastTwoTargetBlocks(transparent, maxDistance);
    }

    @Override
    public Egg throwEgg()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Snowball throwSnowball()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Arrow shootArrow()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T extends Projectile> T launchProjectile(Class<? extends T> projectile)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getRemainingAir()
    {
        return p.getRemainingAir();
    }

    @Override
    public void setRemainingAir(int ticks)
    {
        p.setRemainingAir(ticks);
    }

    @Override
    public int getMaximumAir()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setMaximumAir(int ticks)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void damage(int amount)
    {
        p.damage(amount);
    }

    @Override
    public void damage(int amount, Entity source)
    {
        p.damage(amount, source);
    }

    @Override
    public int getMaximumNoDamageTicks()
    {
        return p.getMaximumNoDamageTicks();
    }

    @Override
    public void setMaximumNoDamageTicks(int ticks)
    {
        p.setMaximumNoDamageTicks(ticks);
    }

    @Override
    public int getLastDamage()
    {
        return p.getLastDamage();
    }

    @Override
    public void setLastDamage(int damage)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int getNoDamageTicks()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setNoDamageTicks(int ticks)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Player getKiller()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean addPotionEffect(PotionEffect effect)
    {
        return p.addPotionEffect(effect);
    }

    @Override
    public boolean addPotionEffect(PotionEffect effect, boolean force)
    {
        return p.addPotionEffect(effect, force);
    }

    @Override
    public boolean addPotionEffects(Collection<PotionEffect> effects)
    {
        return p.addPotionEffects(effects);
    }

    @Override
    public boolean hasPotionEffect(PotionEffectType type)
    {
        return p.hasPotionEffect(type);
    }

    @Override
    public void removePotionEffect(PotionEffectType type)
    {
        p.removePotionEffect(type);
    }

    @Override
    public Collection<PotionEffect> getActivePotionEffects()
    {
        return p.getActivePotionEffects();
    }

    @Override
    public boolean hasLineOfSight(Entity other)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Location getLocation()
    {
        return p.getLocation();
    }

    @Override
    public void setVelocity(Vector velocity)
    {
        p.setVelocity(velocity);
    }

    @Override
    public Vector getVelocity()
    {
        return p.getVelocity();
    }

    @Override
    public World getWorld()
    {
        return p.getWorld();
    }

    @Override
    public boolean teleport(Location location)
    {
        return p.teleport(location);
    }

    @Override
    public boolean teleport(Location location, TeleportCause cause)
    {
        return p.teleport(location, cause);
    }

    @Override
    public boolean teleport(Entity destination)
    {
        return p.teleport(destination);
    }

    @Override
    public boolean teleport(Entity destination, TeleportCause cause)
    {
        return p.teleport(destination, cause);
    }

    @Override
    public List<Entity> getNearbyEntities(double x, double y, double z)
    {
        return p.getNearbyEntities(x, y, z);
    }

    @Override
    public int getEntityId()
    {
        return p.getEntityId();
    }

    @Override
    public int getFireTicks()
    {
        return p.getFireTicks();
    }

    @Override
    public int getMaxFireTicks()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setFireTicks(int ticks)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void remove()
    {
        p.remove();
    }

    @Override
    public boolean isDead()
    {
        return p.isDead();
    }

    @Override
    public boolean isValid()
    {
        return p.isValid();
    }

    @Override
    public Server getServer()
    {
        return p.getServer();
    }

    @Override
    public Entity getPassenger()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean setPassenger(Entity passenger)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isEmpty()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean eject()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public float getFallDistance()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setFallDistance(float distance)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setLastDamageCause(EntityDamageEvent event)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public EntityDamageEvent getLastDamageCause()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UUID getUniqueId()
    {
        return p.getUniqueId();
    }

    @Override
    public int getTicksLived()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setTicksLived(int value)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void playEffect(EntityEffect type)
    {
        p.playEffect(type);
    }

    @Override
    public EntityType getType()
    {
        return p.getType();
    }

    @Override
    public boolean isInsideVehicle()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean leaveVehicle()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Entity getVehicle()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setMetadata(String metadataKey, MetadataValue newMetadataValue)
    {
        p.setMetadata(metadataKey, newMetadataValue);
    }

    @Override
    public List<MetadataValue> getMetadata(String metadataKey)
    {
        return p.getMetadata(metadataKey);
    }

    @Override
    public boolean hasMetadata(String metadataKey)
    {
        return p.hasMetadata(metadataKey);
    }

    @Override
    public void removeMetadata(String metadataKey, Plugin owningPlugin)
    {
        p.removeMetadata(metadataKey, owningPlugin);
    }

    @Override
    public boolean isPermissionSet(String name)
    {
        return p.isPermissionSet(name);
    }

    @Override
    public boolean isPermissionSet(Permission perm)
    {
        return p.isPermissionSet(perm);
    }

    @Override
    public boolean hasPermission(String name)
    {
        return p.hasPermission(name);
    }

    @Override
    public boolean hasPermission(Permission perm)
    {
        return p.hasPermission(perm);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, int ticks)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void removeAttachment(PermissionAttachment attachment)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void recalculatePermissions()
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Set<PermissionAttachmentInfo> getEffectivePermissions()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isOp()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setOp(boolean value)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean isConversing()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void acceptConversationInput(String input)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean beginConversation(Conversation conversation)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void abandonConversation(Conversation conversation)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void abandonConversation(Conversation conversation, ConversationAbandonedEvent details)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void sendMessage(String message)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void sendMessage(String[] messages)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean isOnline()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isBanned()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setBanned(boolean banned)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean isWhitelisted()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setWhitelisted(boolean value)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Player getPlayer()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getFirstPlayed()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public long getLastPlayed()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean hasPlayedBefore()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Map<String, Object> serialize()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void sendPluginMessage(Plugin source, String channel, byte[] message)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Set<String> getListeningPluginChannels()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getDisplayName()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setDisplayName(String name)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String getPlayerListName()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setPlayerListName(String name)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setCompassTarget(Location loc)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Location getCompassTarget()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public InetSocketAddress getAddress()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void sendRawMessage(String message)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void kickPlayer(String message)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void chat(String msg)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean performCommand(String command)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isSneaking()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setSneaking(boolean sneak)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean isSprinting()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setSprinting(boolean sprinting)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void saveData()
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void loadData()
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setSleepingIgnored(boolean isSleeping)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean isSleepingIgnored()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void playNote(Location loc, byte instrument, byte note)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void playNote(Location loc, Instrument instrument, Note note)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void playSound(Location location, Sound sound, float volume, float pitch)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void playEffect(Location loc, Effect effect, int data)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public <T> void playEffect(Location loc, Effect effect, T data)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void sendBlockChange(Location loc, Material material, byte data)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean sendChunkChange(Location loc, int sx, int sy, int sz, byte[] data)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void sendBlockChange(Location loc, int material, byte data)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void sendMap(MapView map)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void updateInventory()
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void awardAchievement(Achievement achievement)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void incrementStatistic(Statistic statistic)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void incrementStatistic(Statistic statistic, int amount)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void incrementStatistic(Statistic statistic, Material material)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void incrementStatistic(Statistic statistic, Material material, int amount)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setPlayerTime(long time, boolean relative)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public long getPlayerTime()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public long getPlayerTimeOffset()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean isPlayerTimeRelative()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void resetPlayerTime()
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void giveExp(int amount)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public float getExp()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setExp(float exp)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int getLevel()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setLevel(int level)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int getTotalExperience()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setTotalExperience(int exp)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public float getExhaustion()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setExhaustion(float value)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public float getSaturation()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setSaturation(float value)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int getFoodLevel()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setFoodLevel(int value)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Location getBedSpawnLocation()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setBedSpawnLocation(Location location)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean getAllowFlight()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setAllowFlight(boolean flight)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void hidePlayer(Player player)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void showPlayer(Player player)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean canSee(Player player)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isFlying()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setFlying(boolean value)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setFlySpeed(float value) throws IllegalArgumentException
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setWalkSpeed(float value) throws IllegalArgumentException
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public float getFlySpeed()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public float getWalkSpeed()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void giveExpLevels(int amount)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setBedSpawnLocation(Location location, boolean force)
    {
        // TODO Auto-generated method stub
        
    }

}
