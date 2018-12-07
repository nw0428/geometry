
Currently this repository contains code to generate a Delaunay triangulation and display it.

org.tufts.compgeo.dcel contains code for a DCEL and a UI that displays it badly.

org.tufts.compgeo.delaunay contains code that generates a Delaunay triangulation. 
The main method in the DelaunayTriangulation.class generates one based, mainly on random points.
If you run the main method in org.tufts.compgeo.delaunay.Test. The code will write a csv to stdout containing the number of points added, how
 long it took to add the last 100 and the total time to add points up till now.