package com.theprogrammningturkey.schematicsoverload.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;

public class SchematicBlock
{
	private IBlockState state;
	private BlockPos pos;
	
	public SchematicBlock(IBlockState state, BlockPos pos)
	{
		this.state = state;
		this.pos = pos;
	}
	
	public IBlockState getBlockState()
	{
		return state;
	}
	
	public BlockPos getBlockOffSet()
	{
		return this.pos;
	}
}
