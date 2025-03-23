package H_beschleunigung;

import lib_cgtools.*;
import static lib_cgtools.Vector.*;
import static lib_cgtools.Random.*;


/*
 * Diffuse ist eine Materialklasse, die für diffuse Oberflächen zuständig ist.
 * Sie implementiert das Material-Interface und beschreibt, wie Licht mit einer 
 * diffusen Oberfläche interagiert. Diffuse Materialien reflektieren Licht in 
 * alle Richtungen gleichmäßig.
 */
public record Diffuse(Sampler albedo, Sampler emission) implements Material {

    /*
     * Berechnet die Reflexionsrichtung eines einfallenden Strahls. 
     * Für diffuse Materialien wird die Reflexion zufällig in alle Richtungen
     * verteilt, unter der Voraussetzung, dass sie in Richtung der normalen
     * Oberfläche reflektiert wird.
     * 
     * Der Eingangsparameter `to_viewer` repräsentiert die Richtung des Betrachters
     * und `hit` enthält die Informationen zum Punkt des Auftreffens des Strahls auf der Oberfläche.
     */
    @Override
    public Direction getReflectionDirection(Direction to_viewer, Hit hit) {

        // Erzeugt eine zufällige Richtung innerhalb des Einheitswürfels (zwischen -1 und 1 für jede Komponente)
        Direction randomDirection = new Direction(
            ((random() * 2) - 1),  // zufälliger Wert zwischen -1 und 1 für x
            ((random() * 2) - 1),  // zufälliger Wert zwischen -1 und 1 für y
            ((random() * 2) - 1)   // zufälliger Wert zwischen -1 und 1 für z
        );

        // Wenn die Länge der zufälligen Richtung kleiner als 1 ist, berechnen wir die Reflexion erneut.
        // Dies könnte helfen, Richtungen zu vermeiden, die zu nah an der Mitte liegen, und könnte ein
        // besseres Sampling der möglichen Richtungen gewährleisten.
        if (length(randomDirection) < 1) {
            return getReflectionDirection(to_viewer, hit);  // Rekursiver Aufruf, um die Reflexion neu zu berechnen.
        }

        // Berechnet die endgültige Reflexionsrichtung als Normalenrichtung plus die zufällige Richtung.
        // `hit.normalV()` stellt die Oberflächen-Normale am Treffpunkt dar.
        // Die Richtung wird normalisiert, um sie zu einer Einheitsrichtung zu machen.
        return normalize(add(randomDirection, hit.normalV()));  // Reflexion = zufällige Richtung + normale Richtung
    }

    /*
     * Berechnet den Reflexionsfaktor des Materials. In einem diffusen Material ist dieser 
     * einfach der Albedo-Wert an der Stelle des Treffpunkts.
     * 
     * `to_viewer` ist die Richtung des Betrachters, `reflection` ist die berechnete Reflexionsrichtung,
     * und `hit` enthält Informationen über den Trefferpunkt.
     */
    @Override
    public Color getReflectionFraction(Direction to_viewer, Direction reflection, Hit hit) {
        // Albedo gibt die Farbe der Oberfläche wieder. Diese wird mit den Texturkoordinaten (u, v) abgerufen.
        return albedo.getColor(hit.u(), hit.v());
    }

    /*
     * Berechnet die Emission des Materials. Diffuse Materialien können auch Licht abstrahlen,
     * zum Beispiel für Materialien, die selbst leuchten.
     * 
     * `to_viewer` ist die Richtung des Betrachters, `hit` enthält Informationen über den Trefferpunkt.
     */
    @Override
    public Color getEmission(Direction to_viewer, Hit hit) {
        // Gibt die Emissionsfarbe des Materials zurück, die anhand der Texturkoordinaten (u, v) abgerufen wird.
        return emission.getColor(hit.u(), hit.v());
    }

    // Die folgenden Methoden werden für dieses Material nicht verwendet und geben immer schwarz zurück.

    /*
     * Berechnet die Ambient-Reflexion des Materials (Umgebungslicht). 
     * Bei einem diffusen Material wird keine zusätzliche Reflexion berechnet, daher gibt diese Methode `black` zurück.
     */
    @Override
    public Color calculateAmbientReflection(Color ambient_light, Hit hit) {
        return black;  // Keine Umgebungsreflexion für diffuse Materialien
    }

    /*
     * Berechnet die direkte Reflexion des Lichts auf der Oberfläche des Materials.
     * Für diffuse Materialien wird ebenfalls keine direkte Reflexion durchgeführt, daher gibt diese Methode `black` zurück.
     */
    @Override
    public Color calculateDirectLightReflection(Direction to_viewer, Hit hit, DirectLight light) {
        return black;  // Keine direkte Reflexion für diffuse Materialien
    }
}
