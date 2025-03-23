package C_szene_background_ebene;

import lib_cgtools.*;
import static lib_cgtools.Vector.*;


/*
 * Die Sphere-Klasse repräsentiert eine Kugel in der 3D-Szene.
 * Sie ermöglicht die Berechnung der Schnittpunkte (Intersektionen) zwischen einem Strahl (Ray) 
 * und der Kugel, um festzustellen, ob der Strahl die Kugel schneidet. 
 * Sie enthält außerdem den Mittelpunkt, den Radius und die Farbe der Kugel.
 */
public class Sphere implements Shape {
    
    private Point center;  // Der Mittelpunkt der Kugel
    private double radius; // Der Radius der Kugel
    private Color color;   // Die Farbe der Kugel

    /*
     * Konstruktor, der die Kugel mit einem Mittelpunkt, einem Radius und einer Farbe erstellt.
     * 
     * - center: Der Mittelpunkt der Kugel.
     * - radius: Der Radius der Kugel.
     * - color: Die Farbe der Kugel.
     */
    public Sphere(Point center, double radius, Color color) {
      this.center = center;
      this.radius = radius;
      this.color = color;
    }

    /*
     * Diese Methode berechnet, ob der gegebene Strahl die Kugel schneidet. 
     * Sie löst die quadratische Gleichung für den Schnittpunkt zwischen Strahl und Kugel.
     * 
     * 1. Der Mittelpunkt der Kugel wird zum Ursprung verschoben.
     * 2. Es werden die Diskriminanten-Parameter der quadratischen Gleichung berechnet.
     * 3. Abhängig vom Wert der Diskriminante gibt es drei Fälle:
     *    - Wenn die Diskriminante > 0 ist, gibt es zwei Lösungen. Es wird das kleinere t gewählt.
     *    - Wenn die Diskriminante == 0 ist, gibt es eine Lösung.
     *    - Wenn die Diskriminante < 0 ist, gibt es keine Lösung (der Strahl schneidet die Kugel nicht).
     * 
     * Rückgabewert: Ein Hit-Objekt, das Informationen zum Schnittpunkt enthält (Treffpunkt, Normale, Farbe),
     * oder null, wenn kein Schnittpunkt vorhanden ist.
     * 
     * - r: Der Strahl, der auf die Kugel trifft.
     */
    public Hit intersect(Ray r) {
        //1. Verschiebung der Kugel zum Ursprung
        Point centerOrigin = subtract(r.origin(), subtract(center, new Point(0,0,0)));

        //2. Berechnung der Diskriminante-Parameter
        double a = dotProduct(r.direction(), r.direction());
        double b = 2 * dotProduct(centerOrigin, r.direction());
        double c = dotProduct(centerOrigin, centerOrigin) - Math.pow(radius, 2);
        double discriminant = Math.pow(b, 2) - 4 * a * c;

        //3. Fallunterscheidung der Diskriminante
        // - Wenn discriminant > 0, gibt es zwei Lösungen
        if (discriminant > 0) {
            double t1 = ((-b + Math.sqrt(discriminant))) / (2 * a);
            double t2 = ((-b - Math.sqrt(discriminant))) / (2 * a);
            double t = Math.min(t1, t2);
            
            if (t < 0) {
                return null;
            }

            if (r.isValid(t)) {
                Direction normalV = divide(subtract(r.pointAt(t), center), radius);
                return new Hit(t, r.pointAt(t), normalV, color);
            }
        }
        
        // - Wenn discriminant == 0, gibt es eine Lösung
        if (discriminant == 0) {
            double t = -b / (2 * a);
            if (t < 0) {
                return null;
            }

            Direction normalV = divide(subtract(r.pointAt(t), center), radius);
            return new Hit(t, r.pointAt(t), normalV, color);
        }
        
        // - Wenn discriminant < 0, gibt es keine Lösung
        return null;
    }

    /*
     * Gibt den Radius der Kugel zurück.
     * 
     * Rückgabewert: Der Radius der Kugel.
     */
    public double getRadius() {
      return radius;
    }

    /*
     * Gibt die Farbe der Kugel zurück.
     * 
     * Rückgabewert: Die Farbe der Kugel.
     */
    public Color getColor() {
      return color;
    }
}
