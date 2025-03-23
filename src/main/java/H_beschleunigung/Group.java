package H_beschleunigung;

import lib_cgtools.*;
import java.util.ArrayList;
import static lib_cgtools.Vector.*;

/*
 * Diese Klasse stellt eine Gruppe von Formen dar, die gemeinsam transformiert und auf Kollisionen überprüft werden.
 * Jede Form in der Gruppe wird durch eine Transformation beeinflusst, die auf den Strahl und die Form angewendet wird.
 */
public class Group implements Shape {

    private ArrayList<Shape> forms;             // Eine Liste von Formen, die zur Gruppe gehören
    private Matrix matrix;                      // Die Transformation der Gruppe
    private Matrix inverseMatrix;               // Die Inverse der Transformationsmatrix
    private Matrix transposedInverseMatrix;     // Die transponierte Inverse der Transformationsmatrix
    private BoundingBox boundingBox;           // Die Bounding-Box der Gruppe, die die gesamte Gruppe umschließt

    /*
     * Konstruktor, der eine neue Gruppe mit einer Transformationsmatrix erstellt.
     * Initialisiert die Liste der Formen und berechnet die Inverse und transponierte Inverse der Matrix.
     */
    public Group(Matrix matrix) {
        this.forms = new ArrayList<>();
        this.matrix = matrix;
        this.inverseMatrix = Matrix.invert(matrix);
        this.transposedInverseMatrix = Matrix.transpose(this.inverseMatrix);
        this.boundingBox = BoundingBox.empty; // Anfangs hat die Gruppe eine leere Bounding-Box
    }

    /*
     * Fügt eine Form zur Gruppe hinzu und erweitert die Bounding-Box, um diese Form einzuschließen.
     */
    public void add(Shape shape) {
        forms.add(shape);  // Fügt die Form zur Liste der Formen hinzu
        this.boundingBox = this.boundingBox.extend(shape.bounds());  // Erweitert die Bounding-Box, um die Form einzuschließen
    }

    /*
     * Setzt eine neue Transformationsmatrix für die Gruppe und berechnet die Inverse und transponierte Inverse.
     */
    public void setTransformation(Matrix newTransformationMatrix) {
        // Multipliziert die aktuelle Matrix mit der neuen Transformationsmatrix
        this.matrix = Matrix.multiply(newTransformationMatrix, this.matrix);

        // Berechnet die Inverse und transponierte Inverse der neuen Matrix
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
     * Gibt die Bounding-Box der gesamten Gruppe zurück.
     */
    @Override
    public BoundingBox bounds() {
        return boundingBox;
    }

    /*
     * Berechnet den nächsten Schnittpunkt eines Strahls mit der Gruppe.
     * Der Strahl wird zuerst in das Koordinatensystem der Gruppe transformiert.
     * Falls der Strahl mit der Bounding-Box der Gruppe kollidiert, wird jede Form innerhalb der Gruppe auf Kollision überprüft.
     */
    @Override
    public Hit intersect(Ray ray) {
        Hit closestHit = null;

        // Transformiert den Ursprung und die Richtung des Strahls in das Koordinatensystem der Gruppe
        Point rayTransformedOrigin = Matrix.multiply(inverseMatrix, ray.origin());
        Direction rayTransformedDirection = Matrix.multiply(inverseMatrix, ray.direction());

        // Erstellt einen transformierten Strahl mit den neuen Ursprungs- und Richtungsvektoren
        Ray transformedRay = new Ray(rayTransformedOrigin, rayTransformedDirection, ray.tMin(), ray.tMax());

        // Überprüft, ob der transformierte Strahl mit der Bounding-Box der Gruppe kollidiert
        if (!this.bounds().intersect(transformedRay)) {
            return null;  // Wenn keine Kollision mit der Bounding-Box vorliegt, gibt es keinen Treffer
        }

        // Überprüft jede Form in der Gruppe auf Kollision mit dem transformierten Strahl
        for (Shape s : forms) {
            Hit hit = s.intersect(transformedRay);  // Überprüft Kollision mit der aktuellen Form
            if (hit != null && (closestHit == null || hit.t() < closestHit.t())) {
                closestHit = hit;  // Wenn ein näherer Treffer gefunden wird, wird dieser gespeichert
            }
        }

        // Falls ein Treffer vorliegt, transformiere den Treffer zurück in das ursprüngliche Koordinatensystem
        if (closestHit != null) {
            Point transformedClosestHitPoint = Matrix.multiply(matrix, closestHit.hit());  // Transformiert den Treffpunkt
            Direction transformedClosestHitDirection = Matrix.multiply(transposedInverseMatrix, closestHit.normalV());  // Transformiert die Normale
            Direction transformedNormal = normalize(transformedClosestHitDirection);  // Normiert die transformierte Normale

            // Erstelle einen neuen Hit mit den transformierten Werten und gebe ihn zurück
            closestHit = new Hit(closestHit.t(), transformedClosestHitPoint, transformedNormal, closestHit.material(), closestHit.u(), closestHit.v());
        }

        return closestHit;  // Rückgabe des nächstgelegenen Treffpunkts
    }
}
