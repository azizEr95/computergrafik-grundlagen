package G_rekursiv_pathtracing;

import lib_cgtools.*;
import static lib_cgtools.Vector.*;

/*
 * Diese Klasse implementiert das Phong-Materialmodell. Es beschreibt ein Material,
 * das Umgebungslicht, diffuse Reflexion und spekulare Reflexion berücksichtigen kann.
 * Die Reflexionen werden basierend auf den Materialeigenschaften und den Lichtquellen berechnet.
 */
public class PhongMaterial implements Material {

    private Sampler kAmbient; // Der Koeffizient für die ambienten Reflexionen (Umgebungslicht)
    private Sampler kDiffuse; // Der Koeffizient für die diffuse Reflexion (Oberflächenfarbe)
    private Sampler kSpecular; // Der Koeffizient für die spekulare Reflexion (Glanzlichter)
    private double shininess; // Der Glanz-Exponent (bestimmt den Glanzgrad des Materials)

    /*
     * Konstruktor für das PhongMaterial.
     * Setzt die Koeffizienten für die verschiedenen Reflexionen und den Glanz-Exponent.
     */
    public  PhongMaterial(Sampler kAmbient, Sampler kDiffuse, Sampler kSpecular, double shininess) {
        this.kAmbient = kAmbient; // Umgebungslichtreflexion
        this.kDiffuse = kDiffuse; // Diffuse Reflexion
        this.kSpecular = kSpecular; // Spekulare Reflexion
        this.shininess = shininess; // Glanz-Exponent
    }

    /*
     * Berechnet die Reflexion des Umgebungslichts. Dies wird durch den
     * Koeffizienten für die Umgebungslichtreflexion und das Umgebungslicht
     * bestimmt.
     */
    @Override
    public Color calculateAmbientReflection(Color ambient_light, Hit hit) {
        Color kAmbientColor = kAmbient.getColor(hit.u(), hit.v()); // Farbwert des Umgebungslichts
        return multiply(kAmbientColor, ambient_light); // Umgebungslichtreflexion berechnen
    }

    /*
     * Berechnet die Reflexion des direkten Lichts von einer Lichtquelle.
     * Es wird sowohl die diffuse als auch die spekulare Reflexion berücksichtigt.
     */
    @Override
    public Color calculateDirectLightReflection(Direction to_viewer, Hit hit, DirectLight light) {
        Direction normale_von_hit = hit.normalV(); // Die Normale am Punkt des Treffers
        Direction lightDirection = light.directionToSource(hit.hit()); // Lichtquelle zum Trefferspunkt
        Color lightColor = light.incomingLight(hit.hit()); // Die Farbe und Intensität des Lichts
        Direction normalized_to_viewer = normalize(to_viewer); // Der normalisierte Vektor zum Betrachter

        Color diffuse = calculateDiffuse(normale_von_hit, lightDirection, lightColor, hit); // Diffuse Reflexion
        Color specular = calculateSpecular(lightDirection, normale_von_hit, normalized_to_viewer, lightColor, hit); // Spekulare Reflexion

        return add(diffuse, specular); // Diffuse und spekulare Reflexion kombinieren
    }
    
    /*
     * Berechnet die diffuse Reflexion. Dieser Term berücksichtigt den Winkel zwischen
     * der Normalen des Treffers und dem Lichtvektor.
     */
    private Color calculateDiffuse(Direction normale_von_hit, Direction lightDirection, Color lightColor, Hit hit) {
        double cos_angle = Math.max(0, dotProduct(normale_von_hit, lightDirection)); // Cosinus des Winkels zwischen Normalen und Lichtrichtung

        Color diffuse = multiply(kDiffuse.getColor(hit.u(), hit.v()), lightColor); // Materialfarbe multipliziert mit Lichtfarbe

        return multiply(diffuse, cos_angle); // Diffuse Reflexion unter Berücksichtigung des Winkels
    }

    /*
     * Berechnet die spekulare Reflexion. Dieser Term berücksichtigt den Glanz-Exponent
     * und den reflektierten Vektor sowie die Richtung zum Betrachter.
     */
    private Color calculateSpecular(Direction lightDirection, Direction normale_von_hit, Direction normalized_to_viewer, Color lightColor, Hit hit) {
        Direction reflectedDirection = subtract(lightDirection, multiply(2 * dotProduct(lightDirection, normale_von_hit), normale_von_hit)); // Reflektierter Vektor

        double skalarProduct = Math.pow(Math.max(0, dotProduct(reflectedDirection, normalized_to_viewer)), shininess); // Spekulare Reflexion basierend auf dem Winkel und dem Glanz-Exponent

        return multiply(skalarProduct, multiply(lightColor, kSpecular.getColor(hit.u(), hit.v()))); // Spekulare Reflexion mit Lichtfarbe und Materialfarbe kombinieren
    }

    /*
     * Die Methode gibt keine Emission zurück, da dieses Material keine eigene Emission hat.
     */
    @Override
    public Color getEmission(Direction to_viewer, Hit hit) {
        return black; // Keine Emission
    }

    /*
     * Diese Methode gibt keine Reflexionsrichtung zurück, da sie für dieses Material nicht definiert ist.
     */
    @Override
    public Direction getReflectionDirection(Direction to_viewer, Hit hit) {
        return null; // Keine Reflexionsrichtung
    }

    /*
     * Diese Methode gibt keinen Reflexionsanteil zurück, da sie für dieses Material nicht definiert ist.
     */
    @Override
    public Color getReflectionFraction(Direction to_viewer, Direction reflection, Hit hit) {
        return black; // Kein Reflexionsanteil
    }
}
