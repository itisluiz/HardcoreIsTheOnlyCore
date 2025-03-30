package com.itisluiz.hardcoreIsTheOnlyCore.listeners;

import com.itisluiz.hardcoreIsTheOnlyCore.features.RegainedHealthWarning;
import com.itisluiz.hardcoreIsTheOnlyCore.utils.EventAssertion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public final class EntityRegainHealthEventListener implements Listener
{
	@EventHandler
	public void onEntityRegainHealth(EntityRegainHealthEvent event)
	{
		if (!EventAssertion.isHardcorePlayer(event.getEntity()))
			return;

		RegainedHealthWarning.getInstance().execute(event);
	}
}
