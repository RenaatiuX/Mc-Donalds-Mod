package com.rena.mcdonalds.client.render.blocks;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rena.mcdonalds.common.tileentities.ChoppingBoardTe;
import com.rena.mcdonalds.common.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class ChoppingBoardRenderer extends TileEntityRenderer<ChoppingBoardTe> {
    public ChoppingBoardRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(ChoppingBoardTe te, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        if (te != null && !te.getInv().getStackInSlot(0).isEmpty()){
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
            Minecraft mc = Minecraft.getInstance();
            RenderUtil.renderItem(te.getInv().getStackInSlot(0), new double[]{0.5d,0.07d,0.5d}, Vector3f.YP.rotation(rotation), matrixStackIn, bufferIn, combinedOverlayIn, RenderUtil.getLightLevel(mc.player.world, te.getPos()), 0.5f);
        }
    }
}
