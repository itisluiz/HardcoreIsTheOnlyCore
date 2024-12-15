package com.itisluiz.hardcoreIsTheOnlyCore.listeners;

import com.itisluiz.hardcoreIsTheOnlyCore.features.LowHealthWarning;
import com.itisluiz.hardcoreIsTheOnlyCore.utils.EventAssertion;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public final class EntityDamageEventListener implements Listener
{
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event)
	{
		if (!EventAssertion.isHardcorePlayer(event.getEntity()))
			return;

		LowHealthWarning.getInstance().execute(event);
	}
}
