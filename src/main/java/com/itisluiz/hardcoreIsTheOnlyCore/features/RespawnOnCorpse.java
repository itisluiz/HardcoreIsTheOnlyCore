package com.itisluiz.hardcoreIsTheOnlyCore.features;

import org.bukkit.*;
import org.bukkit.event.player.PlayerRespawnEvent;

public final class RespawnOnCorpse
{
	private static RespawnOnCorpse instance;

	private RespawnOnCorpse() {}

	public static RespawnOnCorpse getInstance()
	{
		if (instance == null)
			instance = new RespawnOnCorpse();

		return instance;
	}

	public void execute(PlayerRespawnEvent event)
	{
		Location deathLocation = HardcoreEnding.getInstance().getDeathLocation();

		if (event.getPlayer().getGameMode() != GameMode.SPECTATOR || deathLocation == null)
			return;

		event.setRespawnLocation(deathLocation);
	}
}
