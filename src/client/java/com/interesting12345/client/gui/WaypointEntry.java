package com.interesting12345.client.gui;

import com.interesting12345.client.model.Waypoint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

public class WaypointEntry extends ObjectSelectionList.Entry<WaypointEntry> {

    private final Waypoint waypoint;
    private final WaypointListWidget list;

    public WaypointEntry(Waypoint waypoint, WaypointListWidget list) {
        this.list = list;
        this.waypoint = waypoint;
    }

    @Override
    public Component getNarration() {
        return Component.literal("Waypoint" + waypoint.name() + " at x, " + waypoint.x() + ", y, " + waypoint.y() + ", z, " + waypoint.z());
    }

    @Override
    public void renderContent(GuiGraphics graphics, int mouseX, int mouseY, boolean hovered, float partialTick) {

        if (list.getSelected() == this) {
            graphics.fill(getX() + 1, getY() + 1, getX() + getWidth() - 1, getY() + getHeight() - 1, 0xFF40485D);
        } else {
            graphics.fill(getX() + 1, getY() + 1, getX() + getWidth() - 1, getY() + getHeight() - 1, 0xFF262B37);
        }

        graphics.drawString(Minecraft.getInstance().font, Component.literal(waypoint.name()), getX() + 5, getY() + 5, 0xFFFFFFFF, false);
        graphics.drawString(Minecraft.getInstance().font, Component.literal("x: " + waypoint.x() + " y: " + waypoint.y() + " z: " + waypoint.z()),
                getX() + 5, getY() + Minecraft.getInstance().font.lineHeight + 10, 0xFFFFFFFF, false);

    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {

        list.setSelected(this);
        return true;
    }

    public Waypoint getWaypoint() {

        return waypoint;
    }
}
