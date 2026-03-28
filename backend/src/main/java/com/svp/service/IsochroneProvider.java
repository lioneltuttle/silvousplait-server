package com.svp.service;

/**
 * Fournit un polygone type « isochrone » (ici stub rectangulaire) pour filtrer les artisans en PostGIS.
 * Une implémentation future appellera OpenRouteService puis convertira la géométrie en WKT.
 */
public interface IsochroneProvider {

    /**
     * @param longitudeWgs84 longitude demande
     * @param latitudeWgs84  latitude demande
     * @return WKT POLYGON en EPSG:4326
     */
    String buildIsochronePolygonWkt(double longitudeWgs84, double latitudeWgs84);
}
