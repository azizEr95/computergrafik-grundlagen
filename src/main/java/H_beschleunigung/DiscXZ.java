package H_beschleunigung;

import lib_cgtools.*;
import static lib_cgtools.Vector.*;

/*
 * Diese Klasse repräsentiert eine Scheibe auf der XZ-Ebene, die mit einem gegebenen Punkt als Anker, 
 * einem Radius und einem Material definiert ist. Sie implementiert die `Shape`-Schnittstelle und bietet
 * Funktionen zur Berechnung von Treffern und der Begrenzung der Form.
 */
public class DiscXZ implements Shape {

    private Point anchor;    // Der Ankerpunkt der Scheibe (der Mittelpunkt)
    private double radius;   // Der Radius der Scheibe
    private Material material; // Das Material der Scheibe
    private Direction normal;  // Die Normale der Scheibe (immer auf der Y-Achse)
    
    private Point p_min;  // Minimale Begrenzung des Bounding-Boxes
    private Point p_max;  // Maximale Begrenzung des Bounding-Boxes
    private BoundingBox boundingBox; // Die Bounding-Box für schnelle Kollisionserkennung

    /*
     * Konstruktor, der eine Scheibe auf der XZ-Ebene mit einem gegebenen Ankerpunkt, Radius und Material erstellt.
     * Auch eine Bounding-Box wird erstellt, um schnelle Kollisionen zu ermöglichen.
     */
    public DiscXZ(Point anchor, double radius, Material material) {
        this.anchor = anchor;  // Setzt den Ankerpunkt der Scheibe
        this.radius = radius;  // Setzt den Radius der Scheibe
        this.material = material; // Setzt das Material der Scheibe
        this.normal = new Direction(0, 1, 0); // Die Normale der Scheibe zeigt immer nach oben (in Richtung der Y-Achse)

        // Berechnet die minimalen und maximalen Werte für die Bounding-Box
        p_min = new Point((anchor.x()-radius), anchor.y() - 10, (anchor.z()-radius));
        p_max = new Point((anchor.x()+radius), anchor.y() + 10, (anchor.z()+radius));

        // Erstellt die Bounding-Box aus den minimalen und maximalen Punkten
        boundingBox = new BoundingBox(p_min, p_max);
    }

    /*
     * Gibt die Bounding-Box der Scheibe zurück, um schnelle Kollisionserkennung zu ermöglichen.
     */
    @Override
    public BoundingBox bounds() {
        return boundingBox;
    }

    /*
     * Berechnet, ob der gegebene Strahl die Scheibe schneidet.
     * Wenn der Strahl die Scheibe schneidet, wird der Treffer (Hit) zurückgegeben.
     * Andernfalls wird `null` zurückgegeben.
     */
    @Override
    public Hit intersect(Ray ray) {

        // Berechnet den Parameter t, bei dem der Strahl die Scheibe trifft.
        // Hier wird die Gleichung der Ebene benutzt: (P - P0) • N = 0, wobei P der Punkt auf der Scheibe ist
        // und P0 der Ankerpunkt der Scheibe. N ist die Normale der Scheibe.
        double t = (anchor.y() - ray.origin().y()) / ray.direction().y();
        
        // Wenn t ungültig ist, gibt es keinen Treffer
        if (!ray.isValid(t)) {
            return null;
        }
        
        // Berechnet den Treffpunkt des Strahls auf der Ebene der Scheibe
        Point hitPoint = ray.pointAt(t);
        
        // Berechnet den Vektor von der Mitte der Scheibe zum Treffpunkt
        Direction vectorLength = subtract(anchor, hitPoint);

        // Wenn der Treffpunkt außerhalb des Scheibenradius liegt, gibt es keinen Treffer
        if (length(vectorLength) > radius) {
            return null; // Der Punkt ist außerhalb des Kreises
        }

        // Berechnet die Richtungsvektoren in Bezug auf den Ankerpunkt
        Direction rDirection = subtract(hitPoint, anchor);
        
        // Berechnet die Texturkoordinaten u und v für den Treffpunkt
        double u = (rDirection.x() + radius) / (radius + radius);
        double v = 1.0 - ((rDirection.z() + radius) / ((radius + radius)));
        
        // Gibt den Treffer (Hit) mit dem Treffzeitpunkt, Treffpunkt, Normale, Material und Texturkoordinaten zurück
        return new Hit(t, hitPoint, normal, material, u, v);
    }
}
