package com.interesting12345.client.gui;


import com.interesting12345.client.utils.WaypointManager;
import com.interesting12345.client.model.Waypoint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;

public class WaypointDetailsPanel {

    private final WaypointListWidget list;

    private final int x;
    private final int y;
    private final int w;
    private final int h;

    private final Font font = Minecraft.getInstance().font;

    public Button deleteButton;
    public EditBox renameInput;
    public Button saveButton;
    public EditBox inputX;
    public EditBox inputY;
    public EditBox inputZ;

    public WaypointDetailsPanel(int x, int y, int width, int height, WaypointListWidget list) {

        this.x = x;
        this.y = y;
        this.h = height;
        this.w = width;
        this.list = list;
    }

    public void render(GuiGraphics graphics) {

        Waypoint currentSelected = null;

        graphics.fill(x, y, w + x, h + y, 0x8340485D);
        graphics.renderOutline(x, y, w, h, 0xFFFFFFFF);

        graphics.drawString(font, "Waypoint Details", x + 5, y + 5, 0xFFFFFFFF, false);

        graphics.drawString(font, "x:", x + 15, y + 55, 0xFFFFFFFF, false);
        graphics.drawString(font, "y:", x + 65, y + 55, 0xFFFFFFFF, false);
        graphics.drawString(font, "z:", x + 115, y + 55, 0xFFFFFFFF, false);


        deleteButton.active = list.getSelected() != null;
        saveButton.active = !(renameInput.getValue().isEmpty() || inputX.getValue().isEmpty() || inputY.getValue().isEmpty() || inputZ.getValue().isEmpty());

        if (list.getSelected() == null) {
            saveButton.setMessage(Component.literal("Save New"));
        } else {
            saveButton.setMessage(Component.literal("Save"));
        }

        try {
            currentSelected = list.getSelected().getWaypoint();
        } catch (NullPointerException e) {
            graphics.drawString(font, Component.literal("Nothing is selected"), x + 15, y + 15 + font.lineHeight + 5, 0xFFFFFFFF, false);
            return;
        }

        if (currentSelected != null) {
            graphics.drawString(font, Component.literal("Selected: " + currentSelected.name()), x + 15, y + 15 + font.lineHeight + 5, 0xFFFFFFFF, false);
        }
    }

    protected void init() {

        deleteButton = Button.builder(Component.literal("Delete"),  btn -> {

            WaypointManager.removeSelected(list);

            renameInput.setValue("");
            inputX.setValue("");
            inputY.setValue("");
            inputZ.setValue("");

        }).bounds(x + 15, y + 110, 100, 20).build();

        saveButton = Button.builder(Component.literal("Save"), btn -> {

            String newX = inputX.getValue();
            String newY = inputY.getValue();
            String newZ = inputZ.getValue();
            int intX;
            int intY;
            int intZ;

            try {

                newX = newX.replace(",", "");
                newY = newY.replace(",", "");
                newZ = newZ.replace(",", "");
                newX = newX.replace(" ", "");
                newY = newY.replace(" ", "");
                newZ = newZ.replace(" ", "");
                intX = Integer.parseInt(newX);
                intY = Integer.parseInt(newY);
                intZ = Integer.parseInt(newZ);

                if (!renameInput.getValue().isEmpty()) {
                    WaypointManager.saveSelected(list, getRenameInput(), intX, intY, intZ);
                    renameInput.setValue("");
                    inputX.setValue("");
                    inputY.setValue("");
                    inputZ.setValue("");
                }

            } catch (NumberFormatException e) {
                assert true;
            }


        }).bounds(x + 15, y + 140, 100, 20).build();


        inputX = new EditBox(Minecraft.getInstance().font, x + 25, y + 50, 30, 20, Component.empty());
        inputY = new EditBox(Minecraft.getInstance().font, x + 75, y + 50, 30, 20, Component.empty());
        inputZ = new EditBox(Minecraft.getInstance().font, x + 125, y + 50, 30, 20, Component.empty());
        renameInput = new EditBox(Minecraft.getInstance().font, x + 20, y + 80, 150, 20, Component.empty());
    }

    public String getRenameInput() {
        return renameInput.getValue();
    }

    public void setWaypoint(Waypoint waypoint) {
        renameInput.setValue(waypoint.name());
        inputX.setValue(Integer.toString(waypoint.x()));
        inputY.setValue(Integer.toString(waypoint.y()));
        inputZ.setValue(Integer.toString(waypoint.z()));
    }
}