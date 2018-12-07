package org.tufts.compgeo.dcel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.HeadlessException;

public class Ui {

    DelaunayTriangulation delaunayTriangulation;

    public Ui(DelaunayTriangulation delaunayTriangulation) throws HeadlessException {
        this.delaunayTriangulation = delaunayTriangulation;
    }

    public void draw() {
            EventQueue.invokeLater(() -> {
                JFrame f = new JFrame("Swing Paint Demo");
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.add(new Surface(delaunayTriangulation));
                f.pack();
                f.setVisible(true);
            });
    }
}


class Surface extends JPanel {

    private DelaunayTriangulation delaunayTriangulation;

    public Surface(DelaunayTriangulation delaunayTriangulation) {
        this.delaunayTriangulation = delaunayTriangulation;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1000, 1000);
    }

    private void doDrawing(Graphics g) {

        DCEL dcel = delaunayTriangulation.getDcel();
        for (Face face : dcel.getFaces()) {
            face.paintFace(g);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }
}