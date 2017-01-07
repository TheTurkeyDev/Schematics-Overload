package com.theprogrammningturkey.schematicsoverload.util;

import java.util.List;

import com.theprogrammningturkey.schematicsoverload.blocks.SchematicBlock;

public class Schematic
{
	private List<SchematicBlock> blocks;
	private int xSize;
	private int ySize;
	private int zSize;
	private boolean includeAirBlocks;

	public Schematic(List<SchematicBlock> blocks, int xSize, int ySize, int zSize, boolean includeAirBlocks)
	{
		this.blocks = blocks;
		this.xSize = xSize;
		this.ySize = ySize;
		this.zSize = zSize;
		this.includeAirBlocks = includeAirBlocks;
	}

	public List<SchematicBlock> getBlocks()
	{
		return this.blocks;
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