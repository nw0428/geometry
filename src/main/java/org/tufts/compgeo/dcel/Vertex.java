package org.tufts.compgeo.dcel;

public class Vertex {

    private final float x;
    private final float y;
    private String name;
    private HalfEdge edge;
    private final boolean infinite;


    public Vertex(float x, float y, DCEL dcel) {
        this.x = x;
        this.y = y;
        this.infinite = false;
        dcel.getVertices().add(this);
    }

    public Vertex(float x, float y, boolean infinite, DCEL dcel) {
        this.x = x;
        this.y = y;
        this.infinite = infinite;
        dcel.getVertices().add(this);
    }

    public String getName() {
        return name;
    }

    public Vertex setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isInfinite() {
        return infinite;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public HalfEdge getEdge() {
        return edge;
    }

    public Vertex setEdge(HalfEdge edge) {
        this.edge = edge;
        return this;
    }

    public HalfEdge getEdgeOnFace(Face face) {
        HalfEdge e = this.edge;
        while (e.getFace() != face) {
            e = e.getTwin().getNext();
        }
        return e;
    }

    public HalfEdge getEdgeForNeighbor(Vertex vertex) {
        HalfEdge e = this.edge.getTwin();
        while (e.getSource() != vertex) {
            e = e.getNext().getTwin();
        }
        return e;
    }


    @Override
    public String toString() {
        return "Vertex{" +
                "x=" + x +
                ", y=" + y +
                ", name='" + name + '\'' +
                '}';
    }
}
