package F_texturen;

import lib_cgtools.*;
import static lib_cgtools.Vector.*;

/*
 * Die Klasse PointLight implementiert das DirectLight-Interface und stellt ein Punktlicht dar.
 * Ein Punktlicht hat eine feste Position und strahlt Licht in alle Richtungen aus.
 */
public class PointLight implements DirectLight {

    private Point ligthPosition; // Position des Punktlichts
    private Color lightColor;    // Farbe des Lichts (Intensität und Farbe)

    /*
     * Konstruktor, um die Position und Farbe des Punktlichts zu setzen.
     */
    public PointLight(Point lightPosition, Color lightColor) {
        this.ligthPosition = lightPosition;
        this.lightColor = lightColor;
    }

    /*
     * Berechnet die Richtung von der gegebenen Position (z.B. von einem Punkt auf einem Objekt)
     * zur Lichtquelle. Diese Richtung wird durch den normalisierten Vektor zur Lichtquelle dargestellt.
     */
    @Override
    public Direction directionToSource(Point position) {
        return normalize(subtract(ligthPosition, position));  // Normalisiert den Vektor von position zur Lichtquelle
    }

    /*
     * Berechnet die Lichtintensität, die von der Lichtquelle an einem gegebenen Punkt empfangen wird.
     * Der Lichtfall wird durch den Abstand zur Lichtquelle und die Lichtfarbe beeinflusst.
     */
    @Override
    public Color incomingLight(Point position) {
        // Berechnet den Abstand zwischen der Lichtquelle und der Position
        double nenner = 1.0 / Math.pow(length(subtract(ligthPosition, position)), 2);  // Berechnet den Abstand zur Lichtquelle im Quadrat
        return multiply(lightColor, nenner);  // Berechnet die Lichtintensität durch Multiplikation mit dem Umkehrwert des Abstands
    }

    /*
     * Überprüft, ob das Punktlicht von der gegebenen Position aus sichtbar ist.
     * Wenn ein Hindernis zwischen der Position und dem Licht liegt, wird der Punkt als nicht sichtbar betrachtet.
     */
    @Override
    public boolean isVisible(Point position, Shape shapes) {
        // Berechnet die Richtung vom Punkt zur Lichtquelle
        Direction toSource = directionToSource(position); 
        
        // Epsilon-Wert, um kleine numerische Fehler zu vermeiden
        double epsilon = 1e-6;

        // Erzeugt einen Schattenstrahl, der von der gegebenen Position in Richtung der Lichtquelle zeigt
        Ray shadowRay = new Ray(position, toSource, epsilon, length(subtract(ligthPosition, position)));
        
        // Überprüft, ob der Schattenstrahl ein Objekt trifft. Wenn ja, ist das Licht nicht sichtbar.
        Hit shadowHit = shapes.intersect(shadowRay);
        
        // Gibt true zurück, wenn das Licht sichtbar ist (kein Treffer), andernfalls false
        return shadowHit == null;
    }

    /*
     * Erzeugt den Schattenstrahl, der von der gegebenen Position zur Lichtquelle führt.
     * Der Strahl wird leicht versetzt, um Selbstintersektionen zu vermeiden.
     */
    @Override
    public Ray shadowRay(Point position) {
        Direction toSource = directionToSource(position);  // Berechnet die Richtung zur Lichtquelle
        double epsilon = 1e-6;  // Epsilon-Wert, um numerische Fehler zu vermeiden
        // Gibt einen neuen Ray zurück, der von der gegebenen Position zur Lichtquelle zeigt
        return new Ray(position, toSource, epsilon, length(subtract(ligthPosition, position)));
    }
}
