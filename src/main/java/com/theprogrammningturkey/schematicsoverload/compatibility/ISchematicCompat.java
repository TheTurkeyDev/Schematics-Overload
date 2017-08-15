package com.theprogrammningturkey.schematicsoverload.compatibility;

import java.io.File;

import com.theprogrammningturkey.schematicsoverload.util.Schematic;
import com.theprogrammningturkey.schematicsoverload.util.SchematicCreationSettings;

import net.minecraft.world.World;

public interface ISchematicCompat
{
	void createSchematic(String fileName, World world, SchematicCreationSettings settings);

	Schematic loadSchematic(String fileName, boolean includeAirBlocks);

	File getCompatModFolder();

	String getFileExtension();
}
