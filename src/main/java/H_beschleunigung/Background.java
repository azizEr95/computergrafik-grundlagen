package H_beschleunigung;

import lib_cgtools.*;
import static lib_cgtools.Vector.*;



public class Background implements Shape {

    private Material material;
    
    private Point p_min;
    private Point p_max;
    private BoundingBox boundingBox;

    public Background(Material material) {
        this.material = material;

        p_min = new Point(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
        p_max = new Point(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        boundingBox = new BoundingBox(p_min, p_max);
    }

    @Override
    public BoundingBox bounds() {
        return boundingBox;
    }

    /*
     * überprüft, ob ein gegebener Ray mit dem Background in der Szene 
     * schneidet und gibt ein Hit zurück.
     * t ist gleich unendlich um zu simulieren, dass t immer den Background trifft
     * die Direction des rays muss negiert werden, damit er in die entgegengesetzte Richtung zeigt
     */
    @Override
    public Hit intersect(Ray ray) {
        double t = Double.POSITIVE_INFINITY;
        if(Double.isFinite(ray.tMax())) {
            return null;
        }
        Point hitPoint = ray.pointAt(t); //t wird immer getroffen
        Direction normal = negate(ray.direction()); //damit die Normale in die entgegengesetzte Richtung zeugt
        return new Hit(t, hitPoint, normal, material, 0, 0); // geb den Treffer zurück
    }

    public Material getMaterial() {
        return material;
    }
}
