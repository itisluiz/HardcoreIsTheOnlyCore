package com.itisluiz.hardcoreIsTheOnlyCore;

import com.itisluiz.hardcoreIsTheOnlyCore.commands.NewGameCommand;
import com.itisluiz.hardcoreIsTheOnlyCore.listeners.EntityDamageEventListener;
import com.itisluiz.hardcoreIsTheOnlyCore.listeners.PlayerDeathEventListener;
import com.itisluiz.hardcoreIsTheOnlyCore.listeners.PlayerRespawnEventListener;
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
        getServer().getPluginManager().registerEvents(new PlayerDeathEventListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerRespawnEventListener(), this);
    }

    private void registerCommands()
    {
        Objects.requireNonNull(this.getCommand("newgame")).setExecutor(new NewGameCommand());
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
    public void onDisable()
    {


    }
}
