package H_beschleunigung;

import static lib_cgtools.Matrix.*;
import static lib_cgtools.Vector.*;

import lib_cgtools.*;

/*
 * Die Transform-Klasse ist eine Implementierung des Sampler-Interfaces.
 * Sie transformiert Koordinaten mithilfe einer Matrix und leitet die Berechnung der
 * Farben an den enthaltenen Sampler (content) weiter, nachdem die Transformation durchgeführt wurde.
 */
public record Transform(Sampler content, Matrix m) implements Sampler {

    /*
     * Die Methode getColor() berechnet die Farbe für gegebene u, v-Koordinaten
     * unter Anwendung einer Transformation (Matrix) auf die Koordinaten.
     * Die transformierten Koordinaten werden dann an den content-Sampler übergeben.
     */
    @Override
    public Color getColor(double u, double v) {

        // Ursprüngliche Punktkoordinaten im 3D-Raum (u, v, 0)
        Point originalPoint = point(u, v, 0);

        // Transformation des Punktes mithilfe der inversen Matrix
        // Die Transformation wird mit der inversen Matrix durchgeführt, 
        // da wir den ursprünglichen Punkt in den transformierten Raum überführen möchten.
        Point transformedPoint = Matrix.multiply(invert(m), originalPoint);

        // Abrufen der transformierten Koordinaten
        double transformedU = transformedPoint.x();  // Transformiertes u
        double transformedV = transformedPoint.y();  // Transformiertes v

        // Übergabe der transformierten Koordinaten an den content-Sampler
        return content.getColor(transformedU, transformedV);
    }
}
