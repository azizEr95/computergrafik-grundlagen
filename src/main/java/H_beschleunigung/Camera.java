package H_beschleunigung;

import static lib_cgtools.Vector.normalize;

import lib_cgtools.Direction;
import lib_cgtools.Matrix;
import lib_cgtools.Point;



public class Camera {
    
    private double alpha; // entscheidet wie gross die Blende ist
    private int width; // Lochkamera Sichthöhe
    private int height; //Lochkamera Sichtbreite
    private Matrix viewMatrix;

    public Camera(double alpha, int width, int height, Matrix viewMatrix) {
        this.alpha = alpha;
        this.width = width;
        this.height = height;
        this.viewMatrix = viewMatrix;
    }

    /*
     * generateRay bekommt die x und y Werte eines Punktes unserer Bildebene und ist für 
     * die Berechnung eines Strahls verantwortlich von der Kamera zum Punkt auf der Ebene.
     * 
     * x,y,z werden um die Hälfte der Bildebene zentriert, um den Ursprung 0 in der Mitte zu haben
     * yDirection wird negiert, weil unser 2D Koordinatensystem oben links anfängt und y Werte 
     * nach unten negativ bleiben sollen. Deshalb die Negation
     * zDirection durch die Bildbreite durch 2, um die Distanz von zDirection zu berechnen
     * zu korrekten Berechnungen wird die Direction von allen 3 Komponenten normalisiert
     * ein Strahl wird zurückgegeben vom Ursprung 0, der normalisierte Richtungsvektor und der Strahl der von 0 -> unendlich gehen kann
     */
    public Ray generateRay(double x, double y) { 
        Point origin = new Point(0,0,0);
        
        double xDirection = x - width / 2.0;
        double yDirection = - (y - height / 2.0);
        double zDirection = - ((width / 2.0) / Math.tan(alpha / 2.0));
        Direction localDirection = normalize(new Direction(xDirection, yDirection, zDirection));

        //Transformation 
        Direction transformDirection = Matrix.multiply(viewMatrix, localDirection);
        Point transformedOrigin = Matrix.multiply(viewMatrix, origin);

        return new Ray(transformedOrigin, transformDirection, 0.0, Double.POSITIVE_INFINITY);
      }

    public double getAlpha() {
        return alpha;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


}
