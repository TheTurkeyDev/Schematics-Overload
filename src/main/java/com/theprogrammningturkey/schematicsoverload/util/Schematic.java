package com.theprogrammningturkey.schematicsoverload.util;

import java.util.List;

import com.theprogrammningturkey.schematicsoverload.blocks.SchematicBlock;

import net.minecraft.nbt.NBTTagCompound;

public class Schematic
{
	private List<SchematicBlock> blocks;
	private List<NBTTagCompound> entities;
	private boolean includeEntities;
	private int xSize;
	private int ySize;
	private int zSize;
	private boolean includeAirBlocks;

	public Schematic(List<SchematicBlock> blocks, int xSize, int ySize, int zSize, boolean includeAirBlocks)
	{
		this.blocks = blocks;
		this.includeEntities = false;
		this.xSize = xSize;
		this.ySize = ySize;
		this.zSize = zSize;
		this.includeAirBlocks = includeAirBlocks;
	}

	public Schematic(List<SchematicBlock> blocks, List<NBTTagCompound> entities, int xSize, int ySize, int zSize, boolean includeAirBlocks)
	{
		this.blocks = blocks;
		this.entities = entities;
		this.includeEntities = !entities.isEmpty();
		this.xSize = xSize;
		this.ySize = ySize;
		this.zSize = zSize;
		this.includeAirBlocks = includeAirBlocks;
	}

	public List<SchematicBlock> getBlocks()
	{
		return this.blocks;
	}

	public List<NBTTagCompound> getEntities()
	{
		return this.entities;
	}

	public boolean hasEntities()
	{
		return this.includeEntities;
	}

	public int getXSize()
	{
		return this.xSize;
	}

	public int getYSize()
	{
		return this.ySize;
	}

	public int getZSize()
	{
		return this.zSize;
	}

	public boolean includeAirBlocks()
	{
		return this.includeAirBlocks;
	}
}