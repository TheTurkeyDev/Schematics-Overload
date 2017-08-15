package com.theprogrammningturkey.schematicsoverload.compatibility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.theprogrammningturkey.schematicsoverload.util.FileUtil;
import com.theprogrammningturkey.schematicsoverload.util.Schematic;
import com.theprogrammningturkey.schematicsoverload.util.SchematicCreationSettings;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public class RecurrentComplexSchematic implements ISchematicCompat
{

	public static final String STRUCTURE_INFO_JSON_FILENAME = "structure.json";
	public static final String WORLD_DATA_NBT_FILENAME = "worldData.nbt";

	@Override
	public void createSchematic(String fileName, World world, SchematicCreationSettings settings)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public Schematic loadSchematic(String fileName, boolean includeAirBlocks)
	{
		Path path = new File(this.getCompatModFolder().getAbsolutePath() + "/" + fileName).toPath();
		String json = null;
		NBTTagCompound worldData = null;
		try
		{
			ZipInputStream zip = new ZipInputStream(Files.newInputStream(path));

			ZipEntry zipEntry;

			while((zipEntry = zip.getNextEntry()) != null)
			{
				byte[] bytes = completeByteArray(zip);

				if(bytes != null)
				{
					if(STRUCTURE_INFO_JSON_FILENAME.equals(zipEntry.getName()))
						json = new String(bytes);
					else if(WORLD_DATA_NBT_FILENAME.equals(zipEntry.getName()))
						worldData = CompressedStreamTools.readCompressed(new ByteArrayInputStream(bytes));
				}

				zip.closeEntry();
			}
			zip.close();
		} catch(IOException e)
		{

		}

		NBTTagList blocks = worldData.getCompoundTag("blockCollection").getCompoundTag("blocks").getCompoundTag("blocksCompressed").getTagList("data_bytes", 1);
		
		for(int i = 0; i < blocks.tagCount(); i++)
		{
			System.out.println(blocks.get(i));
		}
		
//		if(worldData != null)
//		{
//			NBTTagCompound blockCollection = worldData.getCompoundTag("blockCollection");
//			int xSize = blockCollection.getInteger("width");
//			int ySize = blockCollection.getInteger("height");
//			int zSize = blockCollection.getInteger("length");
//			
//			NBTTagList blockDataArray = worldData.getTagList("mapping", 8);
//			List<CustomEntry<Integer, String>> blockDataIds = new ArrayList<CustomEntry<Integer, String>>();
//			for(int i = 0; i < blockDataArray.tagCount(); i++)
//			{
//				blockDataIds.add(new CustomEntry<Integer, String>(i, blockDataArray.getStringTagAt(i)));
//			}
//
//			NBTTagList metadata = blockCollection.getTagList("metadata", 3);
//			int index = 0;
//			List<Integer> blockArray = new ArrayList<Integer>();
//			for(int i = 0; i < blockCollection.tagCount(); i++)
//			{
//				blockArray.add(blockCollection.getIntAt(i));
//			}
//
//			for(int yOff = 0; yOff < ySize; yOff++)
//			{
//				for(int xOff = (xSize / 2) - xSize; xOff < (xSize / 2); xOff++)
//				{
//					for(int zOff = (zSize / 2) - zSize; zOff < (zSize / 2); zOff++)
//					{
//						int id = blockArray.get(index);
//						String blockData = "";
//						for(CustomEntry<Integer, String> entry : blockDataIds)
//						{
//							if(entry.getKey() == id)
//							{
//								blockData = entry.getValue();
//								break;
//							}
//						}
//						String[] dataParts = blockData.split(":");
//						Block b = Block.REGISTRY.getObject(new ResourceLocation(dataParts[0], dataParts[1]));
//						SchematicBlock osb = new SchematicBlock(b.getStateFromMeta(Integer.parseInt(dataParts[2])), new BlockPos(xOff, yOff, zOff));
//						offsetBlocks.add(osb);
//						index++;
//					}
//				}
//			}
//
//			List<NBTTagCompound> entities = new ArrayList<NBTTagCompound>();
//			JsonArray entArray = json.get("Entities").getAsJsonArray();
//
//			for(JsonElement entNBT : entArray)
//			{
//				try
//				{
//					entities.add((NBTTagCompound) JsonToNBT.getTagFromJson(entNBT.getAsString()));
//				} catch(NBTException e)
//				{
//
//				}
//			}
//
//			JsonArray teArray = json.get("TileEntities").getAsJsonArray();
//			for(JsonElement i : teArray)
//			{
//				for(Entry<String, JsonElement> obj : i.getAsJsonObject().entrySet())
//				{
//					String teData = obj.getKey();
//					for(JsonElement ids : obj.getValue().getAsJsonArray())
//					{
//						int id = ids.getAsInt();
//						SchematicBlock osb = offsetBlocks.get(id);
//						SchematicTileEntity oste = OffsetBlockToTileEntity(osb, teData);
//						if(oste != null)
//							offsetBlocks.set(id, oste);
//					}
//				}
//			}
//
//			for(int i = offsetBlocks.size() - 1; i >= 0; i--)
//			{
//				SchematicBlock osb = offsetBlocks.get(i);
//				if(osb.getBlockState().getBlock().equals(Blocks.AIR) && !includeAirBlocks)
//					offsetBlocks.remove(i);
//			}
//
//			return new Schematic(offsetBlocks, entities, xSize, ySize, zSize, includeAirBlocks);
//
//		}

		return null;
	}

	public static byte[] completeByteArray(InputStream inputStream)
	{
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		int aByte;

		try
		{
			while((aByte = inputStream.read()) >= 0)
			{
				byteArrayOutputStream.write(aByte);
			}
		} catch(Exception ignored)
		{
			return null;
		}

		return byteArrayOutputStream.toByteArray();
	}

	@Override
	public File getCompatModFolder()
	{
		return new File(FileUtil.folder, "Recurrent Complex");
	}

	@Override
	public String getFileExtension()
	{
		return ".rcst";
	}

}
