package com.interesting12345.client.utils;

import com.interesting12345.client.model.Waypoint;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.ShapeRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class WaypointRenderer {

    public static void render(WorldRenderContext context) {

        for (Waypoint waypoint : WaypointManager.getWaypoints()) {

            renderWaypoint(context, waypoint);

        }
    }

    public static void renderWaypoint(WorldRenderContext context, Waypoint waypoint) {

        PoseStack poseStack = context.matrices();
        VertexConsumer buffer = context.consumers().getBuffer(RenderTypes.lines());
        VoxelShape cube = Shapes.box(0, 0, 0, 1, 1, 1);
        Camera camera = context.gameRenderer().getMainCamera();

        poseStack.pushPose();
        //poseStack.translate(0, 100, 0);
        poseStack.translate(
                waypoint.x() - camera.position().x(),
                waypoint.y() - camera.position().y(),
                waypoint.z() - camera.position().z()
        );

        ShapeRenderer.renderShape(poseStack, buffer, cube, 0, 0, 0, 0xFF0000FF, 2.0F);
        poseStack.popPose();
    }
}
