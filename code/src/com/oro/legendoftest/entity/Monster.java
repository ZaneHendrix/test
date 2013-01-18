package com.oro.legendoftest.entity;

import com.oro.legendoftest.level.LevelManager;
import com.oro.legendoftest.utility.Pathing;
import com.oro.legendoftest.utility.Pathing.Node;
import com.oro.legendoftest.utility.Vector2;

public class Monster extends NPC 
{
	Pathing path = new Pathing();
	Node[] finalPath = new Node[20];
	
	@Override
	public void findPath()
	{
		float goalX = Hero.INSTANCE().getX();
		float goalY = Hero.INSTANCE().getY();
		path.findPath(this.getX(), this.getY(), goalX, goalY, finalPath);
		
		Vector2 vector2 = new Vector2();
		for(int i = 0; i < finalPath.length; i++)
		{
			vector2.set(this.getX() - finalPath[i].X, this.getY() - finalPath[i].Y);
			while(this.getX() != finalPath[i].X || this.getY() != finalPath[i].Y)
			{
				for(int a = 0; a < LevelManager.INSTANCE.getCurrentLevel().actors.size(); a++)
				{
					LevelManager.INSTANCE.getCurrentLevel().actors.get(a).setMoving(vector2);
				}
			}
		}
//		timeOfLastUpdate = System.currentTimeMillis();
	}
}
