package com.itisluiz.hardcoreIsTheOnlyCore.listeners;

import com.itisluiz.hardcoreIsTheOnlyCore.features.NewJoinersSpectate;
import com.itisluiz.hardcoreIsTheOnlyCore.utils.EventAssertion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public final class PlayerJoinEventListener implements Listener
{
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		if (!EventAssertion.isHardcorePlayer(event.getPlayer()))
			return;

		NewJoinersSpectate.getInstance().execute(event);
	}
}
