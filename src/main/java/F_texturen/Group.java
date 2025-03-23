package F_texturen;

import static lib_cgtools.Vector.normalize;

import java.util.ArrayList;

import lib_cgtools.*;

/*
 * Die Group-Klasse repräsentiert eine Sammlung von Formen (Shapes) im 3D-Raum, 
 * die gemeinsam eine Transformation erfahren können. Diese Klasse implementiert 
 * das Shape-Interface und ermöglicht es, mehrere geometrische Objekte als eine 
 * einzige Gruppe zu behandeln und gleichzeitig Transformationen auf diese Gruppe anzuwenden.
 * 
 * Die Transformation der Gruppe wird durch Matrizenoperationen durchgeführt, und die 
 * Schnittpunktberechnung für den Strahl wird in das Koordinatensystem der Gruppe transformiert.
 */
public class Group implements Shape {

    private ArrayList<Shape> forms;              // Liste der Formen (Shapes) in der Gruppe
    private Matrix matrix;                       // Die Transformationsmatrix der Gruppe
    private Matrix inverseMatrix;                // Die Inverse der Transformationsmatrix
    private Matrix transposedInverseMatrix;     // Die transponierte Inverse der Transformationsmatrix

    /*
     * Konstruktor, der eine Gruppe mit einer Transformationsmatrix initialisiert.
     * Diese Matrix wird auf alle Formen in der Gruppe angewendet, um deren Position 
     * und Ausrichtung im 3D-Raum zu verändern.
     */
    public Group(Matrix matrix) {
        this.forms = new ArrayList<>();  // Initialisierung der Liste der Formen
        this.matrix = matrix;            // Setzen der Transformationsmatrix
        this.inverseMatrix = Matrix.invert(matrix);  // Berechnung der Inversen der Matrix
        this.transposedInverseMatrix = Matrix.transpose(this.inverseMatrix);  // Berechnung der transponierten Inversen
    }

    /*
     * Diese Methode fügt eine neue Form (Shape) zur Gruppe hinzu.
     * 
     * @param shape Die Form, die zur Gruppe hinzugefügt werden soll.
     */
    public void add(Shape shape) {
        forms.add(shape);  // Hinzufügen der Form zur Liste der Formen
    }

    /*
     * Setzt eine neue Transformationsmatrix für die Gruppe. Die neue Matrix wird 
     * mit der aktuellen Matrix multipliziert, um die Transformation der Gruppe zu 
     * aktualisieren. Danach werden die Inverse und die transponierte Inverse der 
     * neuen Matrix berechnet.
     * 
     * @param newTransformationMatrix Die neue Transformationsmatrix, die auf die Gruppe angewendet werden soll.
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
     * 
     * @return Die Transformationsmatrix der Gruppe.
     */
    public Matrix getTransformation() {
        return this.matrix;
    }

    /*
     * Berechnet den Schnittpunkt eines Strahls mit der Gruppe. Die Methode transformiert
     * den Strahl in das Koordinatensystem der Gruppe und berechnet dann den Schnittpunkt
     * mit jeder Form in der Gruppe. Der nächstgelegene Schnittpunkt wird zurückgegeben.
     * 
     * Der Schnittpunkt wird unter Berücksichtigung der Transformationsmatrix der Gruppe berechnet.
     * 
     * @param ray Der Strahl, mit dem die Schnittpunkte berechnet werden sollen.
     * @return Der nächstgelegene Schnittpunkt des Strahls mit einer der Formen in der Gruppe,
     *         oder null, wenn kein Schnittpunkt gefunden wird.
     */
    @Override
    public Hit intersect(Ray ray) {
        Hit closestHit = null;  // Variable zur Speicherung des nächstgelegenen Schnittpunkts

        // Transformiere den Ursprung und die Richtung des Strahls in das Koordinatensystem der Gruppe
        Point rayTransformedOrigin = Matrix.multiply(inverseMatrix, ray.origin());
        Direction rayTransformedDirection = Matrix.multiply(inverseMatrix, ray.direction());

        // Erstelle einen neuen Strahl mit dem transformierten Ursprung und der transformierten Richtung
        Ray tansformedRay = new Ray(rayTransformedOrigin, rayTransformedDirection, ray.tMin(), ray.tMax());

        // Durchlaufe alle Formen in der Gruppe und berechne den Schnittpunkt mit jeder Form
        for (Shape s : forms) {
            Hit hit = s.intersect(tansformedRay);  // Berechnung des Schnittpunkts mit der aktuellen Form

            // Wenn ein Schnittpunkt gefunden wurde und dieser näher am Ursprung des Strahls liegt
            if (hit != null && (closestHit == null || hit.t() < closestHit.t())) {
                closestHit = hit;  // Setze den gefundenen Schnittpunkt als den nächsten
            }
        }

        // Wenn ein Schnittpunkt gefunden wurde, transformiere ihn zurück in das ursprüngliche Koordinatensystem
        if (closestHit != null) {
            // Transformiere den Schnittpunkt und die Normalenrichtung in das ursprüngliche Koordinatensystem
            Point transformedClosestHitPoint = Matrix.multiply(matrix, closestHit.hit());
            Direction transformedClosestHitDirection = Matrix.multiply(transposedInverseMatrix, closestHit.normalV());
            Direction transformedNormal = normalize(transformedClosestHitDirection);  // Normalisieren der transformierten Normalen

            // Erstelle einen neuen Hit mit dem transformierten Schnittpunkt, der transformierten Normalen und anderen Informationen
            closestHit = new Hit(closestHit.t(), transformedClosestHitPoint, transformedNormal, closestHit.material(), closestHit.u(), closestHit.v());
        }

        // Rückgabe des nächstgelegenen Schnittpunkts
        return closestHit;
    }
}
