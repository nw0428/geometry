package org.tufts.compgeo.dcel;

import static org.tufts.compgeo.dcel.Utils.signedArea;

public class TriangleFaceTreeNode {

    private final Face face;
    private final TriangleFaceTreeNode[] children;
    Vertex a;
    Vertex b;
    Vertex c;

    public TriangleFaceTreeNode(Face face) {
        this.face = face;
        if (face.getOther() != null && face.getOther() != this) {
            throw new IllegalStateException("This shouldn't ever happen");
        }
        face.setOther(this);
        children = new TriangleFaceTreeNode[3];
    }

    public Face getFace() {
        return face;
    }

    public TriangleFaceTreeNode[] getChildren() {
        return children;
    }

    public boolean contains(Vertex vertex) {
        // Set a,b, and c once
        a = a == null ? face.getHalfEdge().getSource() : a;
        b = b == null ? face.getHalfEdge().getNext().getSource() : b;
        c = c == null ? face.getHalfEdge().getPrev().getSource() : c;

        float d1 = signedArea(a, b, vertex);
        float d2 = signedArea(b, c, vertex);
        float d3 = signedArea(c, a, vertex);


        boolean one_neg = (d1 < 0) || (d2 < 0) || (d3 < 0);
        boolean one_pos = (d1 > 0) || (d2 > 0) || (d3 > 0);

        return !(one_neg && one_pos);
    }

    public TriangleFaceTreeNode search(Vertex vertex) {
        int i;
        for (i = 0; i < 3; i++) {
            if (null != children[i] && children[i].contains(vertex)) {
                return children[i].search(vertex);
            }
        }
        return this;
    }
}
