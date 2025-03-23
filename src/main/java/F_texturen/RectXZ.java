package F_texturen;

import static lib_cgtools.Util.*;
import static lib_cgtools.Vector.*;

import lib_cgtools.*; 

/* 
 * Eine Shape-Klasse, die ein 2D-Rechteck in der X-Z-Ebene im 3D-Raum repräsentiert, 
 * das in die Y-Richtung zeigt (nach oben).
 */
public class RectXZ implements Shape {

    private Point anchor;  // Der Ankerpunkt, um den das Rechteck zentriert ist
    private double x_size_half, z_size_half;  // Halbe Größen in X- und Z-Richtung
    private Material material;  // Material des Rechtecks

    /* 
       Konstruktor: Erstellt ein Rechteck, das um den Ankerpunkt zentriert ist.
       Dimensionen: von -x_size/2 bis x_size/2 in X und von -z_size/2 bis z_size/2 in Z.
       Normalenrichtung: in Y-Richtung (nach oben).
    */
    public RectXZ(Point anchor, double x_size, double z_size, Material material) {
        this.anchor = anchor;  // Der Mittelpunkt des Rechtecks im Raum
        this.x_size_half = x_size / 2.0;  // Halbe Breite des Rechtecks
        this.z_size_half = z_size / 2.0;  // Halbe Tiefe des Rechtecks
        this.material = material;  // Material des Rechtecks
    }

    /*
     * Berechnet den Schnittpunkt des Strahls mit dem Rechteck.
     * Wenn der Strahl parallel zur X-Z-Ebene verläuft (d.h. keine Y-Komponente in der Richtung),
     * gibt es keinen Treffer.
     */
    @Override
    public Hit intersect(Ray ray) {
        // Wenn der Strahl parallel zur X-Z-Ebene verläuft, gibt es keinen Treffer
        if (isZero(ray.direction().y())) {
            return null; 
        }

        // Berechnung des Treffpunkts in der Ebene (t-Wert für die Strecke)
        double t = (anchor.y() - ray.origin().y()) / ray.direction().y(); 
        if (!ray.isValid(t)) {
            return null;  // Wenn der t-Wert ungültig ist, gibt es keinen Treffer
        }

        // Berechnung des Punktes auf dem Strahl zum Treffpunkt
        Point p = ray.pointAt(t); 

        // Überprüfung, ob der Punkt innerhalb des Rechtecks liegt
        double x_l = Math.abs(p.x() - anchor.x()); 
        double z_l = Math.abs(p.z() - anchor.z()); 
        if (x_l > x_size_half || z_l > z_size_half) {
            return null;  // Wenn der Punkt außerhalb des Rechtecks liegt, gibt es keinen Treffer
        }

        // Berechnung der Texturkoordinaten
        Direction rightDirection = subtract(p, anchor); 
        double u = (rightDirection.x() + x_size_half) / (x_size_half * 2.0);  // u-Koordinate für Textur
        double v = 1.0 - ((rightDirection.z() + z_size_half) / (z_size_half * 2.0));  // v-Koordinate für Textur

        // Rückgabe des Treffers mit dem Material und den berechneten Texturkoordinaten
        return new Hit(t, p, direction(0,1,0), material, u, v); 
    }
}
