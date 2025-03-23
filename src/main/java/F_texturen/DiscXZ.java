package F_texturen;

import lib_cgtools.*;
import static lib_cgtools.Vector.*;


/*
 * Die DiscXZ-Klasse repräsentiert eine Scheibe, die auf der XZ-Ebene im 3D-Raum liegt.
 * Die Scheibe ist in der Ebene parallel zur XZ-Achse positioniert und hat einen festen Mittelpunkt (anchor),
 * einen bestimmten Radius und ein Material. Sie implementiert das Shape-Interface, um Schnittpunkte mit Strahlen
 * (Rays) zu berechnen.
 */
public class DiscXZ implements Shape {

    private Point anchor;       // Der Mittelpunkt der Scheibe
    private double radius;      // Der Radius der Scheibe
    private Material material;  // Das Material der Scheibe
    private Direction normal;   // Die Normalenrichtung der Scheibe (immer auf der y-Achse, da sie auf der XZ-Ebene liegt)

    /*
     * Konstruktor, der eine Scheibe auf der XZ-Ebene erstellt.
     * Der Mittelpunkt der Scheibe wird durch den 'anchor' bestimmt, der Radius und das Material werden ebenfalls übergeben.
     * Standardmäßig ist die Normalenrichtung der Scheibe in der y-Achse (0, 1, 0).
     */
    public DiscXZ(Point anchor, double radius, Material material) {
        this.anchor = anchor;  // Setzt den Mittelpunkt der Scheibe
        this.radius = radius;  // Setzt den Radius der Scheibe
        this.material = material;  // Setzt das Material der Scheibe
        this.normal = new Direction(0, 1, 0);  // Setzt die Normale (immer auf der y-Achse für eine XZ-Ebene)
    }

    /*
     * Berechnet den Schnittpunkt eines Strahls mit der Scheibe.
     * Zunächst wird überprüft, ob der Strahl die Ebene der Scheibe schneidet (nur entlang der y-Achse).
     * Wenn ein Schnittpunkt existiert, wird überprüft, ob dieser Punkt innerhalb des Radius der Scheibe liegt.
     * Wenn dies der Fall ist, wird der Schnittpunkt zusammen mit der Normalen, dem Material und den Texturkoordinaten zurückgegeben.
     * 
     * Der Schnittpunkt wird als Hit-Objekt mit den folgenden Werten zurückgegeben:
     *   - t: Die Zeit des Schnittpunkts entlang des Strahls
     *   - hitPoint: Der Schnittpunkt im 3D-Raum
     *   - normal: Die Normalenrichtung der Scheibe
     *   - material: Das Material der Scheibe
     *   - u, v: Die Texturkoordinaten auf der Scheibe (normalisiert)
     */
    @Override
    public Hit intersect(Ray ray) {
        // Berechnung der Zeit 't' an dem der Strahl die Ebene der Scheibe schneidet (auf der y-Achse)
        double t = (anchor.y() - ray.origin().y()) / ray.direction().y();
        
        // Überprüfen, ob der Schnittpunkt des Strahls gültig ist (nicht hinter dem Strahlursprung)
        if (!ray.isValid(t)) {
            return null;
        }
        
        // Berechnung des Schnittpunkts des Strahls auf der Ebene
        Point hitPoint = ray.pointAt(t);
        
        // Berechnung des Vektors vom Schnittpunkt zur Mitte der Scheibe (auf der XZ-Ebene)
        Direction vectorLength = subtract(anchor, hitPoint);
        
        // Überprüfen, ob der Schnittpunkt innerhalb des Scheibenradius liegt
        if (length(vectorLength) > radius) {
            return null;  // Wenn der Abstand größer als der Radius ist, gibt es keinen Treffer
        }
        
        // Berechnung des Vektors vom Mittelpunkt der Scheibe zum Schnittpunkt
        Direction rDirection = subtract(hitPoint, anchor);
        
        // Berechnung der Texturkoordinaten u und v auf der Scheibe (normalisiert auf [0, 1])
        double u = (rDirection.x() + radius) / (2 * radius);  // u-Koordinate für die Textur (auf der X-Achse)
        double v = 1.0 - ((rDirection.z() + radius) / (2 * radius));  // v-Koordinate für die Textur (auf der Z-Achse)
        
        // Rückgabe des Hit-Objekts, das den Schnittpunkt, die Normalen, Material und Texturkoordinaten enthält
        return new Hit(t, hitPoint, normal, material, u, v);
    }
}
