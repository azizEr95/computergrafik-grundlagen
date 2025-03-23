package G_rekursiv_pathtracing;

import lib_cgtools.*;
import static lib_cgtools.Vector.*;

/*
 * Diese Klasse ist ein Raytracer, der die Strahlenverfolgung für die Berechnung der Bildbeleuchtung 
 * in einer 3D-Szene übernimmt. Sie berechnet die Farbe eines Pixels basierend auf der Kamerasicht 
 * und der Szene, einschließlich direkter Beleuchtung, Umgebungslicht und Reflexionen.
 */
public class Raytracer implements Sampler {

    private Camera camera;  // Die Kamera, die den Strahl erzeugt
    private Color bgColor;  // Die Hintergrundfarbe (falls kein Objekt getroffen wird)
    private Scene scene;    // Die Szene, die die Objekte und Lichtquellen enthält

    /*
     * Konstruktor des Raytracers, der die Kamera, die Szene und die Hintergrundfarbe initialisiert.
     */
    public Raytracer(Camera camera, Scene scene, Color bgcolor) {
        this.camera = camera;  // Setzt die Kamera
        this.scene = scene;    // Setzt die Szene
        this.bgColor = bgcolor; // Setzt die Hintergrundfarbe
    }

    /*
     * Berechnet die Farbe eines Pixels, indem ein Strahl aus der Kamera erzeugt wird und 
     * die Strahlenverfolgung durchgeführt wird.
     */
    @Override
    public Color getColor(double x, double y) {
        Ray ray = camera.generateRay(x, y); // Erzeugt einen Strahl von der Kamera zum Bildpunkt
        return this.getRadiance(ray, 4); // Ruft die Strahlenverfolgung mit einer maximalen Tiefe von 4 auf
    }

    /*
     * Führt die Strahlenverfolgung für einen gegebenen Strahl und eine gegebene Rekursionstiefe durch.
     * Berechnet die Gesamthelligkeit (Radiance) eines Treffers auf ein Objekt, indem Umgebungslicht, 
     * direkte Beleuchtung und Reflexionen berücksichtigt werden.
     */
    private Color getRadiance(Ray ray, int trace_depth) {
        if (trace_depth == 0) {
            return black; // Rückgabe schwarz, wenn die Rekursionstiefe erreicht ist
        }

        Color totalDirectLight = black; // Initialisiert die direkte Beleuchtung auf schwarz

        // Bestimmt den nächsten Treffer eines Strahls in der Szene
        Hit closestHit = scene.shapes().intersect(ray); 

        // Kein Treffer, also Hintergrundfarbe zurückgeben
        if (closestHit == null) {
            return bgColor;
        }

        // Material des getroffenen Objekts
        Material material = closestHit.material(); 

        // Berechnet die Umgebungsbeleuchtung für das getroffene Objekt
        Color ambient = material.calculateAmbientReflection(bgColor, closestHit);

        // Iteration über alle Lichtquellen in der Szene
        for (DirectLight light : scene.lights()) {
            // Berechnet den Schattenstrahl von der Lichtquelle zum Treffpunkt
            Ray shadowRay = light.shadowRay(closestHit.hit());
            Hit shadowHit = scene.shapes().intersect(shadowRay);

            // Wenn der Schattenstrahl kein Objekt trifft, wird die direkte Beleuchtung berechnet
            if (shadowHit == null) {
                totalDirectLight = add(totalDirectLight, material.calculateDirectLightReflection(ray.direction(), closestHit, light));
            }
        }

        // Berechnet die Emission des Materials
        Color emission = material.getEmission(ray.direction(), closestHit);
        Color reflection = black;  // Initialisiert die Reflexion auf schwarz

        // Berechnet die Richtung des reflektierten Strahls
        Direction reflectedDirection = material.getReflectionDirection(ray.direction(), closestHit);

        if (reflectedDirection == null) {
            // Wenn keine Reflexion berechnet werden soll, gibt es nur die Beleuchtung aus direktem Licht und Umgebungslicht
            return add(totalDirectLight, ambient, emission);
        }

        // Berechnet den Anteil der Reflexion basierend auf der Reflexionsrichtung
        Color reflectFraction = material.getReflectionFraction(ray.direction(), reflectedDirection, closestHit);
        if (!reflectFraction.equals(black)) {
            // Wenn Reflexion vorhanden ist, wird ein reflektierter Strahl erzeugt und die Reflexion wird berechnet
            Ray reflectedRay = new Ray(closestHit.hit(), reflectedDirection, 1e-6, Double.POSITIVE_INFINITY);
            reflection = multiply(reflectFraction, getRadiance(reflectedRay, trace_depth - 1));
        }

        // Gibt die Gesamthelligkeit zurück: Umgebungslicht + direkte Beleuchtung + Reflexion + Emission
        return add(totalDirectLight, ambient, reflection, emission);
    }
}
