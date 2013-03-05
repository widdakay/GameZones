package com.yoerik.GameZones;

import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ClickListener implements Listener {
	
	public void onPlayerSelect(PlayerInteractEvent evt){
		if(evt.getAction() == Action.RIGHT_CLICK_BLOCK){
			ItemStack item = evt.getItem();
			if(item.getTypeId() == GameZones.toolId){
				Location loc = evt.getClickedBlock().getLocation();
				if(GameZones.selectionsRight.containsKey(evt.getPlayer().getName())){
					GameZones.selectionsRight.remove(evt.getPlayer().getName());
				}
				GameZones.selectionsRight.put(evt.getPlayer().getName(), loc);
				evt.getPlayer().sendMessage("Corner 1 selected!");
				evt.setCancelled(true);
			}
		} else if(evt.getAction() == Action.LEFT_CLICK_BLOCK){
			ItemStack item = evt.getItem();
			if(item.getTypeId() == GameZones.toolId){
				Location loc = evt.getClickedBlock().getLocation();
				if(GameZones.selectionsLeft.containsKey(evt.getPlayer().getName())){
					GameZones.selectionsLeft.remove(evt.getPlayer().getName());
				}
				GameZones.selectionsLeft.put(evt.getPlayer().getName(), loc);
				evt.getPlayer().sendMessage("Corner 2 selected!");
				evt.setCancelled(true);
			}
		}
	}

}
