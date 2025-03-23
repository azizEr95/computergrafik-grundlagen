package E_transformationen;

import lib_cgtools.*;
import A_bilderzeugung.*;
import java.util.ArrayList;
import static lib_cgtools.Matrix.*;
import static lib_cgtools.Vector.*;

/**
 * Die Main-Klasse dient zur Erstellung einer 3D-Szene mit einer Kamera, Lichtquellen und verschiedenen Objekten.
 * Sie setzt eine Raytracing-Engine ein, um ein Bild der Szene zu berechnen und zu speichern.
 */
public class Main {
  public static void main(String[] args) {

    // 1. Bildparameter einstellen (Auflösung)
    final int width = 1920;
    final int height = 1080;
    Image image = new Image(width, height);

    // 2. Kamera einrichten
    Matrix translation = Matrix.translation(0, 0, 150); // Kamera entlang der Z-Achse positionieren
    double alpha = Math.toRadians(90); // Kamerawinkel auf 90 Grad setzen
    final Camera cam = new Camera(alpha, width, height, translation);

    // 3. Materialien definieren (Phong-Beleuchtungsmodell)
    PhongMaterial phong1 = new PhongMaterial(
      new Color(0.1, 0, 0), 
      multiply(red, 0.5), // Kräftiges Rot als Diffusfarbe
      new Color(1.0, 1.0, 1.0), // Weiße Highlights für Glanzpunkte
      100 // Hoher Glanzfaktor
    );

    PhongMaterial phong2 = new PhongMaterial(
      new Color(0.1, 0.0, 0.0),
      multiply(white, white), // Weiße Diffusfarbe
      new Color(1.0, 1.0, 1.0),
      20 // Weniger glänzend
    );
    
    PhongMaterial phong3 = new PhongMaterial(
      new Color(0.1, 0.0, 0.0),
      green,
      new Color(1.0, 1.0, 1.0),
      100
    );
    
    PhongMaterial phong4 = new PhongMaterial(
      new Color(0.3, 0.3, 0.3),
      yellow,
      new Color(1.0, 1.0, 1.0),
      800 // Sehr glänzend
    );

    // 4. Lichtquellen erstellen (Richtungslicht)
    ArrayList<DirectLight> lightList = new ArrayList<>();

    DirectionalLight directionalLight = new DirectionalLight(new Direction(1, -100, -1), white);
    DirectionalLight directionalLight2 = new DirectionalLight(new Direction(1, 100, 1), white);
    DirectionalLight directionalLight3 = new DirectionalLight(new Direction(10, 0, 1), white);
    DirectionalLight directionalLight4 = new DirectionalLight(new Direction(-10, 0, 1), white);
    
    lightList.add(directionalLight);
    lightList.add(directionalLight2);
    lightList.add(directionalLight3);
    lightList.add(directionalLight4);

    // 5. Objekte erstellen (Menschen mit verschiedenen Parametern)
    Human human = new Human(
      phong1, 
      -15, -30, -15, -30,
      -90, -90, 90, 90
    );

    Human h2 = new Human(
      phong4, 
      1, 1, 1, 1,
      1, 1, 1, 1
    );

    Human h3 = new Human(
      phong3, 
      -60, 1, 30, 1,
      -30, -90, 30, 90
    );

    // 6. Gruppen für Transformationen erstellen
    Group underGround = new Group(Matrix.translation(direction(0, -0.7, 0))); // Bodenposition

    Group allGroup = new Group(identity); // Oberste Gruppierung für alle Objekte
    Group humanGroup = new Group(identity);
    Group humanGroup2 = new Group(identity);
    Group humanGroup3 = new Group(identity);

    // Gruppen zur Obergruppe hinzufügen
    allGroup.add(underGround);
    allGroup.add(humanGroup);
    allGroup.add(humanGroup2);
    allGroup.add(humanGroup3);

    // Transformationen auf die Gruppen anwenden
    Matrix rotate2 = Matrix.rotation(direction(0, 1, 0), 40); // Drehung um die Y-Achse
    humanGroup.setTransformation(multiply(humanGroup.getTransformation(), rotate2));
    
    humanGroup2.setTransformation(multiply(
      humanGroup.getTransformation(), 
      Matrix.multiply(translation(direction(-60, 1, 1)), rotation(0, -1, 0, 30))
    ));

    humanGroup3.setTransformation(multiply(
      humanGroup.getTransformation(),
      Matrix.multiply(translation(direction(60, 1, 1)), rotation(0, 1, 0, -30))
    ));

    // Objekte den jeweiligen Gruppen hinzufügen
    humanGroup.add(human);
    humanGroup2.add(h2);
    humanGroup3.add(h3);

    // Hintergrund und Boden hinzufügen
    underGround.add(new Background(phong2));

    // 7. Szene rendern und speichern
    Scene scene = new Scene(allGroup, lightList);
    Raytracer forms = new Raytracer(cam, scene, new Color(0.1, 0.1, 0.1));

    final String filename = "doc/E_transformation-human.png";
    image.supersample(2, forms); // Supersampling für bessere Qualität
    image.writeGamma(filename, 2.2); // Gamma-Korrektur anwenden
    System.out.println("Wrote image: " + filename);
  }
}
