package com.theprogrammningturkey.schematicsoverload.commands;

import java.util.ArrayList;
import java.util.List;

import com.theprogrammningturkey.schematicsoverload.SchematicsCore;
import com.theprogrammningturkey.schematicsoverload.util.SchematicUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
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
		if(args.length > 0 && args[0].equalsIgnoreCase("reload"))
		{
			sender.addChatMessage(new TextComponentString("Reloaded"));
		}
		else if(args.length > 0 && args[0].equalsIgnoreCase("version"))
		{
			sender.addChatMessage(new TextComponentString("Schematics Overload Version " + SchematicsCore.VERSION));
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
						if(args.length >= 3)
						{
							if(args[1].equalsIgnoreCase("setPoint"))
							{
								if(args[2].equalsIgnoreCase("1"))
								{
									SchematicUtil.pos1 = new BlockPos((int) player.posX, (int) player.posY - 1, (int) player.posZ);
									sender.addChatMessage(new TextComponentString("Point 1 set"));
								}
								if(args[2].equalsIgnoreCase("2"))
								{
									SchematicUtil.pos2 = new BlockPos((int) player.posX, (int) player.posY - 1, (int) player.posZ);
									sender.addChatMessage(new TextComponentString("Point 2 set"));
								}
							}
							else if(args[1].equalsIgnoreCase("create"))
							{
								if(SchematicUtil.pos1 == null || SchematicUtil.pos2 == null)
								{
									sender.addChatMessage(new TextComponentString("Both points are not set!"));
									return;
								}
								// TODO Create schematic
								sender.addChatMessage(new TextComponentString("Schematic file named " + (args[2].endsWith(".ccs") ? args[2] : args[2] + ".ccs") + " created!"));
								SchematicUtil.pos1 = null;
								SchematicUtil.pos2 = null;
							}
						}
						else
						{
							sender.addChatMessage(new TextComponentString("invalid arguments"));
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
		else if(args[0].equalsIgnoreCase("test"))
		{

		}
		else
		{
			sender.addChatMessage(new TextComponentString("Invalid arguments for the command"));
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

}