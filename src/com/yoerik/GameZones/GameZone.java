package com.yoerik.GameZones;

import org.bukkit.World;
import org.bukkit.entity.Player;

public class GameZone {
	long xPos;
	long zPos;
	World world;

	public GameZone(long x, long z, World worldPos) {
		setPos(x, z, worldPos);
	}

	public void setPos(long x, long z, World worldPos) {
		xPos = x;
		zPos = z;
		world = worldPos;
	}

	public boolean isClaimed() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isPlot(){
		return true; //Return false later
	}

	public boolean isClaimable(){
		
		return true; // All locations are valid, for now. 
	}

	public String getOwner() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean claim(Player player) {
		// TODO Auto-generated method stub
		if (!(this.isClaimable())) {
			return false;
		}
		if (this.isClaimed()) {
			return false;
		}
		return true;

	}

}
