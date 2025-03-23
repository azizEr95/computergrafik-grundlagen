package G_rekursiv_pathtracing;

import lib_cgtools.*;
import static lib_cgtools.Vector.*;

/*
 * Die Klasse `DirectionalLight` repräsentiert eine gerichtete Lichtquelle, die
 * in eine feste Richtung abstrahieren kann. Es handelt sich um eine spezifische
 * Implementierung der `DirectLight`-Schnittstelle, bei der das Licht in eine
 * konstante Richtung strahlt (z. B. Sonnenlicht, das aus einer festen Richtung kommt).
 */
public class DirectionalLight implements DirectLight {
    
    private Direction lightDirection;  // Richtung, in die das Licht abstrahlt
    private Color lightColor;  // Farbe des Lichts

    /*
     * Konstruktor für die DirectionalLight-Klasse.
     * Die Richtung des Lichts und die Farbe werden übergeben und gespeichert.
     */
    public DirectionalLight(Direction lightDirection, Color lightColor) {
        this.lightDirection = lightDirection;  // Richtung des Lichts
        this.lightColor = lightColor;  // Farbe des Lichts
    }
    
    /*
     * Berechnet die Richtung des Lichts von einer gegebenen Position aus.
     * Das Licht kommt aus einer festen Richtung, daher negieren wir die
     * Richtung des Lichts und normalisieren den Vektor, um die Einheit zu erhalten.
     */
    @Override
    public Direction directionToSource(Point position) {
        return normalize(negate(lightDirection));  // Negieren und Normalisieren der Lichtquelle
    }

    /*
     * Gibt die Farbe des Lichts zurück, die von dieser Lichtquelle abstrahlt.
     */
    @Override
    public Color incomingLight(Point position) {
        return lightColor;  // Gibt die Farbe des Lichts zurück
    }

    /*
     * Überprüft, ob das Licht von einem bestimmten Punkt aus sichtbar ist,
     * d.h. ob ein Objekt zwischen dem Punkt und der Lichtquelle im Weg steht.
     * Wenn der Strahl auf ein Objekt trifft, bedeutet das, dass es Schatten wirft
     * und die Lichtquelle nicht sichtbar ist.
     */
    @Override
    public boolean isVisible(Point position, Shape shapes) {
        Direction toSource = directionToSource(position);  // Berechnet die Richtung des Lichts
        double epsilon = 1e-6;  // Kleine Verschiebung, um Selbst-Treffer zu vermeiden

        // Erstellen eines Strahls, der von der gegebenen Position in Richtung der Lichtquelle geht
        Ray ray = new Ray(position, toSource, epsilon, Double.MAX_VALUE);

        // Wenn der Strahl auf ein Objekt trifft, bedeutet das, dass es Schatten wirft
        // und die Lichtquelle nicht sichtbar ist
        Hit shadowHit = shapes.intersect(ray);  // Überprüft, ob der Strahl mit einem Objekt kollidiert
        return shadowHit == null;  // Kein Treffer bedeutet, dass das Licht sichtbar ist
    }

    /*
     * Erzeugt einen Strahl, der von einer gegebenen Position aus in Richtung der Lichtquelle geht,
     * um Schatten zu berechnen.
     */
    @Override
    public Ray shadowRay(Point position) {
        Direction toSource = directionToSource(position);  // Berechnet die Richtung des Lichts
        double epsilon = 1e-6;  // Kleine Verschiebung, um Selbst-Treffer zu vermeiden
        Ray ray = new Ray(position, toSource, epsilon, Double.MAX_VALUE);  // Erzeugt einen Strahl
        return ray;  // Gibt den Schattenstrahl zurück
    }

    /*
     * Gibt die Richtung des Lichts zurück.
     */
    public Direction getlighDirection() {
        return lightDirection;  // Gibt die Richtung des Lichts zurück
    }

    /*
     * Gibt die Farbe des Lichts zurück.
     */
    public Color getlightColor() {
        return lightColor;  // Gibt die Farbe des Lichts zurück
    }
}
