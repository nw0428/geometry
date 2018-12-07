package org.tufts.compgeo.dcel;

import java.util.ArrayDeque;
import java.util.Collection;

public class DCEL {
    private final Collection<Face> faces;
    private final Collection<HalfEdge> halfEdges;
    private final Collection<Vertex> vertices;

    public DCEL() {
        faces = new ArrayDeque<>(1_000_000);
        halfEdges = new ArrayDeque<>(1_000_000);
        vertices = new ArrayDeque<>(1_000_000);
    }

    public Collection<Face> getFaces() {
        return faces;
    }

    public Collection<HalfEdge> getHalfEdges() {
        return halfEdges;
    }

    public Collection<Vertex> getVertices() {
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

    public void removeFace(Face face) {
//        faces.remove(face);
    }

    public void removeEdge(HalfEdge edge) {
//        halfEdges.remove(edge);
    }
}
