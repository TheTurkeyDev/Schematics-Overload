package com.theprogrammningturkey.schematicsoverload.client.gui;

import net.minecraft.client.Minecraft;

public class GuiToggleButton extends GuiLabeledButton
{
	private boolean toggle;

	public GuiToggleButton(int buttonId, int x, int y, int widthIn, int heightIn, String label, boolean enabled)
	{
		super(buttonId, x, y, widthIn, heightIn, enabled ? "True" : "False", label);
		this.toggle = enabled;
	}

	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
	{

		boolean clicked = super.mousePressed(mc, mouseX, mouseY);

		if(clicked)
		{
			toggle = !toggle;
			displayString = (toggle ? "True" : "False");
		}

		return clicked;
	}

	public boolean isToggledOn()
	{
		return this.toggle;
	}

}
