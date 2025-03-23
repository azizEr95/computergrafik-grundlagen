package F_texturen;

import lib_cgtools.*;
import static lib_cgtools.Vector.*;

/*
 * Die Klasse PhongMaterial implementiert das Material-Interface und definiert das Verhalten
 * eines Materials gemäß dem Phong-Beleuchtungsmodell. Dieses Modell beschreibt, wie Licht
 * mit einem Material interagiert, indem es ambientes, diffuses und spekulares Licht berücksichtigt.
 */
public class PhongMaterial implements Material {

    private Sampler kAmbient;  // Ambient Reflexionskoeffizient (Umgebungslicht)
    private Sampler kDiffuse;  // Diffuser Reflexionskoeffizient (diffuse Reflexion)
    private Sampler kSpecular; // Spekularer Reflexionskoeffizient (glanzlichter Reflexion)
    private double shininess;  // Glanz-Exponent (bestimmt die Schärfe des spekularen Lichts)

    /*
     * Konstruktor der PhongMaterial-Klasse, der die verschiedenen Reflexionskoeffizienten 
     * und den Glanz-Exponent setzt.
     */
    public PhongMaterial(Sampler kAmbient, Sampler kDiffuse, Sampler kSpecular, double shininess) {
        this.kAmbient = kAmbient;
        this.kDiffuse = kDiffuse;
        this.kSpecular = kSpecular;
        this.shininess = shininess;
    }

    /*
     * Berechnet den Einfluss des ambienten Lichts auf das Material an der Stelle des 
     * gegebenen Treffers (hit).
     */
    @Override
    public Color calculateAmbientReflection(Color ambient_light, Hit hit) {
        // Berechnet den Einfluss des ambienten Reflexionskoeffizienten auf das Umgebungslicht
        Color kAmbientColor = kAmbient.getColor(hit.u(), hit.v());
        return multiply(kAmbientColor, ambient_light);
    }

    /*
     * Berechnet die Reflexion von direktem Licht auf das Material unter Berücksichtigung der 
     * gegebenen Beleuchtung und Blickrichtung des Betrachters.
     */
    @Override
    public Color calculateDirectLightReflection(Direction to_viewer, Hit hit, DirectLight light) {
        // Berechnet die Normalenrichtung des Punktes
        Direction normale_von_hit = hit.normalV();

        // Berechnet den Lichtvektor von der Lichtquelle zum Punkt
        Direction lightDirection = light.directionToSource(hit.hit());

        // Holt die Lichtfarbe (Intensität und Farbe des Lichts)
        Color lightColor = light.incomingLight(hit.hit());

        // Normalisiert den Richtungsvektor zum Betrachter
        Direction normalized_to_viewer = normalize(to_viewer);

        // Berechnet den diffusen Reflexionsteil
        Color diffuse = calculateDiffuse(normale_von_hit, lightDirection, lightColor, hit);

        // Berechnet den spekularen Reflexionsteil
        Color specular = calculateSpecular(lightDirection, normale_von_hit, normalized_to_viewer, lightColor, hit);

        // Gibt die Summe aus diffusem und spekularem Licht zurück
        return add(diffuse, specular);
    }

    /*
     * Berechnet den diffusen Reflexionsteil des Phong-Beleuchtungsmodells.
     * Dies berücksichtigt, wie das Licht mit der Oberfläche in einem bestimmten Winkel interagiert.
     */
    private Color calculateDiffuse(Direction normale_von_hit, Direction lightDirection, Color lightColor, Hit hit) {
        // Berechnet den Kosinus des Winkels zwischen der Normalen und dem Lichtvektor
        double cos_angle = Math.max(0, dotProduct(normale_von_hit, lightDirection));

        // Berechnet die diffuse Farbe unter Berücksichtigung der Materialeigenschaften und Lichtintensität
        Color diffuse = multiply(kDiffuse.getColor(hit.u(), hit.v()), lightColor);

        // Multipliziert die diffuse Farbe mit dem positiven Wert des Kosinus des Winkels
        return multiply(diffuse, cos_angle);
    }

    /*
     * Berechnet den spekulären Reflexionsteil des Phong-Beleuchtungsmodells.
     * Dieser Teil bezieht sich auf die Reflexion von Licht in einer bestimmten Richtung
     * (zum Betrachter) nach der spekularen Reflexion.
     */
    private Color calculateSpecular(Direction lightDirection, Direction normale_von_hit, Direction normalized_to_viewer, Color lightColor, Hit hit) {
        // Berechnet den reflektierten Vektor des Lichts basierend auf der Normalen des Treffpunkts
        Direction reflectedDirection = subtract(lightDirection, multiply(2 * dotProduct(lightDirection, normale_von_hit), normale_von_hit));

        // Berechnet den spekulären Faktor, indem der reflektierte Vektor mit dem Blickrichtungsvektor zum Betrachter verglichen wird
        double skalarProduct = Math.pow(Math.max(0, dotProduct(reflectedDirection, normalized_to_viewer)), shininess);

        // Kombiniert den spekulären Faktor mit der Lichtfarbe und dem spekularen Reflexionskoeffizienten
        return multiply(skalarProduct, multiply(lightColor, kSpecular.getColor(hit.u(), hit.v())));
    }
}
