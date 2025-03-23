package H_beschleunigung;

import lib_cgtools.*;
import static lib_cgtools.Vector.*;

/*
 * Diese Klasse implementiert eine Punktlichtquelle, die eine spezifische Position und Farbe hat.
 * Sie berechnet die Richtung des Lichts, die Intensität des Lichts und überprüft, ob das Licht
 * von einer gegebenen Position sichtbar ist.
 */
public class PointLight implements DirectLight {

    private Point ligthPosition; // Die Position des Punktlichts
    private Color lightColor; // Die Farbe des Lichts

    /*
     * Konstruktor für das PointLight. Setzt die Position und die Lichtfarbe.
     */
    public PointLight(Point lightPosition, Color lightColor) {
        this.ligthPosition = lightPosition; // Setzt die Position des Lichts
        this.lightColor = lightColor; // Setzt die Farbe des Lichts
    }

    /*
     * Berechnet die Richtung von einem Punkt zur Lichtquelle. 
     * Dies wird durch den Vektor vom Punkt zur Lichtquelle angegeben.
     */
    @Override
    public Direction directionToSource(Point position) {
        return normalize(subtract(ligthPosition, position)); // Normalisiert den Vektor von der Position zur Lichtquelle
    }

    /*
     * Berechnet die Lichtintensität, die auf einem bestimmten Punkt empfangen wird.
     * Die Intensität hängt von der Entfernung zum Lichtpunkt ab und folgt einer inversen quadratischen
     * Abnahme (1/r^2), wobei r die Entfernung zwischen dem Punkt und der Lichtquelle ist.
     */
    @Override
    public Color incomingLight(Point position) {
        double nenner = 1.0 / Math.pow(length(subtract(ligthPosition, position)), 2); // Berechnet die quadratische Entfernung
        return multiply(lightColor, nenner); // Multipliziert die Lichtfarbe mit dem Inversen der quadratischen Entfernung
    }

    /*
     * Überprüft, ob die Lichtquelle von einer gegebenen Position aus sichtbar ist. 
     * Es wird ein Schattenstrahl erzeugt und überprüft, ob er mit einem der Objekte in der Szene kollidiert.
     */
    @Override
    public boolean isVisible(Point position, Shape shapes) {
        Direction toSource = directionToSource(position); // Berechnet die Richtung zum Licht
        double epsilon = 1e-6; // Ein kleiner Wert, um numerische Probleme zu vermeiden

        // Erzeugt einen Schattenstrahl, der etwas entfernt vom Punkt beginnt, um Selbsttreffer zu vermeiden,
        // und das Ende des Strahls wird so gesetzt, dass es vor der Lichtquelle endet.
        Ray shadowRay = new Ray(position, toSource, epsilon, length(subtract(ligthPosition, position)));

        // Prüft, ob der Schattenstrahl mit einem Objekt in der Szene kollidiert.
        // Wenn kein Treffer gefunden wurde, bedeutet dies, dass das Licht sichtbar ist.
        Hit shadowHit = shapes.intersect(shadowRay);
        return shadowHit == null; // Rückgabe true, wenn kein Treffer gefunden wurde (Licht sichtbar)
    }

    /*
     * Erzeugt einen Schattenstrahl von der gegebenen Position zur Lichtquelle.
     * Der Schattenstrahl kann verwendet werden, um zu überprüfen, ob der Punkt im Schatten ist.
     */
    @Override
    public Ray shadowRay(Point position) {
        Direction toSource = directionToSource(position); // Berechnet die Richtung zum Licht
        double epsilon = 1e-6; // Ein kleiner Wert, um numerische Probleme zu vermeiden
        return new Ray(position, toSource, epsilon, length(subtract(ligthPosition, position))); // Gibt den Schattenstrahl zurück
    }
}
