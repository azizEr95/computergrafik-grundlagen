package D_beleuchtung_schatten;

import lib_cgtools.*;
import static lib_cgtools.Vector.*;

/*
 * Die Klasse Raytracer implementiert das Sampler-Interface und führt
 * das Raytracing-Verfahren zur Berechnung der Farbwerte durch.
 */
public class Raytracer implements Sampler {

    private Camera camera;   // Kamera zur Generierung der Strahlen
    private Color bgColor;   // Hintergrundfarbe
    private Scene scene;     // Szene mit Objekten und Lichtquellen

    /*
     * Konstruktor für den Raytracer.
     *
     * - camera: Die Kamera, die Sichtstrahlen erzeugt.
     * - scene: Die Szene, die Objekte und Lichtquellen enthält.
     * - bgColor: Die Hintergrundfarbe, falls kein Objekt getroffen wird.
     */
    public Raytracer(Camera camera, Scene scene, Color bgColor) {
        this.camera = camera;
        this.scene = scene;
        this.bgColor = bgColor;
    }

    /*
     * Berechnet die Farbe an einer bestimmten Pixelkoordinate, indem ein Sichtstrahl
     * erzeugt und mit der Szene interagiert wird.
     *
     * - x, y: Pixelkoordinaten im Bild.
     * Rückgabewert: Die berechnete Farbe an diesem Punkt.
     */
    @Override
    public Color getColor(double x, double y) {

        Ray ray = camera.generateRay(x, y); // Erzeugung des Sichtstrahls
        Color totalDirectLight = black; // Standardwert für direktes Licht ist Schwarz

        // Bestimme das nächstgelegene Objekt, das vom Sichtstrahl getroffen wird
        Hit closestHit = scene.shapes().intersect(ray);

        // Falls kein Treffer erfolgt, wird die Hintergrundfarbe zurückgegeben
        if (closestHit == null) {
            return bgColor;
        }

        // Materialeigenschaften des getroffenen Objekts
        Material material = closestHit.material();

        // Berechnung des Umgebungslichtanteils
        Color ambient = material.calculateAmbientReflection(bgColor);

        // Iteration über alle Lichtquellen der Szene
        for (DirectLight light : scene.lights()) {
            Ray shadowRay = light.shadowRay(closestHit.hit());
            Hit shadowHit = scene.shapes().intersect(shadowRay);

            // Falls kein Schattenwurf vorhanden ist, berechne das direkte Licht
            if (shadowHit == null) {
                totalDirectLight = add(totalDirectLight, material.calculateDirectLightReflection(ray.direction(), closestHit, light));
            }
        }

        // Gesamtbeleuchtung: Umgebungslicht + direktes Licht
        return add(totalDirectLight, ambient);
    }
}
