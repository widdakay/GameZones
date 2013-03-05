package com.yoerik.GameZones;

import org.bukkit.World;
import org.bukkit.entity.Player;

public class GameZone {
	long xPos;
	long yPos;
	World world;
	
	public GameZone(long x, long y, World worldPos) {
		setPos(x, y, worldPos);
	}
	public void setPos(long x, long y, World worldPos) {
		xPos = x;
		yPos = y;
		world = worldPos;
	}
	public boolean isClaimed() {
		// TODO Auto-generated method stub
		return false;
	}
	public String getOwner() {
		// TODO Auto-generated method stub
		return null;
	}
	public boolean claim(Player player) {
		// TODO Auto-generated method stub
		if (this.isClaimed()) {
			return false;
		}
		return true;
		
	}
	
	
}
