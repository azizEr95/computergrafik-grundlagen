package C_szene_background_ebene;

import lib_cgtools.*;
import A_bilderzeugung.*;
import static lib_cgtools.Vector.*;


/*
 * Die Main-Klasse dient als Einstiegspunkt für das Raytracing-Programm.
 * Es erstellt zwei verschiedene Szenen mit einem Gruppenobjekt, das verschiedene Formen wie
 * Kugeln (Schneemänner und Objekte) und eine Hintergrundebene enthält.
 * Das Bild wird mit einer Kamera gerendert, supersampelt und mit Gammakorrektur gespeichert.
 */
public class Main {
  public static void main(String[] args) {
    // Definiert die Bildauflösung
    final int width = 1920;
    final int height = 1080;
    Image image = new Image(width, height);

    // Erstellt eine Kamera mit einem Sichtfeld von 70 Grad
    final Camera cam = new Camera(Math.toRadians(70), width, height);

    // --- Erste Szene: Schneemänner ---
    Group scene = new Group();

    // Hintergrundfarbe (blau)
    scene.add(new Background(blue));

    // Boden als große Scheibe in der XZ-Ebene
    scene.add(new DiscXZ(point(0.0, -0.5, 0.0), 100.0, white));

    // **Erster Schneemann (rechts)**
    scene.add(new Sphere(point(1, -0.5, -2.5), 0.5, white)); // Körper
    scene.add(new Sphere(point(1, 0, -2.5), 0.3, white)); // Kopf

    scene.add(new Sphere(point(0.8, 0.3, -2.5), 0.15, black)); // Linkes Ohr
    scene.add(new Sphere(point(1.2, 0.3, -2.5), 0.15, black)); // Rechtes Ohr

    scene.add(new Sphere(point(0.95, 0.05, -2.0), 0.05, black)); // Rechtes Auge
    scene.add(new Sphere(point(0.75, 0.05, -2.0), 0.05, black)); // Linkes Auge

    // **Zweiter Schneemann (mittig)**
    scene.add(new Sphere(point(0, -0.3, -2.5), 0.3, white)); // Körper
    scene.add(new Sphere(point(0, 0.1, -2.5), 0.2, white)); // Kopf

    scene.add(new Sphere(point(-0.08, 0.09, -2.0), 0.04, black)); // Rechtes Auge
    scene.add(new Sphere(point(0.08, 0.09, -2.0), 0.04, black)); // Linkes Auge

    // **Dritter Schneemann (links)**
    scene.add(new Sphere(point(-1, -0.5, -2.5), 0.5, white)); // Körper
    scene.add(new Sphere(point(-1, 0, -2.5), 0.3, white)); // Kopf
    scene.add(new Sphere(point(-1, 0.3, -2.5), 0.25, black)); // Mütze

    scene.add(new Sphere(point(-0.95, 0.05, -2.0), 0.05, black)); // Rechtes Auge
    scene.add(new Sphere(point(-0.75, 0.05, -2.0), 0.05, black)); // Linkes Auge
    scene.add(new Sphere(point(-0.85, 0.02, -2.0), 0.03, black)); // Nase

    scene.add(new Sphere(point(-0.88, -0.2, -2.0), 0.04, black)); // Oberer Knopf
    scene.add(new Sphere(point(-0.9, -0.3, -2.0), 0.04, black)); // Mittlerer Knopf
    scene.add(new Sphere(point(-0.9, -0.4, -2.0), 0.04, black)); // Unterer Knopf

    // Raytracer-Objekt für die Szene erstellen
    Raytracer forms = new Raytracer(cam, scene, new Color(0.1, 0.1, 0.1));

    // Supersampling für bessere Qualität mit 2-facher Oversampling-Rate
    image.supersample(2, forms);

    // Bild speichern mit Gammakorrektur
    String filenameA03 = "doc/C_szene_background_ebene-picture-01.png";
    image.writeGamma(filenameA03, 2.2);
    System.out.println("Wrote image: " + filenameA03);

    // --- Zweite Szene: Farbige Kugeln auf gelbem Boden ---
    scene = new Group();

    // Hintergrundfarbe (grau)
    scene.add(new Background(gray70));

    // Boden als große Scheibe in der XZ-Ebene (gelb)
    scene.add(new DiscXZ(point(0.0, -0.5, 0.0), 100.0, yellow));

    // Farbige Kugeln in der Szene platzieren
    scene.add(new Sphere(point(-1.0, -0.25, -2.5), 0.7, red));   // Rote Kugel (links)
    scene.add(new Sphere(point(0.0, -0.25, -2.5), 0.5, green));  // Grüne Kugel (mittig)
    scene.add(new Sphere(point(1, -0.25, -2.5), 0.7, blue));     // Blaue Kugel (rechts)

    // Neues Raytracer-Objekt für die zweite Szene
    forms = new Raytracer(cam, scene, new Color(0.1, 0.1, 0.1));

    // Supersampling und Gammakorrektur erneut durchführen
    image.supersample(2, forms);

    // Bild speichern mit Gammakorrektur
    filenameA03 = "doc/C_szene_background_ebene-picture-02.png";
    image.writeGamma(filenameA03, 2.2);
    System.out.println("Wrote image: " + filenameA03);
  }
}
