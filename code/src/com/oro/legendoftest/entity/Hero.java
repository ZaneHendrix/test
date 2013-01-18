package com.oro.legendoftest.entity; 

import com.oro.legendoftest.Direction;
import com.oro.legendoftest.EntityResourceId; 
import com.oro.legendoftest.TerrainType;

public class Hero extends Actor 
{
	private static Hero INSTANCE = null;
	

	public static synchronized Hero INSTANCE()
	{
		if (INSTANCE == null)
		{
			INSTANCE = new Hero();
		}
		return INSTANCE;
	}
	
	public Hero()
	{
		super();
		speed = 0.004f;
		//this.setMoving(Direction.N);
	}
	@Override
	public float getSpeed(TerrainType terrain)
	{
		float newSpeed;
		switch (terrain)
		{
		case SLUDGE:		newSpeed = speed * 1;//.25f;
							break;
		case DIRT_FLOOR: 	newSpeed = speed;
						 	break;
		case STONE_FLOOR:	newSpeed = speed * 1.1f;
							break;
		default:			newSpeed = speed;
							break;
		}
		return newSpeed;
	}
	
	public EntityResourceId getEntityResourceId()
	{
		return EntityResourceId.HERO; 
	}
}