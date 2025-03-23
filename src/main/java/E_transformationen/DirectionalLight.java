package E_transformationen;

import lib_cgtools.*;
import static lib_cgtools.Vector.*;

/*
 * Die DirectionalLight-Klasse repräsentiert eine gerichtete Lichtquelle (z.B. Sonnenlicht),
 * die eine konstante Richtung im gesamten Szenenraum hat. Dieses Lichtmodell ermöglicht 
 * eine einfache Implementierung von Schatten, da die Lichtstrahlen parallel verlaufen.
 */
public class DirectionalLight implements DirectLight {
    private Direction lightDirection; // Richtung, aus der das Licht kommt
    private Color lightColor;         // Farbe des Lichts

    /*
     * Konstruktor zur Initialisierung der Lichtquelle mit einer bestimmten Richtung und Farbe.
     * - lightDirection: Die Richtung, in die das Licht strahlt.
     * - lightColor: Die Farbe des Lichts.
     */
    public DirectionalLight(Direction lightDirection, Color lightColor) {
        this.lightDirection = lightDirection;
        this.lightColor = lightColor;
    }
    
    /*
     * Berechnet die Richtung zum Ursprung der Lichtquelle von einem gegebenen Punkt aus.
     * Da es sich um eine gerichtete Lichtquelle handelt, ist die Richtung immer konstant
     * und wird negiert, um von der Szene zur Lichtquelle zu zeigen.
     * - position: Der Punkt in der Szene, von dem aus die Lichtquelle betrachtet wird.
     * Rückgabewert: Der normalisierte Richtungsvektor zur Lichtquelle.
     */
    @Override
    public Direction directionToSource(Point position) {
        return normalize(negate(lightDirection));
    }

    /*
     * Gibt die Farbe des Lichts zurück.
     * Da das Licht eine gleichmäßige Intensität besitzt, hängt die Farbe nicht vom
     * Punkt in der Szene ab.
     * - position: Der Punkt in der Szene (nicht relevant für gerichtetes Licht).
     * Rückgabewert: Die konstante Farbe der Lichtquelle.
     */
    @Override
    public Color incomingLight(Point position) {
        return lightColor;
    }

    /*
     * Überprüft, ob das Licht eine gegebene Position in der Szene erreichen kann oder
     * ob es durch ein anderes Objekt blockiert wird (Schattenwurf).
     * - position: Der Punkt, von dem aus überprüft wird, ob Licht ankommt.
     * - shapes: Die Szene bzw. die Objekte, die auf Lichtstrahlen geprüft werden.
     * Rückgabewert: `true`, wenn die Position direkt beleuchtet wird (kein Objekt blockiert das Licht),
     *               `false`, wenn die Position im Schatten liegt (ein Objekt blockiert das Licht).
     */
    @Override
    public boolean isVisible(Point position, Shape shapes) {
        Direction toSource = directionToSource(position); // Richtung zur Lichtquelle
        double epsilon = 1e-6; // Kleiner Wert zur Vermeidung von Selbsttreffern

        // Erstellt einen Schattenstrahl, der von der Position in Richtung Lichtquelle geht
        // Der Strahl beginnt knapp über der Oberfläche (epsilon) und geht bis unendlich
        Ray ray = new Ray(position, toSource, epsilon, Double.MAX_VALUE);

        // Prüft, ob der Strahl auf ein Objekt trifft, bevor er die Lichtquelle erreicht
        // Falls ja, liegt die Position im Schatten
        Hit shadowHit = shapes.intersect(ray);
        return shadowHit == null;
    }

    /*
     * Erstellt einen Schattenstrahl von einer gegebenen Position in Richtung der Lichtquelle.
     * Dieser Strahl wird verwendet, um zu prüfen, ob ein Objekt zwischen der Position und
     * der Lichtquelle liegt.
     * - position: Der Punkt in der Szene, von dem aus der Schattenstrahl erzeugt wird.
     * Rückgabewert: Ein `Ray`, der von der Position in Richtung Lichtquelle verläuft.
     */
    @Override
    public Ray shadowRay(Point position) {
        Direction toSource = directionToSource(position);
        double epsilon = 1e-6;
        return new Ray(position, toSource, epsilon, Double.MAX_VALUE);
    }

    /*
     * Gibt die Richtung der Lichtquelle zurück.
     */
    public Direction getLightDirection() {
        return lightDirection;
    }

    /*
     * Gibt die Farbe der Lichtquelle zurück.
     */
    public Color getLightColor() {
        return lightColor;
    }
}
