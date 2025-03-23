package C_szene_background_ebene;

import lib_cgtools.*;

/*
 * Die Klasse Raytracer ist für das Berechnen der Farbe eines jeden Pixels im Bild verantwortlich.
 * Sie verwendet Raytracing, um Strahlen von der Kamera durch die Szene zu senden und 
 * die Farbe der getroffenen Objekte zu bestimmen. Falls kein Objekt getroffen wird, 
 * wird die Hintergrundfarbe zurückgegeben.
 */
public class Raytracer implements Sampler {

    private Camera camera; // Kamera für das Raytracing
    private Shape scene;   // Szene mit allen Objekten
    private Color bgColor; // Hintergrundfarbe für nicht getroffene Strahlen

    /*
     * Konstruktor der Raytracer-Klasse.
     * - camera: Die Kamera, die die Strahlen erzeugt
     * - scene: Die Szene, die durchsucht wird
     * - bgcolor: Die Hintergrundfarbe für Strahlen, die keine Objekte treffen
     */
    public Raytracer(Camera camera, Shape scene, Color bgcolor) {
        this.camera = camera;
        this.scene = scene;
        this.bgColor = bgcolor;
    }

    /*
     * Berechnet die Farbe für einen gegebenen Bildpunkt (Pixel).
     * 
     * - x: x-Koordinate des Pixels
     * - y: y-Koordinate des Pixels
     * 
     * Ablauf:
     * 1. Ein Ray (Strahl) wird durch das Pixel erzeugt.
     * 2. Der Strahl wird mit der Szene geschnitten, um das nächstgelegene Objekt zu finden.
     * 3. Falls ein Objekt getroffen wurde:
     *    - Berechnet die Schattierung der Objektfarbe basierend auf der Normalen.
     *    - Gibt die berechnete Farbe zurück.
     * 4. Falls kein Objekt getroffen wurde, wird die Hintergrundfarbe zurückgegeben.
     * 
     * Rückgabewert: Die berechnete Farbe für das Pixel.
     */
    @Override
    public Color getColor(double x, double y) {
        // Erzeugt einen Strahl von der Kamera durch das Pixel
        Ray ray = camera.generateRay(x, y);

        // Überprüft, ob der Strahl ein Objekt trifft
        Hit closestHit = scene.intersect(ray);

        // Falls ein Treffer existiert, berechne die Farbe basierend auf der Oberflächennormalen
        if (closestHit != null) {
            Direction normal = closestHit.normalV();
            Color color = Util.shade(normal, closestHit.color());
            return color;
        }

        // Falls kein Objekt getroffen wurde, gib die Hintergrundfarbe zurück
        return bgColor;
    }
}
