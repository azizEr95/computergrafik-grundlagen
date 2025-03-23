package G_rekursiv_pathtracing;

import lib_cgtools.*;
import A_bilderzeugung.*;
import java.util.ArrayList;
import static lib_cgtools.Matrix.*;
import static lib_cgtools.Vector.*;

/*
 * Diese `Main`-Klasse ist für die Konfiguration der Szene, die Erstellung der Kamera,
 * der Materialien, Lichter und Geometrien sowie für die Bildgenerierung verantwortlich.
 */
public class Main {
  
  public static void main(String[] args) {
    
    // 1. Bild erstellen und die Bildgröße einstellen
    final int width = 1920;  // Breite des Bildes
    final int height = 1080;  // Höhe des Bildes
    Image image = new Image(width, height);  // Erstelle das Bild mit der angegebenen Größe

    // 2. Kamera erstellen und die Kameraeinstellungen vornehmen
    Matrix translation = Matrix.translation(0, 0, 0.5);  // Translationsmatrix für die Kamera
    double alpha = Math.toRadians(90);  // Blickwinkel (in Radiant) für die Kamera

    // Erstelle die Kamera mit den gegebenen Parametern
    final Camera cam = new Camera(alpha, width, height, translation);

    // 3. Materialerstellung: Hier werden Materialien für die Objekte definiert (wird später spezifiziert)
    // Materialien sind zurzeit nicht im Code enthalten, aber sie werden benötigt, um das Aussehen der Objekte zu definieren.

    // 4. Lichter erstellen
    ArrayList<DirectLight> lightList = new ArrayList<DirectLight>();  // Liste für Lichterquellen
    DirectionalLight directionalLight = new DirectionalLight(new Direction(0, -0.5, -1), white);  // Erstelle ein gerichtetes Licht
    lightList.add(directionalLight);  // Füge das Licht der Lichtquelle hinzu

    // 5. Objekte erstellen
    // Hier werden verschiedene geometrische Objekte und Materialien für die Szene erstellt.
    
    // Hintergrund mit einem konstanten weißen Licht (Emitter)
    Background back = new Background(new Emitter(new ConstantColor(white)));
    
    // Boden (DiscXZ) mit Diffuse-Material (grau und schwarz)
    DiscXZ boden = new DiscXZ(zero, 6, new Diffuse(
      new ConstantColor(gray70), 
      new ConstantColor(black)
    ));
    
    // Verschiedene Kugeln (Sphere) mit verschiedenen Materialien
    Sphere sphere = new Sphere(zero, 0.1, new Diffuse(
      new ConstantColor(red),
      new ConstantColor(black)
    ));

    // Weitere Kugeln mit unterschiedlichen Größen und Farben
    Sphere sphere2 = new Sphere(zero, 0.3, new Diffuse(
      new ConstantColor(blue),
      new ConstantColor(black)
    ));

    Sphere sphere3 = new Sphere(zero, 0.03, new Diffuse(
      new ConstantColor(green),
      new ConstantColor(black)
    ));

    Sphere sphere4 = new Sphere(zero, 0.03, new Diffuse(
      new ConstantColor(green),
      new ConstantColor(black)
    ));

    Sphere sphere5 = new Sphere(zero, 0.03, new Diffuse(
      new ConstantColor(green),
      new ConstantColor(black)
    ));
    
    Sphere sphere6 = new Sphere(zero, 0.2, new Diffuse(
      new ConstantColor(gray30),
      new ConstantColor(black)
    ));

    Sphere sphere7 = new Sphere(zero, 0.08, new Diffuse(
      new ConstantColor(red),
      new ConstantColor(black)
    ));
    
    Sphere sphere8 = new Sphere(zero, 0.03, new Diffuse(
      new ConstantColor(blue),
      new ConstantColor(black)
    ));

    // 6. Gruppen erstellen
    // Gruppen ermöglichen es, mehrere Objekte zu einer hierarchischen Struktur zusammenzufassen.
    
    Group allGroup = new Group(identity);  // Erstelle eine Obergruppe, die keine Transformation hat

    // Erstelle Untergruppen und füge Kugeln hinzu
    Group sphereGroup = new Group(translation(0.1, 0, 0));
    sphereGroup.add(sphere);

    Group sphereGroup2 = new Group(translation(0.4, 0.2, -0.5));
    sphereGroup2.add(sphere2);

    Group sphereGroup3 = new Group(translation(0.2, -0.07, 0.2));
    sphereGroup3.add(sphere3);

    Group sphereGroup4 = new Group(translation(0, -0.07, 0.2));
    sphereGroup4.add(sphere4);

    Group sphereGroup5 = new Group(translation(-0.2, -0.07, 0.2));
    sphereGroup5.add(sphere5);
  
    Group sphereGroup6 = new Group(translation(-0.3, 0.02, -0.3));
    sphereGroup6.add(sphere6);

    Group sphereGroup7 = new Group(translation(-0.1, -0.08, 0));
    sphereGroup7.add(sphere7);
    
    Group sphereGroup8 = new Group(translation(-0.1, 0.03, 0));
    sphereGroup8.add(sphere8);

    // Erstelle den Boden (DiskXZ)
    Group ground = new Group(translation(direction(0, -0.1, 0)));
    ground.add(boden);

    // 6.2. Die Obergruppe (`allGroup`) mit den Untergruppen (Kugeln und Boden) füllen
    allGroup.add(ground);
    allGroup.add(back);
    allGroup.add(sphereGroup);
    allGroup.add(sphereGroup2);
    allGroup.add(sphereGroup3);
    allGroup.add(sphereGroup4);
    allGroup.add(sphereGroup5);
    allGroup.add(sphereGroup6);
    allGroup.add(sphereGroup7);
    allGroup.add(sphereGroup8);

    // 7. Bildgenerierung und Speicherung
    // Erstelle eine Szene mit der Obergruppe und der Liste der Lichter
    Scene scene = new Scene(allGroup, lightList);
    
    // Erstelle den Raytracer mit der Kamera und der Szene
    Raytracer forms = new Raytracer(cam, scene, new Color(0.1, 0.1 , 0.1));

    // Speicher das Bild als PNG-Datei mit Gamma-Korrektur
    final String filename = "doc/G_rekursiv_pathtracing-scene.png";
    image.supersample(4, forms);  // Antialiasing mit Supersampling
    image.writeGamma(filename, 2.2);  // Speichere das Bild mit Gamma-Korrektur auf 2.2
    System.out.println("Wrote image: " + filename);  // Ausgabe der Dateiausgabe
  }
}
