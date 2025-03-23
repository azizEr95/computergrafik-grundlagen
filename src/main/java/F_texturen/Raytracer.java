package F_texturen;

import lib_cgtools.*;
import static lib_cgtools.Vector.*;

/*
 * Die Raytracer-Klasse implementiert das Sampler-Interface und ist dafür verantwortlich, 
 * die Farbwerte für jeden Pixel eines Bildes basierend auf der Szene zu berechnen, 
 * indem Strahlen (Rays) durch die Szene gesendet werden.
 */
public class Raytracer implements Sampler {

    private Camera camera; // Die Kamera, die den Blickwinkel und die Perspektive definiert
    private Color bgColor; // Hintergrundfarbe für den Fall, dass keine Objekte getroffen werden
    private Scene scene;   // Die Szene, die alle Objekte und Lichtquellen enthält

    /*
     * Konstruktor, um den Raytracer mit einer Kamera, einer Szene und einer Hintergrundfarbe zu initialisieren.
     */
    public Raytracer(Camera camera, Scene scene, Color bgcolor) {
        this.camera = camera;
        this.scene = scene;
        this.bgColor = bgcolor;
    }

    /*
     * Berechnet die Farbe für einen gegebenen Pixel (x, y) unter Verwendung der Kamera und der Szene.
     * Ein Ray wird generiert, und es wird überprüft, ob ein Treffer in der Szene stattfindet. 
     * Wenn ja, werden die Reflexionen und Lichtquellen für diesen Punkt berechnet.
     */
    @Override
    public Color getColor(double x, double y) {

        // Generiert einen Ray aus der Kamera für die gegebenen (x, y) Koordinaten
        Ray ray = camera.generateRay(x, y); 
        Color totalDirectLight = black; // Startwert für das direkte Licht (schwarz)

        // Prüft, ob der Ray ein Objekt in der Szene trifft
        Hit closestHit = scene.shapes().intersect(ray); // Gibt den nächsten Treffer zurück oder null, wenn kein Treffer

        // Wenn kein Treffer, gibt es keine Farbe, also wird die Hintergrundfarbe zurückgegeben
        if (closestHit == null) {
            return bgColor;
        }

        // Material des getroffenen Objekts
        Material material = closestHit.material(); 

        // Berechnet das Umgebungslicht (Ambient Reflection) des getroffenen Objekts
        Color ambient = material.calculateAmbientReflection(bgColor, closestHit);

        // Iteriert über alle Lichtquellen in der Szene
        for (DirectLight light : scene.lights()) {
            // Berechnet den Schattenstrahl von der Lichtquelle zum getroffenen Punkt
            Ray shadowRay = light.shadowRay(closestHit.hit());
            Hit shadowHit = scene.shapes().intersect(shadowRay); // Überprüft, ob der Schattenstrahl ein Objekt trifft

            // Wenn der Schattenstrahl kein Objekt trifft, wird das direkte Licht dieser Lichtquelle zum Gesamtlicht addiert
            if (shadowHit == null) {
                totalDirectLight = add(totalDirectLight, material.calculateDirectLightReflection(ray.direction(), closestHit, light));
            }
        }

        // Gibt die Gesamtfarbe zurück, die sich aus dem Umgebungslicht und dem direkten Licht zusammensetzt
        return add(totalDirectLight, ambient);
    }
}
