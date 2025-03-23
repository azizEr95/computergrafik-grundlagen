package B_raytracing_start;

import static lib_cgtools.Vector.*;

import lib_cgtools.*;


/*
 * Diese Klasse beschreibt eine Kugel im 3D-Raum und enthält Methoden zur Berechnung 
 * von Schnittpunkten zwischen einem Strahl (Ray) und der Kugel.
 */
public class Sphere {
    private Point center;    // Der Mittelpunkt der Kugel im 3D-Raum
    private double radius;   // Der Radius der Kugel
    private Color color;     // Die Farbe der Kugel

    /*
     * Konstruktor für die Kugel, der Mittelpunkt, Radius und Farbe festlegt.
     */
    public Sphere(Point center, double radius, Color color) {
        this.center = center;    // Setzt den Mittelpunkt der Kugel
        this.radius = radius;    // Setzt den Radius der Kugel
        this.color = color;      // Setzt die Farbe der Kugel
    }

    /*
     * Diese Methode berechnet den Schnittpunkt eines Strahls mit der Kugel.
     * Wenn der Strahl die Kugel schneidet, wird ein Hit-Objekt mit den Details des Schnittpunkts zurückgegeben.
     * Andernfalls wird null zurückgegeben.
     */
    public Hit intersect(Ray r) {
        // 1. Verschiebe die Kugel in den Ursprung (Zentrum der Kugel soll bei (0, 0, 0) liegen)
        // Berechnung des Vektors vom Strahl-Ursprung zum Ursprung der Kugel
        Point centerOrigin = subtract(r.origin(), subtract(center, new Point(0,0,0)));

        // 2. Berechnung der Parameter für die Diskriminante der quadratischen Gleichung
        // a: Skalarprodukt des Richtungsvektors mit sich selbst (d.h., die Länge des Richtungsvektors im Quadrat)
        double a = dotProduct(r.direction(), r.direction());
        // b: 2 * Skalarprodukt des Vektors vom Ursprung des Strahls zum Zentrum der Kugel mit dem Richtungsvektor
        double b = 2 * dotProduct(centerOrigin, r.direction());
        // c: Der Abstand des Strahls zum Zentrum der Kugel, abzüglich des Quadrats des Radius der Kugel
        double c = dotProduct(centerOrigin, centerOrigin) - Math.pow(radius, 2);
        
        // Berechne die Diskriminante der quadratischen Gleichung
        double discriminant = Math.pow(b, 2) - 4 * a * c;

        // 3. Fallunterscheidung basierend auf dem Wert der Diskriminante:
        // 1. Fall: Diskriminante > 0 -> Zwei Lösungen
        if (discriminant > 0) {
            double t1 = ((-b + Math.sqrt(discriminant))) / (2 * a); // Erste Lösung
            double t2 = ((-b - Math.sqrt(discriminant))) / (2 * a); // Zweite Lösung

            // Wir nehmen das kleinere t, da es der nächste Schnittpunkt ist
            double t = Math.min(t1, t2);

            // Falls das kleinere t negativ ist, prüfen wir das größere t
            if (t < 0) {
                t = Math.max(t1, t2);
                // Falls das größere t ebenfalls negativ ist, gibt es keinen gültigen Schnittpunkt
                if (t < 0) {
                    return null;
                }
            }

            // Wenn der Wert von t im gültigen Bereich liegt
            if (r.isValid(t)) {
                // Berechnung des Normalenvektors an der Schnittstelle (t wird verwendet, um den Punkt zu berechnen)
                Direction normalV = divide(subtract(r.pointAt(t), center), radius);
                // Gibt ein Hit-Objekt zurück, das den Schnittpunkt enthält
                return new Hit(t, r.pointAt(t), normalV, color);
            } 
        }

        // 2. Fall: Diskriminante == 0 -> Eine Lösung (Strahl berührt die Kugel nur an einem Punkt)
        if (discriminant == 0) {
            double t = (-b / (2 * a));  // Berechnung des einzigen Schnittpunkts
            Direction normalV = divide(subtract(r.pointAt(t), center), radius);  // Berechnung des Normalenvektors
            // Gibt ein Hit-Objekt mit dem Schnittpunkt zurück
            return new Hit(t, r.pointAt(t), normalV, color);
        }

        // 3. Fall: Diskriminante < 0 -> Keine Lösung (Strahl schneidet die Kugel nicht)
        return null;
    }

    // Getter für den Radius der Kugel
    public double getRadius() {
        return radius;
    }

    // Getter für die Farbe der Kugel
    public Color getColor() {
        return color;
    }
}
