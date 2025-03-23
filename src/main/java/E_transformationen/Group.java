package E_transformationen;

import lib_cgtools.*;
import java.util.ArrayList;
import static lib_cgtools.Vector.*;

/**
 * Die Group-Klasse repräsentiert eine Sammlung von Formen (Shapes), die eine gemeinsame Transformation haben.
 * Diese Transformation wird durch eine Transformationsmatrix gesteuert, die es ermöglicht, alle enthaltenen
 * Objekte gemeinsam zu verschieben, zu skalieren oder zu drehen.
 * 
 * - Die Gruppe speichert eine Liste von Shapes, die sich in ihr befinden.
 * - Eine Transformation wird auf die gesamte Gruppe angewendet, sodass alle enthaltenen Formen gemeinsam bewegt werden.
 * - Die Methode intersect() berechnet den Schnittpunkt eines Strahls mit den transformierten Formen der Gruppe.
 */

public class Group implements Shape {

    private ArrayList<Shape> forms; // Liste der enthaltenen Formen (Shapes)
    private Matrix matrix; // Transformationsmatrix der Gruppe
    private Matrix inverseMatrix; // Inverse der Transformationsmatrix (für Transformationen des Strahls)
    private Matrix transposedInverseMatrix; // Transponierte inverse Matrix (für Transformationen der Normalenvektoren)

    /**
     * Konstruktor zur Erstellung einer neuen Gruppe mit einer bestimmten Transformationsmatrix.
     * Die Transformationsmatrix beeinflusst alle enthaltenen Objekte in der Gruppe.
     * 
     * -matrix: Die Transformationsmatrix, die auf die Gruppe angewendet wird.
     */
    public Group(Matrix matrix) {
        this.forms = new ArrayList<>();
        this.matrix = matrix;
        this.inverseMatrix = Matrix.invert(matrix);
        this.transposedInverseMatrix = Matrix.transpose(this.inverseMatrix);

    }

    /**
     * Fügt ein neues Shape-Objekt zur Gruppe hinzu.
     */
    public void add(Shape shape) {
        forms.add(shape);
    }

    /**
     * Setzt eine neue Transformationsmatrix für die Gruppe. Diese neue Matrix wird mit der aktuellen Matrix multipliziert.
     * Dadurch werden bestehende Transformationen beibehalten und mit der neuen Transformation kombiniert.
     * Anschließend werden die inverse und die transponierte inverse Matrix neu berechnet.
     * 
     * - newTransformationMatrix: Die neue Transformationsmatrix.
     */
    public void setTransformation(Matrix newTransformationMatrix) {
        this.matrix = Matrix.multiply(newTransformationMatrix, this.matrix);
        this.inverseMatrix = Matrix.invert(this.matrix);
        this.transposedInverseMatrix = Matrix.transpose(this.inverseMatrix);
    }

    /**
     * Gibt die aktuelle Transformationsmatrix der Gruppe zurück.
     */
    public Matrix getTransformation() {
        return this.matrix;
    }

    /**
     * Berechnet den Schnittpunkt eines gegebenen Strahls (Ray) mit der transformierten Gruppe.
     * 
     * - Der Strahl wird zunächst mit der inversen Transformationsmatrix transformiert, sodass er sich
     *   im lokalen Koordinatensystem der Gruppe befindet.
     * - Danach wird für jede Form in der Gruppe überprüft, ob sie vom Strahl getroffen wird.
     * - Falls eine Form getroffen wird, wird der nächstgelegene Schnittpunkt gespeichert.
     * - Falls ein Treffer vorliegt, wird der Schnittpunkt und der Normalenvektor wieder in das globale
     *   Koordinatensystem transformiert.
     * 
     * - ray Der Strahl, der auf die Gruppe trifft.
     * Rückgabewert: Das Hit-Objekt mit den Informationen zum nächsten Schnittpunkt oder null, falls kein Treffer vorliegt.
     */
    @Override
    public Hit intersect(Ray ray) {
        Hit closestHit = null;

        // Transformiere den Ursprung und die Richtung des Strahls ins lokale Koordinatensystem der Gruppe
        Point rayTransformedOrigin = Matrix.multiply(inverseMatrix, ray.origin());
        Direction rayTransformedDirection = Matrix.multiply(inverseMatrix, ray.direction());

        // Erstelle einen neuen Strahl im lokalen Koordinatensystem der Gruppe
        Ray transformedRay = new Ray(rayTransformedOrigin, rayTransformedDirection, ray.tMin(), ray.tMax());
        
        // Prüfe für jedes Shape in der Gruppe, ob es vom transformierten Strahl getroffen wird
        for (Shape s : forms) {
            Hit hit = s.intersect(transformedRay);
            if (hit != null && (closestHit == null || hit.t() < closestHit.t())) {
                closestHit = hit;
            }
        }

        // Falls ein Treffer gefunden wurde, transformiere ihn zurück ins globale Koordinatensystem
        if (closestHit != null) {
            // Transformiere den Treffpunkt zurück in den globalen Raum
            Point transformedClosestHitPoint = Matrix.multiply(matrix, closestHit.hit());

            // Transformiere den Normalenvektor mit der transponierten inversen Matrix
            Direction transformedClosestHitDirection = Matrix.multiply(transposedInverseMatrix, closestHit.normalV());
            Direction transformedNormal = normalize(transformedClosestHitDirection);

            // Erstelle ein neues Hit-Objekt mit den transformierten Werten
            closestHit = new Hit(closestHit.t(), transformedClosestHitPoint, transformedNormal, closestHit.material());
        }
        return closestHit; // Rückgabe des nächstgelegenen Schnittpunkts oder null, falls keiner vorhanden ist
    }
}
