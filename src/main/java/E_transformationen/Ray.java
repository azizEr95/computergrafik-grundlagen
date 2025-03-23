package E_transformationen;

import lib_cgtools.*;
import static lib_cgtools.Vector.*;


public record Ray(Point origin, Direction direction, double tMin, double tMax) {

    /*
     * Die Methode pointAt berechnet den Punkt auf dem Strahl, der dem Parameter t entspricht.
     * Formel: P(t) = origin + t * direction (wobei direction normalisiert wird)
     */
    public Point pointAt(double t) {
        return add(origin, multiply(t, direction));
    }

    /*
     * Die Methode isValid prüft, ob der Parameter t im gültigen Bereich [tMin, tMax] liegt.
     * Ein gültiger Wert für t bedeutet, dass der Strahl innerhalb des Bereichs durch den Vektor verläuft.
     */
    public boolean isValid(double t) {
        return (t >= tMin && t <= tMax);  // Gibt true zurück, wenn t im gültigen Bereich liegt
    }
}
