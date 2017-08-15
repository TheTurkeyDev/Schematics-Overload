package com.theprogrammningturkey.schematicsoverload.client.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import com.theprogrammningturkey.schematicsoverload.SchematicsCore;
import com.theprogrammningturkey.schematicsoverload.compatibility.ISchematicCompat;
import com.theprogrammningturkey.schematicsoverload.compatibility.SchematicsManager;
import com.theprogrammningturkey.schematicsoverload.util.SchematicCreationSettings;
import com.theprogrammningturkey.schematicsoverload.util.SchematicUtil;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SchematicCreationGui extends GuiScreen
{
	private GuiTextField nameField;

	private GuiToggleButton includeEntitiesButton;
	private EntityPlayer player;

	public SchematicCreationGui(EntityPlayer player)
	{
		this.player = player;
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	public void initGui()
	{
		this.buttonList.clear();
		Keyboard.enableRepeatEvents(true);
		int i = this.width / 2;
		this.nameField = new GuiTextField(0, this.fontRendererObj, i - 70, 10, 140, 12);
		this.nameField.setTextColor(-1);
		this.nameField.setDisabledTextColour(-1);
		this.nameField.setEnableBackgroundDrawing(true);
		this.nameField.setMaxStringLength(100);
		this.nameField.setText("Schematic Name");

		this.buttonList.add(new GuiButton(0, i - 50, this.height - 40, 100, 20, "Create"));
		this.buttonList.add(includeEntitiesButton = new GuiToggleButton(1, i, 50, 70, 20, "Include Entities: ", false));
	}

	public void onGuiClosed()
	{
		super.onGuiClosed();
		Keyboard.enableRepeatEvents(false);
	}

	protected void actionPerformed(GuiButton button)
	{
		if(button.enabled)
		{
			if(button.id == 0)
			{
				ISchematicCompat compat = SchematicsManager.getSchematicCompatForName(SchematicsCore.MODID);
				String fileName = nameField.getText();
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
				
				SchematicCreationSettings settings = new SchematicCreationSettings();
				settings.includeEntities = includeEntitiesButton.isToggledOn();
				
				compat.createSchematic(fileName, player.worldObj, settings);
				player.addChatMessage(new TextComponentString("Schematic file " + fileName + " created and put into " + compat.getCompatModFolder().getName() + " folder!"));
				SchematicUtil.pos1 = null;
				SchematicUtil.pos2 = null;
			}
		}
	}

	protected void keyTyped(char p_73869_1_, int p_73869_2_) throws IOException
	{
		if(!this.nameField.textboxKeyTyped(p_73869_1_, p_73869_2_))
			super.keyTyped(p_73869_1_, p_73869_2_);
	}

	protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_) throws IOException
	{
		super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
		this.nameField.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
	}

	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_)
	{
		this.nameField.drawTextBox();
		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
	}
}