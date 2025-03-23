package D_beleuchtung_schatten;

import lib_cgtools.*;
import static lib_cgtools.Vector.*;

/*
 * Die Sphere-Klasse repräsentiert eine Kugel in der 3D-Szene.
 * Sie ermöglicht die Berechnung der Schnittpunkte (Intersektionen) zwischen einem Strahl (Ray) 
 * und der Kugel, um festzustellen, ob der Strahl die Kugel schneidet. 
 * Sie enthält außerdem den Mittelpunkt, den Radius und das Material der Kugel.
 */
public class Sphere implements Shape {
    
    private Point center;     // Mittelpunkt der Kugel
    private double radius;    // Radius der Kugel
    private Material material; // Materialeigenschaften der Kugel

    /*
     * Konstruktor, der eine Kugel mit Mittelpunkt, Radius und Material erstellt.
     * 
     * - center: Mittelpunkt der Kugel.
     * - radius: Radius der Kugel.
     * - material: Materialeigenschaften der Kugel.
     */
    public Sphere(Point center, double radius, Material material) {
        this.center = center;
        this.radius = radius;
        this.material = material;
    }

    /*
     * Berechnet die Schnittpunkte zwischen einem gegebenen Strahl (Ray) und der Kugel.
     * Diese Methode löst die quadratische Gleichung für die Kugel-Schnittpunkt-Berechnung:
     *
     * 1. Der Mittelpunkt der Kugel wird relativ zum Strahlursprung betrachtet.
     * 2. Die Koeffizienten der quadratischen Gleichung werden bestimmt.
     * 3. Die Diskriminante entscheidet über die Anzahl der Lösungen:
     *    - Falls > 0: Zwei Schnittpunkte → Der nächste gültige wird gewählt.
     *    - Falls == 0: Ein Schnittpunkt.
     *    - Falls < 0: Kein Schnittpunkt (Strahl verfehlt die Kugel).
     * 
     * Rückgabewert: Ein Hit-Objekt mit Informationen zum Schnittpunkt (Treffpunkt, Normale, Material),
     * oder null, falls kein Schnittpunkt existiert.
     *
     * - r: Der Strahl, der auf die Kugel trifft.
     */
    @Override
    public Hit intersect(Ray r) {
        // Vektor von Strahlursprung zum Kugelmittelpunkt
        Direction oc = subtract(r.origin(), center);

        // Koeffizienten der quadratischen Gleichung ax^2 + bx + c = 0
        double a = dotProduct(r.direction(), r.direction());
        double b = 2 * dotProduct(oc, r.direction());
        double c = dotProduct(oc, oc) - (radius * radius);
        double discriminant = (b * b) - (4 * a * c);

        // Fallunterscheidung anhand der Diskriminante
        if (discriminant < 0) {
            return null; // Kein Schnittpunkt
        }

        // Berechnung der möglichen t-Werte
        double sqrtD = Math.sqrt(discriminant);
        double t1 = (-b - sqrtD) / (2 * a);
        double t2 = (-b + sqrtD) / (2 * a);

        // Auswahl des kleineren positiven t-Werts (nächster Schnittpunkt)
        double t = (t1 > r.tMin() && t1 < r.tMax()) ? t1 : 
                   (t2 > r.tMin() && t2 < r.tMax()) ? t2 : -1;

        // Falls kein gültiger Schnittpunkt gefunden wurde
        if (t < 0) {
            return null;
        }

        // Berechnung der Normale am Schnittpunkt
        Point hitPoint = r.pointAt(t);
        Direction normalV = normalize(subtract(hitPoint, center));

        return new Hit(t, hitPoint, normalV, material);
    }

    /*
     * Gibt den Radius der Kugel zurück.
     */
    public double getRadius() {
        return radius;
    }

    /*
     * Gibt das Material der Kugel zurück.
     */
    public Material getMaterial() {
        return material;
    }
}
