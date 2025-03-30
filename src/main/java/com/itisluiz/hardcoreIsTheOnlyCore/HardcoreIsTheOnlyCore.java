package com.itisluiz.hardcoreIsTheOnlyCore;

import com.itisluiz.hardcoreIsTheOnlyCore.commands.NewGameCommand;
import com.itisluiz.hardcoreIsTheOnlyCore.commands.ResetDeathMarkCommand;
import com.itisluiz.hardcoreIsTheOnlyCore.listeners.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class HardcoreIsTheOnlyCore extends JavaPlugin
{
    private static HardcoreIsTheOnlyCore instance;

    public static HardcoreIsTheOnlyCore getInstance()
    {
        return instance;
    }

    private void registerEvents()
    {
        getServer().getPluginManager().registerEvents(new EntityDamageEventListener(), this);
        getServer().getPluginManager().registerEvents(new EntityRegainHealthEventListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathEventListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerRespawnEventListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinEventListener(), this);
    }

    private void registerCommands()
    {
        Objects.requireNonNull(this.getCommand("newgame")).setExecutor(new NewGameCommand());
        Objects.requireNonNull(this.getCommand("resetdeathmark")).setExecutor(new ResetDeathMarkCommand());
    }

    @Override
    public void onEnable()
    {
        instance = this;
        saveDefaultConfig();
        registerEvents();
        registerCommands();
    }

    @Override
    public void onDisable() {}
}
