package me.cnaude.plugin.Scavenger;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;


public class ScavengerEventListener_Online implements Listener { 

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDeathEvent(PlayerDeathEvent event) {        
        if ((event.getEntity() instanceof Player)) {                        
            if (isScavengeAllowed(event.getEntity())) {
                RestorationManager.collect(event.getEntity(), event.getDrops(), event);
            }
        }         
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        if ((event.getPlayer() instanceof Player)) {                        
            if (isScavengeAllowed(event.getPlayer())) {
                RestorationManager.enable(event.getPlayer());
            }
        }        
    } 
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        if ((event.getPlayer() instanceof Player)) {                        
            if (isScavengeAllowed(event.getPlayer())) {
                RestorationManager.enable(event.getPlayer());
            }
        }        
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerMove(PlayerMoveEvent event) {                
        if ((event.getPlayer() instanceof Player)) {                        
            if (isScavengeAllowed(event.getPlayer())) {
                RestorationManager.restore(event.getPlayer());
            }
        }        
    }
    
    private boolean isScavengeAllowed(Player player) {                              
        if (ScavengerIgnoreList.isIgnored(player.getName())) {
            return false;
        }
        if (!Scavenger.getSConfig().permsEnabled()) {
            return true;
        }
        if (player.hasPermission("scavenger.scavenge")) {
            return true;
        }
        if (player.hasPermission("scavenger.inv")) {
            return true;
        }
        if (player.hasPermission("scavenger.armour")) {
            return true;
        }
        if ((player.isOp() && Scavenger.getSConfig().opsAllPerms())) {
                return true;
        }
        return false;
    }    
}