package com.itisluiz.hardcoreIsTheOnlyCore.features;

import com.itisluiz.hardcoreIsTheOnlyCore.utils.ZipUtils;
import org.bukkit.*;

import java.io.File;
import java.util.ArrayList;

public final class WorldBackup
{
	private static WorldBackup instance;
	private static final String BACKUP_FOLDER = "hitoc";

	private WorldBackup() {}

	public static WorldBackup getInstance()
	{
		if (instance == null)
			instance = new WorldBackup();

		return instance;
	}

	public void execute()
	{
		ArrayList<File> worldFolders = new ArrayList<File>();

		for (World world : Bukkit.getWorlds())
		{
			world.save();
			worldFolders.add(world.getWorldFolder());
		}

		File backupFolder = new File(Bukkit.getServer().getWorldContainer(), BACKUP_FOLDER);
		if (!backupFolder.exists() && !backupFolder.mkdirs())
		{
			Bukkit.getLogger().warning("Could not create backup folder");
			return;
		}

		int timestamp = (int)(System.currentTimeMillis() / 1000);
		ZipUtils.zipDirectories(worldFolders, new File(backupFolder, "backup_%d.zip".formatted(timestamp)));
	}
}
