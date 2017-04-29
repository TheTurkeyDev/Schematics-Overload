package com.theprogrammningturkey.schematicsoverload.compatibility;

import java.io.File;

import com.theprogrammningturkey.schematicsoverload.util.Schematic;

import net.minecraft.world.World;

public interface ISchematicCompat
{
	void createSchematic(String fileName, World world);

	Schematic loadSchematic(String fileName, boolean includeAirBlocks);

	File getCompatModFolder();

	String getFileExtension();
}
