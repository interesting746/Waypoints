package com.interesting12345.client.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.interesting12345.client.gui.WaypointListWidget;
import com.interesting12345.client.model.Waypoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static com.interesting12345.Waypoints.MOD_ID;

public class WaypointManager {

    public static final List<Waypoint> WAYPOINTS = new ArrayList<>();
    private static final Path FILE = Path.of("config", "waypoints.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void add(Waypoint waypoint) {
        WAYPOINTS.add(waypoint);
        save();
    }

    public static void save() {

        String json = GSON.toJson(WAYPOINTS);

        try {
            Files.writeString(FILE, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load() {

        Type waypointListType = new TypeToken<List<Waypoint>>() {}.getType();

        if (!Files.exists(FILE)) {
            return;
        }

        try {
            String json = Files.readString(FILE);
            List<Waypoint> loadedWaypoints = GSON.fromJson(json, waypointListType);
            if (loadedWaypoints != null) {
                WAYPOINTS.clear();
                WAYPOINTS.addAll(loadedWaypoints);
            }
            LOGGER.info("Loaded {} waypoints", WAYPOINTS.size());

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void removeSelected(WaypointListWidget list) {

        Waypoint currentSelected = null;

        try {
            currentSelected = list.getSelected().getWaypoint();
        } catch (NullPointerException e) {
            return;
        }

        WAYPOINTS.remove(currentSelected);
        save();
        list.reloadWaypoints(WAYPOINTS);
    }

    public static void saveSelected(WaypointListWidget list, String newN, int intX, int intY, int intZ) {

        Waypoint currentSelected = null;

        try {

            currentSelected = list.getSelected().getWaypoint();
            int currIndex = WAYPOINTS.indexOf(currentSelected);


            Waypoint editedWaypoint = new Waypoint(newN, intX, intY, intZ);

            WAYPOINTS.remove(currentSelected);
            WAYPOINTS.add(currIndex, editedWaypoint);
            save();
            list.reloadWaypoints(WAYPOINTS);
            return;

        } catch (NullPointerException e) { assert true; }

        Waypoint newWaypoint = new Waypoint(newN, intX, intY, intZ);
        WAYPOINTS.add(newWaypoint);
        list.reloadWaypoints(WAYPOINTS);
    }

    public static List<Waypoint> getWaypoints() {

        return WAYPOINTS;
    }
}
