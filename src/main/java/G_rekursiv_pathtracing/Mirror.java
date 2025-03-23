package G_rekursiv_pathtracing;

import static lib_cgtools.Random.*;
import static lib_cgtools.Vector.*;

import lib_cgtools.*;

public class Mirror implements Material {
    Color reflectionCoefficient;
    double scatterFactor;

    public Mirror(Color reflectionCoefficient, double scatterFactor) {
        this.reflectionCoefficient = reflectionCoefficient;
        this.scatterFactor = scatterFactor;
    }

    /** 
     * sowie in PhongMaterial Methode calculateSpecular berechnen wir die Reflektion, 
     * aber nur vom Strahl der Kamera
     * Normalisieren wird nicht gebraucht, da es in generateRay schon gemacht wird
    */
    @Override
    public Direction getReflectionDirection(Direction to_viewer, Hit hit) {
        Direction reflectedDirection = subtract(to_viewer, multiply(2 * dotProduct(to_viewer, hit.normalV()), hit.normalV()));
        Direction scatterDirection = new Direction(0, 0, 0);

        if(scatterFactor > 0) {
            scatterDirection = multiply(new Direction(
               random(),
               random() ,
               random()), scatterFactor);
        }

        Direction scatteredReflection = normalize(add(reflectedDirection, scatterDirection));

        // Prüfen, ob die gestreute Richtung in die Oberfläche zeigt
        if (dotProduct(scatteredReflection, hit.normalV()) < 0) {
            scatteredReflection = reflectedDirection; //Korrektur
        }
        return scatteredReflection;
    }

    /** gibt den Koeffizienten zurück */
    @Override
    public Color getReflectionFraction(Direction to_viewer, Direction reflection, Hit hit) {
        return this.reflectionCoefficient;
    }

    /** Spiegel gibt kein eigenes Licht zurück */
    @Override
    public Color getEmission(Direction to_viewer, Hit hit) {
        return black;
    }

    /** brauchen wir hier nicht?? */
    @Override
    public Color calculateAmbientReflection(Color ambient_light, Hit hit) {
        return black;
    }

    @Override
    public Color calculateDirectLightReflection(Direction to_viewer, Hit hit, DirectLight light) {
        return black;
    }
}
