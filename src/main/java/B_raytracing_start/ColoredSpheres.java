package B_raytracing_start;

import java.util.ArrayList;

import lib_cgtools.*;

/*
 * Die ColoredSpheres-Klasse repräsentiert eine einfache 3D-Szene mit Kugeln (Spheres), die 
 * in der Welt platziert sind. Diese Szene kann verwendet werden, um Bilder durch Raytracing zu rendern.
 * Für jedes Pixel wird ein Strahl (Ray) von der Kamera generiert und überprüft, ob er mit einer der Kugeln 
 * schneidet. Wenn ein Schnitt gefunden wird, wird die Farbe des Schnittpunkts unter Berücksichtigung von 
 * Beleuchtungseffekten berechnet.
 */
public class ColoredSpheres implements Sampler{

    private ArrayList<Sphere> spheres;  // Eine Liste von Kugeln, die die Szene bilden
    private Camera camera;              // Die Kamera, die für das Raytracing verwendet wird
    private Color bgColor;             // Die Hintergrundfarbe, die verwendet wird, wenn keine Kugel getroffen wird

    /*
     * Konstruktor für die Erstellung einer Szene mit Kugeln.
     * - camera: Die Kamera, die verwendet wird, um die Strahlen zu erzeugen.
     * - numbspheres: Die Anzahl der Kugeln, die in die Szene eingefügt werden.
     * - width, height: Die Dimensionen des Bildes, die verwendet werden, um die Kugeln zufällig zu platzieren.
     * - bgcolor: Die Hintergrundfarbe, die verwendet wird, wenn kein Schnittpunkt mit einer Kugel gefunden wird.
     */
    public ColoredSpheres(Camera camera, int numbspheres, int width, int height, Color bgcolor) {
        this.camera = camera;
        this.spheres = new ArrayList<>();
        bgColor = bgcolor;

        // Erzeugt eine zufällige Liste von Kugeln
        for(int i = 0; i < numbspheres; i++) {
            // Zufällige Position der Kugel innerhalb der gegebenen Dimensionen der Szene
            Point center = new Point((Random.random() - 0.5) * width / 2, 
                                     (Random.random() - 0.5) * height / 2, 
                                     Random.random() * -2000);  // Kugeln werden näher zur Kamera gesetzt
            double radius = Random.random() * 50;  // Zufälliger Radius, größere Kugeln
            Color color = new Color(Random.random(), Random.random(), Random.random()); // Zufällige Farbe
            spheres.add(new Sphere(center, radius, color)); // Kugel zur Liste hinzufügen
        }
        
        // Sortiert die Kugeln nach ihrem Radius
        spheres.sort((a, b) -> Double.compare(a.getRadius(), b.getRadius()));
    }

    /*
     * Berechnet die Farbe für das Pixel an den Koordinaten (x, y).
     * Zuerst wird ein Strahl von der Kamera zu diesem Pixel generiert.
     * Dann wird überprüft, ob der Strahl mit einer der Kugeln in der Szene schneidet.
     * - x, y: Die Koordinaten des Pixels, für das die Farbe berechnet wird.
     * Rückgabewert: Die berechnete Farbe für das Pixel, entweder basierend auf dem Schnittpunkt 
     * mit einer Kugel oder der Hintergrundfarbe, wenn kein Schnittpunkt gefunden wird.
     */
    @Override
    public Color getColor(double x, double y) {
        Ray ray = camera.generateRay(x, y); // Erzeugt einen Strahl von der Kamera zum Pixel (x, y)
        Hit closestHit = null;  // Hält den nächstgelegenen Schnittpunkt
        double tMin = Double.MAX_VALUE;  // Initialisiert tMin mit einem sehr hohen Wert
        
        // Iteriert durch alle Kugeln, um einen möglichen Schnittpunkt zu finden
        for (Sphere sphere : spheres) {
            Hit hit = sphere.intersect(ray);  // Prüft, ob der Strahl die Kugel schneidet
            if (hit != null) {
                double t = hit.t(); // Der Abstand des Schnittpunkts entlang des Strahls
                if (t < tMin) {  // Wenn der Schnittpunkt näher ist als der bisherige
                    tMin = t;
                    closestHit = hit;  // Setzt den neuen nächstgelegenen Treffer
                }
            }
        }

        // Wenn ein Schnittpunkt gefunden wurde, berechne die Farbe basierend auf Beleuchtungseffekten
        if (closestHit != null) {
            Direction normal = closestHit.normalV();  // Normalenvektor des Schnittpunkts
            Color color = Util.shade(normal, closestHit.color()); // Schattierungsberechnung
            return color; // Gibt die berechnete Farbe zurück
        }

        // Wenn kein Schnittpunkt gefunden wurde, gebe die Hintergrundfarbe zurück
        return bgColor;
    }

    /*
     * Gibt die Liste der Kugeln in der Szene zurück.
     * Rückgabewert: Eine Liste der Kugeln, die die Szene bilden.
     */
    public ArrayList<Sphere> getSpheres() {
        return spheres;
    }
}
