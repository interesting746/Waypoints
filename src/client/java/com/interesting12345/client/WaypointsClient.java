package com.interesting12345.client;

import com.interesting12345.client.gui.WaypointConfigScreen;
import com.interesting12345.client.utils.WaypointManager;
import com.interesting12345.client.utils.WaypointRenderer;
import com.interesting12345.client.model.Waypoint;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static com.interesting12345.Waypoints.MOD_ID;

public class WaypointsClient implements ClientModInitializer {

	public static final List<Waypoint> WAYPOINTS = new ArrayList<>();

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {

		KeyMapping.Category waypoints = KeyMapping.Category.register(Identifier.fromNamespaceAndPath("waypoints-mod", "waypoints"));
		KeyMapping addWaypoint = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.waypoints-mod.add_waypoint", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_Y, waypoints));
		KeyMapping configWaypoints = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.waypoints-mod.config_waypoints", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_U, waypoints));

		// Load waypoints from JSON file
		WaypointManager.load();

		ClientTickEvents.END_CLIENT_TICK.register(client -> {

			if(client.player == null) return;

			while (addWaypoint.consumeClick()) {

				Waypoint newWaypoint = new Waypoint("Waypoint " + (WaypointManager.WAYPOINTS.size() + 1), client.player.getBlockX(), client.player.getBlockY(), client.player.getBlockZ());

				WaypointManager.add(newWaypoint);
				client.player.displayClientMessage(Component.literal("§b[Waypoint Mod]§f Saved waypoint " + newWaypoint.name() + " at: " + client.player.getBlockX() + ", " + client.player.getBlockY() + ", " + client.player.getBlockZ()), false);
			}

			while (configWaypoints.consumeClick()) {

				Screen newScreen = new WaypointConfigScreen(Component.empty());
				Minecraft.getInstance().setScreen(newScreen);
			}
		});

        //context.gameRenderer().getMainCamera().
        WorldRenderEvents.AFTER_ENTITIES.register(WaypointRenderer::render);
	}
}