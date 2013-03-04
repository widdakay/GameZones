package com.yoerik.GameZones;

import org.bukkit.World;

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
	
	
}
