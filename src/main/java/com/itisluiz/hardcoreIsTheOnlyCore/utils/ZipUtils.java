package com.itisluiz.hardcoreIsTheOnlyCore.utils;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class ZipUtils
{
	private static void addFileToZip(ZipOutputStream zos, File file, String parent)
	{
		try
		{
			String zipEntryName = parent == null ? file.getName() : parent + "/" + file.getName();
			if (file.isFile())
			{
				zos.putNextEntry(new ZipEntry(zipEntryName));
				Files.copy(file.toPath(), zos);
			}

			zos.closeEntry();

			if (file.isDirectory())
			{
				File[] children = file.listFiles();
				if (children != null)
				{
					for (File child : children)
						addFileToZip(zos, child, zipEntryName);
				}
			}
		}
		catch (IOException e)
		{
			// Ignore world related session.lock files
			if (file.getName().equals("session.lock"))
				return;

			throw new UncheckedIOException("Error adding file to zip '%s'".formatted(file), e);
		}
	}

	public static void zipDirectories(List<File> files, File outputZip)
	{
		try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(outputZip.toPath())))
		{
			for (File file : files)
				addFileToZip(zos, file, null);
		}
		catch (IOException e)
		{
			throw new UncheckedIOException("Error creating zip file '%s'".formatted(outputZip), e);
		}
	}
}
