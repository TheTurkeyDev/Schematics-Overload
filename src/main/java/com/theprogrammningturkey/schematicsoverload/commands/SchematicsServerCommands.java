package com.theprogrammningturkey.schematicsoverload.commands;

import java.util.ArrayList;
import java.util.List;

import com.theprogrammningturkey.schematicsoverload.SchematicsCore;
import com.theprogrammningturkey.schematicsoverload.blocks.SchematicBlock;
import com.theprogrammningturkey.schematicsoverload.compatibility.ISchematicCompat;
import com.theprogrammningturkey.schematicsoverload.compatibility.SchematicsManager;
import com.theprogrammningturkey.schematicsoverload.util.Schematic;
import com.theprogrammningturkey.schematicsoverload.util.SchematicUtil;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class SchematicsServerCommands extends CommandBase
{
	private List<String> aliases;
	List<String> tab;

	public SchematicsServerCommands()
	{
		aliases = new ArrayList<String>();
		aliases.add("schematicsoverload");
		aliases.add("Schematicsoverload");
		aliases.add("so");

		tab = new ArrayList<String>();
		tab.add("schematic");
		tab.add("reload");
		tab.add("version");
	}

	@Override
	public String getCommandName()
	{
		return "SchematicsOverload";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender)
	{
		return "/SchematicsOverload <load/create/reload/version>";
	}

	@Override
	public List<String> getCommandAliases()
	{
		return aliases;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if(args.length == 0)
		{
			invalidArguments(sender);
			return;
		}

		if(args[0].equalsIgnoreCase("reload"))
		{
			SchematicsManager.loadSchematics();
			sender.addChatMessage(new TextComponentString("Reloaded"));
		}
		else if(args[0].equalsIgnoreCase("version"))
		{
			sender.addChatMessage(new TextComponentString("Schematics Overload Version " + SchematicsCore.VERSION));
		}
		else if(args[0].equalsIgnoreCase("listSchematics"))
		{
			StringBuilder sb = new StringBuilder();
			for(String s : SchematicsManager.getLoadedSchematicNames())
			{
				sb.append(s);
				sb.append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
			sender.addChatMessage(new TextComponentString("Loaded Schematics: " + sb.toString()));
		}
		else if(args[0].equalsIgnoreCase("schematic"))
		{
			if(Minecraft.getMinecraft().isSingleplayer())
			{
				if(sender instanceof EntityPlayer)
				{
					World world = Minecraft.getMinecraft().getIntegratedServer().getEntityWorld();
					EntityPlayer player = (EntityPlayer) sender;
					if(player.capabilities.isCreativeMode)
					{
						if(args[1].equalsIgnoreCase("setPoint"))
						{
							if(args.length < 3)
							{
								invalidArguments(sender);
								return;
							}

							if(args[2].equalsIgnoreCase("1"))
							{
								SchematicUtil.pos1 = new BlockPos((int) player.posX, (int) player.posY - 1, (int) player.posZ);
								sender.addChatMessage(new TextComponentString("Point 1 set"));
							}
							else if(args[2].equalsIgnoreCase("2"))
							{
								SchematicUtil.pos2 = new BlockPos((int) player.posX, (int) player.posY - 1, (int) player.posZ);
								sender.addChatMessage(new TextComponentString("Point 2 set"));
							}
						}
						else if(args[1].equalsIgnoreCase("create"))
						{
							if(args.length < 3)
							{
								invalidArguments(sender);
								return;
							}

							if(SchematicUtil.pos1 == null || SchematicUtil.pos2 == null)
							{
								sender.addChatMessage(new TextComponentString("Both points are not set!"));
								return;
							}
							ISchematicCompat compat = SchematicsManager.getSchematicCompatForName(args.length > 3 ? args[3] : SchematicsCore.MODID);
							String fileName = args[2];
							if(fileName.contains("."))
							{
								if(!fileName.substring(fileName.indexOf(".") - 1).equals(compat.getFileExtension()))
								{
									fileName = fileName.substring(0, fileName.indexOf(".")) + compat.getFileExtension();
								}
							}
							else
							{
								fileName += compat.getFileExtension();
							}

							compat.createSchematic(fileName, world);
							sender.addChatMessage(new TextComponentString("Schematic file " + fileName + " created and put into " + compat.getCompatModFolder().getName() + " folder!"));
							SchematicUtil.pos1 = null;
							SchematicUtil.pos2 = null;
						}
						else if(args[1].equalsIgnoreCase("replace"))
						{
							if(args.length < 4)
							{
								invalidArguments(sender);
								return;
							}

							IBlockState oldState;
							IBlockState newState;
							try
							{
								String[] oldBlockData = args[2].split(":");
								String[] newBlockData = args[3].split(":");
								Block oldBlock = SchematicUtil.getBlock(oldBlockData[0], oldBlockData[1]);
								Block newBlock = SchematicUtil.getBlock(newBlockData[0], newBlockData[1]);

								if(oldBlockData.length > 2)
									oldState = oldBlock.getStateFromMeta(Integer.parseInt(oldBlockData[2]));
								else
									oldState = oldBlock.getDefaultState();

								if(newBlockData.length > 2)
									newState = newBlock.getStateFromMeta(Integer.parseInt(newBlockData[2]));
								else
									newState = newBlock.getDefaultState();
							} catch(Exception e)
							{
								sender.addChatMessage(new TextComponentString("The blocks you have entered are invalid. Use the format \"(Mod):(Block):(Meta)\". Meta is optional"));
								return;
							}

							SchematicUtil.replaceBlocksInSelection(world, oldState, newState);
						}
						else if(args[1].equalsIgnoreCase("spawn"))
						{
							if(args.length < 2)
							{
								invalidArguments(sender);
								return;
							}
							Schematic schematic = SchematicsManager.getSchematic(args[2]);
							if(schematic != null)
							{
								BlockPos playerPos = player.getPosition();
								for(SchematicBlock sb : schematic.getBlocks())
								{
									world.setBlockState(playerPos.add(sb.getBlockOffSet()), sb.getBlockState());
								}
							}
							else
							{
								sender.addChatMessage(new TextComponentString("Sorry, no schematic is loaded with that name!"));
							}
						}
					}
					else
					{
						sender.addChatMessage(new TextComponentString("Sorry, you need to be in creative to use this command"));
					}
				}
			}
			else
			{
				sender.addChatMessage(new TextComponentString("Sorry, but this command only works in single player"));
			}
		}
		else if(args[0].equalsIgnoreCase("spawnSchematic"))
		{

		}
		else if(args[0].equalsIgnoreCase("test"))
		{

		}
		else
		{
			invalidArguments(sender);
		}
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return true;
	}

	@Override
	public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos)
	{
		if(args.length == 0)
			return tab;
		return null;
	}

	public void invalidArguments(ICommandSender sender)
	{
		sender.addChatMessage(new TextComponentString("Invalid arguments for the command"));
	}
}