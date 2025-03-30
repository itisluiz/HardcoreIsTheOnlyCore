package com.itisluiz.hardcoreIsTheOnlyCore.features;

import com.itisluiz.hardcoreIsTheOnlyCore.HardcoreIsTheOnlyCore;
import com.itisluiz.hardcoreIsTheOnlyCore.partials.LastMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public final class LifeSavingBlock
{
	private static LifeSavingBlock instance;
	private final boolean cfgLifeSavingBlockWarning;
	private LastMessage lastMessage;

	private LifeSavingBlock()
	{
		cfgLifeSavingBlockWarning = HardcoreIsTheOnlyCore.getInstance().getConfig().getBoolean("lifeSavingBlockWarning");
	}

	public static LifeSavingBlock getInstance()
	{
		if (instance == null)
			instance = new LifeSavingBlock();

		return instance;
	}

	private void playLifeSavingBlockSound()
	{
		for (Player player : Bukkit.getOnlinePlayers())
			player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1.0f, 1.0f);
	}

	public void execute(EntityDamageEvent entityDamageEvent)
	{
		if (!cfgLifeSavingBlockWarning || entityDamageEvent.getEntityType() != EntityType.PLAYER)
			return;

		Player player = (Player)entityDamageEvent.getEntity();
		if (!player.isBlocking())
			return;

		int remainingHeathIfNotBlocking = Math.max((int)Math.ceil(player.getHealth() - entityDamageEvent.getDamage()), 0);
		int remainingHealth = Math.max((int)Math.ceil(player.getHealth() - entityDamageEvent.getFinalDamage()), 0);

		if (remainingHeathIfNotBlocking >= remainingHealth || remainingHeathIfNotBlocking > 0)
			return;

		String playerNameString = ChatColor.LIGHT_PURPLE + player.getName() + ChatColor.RESET;
		String lifeSavingBlockString = ChatColor.YELLOW + "life-saving block" + ChatColor.RESET;

		String newMessageString = "%s just performed a %s!".formatted(playerNameString, lifeSavingBlockString);
		LastMessage newMessage = new LastMessage(newMessageString);

		if (lastMessage == null || lastMessage.shouldSendNext(newMessage, 250))
		{
			Bukkit.broadcastMessage(newMessageString);
			playLifeSavingBlockSound();
		}

		lastMessage = newMessage;
	}
}
