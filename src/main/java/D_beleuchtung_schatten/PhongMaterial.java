package D_beleuchtung_schatten;

import lib_cgtools.*;
import static lib_cgtools.Vector.*;

/*
 * Die Klasse PhongMaterial implementiert das Phong-Reflexionsmodell, 
 * das die Reflexion von Licht an Oberflächen simuliert. Es besteht aus 
 * einem ambienten, diffusen und spekularen Reflexionsanteil und verwendet 
 * einen Glanzexponenten zur Steuerung der Lichtreflexe.
 */
public class PhongMaterial implements Material {

    private Color kAmbient;   // Koeffizient für ambientes Licht (Umgebungslichtreflexion)
    private Color kDiffuse;   // Koeffizient für diffuses Licht (Streuung durch Oberflächenrauhigkeit)
    private Color kSpecular;  // Koeffizient für spekulares Licht (gerichtete Reflexion)
    private double shininess; // Exponent zur Steuerung der Glanzlicht-Intensität

    /*
     * Konstruktor für das Phong-Material.
     * 
     * - kAmbient: Intensität des Umgebungslichts, das reflektiert wird.
     * - kDiffuse: Materialeigenschaft zur Streuung des Lichts in alle Richtungen.
     * - kSpecular: Stärke und Intensität des spiegelnden Lichtanteils.
     * - shininess: Exponent zur Steuerung der Größe des Glanzpunkts.
     */
    public PhongMaterial(Color kAmbient, Color kDiffuse, Color kSpecular, double shininess) {
        this.kAmbient = kAmbient;
        this.kDiffuse = kDiffuse;
        this.kSpecular = kSpecular;
        this.shininess = shininess;
    }

    /*
     * Berechnet die Reflexion des Umgebungslichts (ambient light).
     * Diese Reflexion ist unabhängig von Lichtquellen oder Betrachtungsrichtung.
     *
     * - ambient_light: Die Intensität des Umgebungslichts in der Szene.
     * Rückgabewert: Die berechnete Reflexionsfarbe für das Umgebungslicht.
     */
    @Override
    public Color calculateAmbientReflection(Color ambient_light) {
        return multiply(kAmbient, ambient_light);
    }

    /*
     * Berechnet die Reflexion des direkten Lichts einer Lichtquelle unter Berücksichtigung 
     * von diffusem und spekularem Licht.
     * 
     * - to_viewer: Richtung vom Schnittpunkt zur Kamera.
     * - hit: Das Schnittpunkt-Objekt, das Normale und Treffpunkt enthält.
     * - light: Die Lichtquelle, die auf das Objekt scheint.
     * Rückgabewert: Die berechnete Reflexionsfarbe für diese Lichtquelle.
     */
    @Override
    public Color calculateDirectLightReflection(Direction to_viewer, Hit hit, DirectLight light) {
        Direction normale_von_hit = hit.normalV(); // Oberflächennormale im Schnittpunkt
        Direction lightDirection = light.directionToSource(hit.hit()); // Richtung zur Lichtquelle
        Color lightColor = light.incomingLight(hit.hit()); // Lichtfarbe und -intensität
        Direction normalized_to_viewer = normalize(to_viewer); // Normalisierte Blickrichtung

        Color diffuse = calculateDiffuse(normale_von_hit, lightDirection, lightColor); // Berechnung des diffusen Anteils
        Color specular = calculateSpecular(lightDirection, normale_von_hit, normalized_to_viewer, lightColor); // Berechnung des spekularen Anteils

        return add(diffuse, specular);
    }

    /*
     * Berechnet die diffuse Reflexion anhand des Phong-Modells.
     * Diese entsteht, wenn Licht auf eine raue Oberfläche trifft und in 
     * viele Richtungen gestreut wird.
     *
     * - normale_von_hit: Normale der Oberfläche am Schnittpunkt.
     * - lightDirection: Richtung der einfallenden Lichtquelle.
     * - lightColor: Farbe und Intensität des Lichts.
     * Rückgabewert: Berechnete diffuse Reflexion als Farbwert.
     */
    private Color calculateDiffuse(Direction normale_von_hit, Direction lightDirection, Color lightColor) {
        // Berechnet das Skalarprodukt (dot product) zwischen Normalenvektor und Lichtvektor
        double cos_angle = Math.max(0, dotProduct(normale_von_hit, lightDirection));

        // Kombination von Materialeigenschaft und Lichtfarbe
        Color diffuse = multiply(kDiffuse, lightColor);

        // RGB-Werte mit dem positiven Wert des Winkels multiplizieren
        return multiply(diffuse, cos_angle);
    }

    /*
     * Berechnet die spekulare Reflexion anhand des Phong-Modells.
     * Diese tritt bei glänzenden Oberflächen auf, wenn Licht direkt reflektiert wird.
     *
     * - lightDirection: Richtung der Lichtquelle.
     * - normale_von_hit: Normale der Oberfläche am Schnittpunkt.
     * - normalized_to_viewer: Normalisierte Richtung zum Betrachter.
     * - lightColor: Farbe und Intensität des Lichts.
     * Rückgabewert: Berechnete spekulare Reflexion als Farbwert.
     */
    private Color calculateSpecular(Direction lightDirection, Direction normale_von_hit, Direction normalized_to_viewer, Color lightColor) {
        // Berechnung des reflektierten Lichtvektors nach dem Gesetz der Reflexion
        Direction reflectedDirection = subtract(lightDirection, multiply(2 * dotProduct(lightDirection, normale_von_hit), normale_von_hit));

        // Berechnung des Spekularlicht-Beitrags: (reflektierter Vektor * Blickrichtung) ^ Shininess
        double skalarProduct = Math.pow(Math.max(0, dotProduct(reflectedDirection, normalized_to_viewer)), shininess);

        // Kombination von spekularem Reflexionskoeffizient, Lichtfarbe und berechneter Reflexionsstärke
        return multiply(skalarProduct, multiply(lightColor, kSpecular));
    }
}
