package C_szene_background_ebene;

import lib_cgtools.*;
import static lib_cgtools.Vector.*;


public record Ray(Point origin, Direction direction, double tMin, double tMax) {

    /*
     * Die Methode pointAt berechnet den Punkt auf dem Strahl, der dem Parameter t entspricht.
     * t wird überprüft, ob es im gültigen Bereich zwischen tMin und tMax liegt.
     * Falls t im gültigen Bereich liegt, wird der Punkt auf dem Strahl basierend auf der Richtung berechnet.
     * 
     * Formel: P(t) = origin + t * direction (wobei direction normalisiert wird)
     */
    public Point pointAt(double t) {
        if (isValid(t) && t >= 0) {  // Prüft, ob t im gültigen Bereich liegt und nicht negativ ist
            Direction directionNormal = normalize(direction);  // Normalisiert den Richtungsvektor
            return add(multiply(t, directionNormal), origin);   // Berechnet den Punkt auf dem Strahl
        }
        return null;  // Gibt null zurück, falls t ungültig ist
    }

    /*
     * Die Methode isValid prüft, ob der Parameter t im gültigen Bereich [tMin, tMax] liegt.
     * Ein gültiger Wert für t bedeutet, dass der Strahl innerhalb des Bereichs durch den Vektor verläuft.
     */
    public boolean isValid(double t) {
        return (t >= tMin && t <= tMax);  // Gibt true zurück, wenn t im gültigen Bereich liegt
    }
}
