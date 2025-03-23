package B_raytracing_start;

import static lib_cgtools.Vector.gray;

import A_bilderzeugung.Image;

public class Main {
  
  public static void main(String[] args) {
    // Bildbreite und -höhe definieren
    final int width = 1024;  // Die Breite des Bildes in Pixeln
    final int height = 512;  // Die Höhe des Bildes in Pixeln

    // Ein neues Image-Objekt wird mit den definierten Abmessungen erstellt
    Image image = new Image(width, height);

    // Eine Kamera wird mit einem Blickwinkel (Alpha) von Pi/12 und den Bildabmessungen erstellt
    final Camera cam = new Camera(Math.PI / 12 , width, height);

    // Eine feste Anzahl von Kugeln für das Bild
    int numSpheres = 50; // Beispiel: 50 Kugeln für das Bild

    // Supersampling anwenden, um die Bildqualität zu verbessern
    // Der zweite Parameter (2) gibt die Anzahl der Samples pro Pixel an
    // Die Szene wird durch das ColoredSpheres-Objekt erzeugt
    image.supersample(2, new ColoredSpheres(cam, numSpheres, width, height, gray));

    // Der Dateiname für das Bild
    final String filename = "doc/B_raytracing_start-spheres.png";

    // Das Bild wird mit Gammakorrektur gespeichert (Gamma-Wert 2.2)
    image.writeGamma(filename, 2.2);

    // Ausgabe des Dateinamens der gespeicherten Datei
    System.out.println("Wrote image: " + filename);
  }
}
