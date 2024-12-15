package com.itisluiz.hardcoreIsTheOnlyCore.utils;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class EventAssertion
{
	public static boolean isHardcorePlayer(Entity entity)
	{
		return entity.getType() == EntityType.PLAYER && entity.getWorld().isHardcore();
	}
}
