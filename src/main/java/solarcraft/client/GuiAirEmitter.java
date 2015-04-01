package solarcraft.client;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import solarcraft.block.tile.TileEntityAirEmitter;
import solarcraft.inventory.ContainerAirEmitter;

public class GuiAirEmitter extends GuiContainer
{
    private static final ResourceLocation guiTextures = new ResourceLocation("solarcraft", "textures/gui/air_emitter_gui.png");
    TileEntityAirEmitter airTile;
    
	public GuiAirEmitter(EntityPlayer player, TileEntityAirEmitter tile)
	{
		super(new ContainerAirEmitter(player, tile));
		this.airTile = tile;
	}

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
    {
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
        this.fontRendererObj.drawString("Air: " + airTile.airTime, 64, 60, 4210752);
    }
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
	{
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(guiTextures);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        
        if(airTile.airTime > 0)
        {
            this.drawTexturedModalRect(k + 56, l + 40, 0, 168, MathHelper.floor_float(64F * (float)airTile.airTime/600F), 16);
        }
	}
}
