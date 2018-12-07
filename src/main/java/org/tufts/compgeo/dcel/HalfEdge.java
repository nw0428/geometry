package org.tufts.compgeo.dcel;

public class HalfEdge {

    private Vertex source;
    private Face face;
    private HalfEdge twin;
    private HalfEdge next; // Next clockwise around the edge of the face
    private HalfEdge prev; // Previous counter clockwise

    public HalfEdge(Vertex source, Face face, DCEL dcel) {
        this.source = source;
        this.face = face;
        source.setEdge(this);
        face.setHalfEdge(this);
        dcel.getHalfEdges().add(this);
    }

    public Vertex getSource() {
        return source;
    }

    public HalfEdge setSource(Vertex source) {
        this.source = source;
        return this;
    }

    public Face getFace() {
        return face;
    }

    public HalfEdge setFace(Face face) {
        this.face = face;
        return this;
    }

    public HalfEdge getTwin() {
        return twin;
    }

    public HalfEdge setTwin(HalfEdge twin) {
        this.twin = twin;
        twin.twin = this;
        return this;
    }

    public HalfEdge getNext() {
        return next;
    }

    public HalfEdge setNext(HalfEdge next) {
        this.next = next;
        next.prev = this;
        return this;
    }

    public HalfEdge getPrev() {
        return prev;
    }

    private HalfEdge setPrev(HalfEdge prev) {
        this.prev = prev;
        return this;
    }

    @Override
    public String toString() {
        return "HalfEdge{" +
                "source=" + source +
                '}';
    }
}
