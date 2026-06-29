package com.interesting12345.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import static com.interesting12345.client.utils.WaypointManager.WAYPOINTS;

public class WaypointConfigScreen extends Screen {

    public Screen parent;

    private WaypointListWidget waypointList;
    private WaypointDetailsPanel detailsPanel;

    public WaypointConfigScreen(Component title, Screen parent) {
        super(title);
        this.parent = parent;
    }

    public WaypointConfigScreen(Component title) {
        super(title);
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(this.parent);
    }

    @Override
    protected void init() {

        int margin = 30;
        int topM = 45;
        int listW = 250;
        int listH = this.height - 55;
        int panelX = listW + 20;
        int panelH = (this.height / 4) * 3;
        int panelW = this.width - listW - 30;

        if (panelW > 400) panelW = 400;

        waypointList = new WaypointListWidget(this.minecraft, listW, listH, topM, this.height - 50, WAYPOINTS);
        detailsPanel = new WaypointDetailsPanel(panelX, topM, panelW, panelH, waypointList);

        waypointList.setSelectionChangedListener(waypoint -> {
            detailsPanel.setWaypoint(waypoint);
        });

        addRenderableWidget(waypointList);

        detailsPanel.init();

        addRenderableWidget(detailsPanel.deleteButton);
        addRenderableWidget(detailsPanel.renameInput);
        addRenderableWidget(detailsPanel.saveButton);
        addRenderableWidget(detailsPanel.inputX);
        addRenderableWidget(detailsPanel.inputY);
        addRenderableWidget(detailsPanel.inputZ);

    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {

        super.render(graphics, mouseX, mouseY, delta);

        graphics.drawString(this.font, "Configure Waypoints", 20, 40 - this.font.lineHeight - 10, 0xFFFFFFFF, true);

        detailsPanel.render(graphics);
    }

    @Override
    public void resize(int i, int j) {
        super.resize(i, j);

        // implement state saving later!!!!!!!!!!!!!!!!

    }
}

