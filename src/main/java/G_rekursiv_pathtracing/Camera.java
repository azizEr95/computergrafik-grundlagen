package G_rekursiv_pathtracing;

import lib_cgtools.*;
import static lib_cgtools.Vector.*;

/*
 * Diese Klasse repräsentiert eine Kamera, die in einer 3D-Szene verwendet wird, um Strahlen zu erzeugen.
 * Sie enthält die Informationen für die Kameraeinstellungen, wie Blende, Bildbreite und -höhe sowie die 
 * Ansichtsmatrix, die die Kameraposition und -ausrichtung beschreibt.
 */
public class Camera {
    
    private double alpha;  // Blendenwinkel der Kamera, bestimmt die Sichtfeldgröße
    private int width;  // Breite des Bildes (Sichtfeld in x-Richtung)
    private int height;  // Höhe des Bildes (Sichtfeld in y-Richtung)
    private Matrix viewMatrix;  // Die Ansichtsmatrix, die die Position und Orientierung der Kamera beschreibt

    /*
     * Konstruktor: Initialisiert die Kamera mit den gegebenen Parametern.
     * alpha (Blende), width (Bildbreite), height (Bildhöhe) und die Ansichtsmatrix.
     */
    public Camera(double alpha, int width, int height, Matrix viewMatrix) {
        this.alpha = alpha;  // Setzt den Blendenwinkel
        this.width = width;  // Setzt die Bildbreite
        this.height = height;  // Setzt die Bildhöhe
        this.viewMatrix = viewMatrix;  // Setzt die Ansichtsmatrix
    }

    /*
     * generateRay berechnet einen Strahl, der von der Kamera aus zu einem Punkt auf der Bildebene zeigt.
     * Diese Methode basiert auf den 2D-Koordinaten x und y der Bildebene und berechnet die Richtung des Strahls
     * ausgehend von der Kamera.
     * 
     * x, y werden relativ zur Bildmitte zentriert, um die Koordinaten auf die Bildmitte (0,0) zu beziehen.
     * yDirection wird negiert, weil das Koordinatensystem der Bildschirmanzeige oft so implementiert ist,
     * dass die y-Werte nach unten zeigen.
     * Der zDirection-Wert wird berechnet, um die Distanz zum Punkt auf der Bildebene zu bestimmen.
     * Der Strahl wird dann unter Berücksichtigung der Kameraansichtsmatrix transformiert.
     */
    public Ray generateRay(double x, double y) { 
        // Ursprungsposition des Strahls (Kamera steht am Ursprung)
        Point origin = new Point(0, 0, 0);
        
        // Berechnung der Richtung des Strahls auf der Bildebene
        double xDirection = x - width / 2.0;  // Zentriert in der Bildmitte
        double yDirection = - (y - height / 2.0);  // Negiert, um den Ursprung oben links zu setzen
        double zDirection = - ((width / 2.0) / Math.tan(alpha / 2.0));  // Berechnet die Distanz in z-Richtung durch die Blende

        // Normalisieren der Richtungs-Vektoren, um eine Richtungseinheit zu erhalten
        Direction localDirection = normalize(new Direction(xDirection, yDirection, zDirection));

        // Transformation der berechneten Richtung und des Ursprungs gemäß der Ansichtsmatrix
        Direction transformDirection = Matrix.multiply(viewMatrix, localDirection);  // Transformiert die Richtung
        Point transformedOrigin = Matrix.multiply(viewMatrix, origin);  // Transformiert den Ursprung

        // Erzeugung eines Strahls mit Ursprung, transformierter Richtung und einer Streckenlänge von 0 bis unendlich
        return new Ray(transformedOrigin, transformDirection, 0.0, Double.POSITIVE_INFINITY);
    }

    /*
     * Getter-Methode für den Blendenwinkel (alpha).
     */
    public double getAlpha() {
        return alpha;  // Gibt den Blendenwinkel zurück
    }

    /*
     * Getter-Methode für die Bildbreite.
     */
    public int getWidth() {
        return width;  // Gibt die Bildbreite zurück
    }

    /*
     * Getter-Methode für die Bildhöhe.
     */
    public int getHeight() {
        return height;  // Gibt die Bildhöhe zurück
    }

}
