package G_rekursiv_pathtracing;

import static lib_cgtools.Matrix.*;
import static lib_cgtools.Vector.*;

import lib_cgtools.*;

/**
 * Die Transform-Klasse implementiert das Sampler-Interface und ermöglicht es, eine Textur
 * zu transformieren. Sie nimmt eine Matrix und transformiert die Texturkoordinaten (u, v) 
 * vor der Abfrage der Farbe aus der zugrunde liegenden Textur (content).
 */
public record Transform(Sampler content, Matrix m) implements Sampler {
    
    /**
     * Diese Methode berechnet die transformierte Farbe für die gegebenen Texturkoordinaten (u, v).
     * Sie transformiert die Koordinaten zuerst mit der gegebenen Matrix und fragt dann 
     * die transformierte Textur ab.
     * 
     * 1. Der ursprüngliche Punkt (u, v) wird erstellt.
     * 2. Der Punkt wird mit der inversen Matrix transformiert.
     * 3. Die transformierten Koordinaten (u', v') werden an den Sampler (content) weitergegeben, 
     *    um die entsprechende Farbe zu erhalten.
     * 
     * - u: Die u-Koordinate der Textur.
     * - v Die v-Koordinate der Textur.
     * Rückgabe: Die Farbe der transformierten Textur an den neuen Koordinaten (u', v').
     */
    @Override
    public Color getColor(double u, double v) {

        // Erstellen des ursprünglichen Punktes mit den Texturkoordinaten (u, v) und z = 0
        Point originalPoint = point(u, v, 0);

        // Transformation des Punktes durch Anwendung der inversen Matrix
        Point transformedPoint = Matrix.multiply(invert(m), originalPoint);

        // Abrufen der transformierten Koordinaten (u' und v' sind die x- und y-Komponenten des transformierten Punktes)
        double transformedU = transformedPoint.x();
        double transformedV = transformedPoint.y();

        // Übergabe der transformierten Koordinaten an den content-Sampler und Rückgabe der abgerufenen Farbe
        return content.getColor(transformedU, transformedV);
    }
}
