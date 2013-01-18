package com.oro.legendoftest;

import com.oro.legendoftest.utility.Vector2;

public class Entity
{
	// ***********************************
	// Member variables
	// ***********************************
	
	Vector2 position = new Vector2();
	// These are positions in chunk-space, rather than in pixels.
	float xPos;
	float yPos;
	
	
	// ***********************************
	// Constructor
	// ***********************************
	public Entity()
	{
		
	}
	
	// ***********************************
	// Public Member Functions (methods)
	// ***********************************
	public void setPosition(float x, float y)
	{
		position.set(x, y);
	}
	
	public float getX()
	{
		return position.getX();
	}
	
	public float getY()
	{
		return position.getY();
	}
	public EntityResourceId getEntityResourceId()
	{
		return EntityResourceId.ENTITY_PLACEHOLDER;
	}
}
