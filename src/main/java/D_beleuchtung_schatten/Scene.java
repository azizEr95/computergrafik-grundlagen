package D_beleuchtung_schatten;

import java.util.List;

/**
 * Die Scene-Klasse repräsentiert eine Szene im Raytracer.
 * Sie besteht aus einer Sammlung von Objekten (Shapes) und Lichtquellen (DirectLights).
 * 
 * - Die Objekte in der Szene werden für die Berechnung von Strahlen-Schnittpunkten verwendet.
 * - Die Lichtquellen bestimmen, wie die Objekte beleuchtet werden.
 * 
 * Die Klasse ist als Java-Record implementiert, wodurch sie automatisch Konstruktor, Getter 
 * und Methoden wie equals() und hashCode() generiert.
 */
public record Scene(Shape shapes, List<DirectLight> lights) {
}

