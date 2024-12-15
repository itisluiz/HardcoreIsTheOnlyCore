package com.itisluiz.hardcoreIsTheOnlyCore.features;

import com.itisluiz.hardcoreIsTheOnlyCore.HardcoreIsTheOnlyCore;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

public final class NewJoinersSpectate
{
	private static NewJoinersSpectate instance;
	private final boolean cfgForceSpectatorOnNewJoin;

	private NewJoinersSpectate()
	{
		cfgForceSpectatorOnNewJoin = HardcoreIsTheOnlyCore.getInstance().getConfig().getBoolean("forceSpectatorOnNewJoin");
	}

	public static NewJoinersSpectate getInstance()
	{
		if (instance == null)
			instance = new NewJoinersSpectate();

		return instance;
	}

	public void execute(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		if (!cfgForceSpectatorOnNewJoin || player.getGameMode() == GameMode.SPECTATOR)
			return;

		boolean isWorldDeathMarked = HardcoreEnding.getInstance().isWorldMarkedWithDeath(player.getWorld());
		if (!isWorldDeathMarked)
			return;

		player.setGameMode(GameMode.SPECTATOR);
		player.sendMessage("You've been moved to " + ChatColor.AQUA + "spectator mode" + ChatColor.RESET + " because someone has died");
	}
}
