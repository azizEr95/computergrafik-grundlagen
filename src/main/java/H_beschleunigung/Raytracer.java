package H_beschleunigung;

import static lib_cgtools.Vector.*;

import lib_cgtools.*;

/*
 * Diese Klasse ist für das Raytracing verantwortlich. Sie sendet Strahlen von der Kamera aus und berechnet die Beleuchtung der getroffenen Objekte in der Szene.
 * Der Raytracer berechnet die direkte Beleuchtung, Umgebungsbeleuchtung, Reflexionen und Emissionen für jedes Pixel.
 */
public class Raytracer implements Sampler {

    private Camera camera;  // Kamera, die die Strahlen erzeugt
    private Color bgColor;  // Hintergrundfarbe, falls kein Treffer gefunden wird
    private Scene scene;    // Die Szene, in der das Raytracing stattfindet

    // Konstruktor für den Raytracer
    public Raytracer(Camera camera, Scene scene, Color bgcolor) {
        this.camera = camera;
        this.scene = scene;
        this.bgColor = bgcolor;
    }

    @Override
    // Diese Methode wird aufgerufen, um die Farbe eines bestimmten Pixels zu berechnen
    public Color getColor(double x, double y) {
        Ray ray = camera.generateRay(x, y);  // Generiere den Sichtstrahl für das Pixel
        return this.getRadiance(ray, 4);  // Berechne die Radiance des Strahls (maximale Rekursionstiefe = 4)
    }

    // Diese Methode berechnet die Radiance eines Strahls unter Berücksichtigung der Beleuchtung
    private Color getRadiance(Ray ray, int trace_depth) {
        if (trace_depth == 0) {
            return black;  // Abbruchbedingung: Keine Rekursion, wenn die maximale Tiefe erreicht ist
        }

        Color totalDirectLight = black;  // Standardwert für direkte Beleuchtung (schwarz)

        // Bestimme den nächsten Treffer (Hit) des Strahls mit der Szene
        Hit closestHit = scene.shapes().intersect(ray);  // Finde das nächste Objekt, auf das der Strahl trifft

        // Falls der Strahl kein Objekt trifft, gibt es keine Beleuchtung (Hintergrundfarbe wird verwendet)
        if (closestHit == null) {
            return bgColor;
        }

        // Holen des Materials des getroffenen Objekts
        Material material = closestHit.material();

        // Berechnung der Umgebungsbeleuchtung für den Treffer
        Color ambient = material.calculateAmbientReflection(bgColor, closestHit);

        // Iteriere über alle Lichtquellen in der Szene
        for (DirectLight light : scene.lights()) {
            // Erzeuge einen Schattenstrahl, der von der Lichtquelle zum getroffenen Punkt geht
            Ray shadowRay = light.shadowRay(closestHit.hit());
            // Überprüfe, ob der Schattenstrahl ein anderes Objekt trifft
            Hit shadowHit = scene.shapes().intersect(shadowRay);

            // Falls der Schattenstrahl kein Objekt trifft, wird Licht auf das Objekt geworfen
            if (shadowHit == null) {
                // Berechne die direkte Beleuchtung des getroffenen Objekts durch diese Lichtquelle
                totalDirectLight = add(totalDirectLight, material.calculateDirectLightReflection(ray.direction(), closestHit, light));
            }
        }

        // Berechnung der Emission (Licht, das vom Objekt selbst ausgeht)
        Color emission = material.getEmission(ray.direction(), closestHit);

        // Berechnung der Reflexion
        Color reflection = black;
        Direction reflectedDirection = material.getReflectionDirection(ray.direction(), closestHit);

        // Wenn keine Reflexion vorhanden ist, return die Summe der direkten Beleuchtung und Umgebungsbeleuchtung
        if (reflectedDirection == null) {
            return add(totalDirectLight, ambient, emission);
        }

        // Berechne den Reflexionsanteil
        Color reflectFraction = material.getReflectionFraction(ray.direction(), reflectedDirection, closestHit);
        
        // Wenn der Reflexionsanteil nicht schwarz ist, führe Rekursion für die Reflexion durch
        if (!reflectFraction.equals(black)) {
            Ray reflectedRay = new Ray(closestHit.hit(), reflectedDirection, 1e-6, Double.POSITIVE_INFINITY);
            // Berechne die Beleuchtung durch Reflexion unter Verringerung der Rekursionstiefe
            reflection = multiply(reflectFraction, getRadiance(reflectedRay, trace_depth - 1));
        }

        // Die Gesamtbeleuchtung besteht aus:
        // - Umgebungsbeleuchtung
        // - Direkter Beleuchtung von Lichtquellen
        // - Reflexionen
        return add(totalDirectLight, ambient, reflection, emission);
    }
}
