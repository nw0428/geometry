package org.tufts.compgeo.dcel;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class DCEL {
    private final List<Face> faces;
    private final List<HalfEdge> halfEdges;
    private final List<Vertex> vertices;

    public DCEL() {
        faces = new LinkedList<>();
        halfEdges = new LinkedList<>();
        vertices = new LinkedList<>();
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
