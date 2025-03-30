package com.itisluiz.hardcoreIsTheOnlyCore.features;

import com.itisluiz.hardcoreIsTheOnlyCore.HardcoreIsTheOnlyCore;
import com.itisluiz.hardcoreIsTheOnlyCore.partials.LastMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class RegainedHealthWarning
{
	private static RegainedHealthWarning instance;
	private final int cfgLowHealthWarningThreshold;
	private final int cfgRegainedHealthWarningThreshold;
	private final Set<UUID> lowHealthPlayerIds = new HashSet<>();
	private LastMessage lastMessage;

	private RegainedHealthWarning()
	{
		cfgLowHealthWarningThreshold = HardcoreIsTheOnlyCore.getInstance().getConfig().getInt("lowHealthWarningThreshold");
		cfgRegainedHealthWarningThreshold = HardcoreIsTheOnlyCore.getInstance().getConfig().getInt("regainedHealthWarningThreshold");
	}

	public static RegainedHealthWarning getInstance()
	{
		if (instance == null)
			instance = new RegainedHealthWarning();

		return instance;
	}

	private void playRegainedHealthSound()
	{
		for (Player player : Bukkit.getOnlinePlayers())
			player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_FLUTE, 1.0f, 1.0f);
	}

	// Execute for damage event
	public void execute(EntityDamageEvent entityDamageEvent)
	{
		if (entityDamageEvent.getEntityType() != EntityType.PLAYER)
			return;

		Player player = (Player)entityDamageEvent.getEntity();
		int remainingHealth = Math.max((int)Math.ceil(player.getHealth() - entityDamageEvent.getFinalDamage()), 0);

		if (remainingHealth <= cfgLowHealthWarningThreshold)
			lowHealthPlayerIds.add(player.getUniqueId());
	}

	// Execute for heal event
	public void execute(EntityRegainHealthEvent entityRegainHealthEvent)
	{
		if (entityRegainHealthEvent.getEntityType() != EntityType.PLAYER)
			return;

		Player player = (Player)entityRegainHealthEvent.getEntity();
		int remainingHealth = (int)Math.ceil(player.getHealth() + entityRegainHealthEvent.getAmount());

		if (remainingHealth <= cfgRegainedHealthWarningThreshold || !lowHealthPlayerIds.contains(player.getUniqueId()))
			return;

		String playerNameString = ChatColor.LIGHT_PURPLE + player.getName() + ChatColor.RESET;
		String regainedHealthString = ChatColor.GREEN + "doing okay" + ChatColor.RESET;

		String newMessageString = "%s is now %s!".formatted(playerNameString, regainedHealthString);
		LastMessage newMessage = new LastMessage(newMessageString);

		if (lastMessage == null || lastMessage.shouldSendNext(newMessage, 1000))
		{
			Bukkit.broadcastMessage(newMessageString);
			playRegainedHealthSound();
		}

		lowHealthPlayerIds.remove(player.getUniqueId());
		lastMessage = newMessage;
	}

	// Execute for death event
	public void execute(PlayerDeathEvent playerDeathEvent)
	{
		Player deadPlayer = playerDeathEvent.getEntity();
		lowHealthPlayerIds.remove(deadPlayer.getUniqueId());
	}
}
