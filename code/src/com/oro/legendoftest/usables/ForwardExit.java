package com.oro.legendoftest.usables;

import com.oro.legendoftest.IUsable;
import com.oro.legendoftest.level.LevelManager;

public class ForwardExit implements IUsable
{
	@Override
	public void use()
	{
		LevelManager.INSTANCE.goForward();
		System.out.println("Go Forward");

	}

}
