package com.theprogrammningturkey.schematicsoverload.util;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SchematicUtil
{
	public static BlockPos pos1;
	public static BlockPos pos2;

	public static void replaceBlocksInSelection(World world, IBlockState oldState, IBlockState newState)
	{
		if(pos1 == null || pos2 == null)
			return;
		CustomEntry<BlockPos, BlockPos> highlow = SchematicUtil.getHigherLowerPosition(pos1, pos2);
		BlockPos large = highlow.getKey();
		BlockPos small = highlow.getValue();
		BlockPos currentPos;

		for(int y = small.getY(); y < large.getY(); y++)
		{
			for(int x = small.getX(); x < large.getX(); x++)
			{
				for(int z = small.getZ(); z < large.getZ(); z++)
				{
					currentPos = new BlockPos(x, y, z);
					if(world.getBlockState(currentPos).equals(oldState))
					{
						world.setBlockState(currentPos, newState);
					}
				}
			}
		}
	}

	public static CustomEntry<BlockPos, BlockPos> getHigherLowerPosition(BlockPos loc1, BlockPos loc2)
	{
		int largeX = loc1.getX() > loc2.getX() ? loc1.getX() : loc2.getX();
		int smallX = loc1.getX() < loc2.getX() ? loc1.getX() : loc2.getX();
		int largeY = loc1.getY() > loc2.getY() ? loc1.getY() : loc2.getY();
		int smallY = loc1.getY() < loc2.getY() ? loc1.getY() : loc2.getY();
		int largeZ = loc1.getZ() > loc2.getZ() ? loc1.getZ() : loc2.getZ();
		int smallZ = loc1.getZ() < loc2.getZ() ? loc1.getZ() : loc2.getZ();
		return new CustomEntry<BlockPos, BlockPos>(new BlockPos(largeX, largeY, largeZ), new BlockPos(smallX, smallY, smallZ));
	}
	
	public static Block getBlock(String mod, String blockName)
	{
		return Block.REGISTRY.getObject(new ResourceLocation(mod, blockName));
	}
}
