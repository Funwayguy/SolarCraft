package solarcraft.client;

import java.util.ArrayList;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;
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
    protected void drawGuiContainerForegroundLayer(int mx, int my)
    {
        this.fontRendererObj.drawString(StatCollector.translateToLocalFormatted("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
        String oxygenTxt = StatCollector.translateToLocalFormatted("fluid.oxygen");
        this.fontRendererObj.drawString(oxygenTxt, 36, 24, 4210752);
        this.fontRendererObj.drawString("RF", 116, 24, 4210752);
        
        if(isWithin(mx, my, 16, 40, 64, 16))
        {
        	ArrayList<String> info = new ArrayList<String>();
        	info.add(airTile.airTime + " / " + airTile.getCapacity() + " mB");
        	this.drawHoveringText(info, mx - this.guiLeft, my - this.guiTop, this.fontRendererObj);
        } else if(isWithin(mx, my, 96, 40, 64, 16))
        {
        	ArrayList<String> info = new ArrayList<String>();
        	info.add(airTile.power + " / " + this.airTile.getMaxEnergyStored(ForgeDirection.NORTH) + " RF");
        	this.drawHoveringText(info, mx - this.guiLeft, my - this.guiTop, this.fontRendererObj);
        }
    }
    
    public boolean isWithin(int mx, int my, int startX, int startY, int sizeX, int sizeY)
    {
    	return mx - this.guiLeft >= startX && my - this.guiTop >= startY && mx - this.guiLeft < startX + sizeX && my - this.guiTop < startY + sizeY;
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
            this.drawTexturedModalRect(k + 16, l + 40, 0, 168, MathHelper.floor_float(64F * (float)airTile.airTime/(float)airTile.getCapacity()), 16);
        }
        
        if(airTile.power > 0)
        {
            this.drawTexturedModalRect(k + 96, l + 40, 64, 168, MathHelper.floor_float(64F * (float)airTile.power/(float)airTile.getMaxEnergyStored(ForgeDirection.NORTH)), 16);
        }
	}
}
