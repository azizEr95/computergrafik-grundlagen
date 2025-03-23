package F_texturen;

import lib_cgtools.*;
import A_bilderzeugung.*;
import java.util.ArrayList;
import static lib_cgtools.Matrix.*;
import static lib_cgtools.Vector.*;

/*
 * Die Main-Klasse enthält die Hauptlogik für die Szeneerstellung und Bildgenerierung 
 * eines 3D-Renders. In dieser Klasse werden Kamera, Lichtquellen, Materialien und 
 * geometrische Objekte erstellt, eine Szene aufgebaut und das Bild mithilfe eines 
 * Raytracers gerendert. Die Szene wird auf ein Bild mit Supersampling angewendet, 
 * und das finale Bild wird auf der Festplatte gespeichert.
 */
public class Main {
  public static void main(String[] args) {

    // 1. Bild einstellen: Bildgröße festlegen und ein Image-Objekt erstellen
    final int width = 1920;  // Breite des Bildes
    final int height = 1080; // Höhe des Bildes
    Image image = new Image(width, height);  // Neues Image-Objekt erstellen

    // 2. Kamera einstellen: Transformationen und Blickwinkel der Kamera definieren
    Matrix translation = Matrix.translation(0, 2, 4);  // Verschiebung der Kamera im 3D-Raum
    Matrix rotation = Matrix.rotation(direction(-1, 0, 0), 30);  // Rotation der Kamera um die X-Achse um 30 Grad
    Matrix viewMatrix = Matrix.multiply(translation, rotation);  // Kombinierte View-Matrix
    double alpha = Math.toRadians(90);  // Kamera-Sichtwinkel (FOV) auf 90 Grad setzen

    final Camera cam = new Camera(alpha, width, height, viewMatrix);  // Kamera-Objekt erstellen

    // 3. Material erstellen: Hier wird ein Phong-Material verwendet (d.h. Diffuse, Reflektion, Spekular etc.)

    // 4. Lichtquellen erstellen
    ArrayList<DirectLight> lightList = new ArrayList<>();  // Liste für Lichtquellen

    // Direktionales Licht (unabhängig von der Position, aber eine feste Richtung)
    DirectionalLight directionalLight = new DirectionalLight(new Direction(-5, -5, -6), white);  // Lichtquelle mit Richtung
    // Punktlichtquellen (habe eine Position und eine Intensität)
    PointLight pointLight = new PointLight(new Point(1, 5, 1), multiply(white, 25.0));  
    PointLight pointLight2 = new PointLight(new Point(1, 5, 1), multiply(white, 25.0));
    
    // Lichtquellen zur Liste hinzufügen
    lightList.add(directionalLight);
    lightList.add(pointLight);
    lightList.add(pointLight2);
  

    // 5. Untergruppe erstellen: Dies ist eine Sammlung von Formen, die als "Boden" dient
    Group underGround = new Group(Matrix.translation(direction(0, -0.7, 0)));  // Untergruppe mit einer Translation

    // 6. Gruppen erstellen: Hier wird die Hauptgruppe (allGroup) erstellt, die alle Objekte enthält
    Group allGroup = new Group(identity);  // Obergruppe (Hauptgruppe)

    // 6.2. Füge der Obergruppe die Untergruppe hinzu (der Boden)
    allGroup.add(underGround);
    
    // Texturen laden und für die Objekte bereitstellen
    Sampler imageTexture = new ImageTexture("/Users/aziz/Desktop/Sterne.PNG");  // Textur für den Boden
    Sampler imageCube = new ImageTexture("/Users/aziz/Desktop/Texture.JPG");  // Textur für die erste Kugel
    Sampler imageSphere = new ImageTexture("/Users/aziz/Desktop/Erde.JPG");  // Textur für die zweite Kugel
    
    // 7. Erstelle und transformiere Texturen
    RepeatTexture reap = new RepeatTexture(imageSphere);  // Wiederholung der Textur auf der Kugel
    // Kugel 1 erstellen mit Textur und Phong-Material
    Sphere sphere = new Sphere(new Point(1, 1, 0), 0.7, new PhongMaterial(
      new ConstantColor(black),
      reap,
      new ConstantColor(black),
       10000)
    );
    Group sphereGroup = new Group(identity);  // Gruppe für die erste Kugel
    sphereGroup.add(sphere);  // Kugel zur Gruppe hinzufügen
    
    // Kugel 2 erstellen mit einer anderen Textur
    Sphere sphere2 = new Sphere(new Point(-3, 1, 0), 0.3, new PhongMaterial(
      new ConstantColor(black), 
      imageCube,
      new ConstantColor(black),
      10000)
    );

    // Erstelle ein Rechteck auf der XZ-Ebene (für den Boden)
    RectXZ rectUV = new RectXZ(point(0, 0, 0), 100, 200, new PhongMaterial(
      new ConstantColor(black),
      new Transform(new RepeatTexture(imageTexture), Matrix.scaling(0.1, 0.1, 1)),
      new ConstantColor(black),
      100000)
    );

    // Hintergrund erstellen (mit einfachem Phong-Material)
    Background back = new Background(new PhongMaterial(
      new ConstantColor(black),
      new ConstantColor(black),
      new ConstantColor(black),
      10000));

    // Objekte zur Untergruppe hinzufügen
    underGround.add(rectUV);
    // Objekte zur Hauptgruppe hinzufügen
    allGroup.add(sphere);
    allGroup.add(sphere2);
    allGroup.add(back);

    // 8. Bildgenerierung und Speicherung: Die Szene wird gerendert und als Bild gespeichert
    Scene scene = new Scene(allGroup, lightList);  // Szene mit allen Gruppen und Lichtquellen erstellen
    Raytracer forms = new Raytracer(cam, scene, new Color(0.1, 0.1, 0.1));  // Raytracer mit Kamera und Szene initialisieren
  
    final String filename = "doc/F_texturen-scene.png";  // Ziel-Dateipfad für das Bild
    image.supersample(2, forms);  // Supersampling durchführen (mit Faktor 2)
    image.writeGamma(filename, 2.2);  // Bild speichern und Gamma-Korrektur anwenden
    System.out.println("Wrote image: " + filename);  // Bestätigung in der Konsole ausgeben
  }
}
