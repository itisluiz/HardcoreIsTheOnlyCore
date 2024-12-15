package com.itisluiz.hardcoreIsTheOnlyCore.commands;

import com.itisluiz.hardcoreIsTheOnlyCore.features.NewGame;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public final class NewGameCommand implements CommandExecutor
{
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
	{
		NewGame.getInstance().execute();
		return true;
	}
}
