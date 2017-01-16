package com.theprogrammningturkey.schematicsoverload.proxy;

import com.theprogrammningturkey.schematicsoverload.listener.WorldRenderListener;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy
{

	@Override
	public boolean isClient()
	{
		return true;
	}

	public void registerRenderings()
	{
	}

	public void registerEvents()
	{
		MinecraftForge.EVENT_BUS.register(new WorldRenderListener());
	}

	@Override
	public EntityPlayer getClientPlayer()
	{
		return Minecraft.getMinecraft().thePlayer;
	}
}
