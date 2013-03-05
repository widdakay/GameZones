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
		long[] x1;
		long[] z1;
		long[] x2;
		long[] z2; //Arrays of the corners of the plots, 1 being larger corner and 2 being smaller
		int amnt = 0; //Amount of total corners/plots there are
		x1 = new long[amnt];
		z1 = new long[amnt];
		x2 = new long[amnt];
		z2 = new long[amnt];
		//loop here to fill the arrays with the actual corners
		for(int i = 0; i < amnt; i++){
			World world = //Read world from plot here, using integer i
			if(worldPos == world){
			if(xPos <= x1[i] && xPos >= x2[i]){
				if(zPos <= z1[i] && zPos >= z2[i]){
					return true;
				}
			}
		}
		}
		return true; //Return false later
	}

	public String getOwner() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean claim(Player player) {
		// TODO Auto-generated method stub
		if(!(this.isPlot())){
			return false;
		}
		if (this.isClaimed()) {
			return false;
		}
		return true;

	}

}
