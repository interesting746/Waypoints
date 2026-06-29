package com.interesting12345.client.gui;

import com.interesting12345.client.model.Waypoint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ObjectSelectionList;

import java.util.List;
import java.util.function.Consumer;

public class WaypointListWidget extends ObjectSelectionList<WaypointEntry> {

    private Consumer<Waypoint> selectionChangedListener;

    public WaypointListWidget(Minecraft minecraft, int width, int height, int top, int bottom, List<Waypoint> waypoints) {
        super(minecraft, width, height, top, bottom);

        for (Waypoint waypoint : waypoints) {

            WaypointEntry entry = new WaypointEntry(waypoint, this);

            addEntry(entry, 40);

        }
    }

    public void reloadWaypoints(List<Waypoint> waypoints) {

        clearEntries();
        for (Waypoint waypoint : waypoints) {

            WaypointEntry entry = new WaypointEntry(waypoint, this);
            addEntry(entry, 40);
        }
    }

    public void setSelectionChangedListener(Consumer<Waypoint> listener) {
        this.selectionChangedListener = listener;
    }

    @Override
    public void setSelected(WaypointEntry entry) {

        super.setSelected(entry);

        selectionChangedListener.accept(entry.getWaypoint());
        /*
        if (selectionChangedListener != null) {
            selectionChangedListener.accept(
                    entry == null ? null : entry.getWaypoint()
            );
        }

         */
    }
}
