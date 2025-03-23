package H_beschleunigung;

import static lib_cgtools.Vector.*;

import lib_cgtools.*;

/*
 * Die Sphere-Klasse implementiert das Shape-Interface und stellt eine Kugel dar.
 * Sie berechnet den Schnittpunkt eines Strahls mit der Kugel und liefert die 
 * entsprechende Materialinformationen und Normale am Schnittpunkt.
 */
public class Sphere implements Shape {
    private Point center;   // Mittelpunkt der Kugel
    private double radius;  // Radius der Kugel
    private Material material;  // Material der Kugel

    private Point p_min;  // Die untere Ecke der Bounding Box (Min-Point)
    private Point p_max;  // Die obere Ecke der Bounding Box (Max-Point)
    private BoundingBox boundingBox;  // Die Bounding Box der Kugel

    /*
     * Konstruktor der Sphere-Klasse. Initialisiert die Kugel mit Mittelpunkt,
     * Radius und Material und berechnet die Bounding Box.
     */
    public Sphere(Point center, double radius, Material material) {
        this.center = center;
        this.radius = radius;
        this.material = material;

        // Berechnung der Bounding Box: Die Bounding Box umschließt die Kugel
        p_min = new Point((center.x() - radius), (center.y() - radius), (center.z() - radius));
        p_max = new Point((center.x() + radius), (center.y() + radius), (center.z() + radius));
        boundingBox = new BoundingBox(p_min, p_max);
    }

    @Override
    public BoundingBox bounds() {
        return boundingBox;  // Rückgabe der Bounding Box der Kugel
    }

    /*
     * Diese Methode prüft, ob der übergebene Ray (Strahl) mit der Kugel schneidet.
     * Wenn ein Schnittpunkt existiert, wird das Hit-Objekt zurückgegeben, 
     * das Details zum Schnittpunkt enthält (z.B. die Position, Normale, Material, u/v-Koordinaten).
     */
    @Override
    public Hit intersect(Ray r) {
        // Diskriminante für die quadratische Gleichung zur Bestimmung der Schnittpunkte
        Point centerOrigin = subtract(r.origin(), subtract(center, new Point(0, 0, 0)));
        double a = dotProduct(r.direction(), r.direction());
        double b = 2 * dotProduct(centerOrigin, r.direction());
        double c = dotProduct(centerOrigin, centerOrigin) - Math.pow(radius, 2);
        double discriminant = Math.pow(b, 2) - 4 * a * c;

        // Es gibt zwei Schnittpunkte (t1 und t2) im Falle eines positiven Diskriminanten
        if (discriminant > 0) {
            double t1 = ((-b + Math.sqrt(discriminant))) / (2 * a);  // Lösung der quadratischen Gleichung
            double t2 = ((-b - Math.sqrt(discriminant))) / (2 * a);  // Lösung der quadratischen Gleichung
            double t = Math.min(t1, t2);  // Wähle den kleineren Wert (nächster Schnittpunkt)
            if (t < 0) {
                return null;  // Wenn der Schnittpunkt hinter dem Strahlursprung liegt, ignorieren
            }
            if (r.isValid(t)) {  // Überprüfen, ob der Treffer gültig ist
                Point hitP = r.pointAt(t);  // Berechne den Trefferpunkt
                Direction normalV = divide((subtract(hitP, center)), radius);  // Normale an der Stelle des Schnittpunkts
                Direction hitPV = subtract(hitP, center);  // Vektor vom Zentrum der Kugel zum Trefferpunkt
                double teta = Math.PI - Math.acos(hitPV.y() / this.radius);  // Berechne den polarwinkel (teta)
                double phi = Math.PI + Math.atan2(hitPV.x(), hitPV.z());  // Berechne den azimutwinkel (phi)
                
                // Berechne die UV-Koordinaten für Texturmapping
                double u = phi / (2.0 * Math.PI);
                double v = (teta / Math.PI);

                return new Hit(t, r.pointAt(t), normalV, material, u, v);  // Rückgabe des Hit-Objekts
            } 
        }
        
        // Falls der Diskriminant null ist, gibt es einen einzigen Schnittpunkt
        if (discriminant == 0) {
            double t = (-b / (2 * a));  // Berechne den einzigen Schnittpunkt
            if(t < 0) {
                return null;  // Wenn der Schnittpunkt hinter dem Strahlursprung liegt, ignorieren
            }
            Point hitP = r.pointAt(t);  // Berechne den Trefferpunkt
            Direction normalV = divide((subtract(hitP, center)), radius);  // Normale an der Stelle des Schnittpunkts
            Direction hitPV = subtract(hitP, center);  // Vektor vom Zentrum der Kugel zum Trefferpunkt
            double teta = Math.PI - Math.acos(hitPV.y() / this.radius);  // Berechne den polarwinkel (teta)
            double phi = Math.PI + Math.atan2(hitPV.x(), hitPV.z());  // Berechne den azimutwinkel (phi)
                
            // Berechne die UV-Koordinaten für Texturmapping
            double u = phi / (2.0 * Math.PI);
            double v = (teta / Math.PI);
                
            return new Hit(t, r.pointAt(t), normalV, material, u, v);  // Rückgabe des Hit-Objekts
        }
        
        return null;  // Falls keine Schnittpunkte existieren, gebe null zurück
    }

    // Getter für den Radius der Kugel
    public double getRadius() {
        return radius;
    }

    // Getter für das Material der Kugel
    public Material getMaterial() {
        return material;
    }
}
