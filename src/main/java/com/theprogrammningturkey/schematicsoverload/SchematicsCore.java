package com.theprogrammningturkey.schematicsoverload;

import java.io.File;

import org.apache.logging.log4j.Logger;

import com.theprogrammningturkey.schematicsoverload.commands.SchematicsServerCommands;
import com.theprogrammningturkey.schematicsoverload.proxy.CommonProxy;
import com.theprogrammningturkey.schematicsoverload.util.FileUtil;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = SchematicsCore.MODID, version = SchematicsCore.VERSION, name = SchematicsCore.NAME)
public class SchematicsCore
{
	public static final String MODID = "schematicsoverload";
	public static final String NAME = "Schematics Overload";
	public static final String VERSION = "@Version@";

	public static final String SCHEMATIC_VERSION = "1.0";

	public static Logger logger;

	@SidedProxy(clientSide = "com.theprogrammningturkey.schematicsoverload.proxy.ClientProxy", serverSide = "com.theprogrammningturkey.schematicsoverload.proxy.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void load(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();

		FileUtil.folder = new File(event.getSuggestedConfigurationFile().getParentFile().getAbsolutePath() + "/Schematic Overload");
		FileUtil.folder.mkdirs();
	}

	@EventHandler
	public void postInit(FMLPreInitializationEvent event)
	{
		proxy.registerEvents();
		proxy.registerRenderings();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{

	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
	}

	@EventHandler
	public void serverLoad(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new SchematicsServerCommands());

		/*
		 * if(event.getSide().isClient()) { logger.log(Level.INFO, "Client-side commands loaded"); ClientCommandHandler.instance.registerCommand(new CCubesClientCommands()); } else if(event.getSide().isServer()) { logger.log(Level.INFO, "Server-side commands loaded"); event.registerServerCommand(new SchematicsServerCommands()); }
		 */
	}
}
