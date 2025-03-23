package E_transformationen;

import lib_cgtools.*;

/*
 * Das Interface DirectLight definiert die grundlegenden Methoden für Lichtquellen
 * in einer Raytracing-Szene. Es ermöglicht die Berechnung der Lichtintensität, 
 * Lichtstrahlrichtung und Sichtbarkeit eines Punktes in Bezug auf die Lichtquelle.
 */
public interface DirectLight {

    /*
     * Berechnet die Richtung von einem gegebenen Punkt zur Lichtquelle.
     * - position: Der Punkt in der Szene, für den die Richtung zur Lichtquelle bestimmt wird.
     * Rückgabewert: Der Richtungsvektor von der Position zur Lichtquelle.
     */
    public Direction directionToSource(Point position);

    /*
     * Gibt die Lichtfarbe und Intensität zurück, die von der Lichtquelle an einem
     * bestimmten Punkt in der Szene ankommt.
     * - position: Der Punkt in der Szene.
     * Rückgabewert: Die Lichtfarbe an dieser Position.
     */
    public Color incomingLight(Point position);

    /*
     * Überprüft, ob das Licht eine gegebene Position erreichen kann oder ob es durch
     * ein anderes Objekt blockiert wird (Schattenwurf).
     * - position: Der Punkt, für den überprüft wird, ob er beleuchtet wird.
     * - shape: Die Szene oder Objekte, die als Hindernisse für das Licht wirken können.
     * Rückgabewert: `true`, wenn die Position beleuchtet wird (kein Schatten),
     *               `false`, wenn die Position im Schatten liegt.
     */
    public boolean isVisible(Point position, Shape shape);

    /*
     * Erstellt einen Schattenstrahl (Shadow Ray) von einer gegebenen Position zur Lichtquelle.
     * Dieser Strahl wird verwendet, um zu prüfen, ob ein Objekt das Licht blockiert.
     * - position: Der Punkt, von dem aus der Schattenstrahl erzeugt wird.
     * Rückgabewert: Ein `Ray`, der von der Position zur Lichtquelle zeigt.
     */
    public Ray shadowRay(Point position);
}
