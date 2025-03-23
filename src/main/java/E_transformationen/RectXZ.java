package E_transformationen;

import static lib_cgtools.Util.*;
import static lib_cgtools.Vector.*;

import lib_cgtools.*;

/**
 * Ein 2D-Rechteck auf der X-Z-Ebene in einer 3D-Szene.
 * Das Rechteck ist immer horizontal ausgerichtet (normal in Y-Richtung).
 */
public class RectXZ implements Shape {

    private Point anchor; // Mittelpunkt des Rechtecks
    private double x_size_half, z_size_half; // Halbe Breite und Tiefe des Rechtecks
    private Material material; // Materialeigenschaften des Rechtecks

    /**
     * Erstellt ein Rechteck, das um den Ankerpunkt `anchor` zentriert ist.
     * Die Dimensionen erstrecken sich von `-x_size/2` bis `x_size/2` in X-Richtung
     * und von `-z_size/2` bis `z_size/2` in Z-Richtung.
     *
     * - anchor: Mittelpunkt des Rechtecks.
     * - x_size: Breite des Rechtecks entlang der X-Achse.
     * - z_size: Tiefe des Rechtecks entlang der Z-Achse.
     * - material: Materialeigenschaften des Rechtecks.
     */
    public RectXZ(Point anchor, double x_size, double z_size, Material material) {
        this.anchor = anchor;
        this.x_size_half = x_size / 2.0;
        this.z_size_half = z_size / 2.0;
        this.material = material;
    }

    /**
     * Berechnet den Schnittpunkt des Rechtecks mit einem gegebenen Strahl (Ray).
     *
     * - ray: Der Strahl, der auf das Rechteck treffen könnte.
     * 
     * Rückgabewert: Ein `Hit`-Objekt mit den Kollisionsinformationen oder `null`, falls kein Treffer vorliegt.
     */
    @Override
    public Hit intersect(Ray ray) {

        // Überprüfen, ob der Strahl parallel zur X-Z-Ebene verläuft (d.h. keine Schnittmöglichkeit)
        if (isZero(ray.direction().y()))
            return null;

        // Berechnung des Schnittpunkts des Strahls mit der Ebene des Rechtecks
        double t = (anchor.y() - ray.origin().y()) / ray.direction().y();
        if (!ray.isValid(t))
            return null;

        // Prüfen, ob der Schnittpunkt innerhalb der Rechtecksdimensionen liegt
        Point p = ray.pointAt(t);
        double x_l = Math.abs(p.x() - anchor.x());
        double z_l = Math.abs(p.z() - anchor.z());

        if (x_l > x_size_half || z_l > z_size_half)
            return null;

        // Treffer erzeugen und zurückgeben
        return new Hit(t, p, direction(0, 1, 0), material);
    }
}
