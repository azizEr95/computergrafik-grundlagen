package G_rekursiv_pathtracing;

import lib_cgtools.*;
import static lib_cgtools.Vector.*;

/*
 * Diese Klasse repräsentiert den Hintergrund (Background) in einer Szene.
 * Sie implementiert das Shape-Interface und behandelt den Schnittpunkt von Rays mit dem Hintergrund.
 */
public class Background implements Shape {

    private Material material;  // Material des Hintergrundes

    /*
     * Konstruktor: Initialisiert den Hintergrund mit dem angegebenen Material.
     */
    public Background(Material material) {
        this.material = material;  // Setzt das Material des Hintergrundes
    }

    /*
     * Berechnet den Schnittpunkt eines gegebenen Rays mit dem Hintergrund.
     * Ein Treffer wird immer an "t = unendlich" zurückgegeben, was bedeutet, dass der 
     * Ray immer mit dem Hintergrund schneidet, wenn er nicht mit etwas anderem in der Szene 
     * kollidiert. Die Richtung des Rays wird negiert, um sicherzustellen, dass die Normale 
     * des Hintergrunds in die entgegengesetzte Richtung zeigt.
     */
    @Override
    public Hit intersect(Ray ray) {
        double t = Double.POSITIVE_INFINITY;  // Ein sehr großer Wert, um den Hintergrund immer zu treffen
        
        // Wenn der t-Wert des Rays nicht endlich ist, bedeutet das, dass der Ray keine Endpunkte hat (ungültig),
        // und es gibt daher keinen Schnittpunkt mit dem Hintergrund.
        if(Double.isFinite(ray.tMax())) {
            return null;  // Es gibt keinen Schnittpunkt, wenn der Ray ungültig ist
        }
        
        // Berechnet den Punkt auf dem Ray, der den Hintergrund trifft
        Point hitPoint = ray.pointAt(t);  // t ist immer positiv unendlich, der Trefferpunkt wird daher immer hier liegen
        
        // Die Normale des Hintergrundes zeigt in die entgegengesetzte Richtung des Rays, 
        // daher negieren wir die Richtung des Rays, um die richtige Normale zu erhalten
        Direction normal = negate(ray.direction());  // Normale des Hintergrunds nach der Negation der Ray-Richtung
        
        // Rückgabe eines Hits mit den berechneten Werten (t, hitPoint, normal, material, u, v)
        return new Hit(t, hitPoint, normal, material, 0, 0);  // u und v sind hier immer 0, da es keine Texturen gibt
    }

    /*
     * Getter-Methode für das Material des Hintergrundes.
     */
    public Material getMaterial() {
        return material;  // Gibt das Material des Hintergrundes zurück
    }
}
