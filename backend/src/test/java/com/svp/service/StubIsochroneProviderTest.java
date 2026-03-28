package com.svp.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StubIsochroneProviderTest {

    @Test
    void sameInputsProduceSameWkt() {
        var p = new StubIsochroneProvider();
        String a = p.buildIsochronePolygonWkt(2.3522, 48.8566);
        String b = p.buildIsochronePolygonWkt(2.3522, 48.8566);
        assertEquals(a, b);
        assertTrue(a.startsWith("POLYGON(("));
    }
}
