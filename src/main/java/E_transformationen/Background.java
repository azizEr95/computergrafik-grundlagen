package E_transformationen;

import lib_cgtools.*;
import static lib_cgtools.Vector.*;


/*
 * Die Background-Klasse repräsentiert den Hintergrund einer Raytracing-Szene.
 * Sie definiert eine unendlich große Fläche, die immer getroffen wird, wenn 
 * kein anderes Objekt im Strahlengang liegt. Dadurch wird eine Umgebungsszene simuliert.
 */

public class Background implements Shape {

    private Material material;  // Die Farbe des Hintergrunds

    public Background(Material material) {
        this.material = material;
    }

    /*
     * Überprüft, ob ein gegebener Strahl (Ray) den Hintergrund trifft.
     * Da der Hintergrund unendlich weit entfernt ist, wird immer eine 
     * Schnittstelle erzeugt, wenn kein anderes Objekt getroffen wird.
     * 
     * - ray: Der Strahl, der auf den Hintergrund trifft.
     * Rückgabewert: Ein Hit-Objekt, das den Trefferpunkt und die Eigenschaften speichert.
     */
    @Override
    public Hit intersect(Ray ray) {
        // Setzt t auf unendlich, da der Hintergrund immer im Unendlichen getroffen wird
        double t = Double.POSITIVE_INFINITY;
        if(Double.isFinite(ray.tMax())) {
            return null;
        }
        // Berechnet den Treffpunkt entlang des Strahls (wird hier nicht direkt genutzt)
        Point hitPoint = ray.pointAt(t); 

        // Die Normale zeigt genau entgegengesetzt zur Strahlrichtung, um eine konsistente Beleuchtung zu ermöglichen
        Direction normal = negate(ray.direction());

        // Gibt ein Hit-Objekt mit Trefferinformationen zurück
        return new Hit(t, hitPoint, normal, material);
    }

    /*
     * Gibt das Material des Hintergrunds zurück.
     */
    public Material getMaterial() {
        return material;
    }
}

