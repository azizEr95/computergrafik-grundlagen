package F_texturen;

import lib_cgtools.*;
import static lib_cgtools.Vector.*;

/**
 * Die Camera-Klasse repräsentiert eine Kamera, die in einem Raytracer verwendet wird.
 * Sie berechnet die Strahlen (Rays), die von der Kamera zu den Pixeln auf der Bildebene
 * führen. Durch die Transformation mit einer View-Matrix kann die Kamera im Raum
 * positioniert und ausgerichtet werden.
 */
public class Camera {
    
    private double alpha; // Die Blendenöffnung der Kamera, die den Sichtwinkel bestimmt.
    private int width;    // Die Breite der Bildebene (in Pixeln).
    private int height;   // Die Höhe der Bildebene (in Pixeln).
    private Matrix viewMatrix; // Die Transformationsmatrix der Kamera für Position und Orientierung.

    public Camera(double alpha, int width, int height, Matrix viewMatrix) {
        this.alpha = alpha;
        this.width = width;
        this.height = height;
        this.viewMatrix = viewMatrix;
    }

    /**
     * Diese Methode erzeugt einen Strahl (Ray) von der Kamera zum angegebenen Punkt (x, y) auf der Bildebene.
     * Die Berechnung erfolgt in mehreren Schritten:
     * 
     * 1. Das Pixel (x, y) wird so transformiert, dass der Ursprung der Bildebene in der Mitte liegt.
     * 2. Die y-Richtung wird negiert, da das 2D-Koordinatensystem oben links beginnt.
     * 3. Die z-Koordinate wird so berechnet, dass der Strahl aus der Kamera herausführt und den Sichtwinkel (alpha) berücksichtigt.
     * 4. Die Richtung des Strahls wird normalisiert.
     * 5. Die Richtung und der Ursprung des Strahls werden mit der View-Matrix transformiert.
     * 
     * - x: Die x-Koordinate des Pixels auf der Bildebene
     * - y: Die y-Koordinate des Pixels auf der Bildebene
     */
    public Ray generateRay(double x, double y) { 
        // Ursprung der Kamera (vor Transformation)
        Point origin = new Point(0, 0, 0);
        
        // Berechnung der x- und y-Komponenten relativ zur Mitte der Bildebene
        double xDirection = x - width / 2.0;
        double yDirection = - (y - height / 2.0); // Negation, da das Koordinatensystem y nach unten wachsen lässt

        // Berechnung der z-Komponente basierend auf der Blendenöffnung (alpha)
        double zDirection = - ((width / 2.0) / Math.tan(alpha / 2.0));

        // Normalisierung des Richtungsvektors für den Strahl
        Direction localDirection = normalize(new Direction(xDirection, yDirection, zDirection));

        // Transformation der Richtung und des Ursprungs mit der View-Matrix
        Direction transformedDirection = Matrix.multiply(viewMatrix, localDirection);
        Point transformedOrigin = Matrix.multiply(viewMatrix, origin);

        // Rückgabe eines Strahls mit transformiertem Ursprung und Richtung
        return new Ray(transformedOrigin, transformedDirection, 0.0, Double.POSITIVE_INFINITY);
      }

    /**
     * Gibt die Blendenöffnung (alpha) zurück, die den Sichtwinkel der Kamera bestimmt.
     */
    public double getAlpha() {
        return alpha;
    }

     /**
     * Gibt die Breite der Bildebene zurück.
     */
    public int getWidth() {
        return width;
    }

    /*
     * Gibt die Höhe der Bildebene zurück.
     */
    public int getHeight() {
        return height;
    }


}
