package D_beleuchtung_schatten;

import lib_cgtools.*;
import static lib_cgtools.Vector.*;


/*
 * Die Camera-Klasse repräsentiert eine Kamera, die zur Erzeugung von Raytracing-Bildern 
 * verwendet wird. Sie berechnet die Strahlen (Rays), die von der Kamera zu den Pixeln der 
 * Bildebene gerichtet sind, und ermöglicht die Simulation von Perspektive und Fokussierung.
 * Zusätzlich wird der Sichtwinkel durch die Blende (alpha) gesteuert.
 */
public class Camera {
    
    private double alpha; // Die Blendenöffnung der Kamera, die den Sichtwinkel bestimmt
    private int width;    // Die Breite der Bildebene (in Pixeln)
    private int height;   // Die Höhe der Bildebene (in Pixeln)

    public Camera(double alpha, int width, int height) {
        this.alpha = alpha;
        this.width = width;
        this.height = height;
    }

    /*
     * Diese Methode erzeugt einen Strahl (Ray) von der Kamera zum angegebenen Punkt (x, y) auf der Bildebene.
     * Die x- und y-Koordinaten werden relativ zur Mitte der Bildebene zentriert, um die Perspektive korrekt zu berechnen.
     * Die z-Koordinate des Strahls wird basierend auf dem Sichtwinkel (alpha) berechnet, um eine realistische Tiefe 
     * zu erzeugen. Der berechnete Richtungsvektor wird dann normalisiert, um die Richtung des Strahls zu definieren.
     * 
     * - x: Die x-Koordinate des Pixels auf der Bildebene
     * - y: Die y-Koordinate des Pixels auf der Bildebene
     * Rückgabewert: Ein Ray, der vom transformierten Ursprung der Kamera in die berechnete Richtung zeigt.
     */
    public Ray generateRay(double x, double y) {
        // Berechnet die x-Richtung: Verschiebung des Pixels relativ zur Bildmitte
        double xDirection = x - width / 2.0;
        
        // Berechnet die y-Richtung: Verschiebung des Pixels relativ zur Bildmitte und Negation, da y-Koordinaten im Bildsystem nach unten wachsen
        double yDirection = - (y - height / 2.0);
        
        // Berechnet die z-Richtung, die von der Blendenöffnung (alpha) und der Bildbreite abhängt
        // Dies simuliert die Entfernung von der Kamera zur Bildebene
        double zDirection = - ((width / 2.0) / Math.tan(alpha / 2.0));
        
        // Normalisiert den Richtungsvektor, sodass der Strahl eine Einheit in Richtung des Ziels zeigt
        Direction normalizeDirection = normalize(new Direction(xDirection, yDirection, zDirection));
        
        // Gibt einen Ray zurück, der vom Ursprung der Kamera zum berechneten Punkt auf der Bildebene geht
        return new Ray(new Point(0, 0, 0), normalizeDirection, 0.0, Double.POSITIVE_INFINITY);
    }

    /*
     * Gibt den Wert der Blendenöffnung (alpha) zurück, der den Sichtwinkel der Kamera steuert.
     */
    public double getAlpha() {
        return alpha;
    }

    /*
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
