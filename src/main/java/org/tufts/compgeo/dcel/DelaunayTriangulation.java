package org.tufts.compgeo.dcel;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class DelaunayTriangulation {

    private final Set<Vertex> vertices;
    private final DCEL dcel;
    private final TriangleFaceTreeNode faceTree;
    private final float max;

    public DelaunayTriangulation(float max) {
        vertices = new HashSet<>();
        dcel = new DCEL();

        this.max = max;

        Vertex a = new Vertex(0, 5 * max, true, dcel).setName("1");
        Vertex b = new Vertex(0, 0, true,  dcel).setName("2");
        Vertex c = new Vertex(5 * max, 0, true, dcel).setName("3");
        Face face = new Face(dcel, true).setName("Original Face");


        setup(a, b, c, face);
        faceTree = new TriangleFaceTreeNode(face);
        dcel.sanity();
    }

    private void setup(Vertex a, Vertex b, Vertex c, Face face) {
        Face infinFace = new Face(dcel, true).setName("Outer face");
        HalfEdge ab = new HalfEdge(a, face, dcel);
        HalfEdge ba = new HalfEdge(b, infinFace, dcel);
        ab.setTwin(ba);

        HalfEdge bc = new HalfEdge(b, face, dcel);
        HalfEdge cb = new HalfEdge(c, infinFace, dcel);
        bc.setTwin(cb);

        HalfEdge ca = new HalfEdge(c, face, dcel);
        HalfEdge ac = new HalfEdge(a, infinFace, dcel);
        ca.setTwin(ac);

        ab.setNext(bc);
        bc.setNext(ca);
        ca.setNext(ab);

        ba.setNext(ac);
        ac.setNext(cb);
        cb.setNext(ba);

        face.setHalfEdge(ab);
    }

    public Vertex insertVertex(float xcoord, float ycoord) {
        Vertex vertex = new Vertex(xcoord, ycoord, dcel);

        TriangleFaceTreeNode container = faceTree.search(vertex);

        // Create new faces and remove irrelevant face from dcel
        Face nab = new Face(dcel);
        Face nbc = new Face(dcel);
        Face nca = new Face(dcel);
        dcel.getFaces().remove(container.getFace());

        TriangleFaceTreeNode[] children = container.getChildren();
        children[0] = new TriangleFaceTreeNode(nab);
        children[1] = new TriangleFaceTreeNode(nbc);
        children[2] = new TriangleFaceTreeNode(nca);


        // Walk clockwise to get the edges of the old triangle
        HalfEdge ab = container.getFace().getHalfEdge();
        HalfEdge bc = ab.getNext();
        HalfEdge ca = bc.getNext();


        // Set up nab->n
        ab.setFace(nab);
        HalfEdge na = new HalfEdge(vertex, nab, dcel);
        na.setNext(ab);
        HalfEdge bn = new HalfEdge(bc.getSource(), nab, dcel);
        ab.setNext(bn);
        bn.setNext(na);


        // Set up nbc->n
        bc.setFace(nbc);
        HalfEdge nb = new HalfEdge(vertex, nbc, dcel);
        nb.setTwin(bn);
        nb.setNext(bc);
        HalfEdge cn = new HalfEdge(ca.getSource(), nbc, dcel);
        bc.setNext(cn);
        cn.setNext(nb);


        //setup nca->n
        ca.setFace(nca);
        HalfEdge nc = new HalfEdge(vertex, nca, dcel);
        nc.setTwin(cn);
        nc.setNext(ca);
        HalfEdge an = new HalfEdge(ab.getSource(), nca, dcel);
        an.setTwin(na);
        ca.setNext(an);
        an.setNext(nc);

        dcel.sanity();

        Vertex a = ab.getSource();
        Vertex x = a;
        Vertex y;
        boolean progress = false;
        do {
            HalfEdge nx = vertex.getEdgeForNeighbor(x);
            HalfEdge yx = nx.getNext().getTwin();
            y = yx.getSource();
            progress = true;
            if (!(x.isInfinite() && y.isInfinite())) {
                if (yx.getFace().circumcircleContains(vertex)) {
                    //Flip the xy edge. Delete the two old faces, make two new ones, etc.
                    Vertex z = yx.getPrev().getSource();

                    Face nxz = new Face(dcel);
                    Face zyn = new Face(dcel);
                    TriangleFaceTreeNode tnxz = new TriangleFaceTreeNode(nxz);
                    TriangleFaceTreeNode tzyn = new TriangleFaceTreeNode(zyn);

                    TriangleFaceTreeNode yxnode = yx.getFace().getOther();
                    TriangleFaceTreeNode xynode = yx.getTwin().getFace().getOther();
                    yxnode.getChildren()[0] = tnxz;
                    yxnode.getChildren()[1] = tzyn;
                    xynode.getChildren()[0] = tnxz;
                    xynode.getChildren()[1] = tzyn;

                    dcel.getFaces().remove(yxnode.getFace());
                    dcel.getFaces().remove(xynode.getFace());

                    HalfEdge zy = z.getEdgeForNeighbor(y);
                    HalfEdge xz = x.getEdgeForNeighbor(z);
                    HalfEdge yn = y.getEdgeForNeighbor(vertex);


                    HalfEdge zn = new HalfEdge(z, nxz, dcel);
                    HalfEdge nz = new HalfEdge(vertex, zyn, dcel);
                    zn.setTwin(nz);

                    nx.setFace(nxz);
                    xz.setFace(nxz);
                    nx.setNext(xz);
                    xz.setNext(zn);

                    zy.setFace(zyn);
                    yn.setFace(zyn);
                    nz.setNext(zy);
                    zy.setNext(yn);
                    progress = false;
                }
            }
            if (progress) x = y;
        } while (x != a || !progress);

        return vertex;
    }


    public void draw() {
        Ui ui = new Ui(this);
        ui.draw();
    }


    public static void main(String[] args) throws InterruptedException {
        DelaunayTriangulation delaunayTriangulation = new DelaunayTriangulation(600);

        delaunayTriangulation.insertVertex(200.01f,200.1f).setName("a");
        delaunayTriangulation.insertVertex(100.2f,200f).setName("b");
        delaunayTriangulation.insertVertex(199.99f,100.01f).setName("c");
        delaunayTriangulation.draw();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            Thread.sleep(3000);
            delaunayTriangulation.insertVertex(random.nextFloat()*500, random.nextFloat() * 500)
                    .setName(Integer.toString(i));
            delaunayTriangulation.draw();
        }


    }

    public float getMax() {
        return max;
    }

    public DCEL getDcel() {
        return dcel;
    }
}
