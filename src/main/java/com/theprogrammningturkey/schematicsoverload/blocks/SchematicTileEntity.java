package com.theprogrammningturkey.schematicsoverload.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

public class SchematicTileEntity extends SchematicBlock
{
	private NBTTagCompound nbt;
	
	public SchematicTileEntity(IBlockState state, BlockPos pos, NBTTagCompound nbt)
	{
		super(state, pos);
		this.nbt = nbt;
	}
	
	public NBTTagCompound getNBT()
	{
		return this.nbt;
	}

}
