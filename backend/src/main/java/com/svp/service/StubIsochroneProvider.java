package com.svp.service;

import jakarta.enterprise.context.ApplicationScoped;

/**
 * Approximation locale d’une zone couvrable (rectangle autour du point).
 * Remplace un isochrone ORS tant que l’instance locale n’est pas branchée.
 */
@ApplicationScoped
public class StubIsochroneProvider implements IsochroneProvider {

    /** ~5 km à la latitude de Paris (degrés en longitude/latitude). */
    private static final double DELTA_DEG = 0.045;

    @Override
    public String buildIsochronePolygonWkt(double longitudeWgs84, double latitudeWgs84) {
        double lon = longitudeWgs84;
        double lat = latitudeWgs84;
        double d = DELTA_DEG;
        // Rectangle fermé (5 sommets, dernier = premier)
        return "POLYGON(("
                + (lon - d) + " " + (lat - d) + ","
                + (lon + d) + " " + (lat - d) + ","
                + (lon + d) + " " + (lat + d) + ","
                + (lon - d) + " " + (lat + d) + ","
                + (lon - d) + " " + (lat - d)
                + "))";
    }
}
