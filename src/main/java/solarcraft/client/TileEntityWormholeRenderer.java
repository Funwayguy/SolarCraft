package solarcraft.client;

import java.nio.FloatBuffer;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

public class TileEntityWormholeRenderer extends TileEntitySpecialRenderer
{
    private static final ResourceLocation field_147529_c = new ResourceLocation("textures/environment/end_sky.png"); // Background
    private static final ResourceLocation field_147526_d = new ResourceLocation("textures/entity/end_portal.png"); // Starry Overlay
    private static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("solarcraft", "models/box.obj"));
    private static final Random field_147527_e = new Random(31100L);
    FloatBuffer buffer = GLAllocation.createDirectFloatBuffer(16);
    
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTick)
	{
		Minecraft mc = Minecraft.getMinecraft();
		float zSpin = 0F;//(Minecraft.getSystemTime()/1000F)%360F * 10F;
		float hSpin = mc.thePlayer.rotationYawHead * -1F - (mc.thePlayer.rotationYawHead - mc.thePlayer.prevRotationYawHead) * partialTick;
		float vSpin = mc.thePlayer.rotationPitch * 1F - (mc.thePlayer.rotationPitch - mc.thePlayer.prevRotationPitch) * partialTick;
		boolean useModel = false;
		
		GL11.glPushMatrix();
		if(useModel)
		{
			GL11.glTranslated(x + 0.5D, y + 0.5D, z + 0.5D);
			GL11.glRotatef(hSpin, 0F, 1F, 0F);
			GL11.glRotatef(vSpin, 1F, 0F, 0F);
			GL11.glRotatef(zSpin, 0F, 0F, 1F);
			GL11.glScaled(5D, 5D, 0.5D);
		} else
		{
			hSpin = 0F;
			vSpin = 0F;
		}
        GL11.glDisable(GL11.GL_LIGHTING);
        field_147527_e.setSeed(31100L);
        
        int start = 0;
        int end = 2;
        
        for (int i = start; i < end; ++i)
        {
            GL11.glPushMatrix();
            float f5 = (float)(end - i);
            float f6 = 0.0625F;
            float f7 = 1.0F / (f5 + 1.0F);

            if (i == start)
            {
            	this.bindTexture(field_147529_c);
                f7 = 0F;
                f5 = 65.0F;
                f6 = 0.125F;
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            }

            if (i == start + 1)
            {
                this.bindTexture(field_147526_d);
                this.bindTexture(field_147526_d);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
                f6 = 0.5F;
            }
            GL11.glTexGeni(GL11.GL_S, GL11.GL_TEXTURE_GEN_MODE, GL11.GL_OBJECT_LINEAR);
            GL11.glTexGeni(GL11.GL_T, GL11.GL_TEXTURE_GEN_MODE, GL11.GL_OBJECT_LINEAR);
            GL11.glTexGeni(GL11.GL_R, GL11.GL_TEXTURE_GEN_MODE, GL11.GL_OBJECT_LINEAR);
            GL11.glTexGeni(GL11.GL_Q, GL11.GL_TEXTURE_GEN_MODE, GL11.GL_EYE_LINEAR);
            GL11.glTexGen(GL11.GL_S, GL11.GL_OBJECT_PLANE, this.func_147525_a(1.0F, 0.0F, 0.0F, 0.0F));
            GL11.glTexGen(GL11.GL_T, GL11.GL_OBJECT_PLANE, this.func_147525_a(0.0F, 0.0F, 1.0F, 0.0F));
            GL11.glTexGen(GL11.GL_R, GL11.GL_OBJECT_PLANE, this.func_147525_a(0.0F, 0.0F, 0.0F, 1.0F));
            GL11.glTexGen(GL11.GL_Q, GL11.GL_EYE_PLANE, this.func_147525_a(0.0F, 1.0F, 0.0F, 0.0F));
            GL11.glEnable(GL11.GL_TEXTURE_GEN_S);
            GL11.glEnable(GL11.GL_TEXTURE_GEN_T);
            GL11.glEnable(GL11.GL_TEXTURE_GEN_R);
            GL11.glEnable(GL11.GL_TEXTURE_GEN_Q);
            GL11.glPopMatrix();
            GL11.glMatrixMode(GL11.GL_TEXTURE);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glTranslatef(0.0F, (float)(Minecraft.getSystemTime() % 50000L) / 50000.0F, 0.0F);
            GL11.glScalef(f6, f6, f6);
            GL11.glTranslatef(0.5F, 0.5F, 0.0F);
            if(useModel)
            {
            	GL11.glRotatef(-hSpin, 0F, 0F, 1F);
    			//GL11.glRotatef(-zSpin, 0F, 1F, 0F);
    			//GL11.glRotatef(-vSpin, 1F, 0F, 0F);
            }
            //GL11.glRotatef((float)(i * i * 4321 + i * 9) * 2.0F, 0.0F, 0.0F, 1.0F);
            GL11.glTranslatef(-0.5F, -0.5F, 0.0F);
            
            float R = 1F;//field_147527_e.nextFloat() * 0.5F + 0.1F;
            float G = 1F;//field_147527_e.nextFloat() * 0.5F + 0.4F;
            float B = 1F;//field_147527_e.nextFloat() * 0.5F + 0.5F;
            float A = 1F;

            if (i == start)
            {
                B = 1F;
                G = 1F;
                R = 1F;
                A = 1F;
            }
            
            if(useModel)
            {
            	GL11.glColor4f(R * f7, G * f7, B * f7, A);
            	model.renderAll();
            } else
            {
	            Tessellator tessellator = Tessellator.instance;
	            tessellator.startDrawingQuads();
	            
	            tessellator.setColorRGBA_F(R * f7, G * f7, B * f7, A);
	            
	            Vec3 point00 = Vec3.createVectorHelper(-0.5D, 0D, -0.5D);
	            point00.rotateAroundY(hSpin);
	            point00.xCoord += x + 0.5D;
	            point00.zCoord += z + 0.5D;
	            
	            Vec3 point10 = Vec3.createVectorHelper(0.5D, 0D, -0.5D);
	            point10.rotateAroundY(hSpin);
	            point10.xCoord += x + 0.5D;
	            point10.zCoord += z + 0.5D;
	            
	            Vec3 point01 = Vec3.createVectorHelper(-0.5D, 0D, 0.5D);
	            point01.rotateAroundY(hSpin);
	            point01.xCoord += x + 0.5D;
	            point01.zCoord += z + 0.5D;
	            
	            Vec3 point11 = Vec3.createVectorHelper(0.5D, 0D, 0.5D);
	            point11.rotateAroundY(hSpin);
	            point11.xCoord += x + 0.5D;
	            point11.zCoord += z + 0.5D;
	            
	            tessellator.addVertex(point00.xCoord, y + 1D, point00.zCoord);
	            tessellator.addVertex(point01.xCoord, y + 1D, point01.zCoord);
	            tessellator.addVertex(point11.xCoord, y + 1D, point11.zCoord);
	            tessellator.addVertex(point10.xCoord, y + 1D, point10.zCoord);
	            
	            tessellator.addVertex(point10.xCoord, y + 0D, point10.zCoord);
	            tessellator.addVertex(point11.xCoord, y + 0D, point11.zCoord);
	            tessellator.addVertex(point01.xCoord, y + 0D, point01.zCoord);
	            tessellator.addVertex(point00.xCoord, y + 0D, point00.zCoord);
	            
	            tessellator.addVertex(point00.xCoord, y + 0D, point00.zCoord);
	            tessellator.addVertex(point01.xCoord, y + 0D, point01.zCoord);
	            tessellator.addVertex(point01.xCoord, y + 1D, point01.zCoord);
	            tessellator.addVertex(point00.xCoord, y + 1D, point00.zCoord);
	            
	            tessellator.addVertex(point10.xCoord, y + 1D, point10.zCoord);
	            tessellator.addVertex(point11.xCoord, y + 1D, point11.zCoord);
	            tessellator.addVertex(point11.xCoord, y + 0D, point11.zCoord);
	            tessellator.addVertex(point10.xCoord, y + 0D, point10.zCoord);
	            
	            tessellator.addVertex(point00.xCoord, y + 1D, point00.zCoord);
	            tessellator.addVertex(point10.xCoord, y + 1D, point10.zCoord);
	            tessellator.addVertex(point10.xCoord, y + 0D, point10.zCoord);
	            tessellator.addVertex(point00.xCoord, y + 0D, point00.zCoord);
	            
	            tessellator.addVertex(point01.xCoord, y + 0D, point01.zCoord);
	            tessellator.addVertex(point11.xCoord, y + 0D, point11.zCoord);
	            tessellator.addVertex(point11.xCoord, y + 1D, point11.zCoord);
	            tessellator.addVertex(point01.xCoord, y + 1D, point01.zCoord);
	            
	            tessellator.draw();
            }
            
            GL11.glPopMatrix();
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
        }

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_GEN_S);
        GL11.glDisable(GL11.GL_TEXTURE_GEN_T);
        GL11.glDisable(GL11.GL_TEXTURE_GEN_R);
        GL11.glDisable(GL11.GL_TEXTURE_GEN_Q);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

    private FloatBuffer func_147525_a(float p_147525_1_, float p_147525_2_, float p_147525_3_, float p_147525_4_)
    {
        this.buffer.clear();
        this.buffer.put(p_147525_1_).put(p_147525_2_).put(p_147525_3_).put(p_147525_4_);
        this.buffer.flip();
        return this.buffer;
    }
}
