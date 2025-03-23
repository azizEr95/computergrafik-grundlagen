package E_transformationen;

import lib_cgtools.*;
import static lib_cgtools.Vector.*;


/*
 * Die DiscXZ-Klasse repräsentiert eine Scheibe, die parallel zur XZ-Ebene liegt.
 * Diese Scheibe ist kreisförmig und besitzt einen definierten Radius.
 */
public class DiscXZ implements Shape {

    private Point anchor;  // Der Mittelpunkt der Scheibe in der 3D-Welt
    private double radius; // Der Radius der Scheibe
    private Material material; // Material der Scheibe
    private Direction normal; // Die Normalenrichtung der Scheibenebene (immer nach oben)


    /*
     * Konstruktor für die DiscXZ-Klasse.
     * Initialisiert die Scheibe mit einem Ankerpunkt, einem Radius und einer Farbe.
     * Die Normale ist fest auf (0,1,0) gesetzt, da die Scheibe parallel zur XZ-Ebene liegt.
     * 
     * - anchor: Der Mittelpunkt der Scheibe
     * - radius: Der Radius der Scheibe
     * - color: Die Farbe der Scheibe
     */
    public DiscXZ(Point anchor, double radius, Material material) {
        this.anchor = anchor;
        this.radius = radius;
        this.material = material;
        this.normal = new Direction(0, 1, 0);
    }


    /*
     * Berechnet die Schnittstelle zwischen einem Strahl (Ray) und der Scheibe.
     * 
     * Ein Strahl schneidet die Ebene der Scheibe, wenn seine y-Koordinate mit der y-Koordinate
     * des Ankerpunkts übereinstimmt. Dann wird geprüft, ob der Schnittpunkt innerhalb des 
     * definierten Radius der Scheibe liegt.
     * 
     * - ray: Der Strahl, der auf die Scheibe trifft.
     * Rückgabewert: Ein Hit-Objekt, falls der Strahl die Scheibe trifft, sonst null.
     */
    @Override
    public Hit intersect(Ray ray) {
        
        // Berechnet den Parameter t für den Schnittpunkt mit der XZ-Ebene
        double t = (anchor.y() - ray.origin().y()) / ray.direction().y();

        // Überprüft, ob der Wert von t innerhalb des gültigen Bereichs des Strahls liegt
        if(!ray.isValid(t)) {
            return null;
        } 

        // Berechnet den exakten Schnittpunkt in der 3D-Welt
        Point hitPoint = ray.pointAt(t);

        // Berechnet den Abstand des Schnittpunkts vom Mittelpunkt der Scheibe
        Direction vectorLength = subtract(anchor, hitPoint);
        if(length(vectorLength) > radius) { 
            return null; // Liegt der Punkt außerhalb des Radius, gibt es keinen Treffer
        }

        // Gibt einen Treffer mit den berechneten Werten zurück
        return new Hit(t, hitPoint, normal, material);
    }
}
