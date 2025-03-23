package D_beleuchtung_schatten;

import lib_cgtools.*;
import static lib_cgtools.Vector.*;

/*
 * Die Klasse PointLight implementiert eine punktförmige Lichtquelle,
 * die Licht in alle Richtungen abstrahlt. Die Intensität des Lichts
 * nimmt mit dem Quadrat der Entfernung zum Lichtpunkt ab.
 */
public class PointLight implements DirectLight {

    private Point lightPosition; // Position der Punktlichtquelle im Raum
    private Color lightColor;    // Farbe und Intensität des Lichts

    /*
     * Konstruktor für die Punktlichtquelle.
     * 
     * - lightPosition: Die Position des Lichts im 3D-Raum.
     * - lightColor: Die Farbe und Intensität des emittierten Lichts.
     */
    public PointLight(Point lightPosition, Color lightColor) {
        this.lightPosition = lightPosition;
        this.lightColor = lightColor;
    }

    /*
     * Berechnet die normierte Richtung vom gegebenen Punkt zur Lichtquelle.
     * 
     * - position: Der Punkt, für den die Lichtquellenrichtung bestimmt wird.
     * Rückgabewert: Normalisierter Richtungsvektor von position zur Lichtquelle.
     */
    @Override
    public Direction directionToSource(Point position) {
        return normalize(subtract(lightPosition, position));
    }

    /*
     * Berechnet die Lichtintensität, die von der Punktlichtquelle am gegebenen Punkt ankommt.
     * Die Intensität nimmt mit dem Quadrat der Entfernung ab.
     *
     * - position: Der Punkt, an dem die einfallende Lichtintensität berechnet wird.
     * Rückgabewert: Lichtfarbe skaliert mit dem inversen Quadrat der Entfernung.
     */
    @Override
    public Color incomingLight(Point position) {
        double distanceSquared = Math.pow(length(subtract(lightPosition, position)), 2);
        double attenuation = 1.0 / distanceSquared; // 1 / (Entfernung²)
        return multiply(lightColor, attenuation);
    }

    /*
     * Prüft, ob die Lichtquelle von einem bestimmten Punkt aus sichtbar ist oder durch ein Objekt blockiert wird.
     * Wenn kein Schnittpunkt mit einer Geometrie gefunden wird, ist die Lichtquelle sichtbar.
     *
     * - position: Der Punkt, von dem aus überprüft wird, ob das Licht sichtbar ist.
     * - shapes: Das Objekt, das möglicherweise einen Schatten wirft.
     * Rückgabewert: true, wenn kein Objekt das Licht blockiert, sonst false.
     */
    @Override
    public boolean isVisible(Point position, Shape shapes) {
        Direction toSource = directionToSource(position);
        double epsilon = 1e-6;

        // Erzeugt einen Strahl, der von der Position in Richtung der Lichtquelle verläuft.
        // Der Strahl beginnt leicht versetzt, um Selbsttreffer zu vermeiden.
        Ray shadowRay = new Ray(position, toSource, epsilon, length(subtract(lightPosition, position)));

        // Falls ein Schnittpunkt gefunden wird, ist die Lichtquelle nicht sichtbar.
        Hit shadowHit = shapes.intersect(shadowRay);
        return shadowHit == null;
    }

    /*
     * Erstellt einen Schattenstrahl von einer gegebenen Position zur Lichtquelle.
     * Der Schattenstrahl wird für Schattentests verwendet.
     *
     * - position: Der Punkt, von dem aus der Schattenstrahl erzeugt wird.
     * Rückgabewert: Schattenstrahl in Richtung der Lichtquelle.
     */
    @Override
    public Ray shadowRay(Point position) {
        Direction toSource = directionToSource(position);
        double epsilon = 1e-6;
        return new Ray(position, toSource, epsilon, length(subtract(lightPosition, position)));
    }
}
