package A_bilderzeugung;

import lib_cgtools.*;

/*
 * Die DiscModel-Klasse modelliert eine Scheibe (Disc) mit einem bestimmten Mittelpunkt,
 * Radius und einer Farbe. Sie stellt Funktionen bereit, um zu überprüfen, ob ein Punkt 
 * innerhalb der Scheibe liegt und gibt die Eigenschaften der Scheibe zurück.
 */
public class DiscModel {
    private double midX;   // x-Koordinate des Mittelpunkts der Scheibe
    private double midY;   // y-Koordinate des Mittelpunkts der Scheibe
    private double radius; // Der Radius der Scheibe
    private Color color;   // Die Farbe der Scheibe

    /*
     * Konstruktor zum Erstellen einer Disc mit einem gegebenen Mittelpunkt (x, y), 
     * einem Radius und einer Farbe.
     * 
     * - x: x-Koordinate des Mittelpunkts
     * - y: y-Koordinate des Mittelpunkts
     * - radius: Der Radius der Scheibe
     * - color: Die Farbe der Scheibe
     */
    public DiscModel(double x, double y, double radius, Color color) {
        midX = x;
        midY = y;
        this.radius = radius;
        this.color = color;
    }

    /*
     * Überprüft, ob der Punkt (x, y) innerhalb der Scheibe liegt.
     * Der Abstand zwischen dem Punkt (x, y) und dem Mittelpunkt der Scheibe wird berechnet
     * und mit dem Radius verglichen. Wenn der Abstand kleiner oder gleich dem Radius ist,
     * liegt der Punkt innerhalb der Scheibe.
     * 
     * Formel: d = √((x - midX)^2 + (y - midY)^2)
     * - d: Der Abstand zwischen dem Punkt (x, y) und dem Mittelpunkt der Scheibe.
     * - x, y: Die Koordinaten des zu überprüfenden Punkts.
     * Rückgabewert: true, wenn der Punkt innerhalb der Scheibe liegt, andernfalls false.
     */
    public boolean coversPoint(double x, double y) {
        double d = Math.sqrt(Math.pow(x - midX, 2) + Math.pow(y - midY, 2));
        return d <= radius;
    }

    /*
     * Gibt den Radius der Scheibe zurück.
     */
    public double getRadius() {
        return radius;
    }

    /*
     * Gibt die Farbe der Scheibe zurück.
     */
    public Color getColor() {
        return color;
    }
}
