package org.tufts.compgeo.dcel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public class Face {

    private HalfEdge halfEdge;
    private TriangleFaceTreeNode other;
    private String name;
    private final boolean infinite;

    public Face(DCEL dcel) {
        dcel.getFaces().add(this);
        infinite = false;
    }

    public Face(DCEL dcel, boolean infinite) {
        dcel.getFaces().add(this);
        this.infinite = infinite;
    }

    public HalfEdge getHalfEdge() {
        return halfEdge;
    }

    public Face setHalfEdge(HalfEdge halfEdge) {
        this.halfEdge = halfEdge;
        return this;
    }

    public Face setOther(TriangleFaceTreeNode other) {
        this.other = other;
        return this;
    }

    public String getName() {
        return name;
    }

    public Face setName(String name) {
        this.name = name;
        return this;
    }

    public TriangleFaceTreeNode getOther() {
        return other;
    }

    public boolean isDeleted() {
        for (int i = 0; i < 3; i++) {
            if (other.getChildren()[i] != null) {
                return true;
            }
        }
        return false;
    }

    public void paintFace(Graphics g) {
        if (infinite) return;
        Polygon polygon = new Polygon();
        HalfEdge start = getHalfEdge();
        polygon.addPoint((int)start.getSource().getX(), (int)start.getSource().getY());
        HalfEdge next = start.getNext();
        while (next != start) {
            int x = (int) next.getSource().getX();
            int y = (int) next.getSource().getY();
            polygon.addPoint(x, y);
            g.drawString(next.getSource().getName(), x, y);
            next = next.getNext();
        }
        g.setColor(new Color(133, 49, 44));
        g.setColor(new Color(0,0,0));
        g.drawPolygon(polygon);
    }


    // Calculate the determinant of a thing
    public boolean circumcircleContains(Vertex vertex) {
        
        Vertex a = this.getHalfEdge().getSource();
        Vertex b = this.getHalfEdge().getNext().getSource();
        Vertex c = this.getHalfEdge().getNext().getNext().getSource();
        
        float a11 = a.getX() - vertex.getX();
        float a21 = b.getX() - vertex.getX();
        float a31 = c.getX() - vertex.getX();

        float a12 = a.getY() - vertex.getY();
        float a22 = b.getY() - vertex.getY();
        float a32 = c.getY() - vertex.getY();

        float a13 = (a.getX() - vertex.getX()) * (a.getX() - vertex.getX()) + (a.getY() - vertex.getY()) * (a.getY() - vertex.getY());
        float a23 = (b.getX() - vertex.getX()) * (b.getX() - vertex.getX()) + (b.getY() - vertex.getY()) * (b.getY() - vertex.getY());
        float a33 = (c.getX() - vertex.getX()) * (c.getX() - vertex.getX()) + (c.getY() - vertex.getY()) * (c.getY() - vertex.getY());

        float det = a11 * a22 * a33 + a12 * a23 * a31 + a13 * a21 * a32 - a13 * a22 * a31 - a12 * a21 * a33 - a11 * a23 * a32;

        return det < 0.0;
    }
}
