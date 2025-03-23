package G_rekursiv_pathtracing;

import lib_cgtools.*;
import static lib_cgtools.Vector.*;

/**
 * Die Sphere-Klasse repräsentiert eine Kugel in der 3D-Welt und implementiert das Shape-Interface.
 * Diese Klasse ermöglicht die Berechnung von Schnittpunkten zwischen einem Strahl (Ray) und der Kugel
 * und speichert Informationen über die Kugel wie ihren Mittelpunkt, Radius und Material.
 */
public class Sphere implements Shape {
    
    private Point center;    // Der Mittelpunkt der Kugel
    private double radius;   // Der Radius der Kugel
    private Material material; // Das Material der Kugel

    public Sphere(Point center, double radius, Material material) {
        this.center = center;
        this.radius = radius;
        this.material = material;
    }

    /**
     * Berechnet, ob ein gegebener Strahl (Ray) die Kugel schneidet.
     * Dies erfolgt durch Berechnung der Diskriminante der quadratischen Gleichung, 
     * die aus der Strahlengleichung und der Kugelgleichung resultiert.
     * Falls ein Schnittpunkt existiert, wird ein Hit-Objekt zurückgegeben, 
     * das Informationen über den Schnittpunkt und die Texturkoordinaten enthält.
     * 
     * - r Der Strahl (Ray), der auf die Kugel trifft
     * - Rückgabe: Ein Hit-Objekt, wenn ein Schnittpunkt gefunden wurde, andernfalls null
     */
    public Hit intersect(Ray r) {
        // Berechnung des Vektors von der Strahlursprungsposition zum Mittelpunkt der Kugel
        Point centerOrigin = subtract(r.origin(), subtract(center, new Point(0, 0, 0)));
        
        // Berechnung der Koeffizienten der quadratischen Gleichung
        double a = dotProduct(r.direction(), r.direction());
        double b = 2 * dotProduct(centerOrigin, r.direction());
        double c = dotProduct(centerOrigin, centerOrigin) - Math.pow(radius, 2);
        
        // Berechnung der Diskriminante
        double discriminant = Math.pow(b, 2) - 4 * a * c;

        // Fall 1: Zwei Schnittpunkte (Diskriminante > 0)
        if (discriminant > 0) {
            // Berechnung der beiden Lösungen t1 und t2 (p-q Formel)
            double t1 = ((-b + Math.sqrt(discriminant))) / (2 * a);
            double t2 = ((-b - Math.sqrt(discriminant))) / (2 * a);

            // Die kleinere Lösung wird verwendet
            double t = Math.min(t1, t2);

            // Wenn der Schnittpunkt negativ ist, gibt es keinen gültigen Schnittpunkt
            if (t < 0) {
                return null;
            }

            // Überprüfen, ob der Schnittpunkt innerhalb des gültigen Strahlbereichs liegt
            if (r.isValid(t)) {
                Point hitP = r.pointAt(t);  // Der tatsächliche Schnittpunkt
                Direction normalV = divide((subtract(hitP, center)), radius); // Normalenvektor an der Oberfläche

                // Berechnung der Texturkoordinaten (u, v) der Kugeloberfläche
                Direction hitPV = subtract(hitP, center);
                double teta = Math.PI - Math.acos(hitPV.y() / this.radius); // Polarwinkel
                double phi = Math.PI + Math.atan2(hitPV.x(), hitPV.z()); // Azimutwinkel

                double u = phi / (2.0 * Math.PI);  // Normalisierte u-Texturkoordinate
                double v = (teta / Math.PI);       // Normalisierte v-Texturkoordinate

                // Rückgabe des Hit-Objekts mit den berechneten Informationen
                return new Hit(t, r.pointAt(t), normalV, material, u, v);
            } 
        }

        // Fall 2: Ein Schnittpunkt (Diskriminante == 0)
        if (discriminant == 0) {
            // Berechnung des Schnittpunkts t
            double t = (-b / (2 * a));

            // Wenn t negativ ist, gibt es keinen gültigen Schnittpunkt
            if (t < 0) {
                return null;
            }

            // Berechnung der Schnittpunkt- und Normalenvektor
            Point hitP = r.pointAt(t);
            Direction normalV = divide((subtract(hitP, center)), radius); // Normalenvektor
            Direction hitPV = subtract(hitP, center);

            // Berechnung der Texturkoordinaten
            double teta = Math.PI - Math.acos(hitPV.y() / this.radius); // Polarwinkel
            double phi = Math.PI + Math.atan2(hitPV.x(), hitPV.z()); // Azimutwinkel

            double u = phi / (2.0 * Math.PI);  // Normalisierte u-Texturkoordinate
            double v = (teta / Math.PI);       // Normalisierte v-Texturkoordinate

            // Rückgabe des Hit-Objekts
            return new Hit(t, r.pointAt(t), normalV, material, u, v);
        }

        // Fall 3: Kein Schnittpunkt (Diskriminante < 0)
        return null;
    }

    /**
     * Gibt den Radius der Kugel zurück.
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Gibt das Material der Kugel zurück.
     */
    public Material getMaterial() {
        return material;
    }
}
