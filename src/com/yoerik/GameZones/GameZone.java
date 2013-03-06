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
<<<<<<< HEAD
	}

	public boolean isClaimable(){
		
		return true; // All locations are valid, for now. 
=======
>>>>>>> parent of 4e5c255... renamed and fixed isPlot function to isClaimable
	}

	public String getOwner() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean claim(Player player) {
		// TODO Auto-generated method stub
<<<<<<< HEAD
		if (!(this.isClaimable())) {
			return false; // false?
=======
		if(!(this.isPlot())){
			return false;
>>>>>>> Revert "renamed and fixed isPlot function to isClaimable"
		}
		if (this.isClaimed()) {
			return false;
		}
		return true;

	}

}
