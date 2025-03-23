package D_beleuchtung_schatten;

import lib_cgtools.*;
import java.util.ArrayList;
import A_bilderzeugung.Image;
import static lib_cgtools.Vector.*;

/*
 * Die Main-Klasse ist der Einstiegspunkt des Programms und erzeugt eine Szene mit verschiedenen
 * Kugeln, einem Hintergrund und einer Bodenebene. Zusätzlich werden Lichtquellen hinzugefügt, 
 * um Beleuchtungseffekte wie Schatten und Reflexionen zu simulieren. Das gerenderte Bild wird 
 * anschließend als PNG gespeichert.
 */
public class Main {
  public static void main(String[] args) {
    final int width = 1920;  // Bildbreite
    final int height = 1080; // Bildhöhe
    Image image = new Image(width, height); // Erstellt ein neues Bild-Objekt

    final Camera cam = new Camera(Math.toRadians(80), width, height); // Erstellt eine Kamera mit einem Sichtfeld von 80 Grad

    // Szene-Elemente
    Group shapeGroup = new Group(); // Sammlung von Objekten in der Szene
    ArrayList<DirectLight> lightList = new ArrayList<DirectLight>(); // Liste von Lichtquellen

    Scene scene = new Scene(shapeGroup, lightList); // Erstellt die Szene mit Objekten und Lichtern

    // Materialien für die Kugeln (Phong-Material für realistische Beleuchtung)
    PhongMaterial phong1 = new PhongMaterial(
      new Color(0.1, 0, 0), 
      multiply(red, 0.5), // kDiffuse (kräftiges Rot)
      new Color(1.0, 1.0, 1.0), // kSpecular (weiß für Lichtreflexe)
      100 // Shininess (Glanzgrad)
    );
    
    PhongMaterial phong2 = new PhongMaterial(
      new Color(0.1, 0.0, 0.0),
      multiply(blue, blue), // kDiffuse (blau)
      new Color(1.0, 1.0, 1.0), // kSpecular (weiß),
      30 // Mittlerer Glanz
    );
    
    PhongMaterial phong3 = new PhongMaterial(
      new Color(0.3, 0.3, 0.3),
      green, // kDiffuse (grün)
      new Color(1.0, 1.0, 1.0), 
      30 // Mittlerer Glanz
    );
    
    PhongMaterial phong4 = new PhongMaterial(
      new Color(0.3, 0.3, 0.3),
      yellow, // kDiffuse (gelb)
      new Color(1.0, 1.0, 1.0),
      800 // Sehr hoher Glanz (stark reflektierend)
    );
    
    PhongMaterial phong5 = new PhongMaterial(
      new Color(0.3, 0.3, 0.3),
      gray70, // kDiffuse (grau für den Boden)
      new Color(1.0, 1.0, 1.0),
      800 // Sehr hoher Glanz (stark reflektierend)
    );

    // Lichtquellen
    DirectionalLight directionalLight = new DirectionalLight(new Direction(10, -10, -10), white);
    DirectionalLight directionalLight2 = new DirectionalLight(new Direction(-60, -20, -5), white);
    PointLight pointLight = new PointLight(new Point(2, 5, 5), multiply(white, 25.0));
    PointLight pointLight2 = new PointLight(new Point(2, 5, 5), multiply(white, 25.0));
    PointLight pointLight3 = new PointLight(new Point(2, 5, 5), multiply(white, 25.0));

    // Objekte zur Szene hinzufügen
    shapeGroup.add(new Sphere(point(1, -0.1, -2.5), 0.5, phong1)); // Rote Kugel
    shapeGroup.add(new Sphere(point(0, 0.3, -2.5), 0.5, phong2)); // Blaue Kugel
    shapeGroup.add(new Sphere(point(-1.2, -0.1, -2.5), 0.5, phong3)); // Grüne Kugel

    shapeGroup.add(new Background(phong4)); // Hintergrundfarbe
    shapeGroup.add(new DiscXZ(point(0.0, -0.5, 0.0), 100.0, phong5)); // Bodenebene

    // Lichtquellen zur Szene hinzufügen
    lightList.add(directionalLight);
    lightList.add(directionalLight2);
    lightList.add(pointLight);
    lightList.add(pointLight2);
    lightList.add(pointLight3);

    // Raytracer initialisieren
    Raytracer forms = new Raytracer(cam, scene, new Color(0.1, 0.1, 0.1));

    // Bildgenerierung und Speicherung
    String filename = "doc/D_beleuchtung_schatten-spheres.png";
    image.supersample(2, forms); // Supersampling für bessere Bildqualität (Antialiasing)
    image.writeGamma(filename, 2.2); // Gamma-Korrektur für realistische Farbdarstellung

    System.out.println("Wrote image: " + filename);
  }
}
