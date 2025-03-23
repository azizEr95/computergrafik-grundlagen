package G_rekursiv_pathtracing;

import lib_cgtools.*;
import java.util.ArrayList;
import static lib_cgtools.Vector.*;

/*
 * Die `Group`-Klasse repräsentiert eine Sammlung von `Shape`-Objekten, die zusammen in einem
 * globalen Transformationsrahmen transformiert werden können. Diese Klasse ermöglicht das Gruppieren
 * von geometrischen Formen, sodass diese als eine einzige Entität behandelt werden können.
 * Die Gruppe kann mit einer Transformationsmatrix versehen werden, und jedes `Shape` in der Gruppe wird
 * unter dieser Transformation gehandhabt.
 */
public class Group implements Shape {

    private ArrayList<Shape> forms;  // Eine Liste von Formen, die zur Gruppe gehören
    private Matrix matrix;  // Die Transformationsmatrix, die auf die Gruppe angewendet wird
    private Matrix inverseMatrix;  // Die Inverse der Transformationsmatrix
    private Matrix transposedInverseMatrix;  // Die transponierte Inverse der Transformationsmatrix

    /*
     * Konstruktor: Erzeugt eine neue Gruppe mit einer Transformationsmatrix.
     * Die Matrix wird gespeichert, ebenso ihre Inverse und transponierte Inverse für spätere Berechnungen.
     */
    public Group(Matrix matrix) {
        this.forms = new ArrayList<>();
        this.matrix = matrix;
        this.inverseMatrix = Matrix.invert(matrix);  // Inverse der Matrix für Transformationsberechnungen
        this.transposedInverseMatrix = Matrix.transpose(this.inverseMatrix);  // Transponierte Inverse der Matrix
    }

    /*
     * Fügt eine Form zur Gruppe hinzu.
     * Diese Methode speichert eine neue Form in der `forms`-Liste.
     */
    public void add(Shape shape) {
        forms.add(shape);  // Die Form wird der Liste der Formen hinzugefügt
    }

    /*
     * Setzt die Transformationsmatrix auf eine neue Matrix.
     * Multipliziert die aktuelle Matrix mit der neuen Transformationsmatrix und aktualisiert
     * die Inverse und die transponierte Inverse.
     */
    public void setTransformation(Matrix newTransformationMatrix) {
        // Multipliziere die aktuelle Matrix mit der neuen Transformationsmatrix
        this.matrix = Matrix.multiply(newTransformationMatrix, this.matrix);

        // Berechne die Inverse und transponierte Inverse der neuen Matrix
        this.inverseMatrix = Matrix.invert(this.matrix);
        this.transposedInverseMatrix = Matrix.transpose(this.inverseMatrix);
    }

    /*
     * Gibt die aktuelle Transformationsmatrix der Gruppe zurück.
     */
    public Matrix getTransformation() {
        return this.matrix;
    }

    /*
     * Berechnet den Schnittpunkt des Strahls mit der Gruppe.
     * Der Strahl wird in das lokale Koordinatensystem der Gruppe transformiert und mit
     * jeder Form in der Gruppe getestet. Es wird der nächstgelegene Treffer zurückgegeben.
     */
    @Override
    public Hit intersect(Ray ray) {
        Hit closestHit = null;

        // Transformiere den Ursprung und die Richtung des Strahls in das Koordinatensystem der Gruppe
        Point rayTransformedOrigin = Matrix.multiply(inverseMatrix, ray.origin());
        Direction rayTransformedDirection = Matrix.multiply(inverseMatrix, ray.direction());

        // Erzeuge den transformierten Strahl, der nun im Koordinatensystem der Gruppe liegt
        Ray transformedRay = new Ray(rayTransformedOrigin, rayTransformedDirection, ray.tMin(), ray.tMax());
        
        // Überprüfe jede Form in der Gruppe auf einen Schnittpunkt mit dem transformierten Strahl
        for (Shape s : forms) {
            Hit hit = s.intersect(transformedRay);
            if (hit != null && (closestHit == null || hit.t() < closestHit.t())) {
                closestHit = hit;  // Speichere den nächstgelegenen Treffer
            }
        }

        // Wenn ein Treffer gefunden wurde, transformiere den Treffpunkt zurück in das globale Koordinatensystem
        if (closestHit != null) {
            Point transformedClosestHitPoint = Matrix.multiply(matrix, closestHit.hit());  // Transformiere den Punkt
            Direction transformedClosestHitDirection = Matrix.multiply(transposedInverseMatrix, closestHit.normalV());  // Transformiere die Normalenrichtung
            Direction transformedNormal = normalize(transformedClosestHitDirection);  // Normalisiere die transformierte Richtung

            // Erzeuge ein neues `Hit`-Objekt mit den transformierten Werten
            closestHit = new Hit(closestHit.t(), transformedClosestHitPoint, transformedNormal, closestHit.material(), closestHit.u(), closestHit.v());
        }

        return closestHit;  // Rückgabe des nächstgelegenen Schnittpunkts, oder null, wenn kein Treffer vorliegt
    }
}
