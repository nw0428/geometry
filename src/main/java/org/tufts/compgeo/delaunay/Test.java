package org.tufts.compgeo.delaunay;

import java.time.Instant;
import java.util.Random;

public class Test {

    public static void main(String[] args) {
        Float max = 600_000f;
        DelaunayTriangulation delaunayTriangulation = new DelaunayTriangulation(max);

        Random random = new Random();
        Long start = Instant.now().toEpochMilli();
        Long last = start;
        for (int i = 0; i < 100_000_000; i++) {
            delaunayTriangulation.insertVertex(random.nextFloat()*max, random.nextFloat() * max)
                    .setName(Integer.toString(i));
            if (i%50_000 == 0) {
                Long now = Instant.now().toEpochMilli();
                System.out.printf("%d, %d, %d, list\n", i, now - last, now - start);
                last = now;
            }
        }

    }
}
