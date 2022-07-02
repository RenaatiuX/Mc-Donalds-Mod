package com.rena.mcdonalds.client.render.blocks;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rena.mcdonalds.McDonalds;
import com.rena.mcdonalds.client.model.block.TopElectricIron;
import com.rena.mcdonalds.common.tileentities.ElectricIronTe;
import com.rena.mcdonalds.common.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class ElectricIronRenderer extends TileEntityRenderer<ElectricIronTe> {

    private static final ResourceLocation MODEL_TEXTURE = McDonalds.modLoc("textures/block/top_electric_iron.png");
    private static final TopElectricIron MODEL = new TopElectricIron();

    public ElectricIronRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(ElectricIronTe te, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        if (te != null){
            Minecraft mc = Minecraft.getInstance();
            matrixStackIn.push();
            float rotation = 0;
            Direction facing = te.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING);
            switch (facing) {
                case EAST:
                    rotation = 90;
                    break;
                case SOUTH:
                    rotation = 180;
                    break;
                case WEST:
                    rotation = 270;
                    break;
                default:
                    break;
            }
            if (te.isClosing()){

            }
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(rotation));
            matrixStackIn.translate(0.1, 1.2f, 0);
            matrixStackIn.rotate(Vector3f.XN.rotationDegrees(te.getDegrees()));
            matrixStackIn.translate(-0.6, 0, 0.65);
            matrixStackIn.rotate(Vector3f.XN.rotationDegrees(180));
            MODEL.render(matrixStackIn, bufferIn.getBuffer(MODEL.getRenderType(MODEL_TEXTURE)), RenderUtil.getLightLevel(mc.world, te.getPos().up()), combinedOverlayIn, 1,1,1,1f);
            matrixStackIn.pop();
            if (!te.getInv().getStackInSlot(0).isEmpty()){
                RenderUtil.renderItem(te.getInv().getStackInSlot(0), new double[]{0.5, 1.01, 0.5}, Vector3f.YP.rotationDegrees(rotation), matrixStackIn, bufferIn, combinedOverlayIn, RenderUtil.getLightLevel(mc.world, te.getPos()), 0.5f);
            }
        }
    }
}
