package com.itisluiz.hardcoreIsTheOnlyCore.features;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;

public final class HardcoreEnding
{
	private static HardcoreEnding instance;
	private Location deathLocation;

	private HardcoreEnding() {}

	public static HardcoreEnding getInstance()
	{
		if (instance == null)
			instance = new HardcoreEnding();

		return instance;
	}

	private void sendFinalMessage(Player deadPlayer)
	{
		String finalMessageString = String.format("Game over! " + ChatColor.LIGHT_PURPLE + "%s" +
			ChatColor.RESET + " has fallen!", deadPlayer.getName());

		World world = Bukkit.getWorlds().getFirst();
		int dayCount = (int)(world.getFullTime() / 24000);

		String statisticsString = String.format("Game lasted " + ChatColor.AQUA + "%d" +
			ChatColor.RESET + " days", dayCount);

		String newGameString = "Use " + ChatColor.AQUA + "/newgame" + ChatColor.RESET +
			" to start a new game";

		Bukkit.broadcastMessage(finalMessageString);
		Bukkit.broadcastMessage(statisticsString);
		Bukkit.broadcast(newGameString, "hitoc.gamemaster");
	}

	private void forceSpectatorMode(Location deathLocation)
	{
		for (Player player : Bukkit.getOnlinePlayers())
		{
			player.setGameMode(GameMode.SPECTATOR);

			if (!player.isDead())
				player.teleport(deathLocation);
		}
	}

	public Location getDeathLocation()
	{
		return deathLocation;
	}

	public void execute(EntityDeathEvent entityDeathEvent)
	{
		Player deadPlayer = (Player)entityDeathEvent.getEntity();
		deathLocation = deadPlayer.getLocation();
		sendFinalMessage(deadPlayer);
		forceSpectatorMode(deathLocation);
	}
}
