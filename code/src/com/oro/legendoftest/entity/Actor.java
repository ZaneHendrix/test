package com.oro.legendoftest.entity;

import com.oro.legendoftest.Direction;
import com.oro.legendoftest.Entity;
import com.oro.legendoftest.EntityResourceId;
import com.oro.legendoftest.TerrainType;
import com.oro.legendoftest.utility.Vector2;

public class Actor extends Entity
{
	// ***********************************
	// Member variables
	// ***********************************
	
	Vector2 moving = new Vector2();
	//stat variables
	int maximumHealth;
	int currentHealth;
	int damage;
	float reach = 0.0f;
	float speed = 0.0035f;
//	public long timeOfLastUpdate = 0;
	
	// ***********************************
	// Constructor
	// ***********************************	
	public Actor()
	{
		super();
	}
	
	public void setMoving(Direction direction)
	{
		moving = direction.getVector();
	}
	public void setMoving(Vector2 direction)
	{
		moving = direction;
	}
		
	public EntityResourceId getEntityResourceId()
	{
		return EntityResourceId.ACTOR_PLACEHOLDER;
	}
	
	public int getMaximumHealth()
	{
		return maximumHealth;
	}
	
	public int getCurrentHealth()
	{
		return currentHealth;
	}
	
	public int getDamage()
	{
		return damage;
	}

	public float getReach()
	{
		return reach;
	}
	
	public float getSpeed(TerrainType terrain)
	{
		return speed;
	}
	
	public Vector2 getMovingDirection()
	{
		return moving;
	}
	
	public void findPath()
	{
		
	}
}
