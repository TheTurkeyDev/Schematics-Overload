package com.theprogrammningturkey.schematicsoverload.compatibility;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.theprogrammningturkey.schematicsoverload.SchematicsCore;
import com.theprogrammningturkey.schematicsoverload.util.Schematic;

public class SchematicsManager
{
	private static Map<String, Schematic> loadedSchematics = new HashMap<String, Schematic>();
	private static Map<String, ISchematicCompat> schematicTypes = new HashMap<String, ISchematicCompat>();

	static
	{
		schematicTypes.put(SchematicsCore.MODID, new ModSchematic());
		schematicTypes.put("legacy", new LegacySchematic());
		schematicTypes.put("chancecubes", new ChanceCubesSchematic());
		//schematicTypes.put("reccomplex", new RecurrentComplexSchematic());
	}

	public static ISchematicCompat getSchematicCompatForName(String name)
	{
		return schematicTypes.get(name);
	}

	public static void addSchematic(String name, Schematic schematic)
	{
		loadedSchematics.put(name, schematic);
	}

	public static Schematic getSchematic(String name)
	{
		return loadedSchematics.get(name);
	}

	public static List<String> getLoadedSchematicNames()
	{
		return new ArrayList<String>(loadedSchematics.keySet());
	}

	public static void loadSchematics()
	{
		loadedSchematics.clear();
		for(String s : schematicTypes.keySet())
		{
			ISchematicCompat compat = schematicTypes.get(s);
			File folder = compat.getCompatModFolder();
			if(!folder.exists())
				folder.mkdirs();

			for(File f : folder.listFiles())
			{
				String name = f.getName().substring(0, f.getName().indexOf("."));
				if(loadedSchematics.containsKey(name))
				{
					loadedSchematics.put(f.getName(), compat.loadSchematic(f.getName(), true));
				}
				else
				{
					loadedSchematics.put(name, compat.loadSchematic(f.getName(), true));
				}
			}
		}
	}
}
