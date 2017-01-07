package com.theprogrammningturkey.schematicsoverload.compatibility;

import java.io.File;

import com.theprogrammningturkey.schematicsoverload.util.Schematic;

import net.minecraft.world.World;

interface ISchematicCompat
{
	public void createSchematic(String fileName, World world);
	
	public Schematic loadSchematic(String fileName, boolean includeAirBlocks);
	
	public File getCompatModFolder();
}
