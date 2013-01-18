package com.oro.legendoftest.usables;

import com.oro.legendoftest.IUsable;
import com.oro.legendoftest.level.LevelManager;

public class BackExit implements IUsable
{
	@Override
	public void use()
	{
		if (LevelManager.INSTANCE.isAtStart())
		{
			// TODO: Tease the player for running away in fear!
		}
		else
		{
			LevelManager.INSTANCE.goBack();
			System.out.println("Go Back");
		}
	}

}
