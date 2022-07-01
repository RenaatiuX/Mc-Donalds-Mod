package com.rena.mcdonalds.client.render.blocks;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rena.mcdonalds.common.tileentities.ChoppingBoardTe;
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
            renderItem(te.getInv().getStackInSlot(0), new double[]{0.5d,0.07d,0.5d}, Vector3f.YP.rotation(rotation), matrixStackIn, bufferIn, combinedOverlayIn, getLightLevel(mc.player.world, te.getPos()), 0.5f);
        }
    }

    public static int getLightLevel(World world, BlockPos pos) {
        int bLight = world.getLightFor(LightType.BLOCK, pos);
        int sLight = world.getLightFor(LightType.SKY, pos);
        return LightTexture.packLight(bLight, sLight);
    }
    public static void renderItem(ItemStack stack, double[] translation, Quaternion rotation, MatrixStack matrixStack,
                                  IRenderTypeBuffer buffer, int combinedOverlay, int lightLevel, float scale) {
        Minecraft mc = Minecraft.getInstance();
        matrixStack.push();
        matrixStack.translate(translation[0], translation[1], translation[2]);
        matrixStack.rotate(rotation);
        matrixStack.rotate(Vector3f.XN.rotationDegrees(90));
        matrixStack.scale(scale, scale, scale);

        IBakedModel model = mc.getItemRenderer().getItemModelWithOverrides(stack, null, null);
        mc.getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.NONE, true, matrixStack, buffer,
                lightLevel, combinedOverlay, model);
        matrixStack.pop();
    }
}
