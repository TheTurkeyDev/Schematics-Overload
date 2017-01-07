package com.theprogrammningturkey.schematicsoverload;

import java.io.File;

import org.apache.logging.log4j.Logger;

import com.theprogrammningturkey.schematicsoverload.util.FileUtil;

import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = SchematicsCore.MODID, version = SchematicsCore.VERSION, name = SchematicsCore.NAME)
public class SchematicsCore
{
    public static final String MODID = "schematicsoverload";
    public static final String NAME = "Schematics Overload";
    public static final String VERSION = "@Version@";
    
	public static Logger logger;
    
	@EventHandler
	public void load(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();
		
		FileUtil.folder = new File(event.getSuggestedConfigurationFile().getParentFile().getAbsolutePath() + "/Schematic Overload");
		FileUtil.folder.mkdirs();
	}
	
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
	    
    }
}
