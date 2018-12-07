package org.tufts.compgeo.dcel;

import java.util.HashSet;
import java.util.Set;

public class DCEL {
    private final Set<Face> faces;
    private final Set<HalfEdge> halfEdges;
    private final Set<Vertex> vertices;

    public DCEL() {
        faces = new HashSet<>();
        halfEdges = new HashSet<>();
        vertices = new HashSet<>();
    }

    public Set<Face> getFaces() {
        return faces;
    }

    public Set<HalfEdge> getHalfEdges() {
        return halfEdges;
    }

    public Set<Vertex> getVertices() {
        return vertices;
    }

    public void sanity() {
        for (Face face : faces) {
            HalfEdge start = face.getHalfEdge();
            if (start != start.getNext().getNext().getNext() ||
                    start.getNext().getTwin() == null ||
                    start.getNext().getNext().getTwin() == null){
                throw new IllegalStateException("Bad bad bad");
            }
        }
    }
}
