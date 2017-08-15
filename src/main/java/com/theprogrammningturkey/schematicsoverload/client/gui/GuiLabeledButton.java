package com.theprogrammningturkey.schematicsoverload.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;

public class GuiLabeledButton extends GuiButton
{
	private String label;

	public GuiLabeledButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, String label)
	{
		super(buttonId, x, y, widthIn, heightIn, buttonText);
		this.label = label;
	}

	public void drawButton(Minecraft mc, int mouseX, int mouseY)
	{
		super.drawButton(mc, mouseX, mouseY);
		if(this.visible)
		{
			FontRenderer fontrenderer = mc.fontRendererObj;
			this.drawCenteredString(fontrenderer, this.label, this.xPosition - (fontrenderer.getStringWidth(this.label) / 2), this.yPosition + (this.height - 8) / 2, 14737632);
		}
	}
}