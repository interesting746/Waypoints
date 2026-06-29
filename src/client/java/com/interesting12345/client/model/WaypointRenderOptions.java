package com.interesting12345.client.model;

public record WaypointRenderOptions (
        boolean drawBox,
        boolean drawLabel,
        boolean drawDistance,
        int color,
        float lineWidth) {}
