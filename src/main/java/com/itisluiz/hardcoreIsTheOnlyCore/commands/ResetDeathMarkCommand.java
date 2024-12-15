package com.itisluiz.hardcoreIsTheOnlyCore.commands;

import com.itisluiz.hardcoreIsTheOnlyCore.features.HardcoreEnding;
import com.itisluiz.hardcoreIsTheOnlyCore.features.NewGame;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public final class ResetDeathMarkCommand implements CommandExecutor
{
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
	{
		HardcoreEnding.getInstance().markWorldsWithDeath(false);

		Bukkit.broadcastMessage("Death marks have been " + ChatColor.GREEN + "reset"
			+ ChatColor.RESET + " for all worlds.");

		return true;
	}
}
