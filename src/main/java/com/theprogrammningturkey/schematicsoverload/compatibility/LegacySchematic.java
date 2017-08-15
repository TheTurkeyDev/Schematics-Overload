package com.theprogrammningturkey.schematicsoverload.compatibility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.theprogrammningturkey.schematicsoverload.blocks.SchematicBlock;
import com.theprogrammningturkey.schematicsoverload.blocks.SchematicTileEntity;
import com.theprogrammningturkey.schematicsoverload.util.FileUtil;
import com.theprogrammningturkey.schematicsoverload.util.Schematic;
import com.theprogrammningturkey.schematicsoverload.util.SchematicCreationSettings;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LegacySchematic implements ISchematicCompat
{
	public Schematic loadSchematic(String fileName, boolean includeAirBlocks)
	{
		File schematic = new File(this.getCompatModFolder(), fileName);
		NBTTagCompound nbtdata;
		try
		{
			FileInputStream is = new FileInputStream(schematic);
			nbtdata = CompressedStreamTools.readCompressed(is);
			is.close();
		} catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}

		short width = nbtdata.getShort("Width");
		short height = nbtdata.getShort("Height");
		short length = nbtdata.getShort("Length");

		byte[] blocks = nbtdata.getByteArray("Blocks");
		byte[] data = nbtdata.getByteArray("Data");
		List<SchematicBlock> offsetBlocks = new ArrayList<SchematicBlock>();

		NBTTagList tileentities = nbtdata.getTagList("TileEntities", 10);

		int i = 0;
		short halfLength = (short) (length / 2);
		short halfWidth = (short) (width / 2);

		for(int yy = 0; yy < height; yy++)
		{
			for(int zz = 0; zz < length; zz++)
			{
				for(int xx = 0; xx < width; xx++)
				{
					int j = blocks[i];
					if(j < 0)
						j = 128 + (128 + j);

					Block b = Block.getBlockById(j);
					if(b != Blocks.AIR)
						offsetBlocks.add(new SchematicBlock(b.getStateFromMeta(data[i]), new BlockPos(halfWidth - xx, yy, halfLength - zz)));
					i++;
				}
			}
		}

		if(tileentities != null)
		{
			for(int i1 = 0; i1 < tileentities.tagCount(); ++i1)
			{
				NBTTagCompound nbttagcompound4 = tileentities.getCompoundTagAt(i1);
				TileEntity tileentity = TileEntity.func_190200_a(null, nbttagcompound4);

				if(tileentity != null)
				{
					IBlockState b = null;
					for(SchematicBlock osb : offsetBlocks)
						if(osb.getBlockOffSet().equals(tileentity.getPos()))
							b = osb.getBlockState();
					if(b == null)
						b = Blocks.STONE.getDefaultState();
					offsetBlocks.add(new SchematicTileEntity(b, tileentity.getPos(), nbttagcompound4));
				}
			}
		}

		return new Schematic(offsetBlocks, width, height, length, includeAirBlocks);
	}

	@Override
	public void createSchematic(String fileName, World world, SchematicCreationSettings settings)
	{

	}

	@Override
	public File getCompatModFolder()
	{
		return new File(FileUtil.folder, "Legacy");
	}

	@Override
	public String getFileExtension()
	{
		return ".schematic";
	}
}
