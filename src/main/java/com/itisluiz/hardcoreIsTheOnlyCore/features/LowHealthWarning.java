package com.itisluiz.hardcoreIsTheOnlyCore.features;

import com.itisluiz.hardcoreIsTheOnlyCore.HardcoreIsTheOnlyCore;
import com.itisluiz.hardcoreIsTheOnlyCore.partials.LastMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;

public final class LowHealthWarning
{
	private static LowHealthWarning instance;
	private final int cfgLowHealthWarningThreshold;
	private LastMessage lastMessage;

	private LowHealthWarning()
	{
		cfgLowHealthWarningThreshold = HardcoreIsTheOnlyCore.getInstance().getConfig().getInt("lowHealthWarningThreshold");
	}

	public static LowHealthWarning getInstance()
	{
		if (instance == null)
			instance = new LowHealthWarning();

		return instance;
	}

	private void playLowHealthSound(int remainingHealth)
	{
		float pitchModifier = (1.0f - (Math.max(remainingHealth, 1.0f) / cfgLowHealthWarningThreshold));
		float pitch = 1.0f + Math.clamp(pitchModifier, 0, 1);

		for (Player player : Bukkit.getOnlinePlayers())
			player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE, 1.0f, pitch);
	}

	private HashSet<String> getPlayerAilments(Player player) {
		HashSet<String> ailments = new HashSet<>();

		if (player.hasPotionEffect(PotionEffectType.WITHER))
			ailments.add(ChatColor.DARK_GRAY + "Withered" + ChatColor.RESET);

		if (player.hasPotionEffect(PotionEffectType.POISON))
			ailments.add(ChatColor.GREEN + "Poisoned" + ChatColor.RESET);

		if (player.hasPotionEffect(PotionEffectType.BLINDNESS))
			ailments.add(ChatColor.BLACK + "Blind" + ChatColor.RESET);

		if (player.hasPotionEffect(PotionEffectType.SLOWNESS))
			ailments.add(ChatColor.BLUE + "Slowed" + ChatColor.RESET);

		if (player.getFireTicks() > 0)
			ailments.add(ChatColor.RED + "On Fire" + ChatColor.RESET);

		if (player.getRemainingAir() == 0)
			ailments.add(ChatColor.AQUA + "Drowning" + ChatColor.RESET);

		if (player.getFoodLevel() == 0)
			ailments.add(ChatColor.YELLOW + "Starving" + ChatColor.RESET);

		return ailments;
	}

	public void execute(EntityDamageEvent entityDamageEvent)
	{
		if (entityDamageEvent.getEntityType() != EntityType.PLAYER)
			return;

		Player player = (Player)entityDamageEvent.getEntity();
		int remainingHealth = Math.max((int)Math.ceil(player.getHealth() - entityDamageEvent.getFinalDamage()), 0);

		if (remainingHealth > cfgLowHealthWarningThreshold || remainingHealth == 0)
			return;

		String playerNameString = ChatColor.LIGHT_PURPLE + player.getName() + ChatColor.RESET;

		String remainingHealthString = (remainingHealth <= 5 ? ChatColor.RED : ChatColor.YELLOW)
				+ String.valueOf(remainingHealth) + ChatColor.RESET;

		HashSet<String> ailments = getPlayerAilments(player);

		String newMessageString;
		if (!ailments.isEmpty())
		{
			String ailmentsString = String.join(", ", ailments);
			newMessageString = "%s is down to %s hp while %s!".formatted(playerNameString, remainingHealthString, ailmentsString);
		}
		else
			newMessageString = "%s is down to %s hp!".formatted(playerNameString, remainingHealthString);

		LastMessage newMessage = new LastMessage(newMessageString);

		if (lastMessage != null && lastMessage.shouldSendNext(newMessage, 1000))
		{
			Bukkit.broadcastMessage(newMessageString);
			playLowHealthSound(remainingHealth);
		}

		lastMessage = newMessage;
	}
}
