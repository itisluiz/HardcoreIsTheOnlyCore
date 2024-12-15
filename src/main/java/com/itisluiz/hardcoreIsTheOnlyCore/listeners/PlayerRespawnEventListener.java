package com.itisluiz.hardcoreIsTheOnlyCore.listeners;

import com.itisluiz.hardcoreIsTheOnlyCore.features.RespawnOnCorpse;
import com.itisluiz.hardcoreIsTheOnlyCore.utils.EventAssertion;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;

public final class PlayerRespawnEventListener implements Listener
{
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event)
	{
		if (!EventAssertion.isHardcorePlayer(event.getPlayer()))
			return;

		RespawnOnCorpse.getInstance().execute(event);
	}
}
