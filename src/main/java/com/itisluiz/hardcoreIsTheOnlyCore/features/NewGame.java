package com.itisluiz.hardcoreIsTheOnlyCore.features;

import com.itisluiz.hardcoreIsTheOnlyCore.HardcoreIsTheOnlyCore;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.UncheckedIOException;

public final class NewGame
{
	private static NewGame instance;
	private final boolean cfgBackupWorldsOnNewGame;

	private NewGame()
	{
		cfgBackupWorldsOnNewGame = HardcoreIsTheOnlyCore.getInstance().getConfig().getBoolean("backupWorldsOnNewGame");
	}

	public static NewGame getInstance()
	{
		if (instance == null)
			instance = new NewGame();

		return instance;
	}

	private void deleteWorlds()
	{
		for (World world : Bukkit.getWorlds())
		{
			try
			{
				FileUtils.deleteDirectory(world.getWorldFolder());
			}
			catch (IOException e)
			{
				throw new UncheckedIOException("Could not delete world '%s'".formatted(world.getName()), e);
			}
		}
	}

	private void kickPlayers()
	{
		for (Player player : Bukkit.getOnlinePlayers())
			player.kickPlayer(ChatColor.AQUA + "Starting a new game: " + ChatColor.RESET + "Restarting server");
	}

	public void execute()
	{
		if (cfgBackupWorldsOnNewGame)
		{
			Bukkit.broadcastMessage(ChatColor.AQUA + "Starting a new game: " + ChatColor.RESET + "Backing up worlds");
			WorldBackup.getInstance().execute();
		}

		Bukkit.broadcastMessage(ChatColor.AQUA + "Starting a new game: " + ChatColor.RESET + "Deleting worlds");
		deleteWorlds();
		kickPlayers();

		// Kill the server, otherwise it gets stuck attempting to save the world
		Runtime.getRuntime().halt(255);
	}
}
