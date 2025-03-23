package H_beschleunigung;

import lib_cgtools.*;
import A_bilderzeugung.*;
import java.util.Random;
import java.util.ArrayList;
import static lib_cgtools.Vector.*;

/*
 * Diese Klasse erstellt eine Szene mit verschiedenen Objekten und rendert sie.
 * Sie misst die Laufzeiten für unterschiedliche Anzahlen von Objekten (10, 100, 1000, 10000)
 * und speichert die Bilder im PNG-Format.
 */
public class Main {
    public static void main(String[] args) {
        // Die Anzahl der Objekte, die in der Szene enthalten sein sollen
        int[] objectCounts = { 10, 100, 1000, 10000 };

        // Liste, die die Laufzeiten für jedes Szenario speichert
        ArrayList<Long> runtimesWithBoundingBox = new ArrayList<>();
        
        // Erzeugt eine Random-Instanz, um zufällige Positionen für die Objekte zu generieren
        Random random = new Random();

        // Iteriere über die verschiedenen Objektanzahlen
        for (int count : objectCounts) {
            // Starte die Zeitmessung
            long startTimeWith = System.currentTimeMillis();

            final int width = 1920;  // Breite des Bildes
            final int height = 1080; // Höhe des Bildes
            Image image = new Image(width, height);  // Erstelle ein neues Bild

            // Berechne den Radius der Disk dynamisch basierend auf der Anzahl der Objekte
            double discRadius = Math.min(count / 20.0, 300.0);

            // Berechne die Position der Kamera
            double cameraDistance = discRadius * 1.8;
            double cameraHeight = discRadius * -0.3;
            Point viewPoint = new Point(0, cameraHeight, cameraDistance);
            Matrix viewMatrix = Matrix.rotation(xAxis, -40);  // Drehmatrix für die Kamera
            Matrix matrix = Matrix.translation(viewPoint);    // Übersetzungsmatrix für die Kamera
            Matrix addMatrix = Matrix.multiply(viewMatrix, matrix);  // Gesamtmatrix der Kamera

            // Erstelle die Kamera mit einem Sichtwinkel von 70 Grad
            Camera camera = new Camera(70, width, height, addMatrix);

            // Erstelle die Hauptgruppe (alle Objekte werden hierhin hinzugefügt)
            Group group = new Group(Matrix.identity());

            // Erstelle eine Liste für die Lichtquellen
            ArrayList<DirectLight> lightList = new ArrayList<>();
            Scene scene = new Scene(group, lightList);

            // Erstelle Materialien für die Objekte
            Material greenMaterial = new PhongMaterial(new ConstantColor(green), new ConstantColor(green),
                    new ConstantColor(white), 50);
            Material groundMaterial = new PhongMaterial(new ConstantColor(white), new ConstantColor(white),
                    new ConstantColor(black), 0);

            // Füge eine Lichtquelle (DirectionalLight) zur Szene hinzu
            lightList.add(new DirectionalLight(direction(-1, -1, -0.8), color(0.8, 0.8, 0.8)));

            // Erstelle Untergruppen für das Gruppieren der Objekte
            int groupCount = 10;
            Group[] subGroups = new Group[groupCount];
            for (int i = 0; i < groupCount; i++) {
                subGroups[i] = new Group(Matrix.identity());
                group.add(subGroups[i]);
            }

            // Füge zufällig platzierte Objekte (Sphären) zur Szene hinzu
            for (int i = 0; i < count; i++) {
                double angle = random.nextDouble() * 2 * Math.PI;  // Zufälliger Winkel
                double radius = Math.sqrt(random.nextDouble()) * discRadius;  // Zufälliger Radius
                double x = Math.cos(angle) * radius;
                double z = Math.sin(angle) * radius;

                // Erstelle eine neue Sphäre mit zufälliger Position und füge sie der Gruppe hinzu
                Sphere sphere = new Sphere(new Point(x, 2.5, z), 0.5, greenMaterial);
                int groupIndex = i % groupCount;  // Verteile die Sphären auf verschiedene Untergruppen
                subGroups[groupIndex].add(sphere);
            }

            // Füge den Boden (eine DiscXZ) zur Szene hinzu
            group.add(new DiscXZ(new Point(0, 1, 0), discRadius + 1, groundMaterial));

            // Erstelle einen Raytracer, um die Szene zu rendern
            Raytracer raytracer = new Raytracer(camera, scene, new Color(0.1, 0.1, 0.1));

            // Starte das Supersampling und rendere das Bild
            image.supersample(1, raytracer);

            // Speichere das gerenderte Bild
            String filename = "doc/H_beschleunigung-scene-" + count + ".png";
            image.write(filename);

            // Stoppe die Zeitmessung und berechne die Laufzeit
            long endTimeWith = System.currentTimeMillis();
            runtimesWithBoundingBox.add(endTimeWith - startTimeWith);
        }

        // Gib die Laufzeiten für die verschiedenen Objektzahlen aus
        System.out.println("Anzahl Objekte | Mit BoundingBox (ms)");
        for (int i = 0; i < objectCounts.length; i++) {
            System.out.printf("%15d | %20d\n", objectCounts[i], runtimesWithBoundingBox.get(i));
        }
    }
}
