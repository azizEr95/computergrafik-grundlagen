package H_beschleunigung;

import lib_cgtools.*;

/*
 * Das Interface `DirectLight` repräsentiert eine allgemeine Lichtquelle,
 * die von einer bestimmten Position aus gesehen wird. Jede Klasse, die dieses
 * Interface implementiert, muss Methoden bereitstellen, um die Richtung des Lichts
 * zu berechnen, die Lichtmenge zu bestimmen, die auf eine Position trifft, und
 * um zu überprüfen, ob das Licht von einer gegebenen Position sichtbar ist.
 */
public interface DirectLight {

    /*
     * Berechnet den Richtungsvektor von einer gegebenen Position zur Lichtquelle.
     * Diese Methode gibt die Richtung des Lichts relativ zur Position zurück.
     * Der Vektor zeigt auf die Lichtquelle.
     */
    public Direction directionToSource(Point position);

    /*
     * Berechnet die Menge des Lichts, das von der Lichtquelle an eine gegebene
     * Position ankommt. Diese Methode gibt die Farbe des Lichts zurück, das
     * an der gegebenen Position angekommen ist.
     */
    public Color incomingLight(Point position);

    /*
     * Überprüft, ob die Lichtquelle von der gegebenen Position aus sichtbar ist.
     * Diese Methode gibt `true` zurück, wenn die Lichtquelle sichtbar ist (d.h.
     * keine Objekte im Weg stehen) und `false`, wenn die Lichtquelle blockiert wird.
     */
    public boolean isVisible(Point position, Shape shape);

    /*
     * Gibt einen Strahl zurück, der von der gegebenen Position zur Lichtquelle geht.
     * Dieser Strahl wird häufig verwendet, um Schatten zu berechnen und zu überprüfen,
     * ob ein Objekt das Licht blockiert.
     */
    public Ray shadowRay(Point position);
}
