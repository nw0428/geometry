package org.tufts.compgeo.dcel;

public class Utils {

    public static float signedArea(Vertex a, Vertex b, Vertex c) {
        return (a.getX() - c.getX()) * (b.getY() - c.getY()) - (b.getX() - c.getX()) * (a.getY() - c.getY());
    }
}
