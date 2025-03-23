/** 
 * @author hartmut.schirmacher@bht-berlin.de & henrik.tramberend@bht-berlin.de 
 */
package A_bilderzeugung;

import static lib_cgtools.Vector.*;

import lib_cgtools.*;

/*
 * Die Main-Klasse demonstriert die Erzeugung von Bildern durch verschiedene Sampling-Techniken.
 * Es werden drei Bilder erzeugt:
 * 1. Ein Bild mit einer konstanten Hintergrundfarbe
 * 2. Ein Bild mit zufällig platzierten farbigen Scheiben
 * 3. Ein Bild mit Scheiben und angewendetem Supersampling (Anti-Aliasing)
 */

public class Main {

  public static void main(String[] args) {
    // Bildgröße definieren
    final int width = 512;  // Bildbreite
    final int height = 256; // Bildhöhe
    
    // Erstellen eines neuen Bildes
    Image image = new Image(width, height);

    // Erstellen eines ConstantColor-Samplers mit einer festen Hintergrundfarbe
    ConstantColor content = new ConstantColor(new Color(0.2, 0.4, 0.9));

    // Füllen des Bildes mit der konstanten Farbe
    image.sample(content);

    // Speichern des Bildes mit konstantem Farbinhalt
    final String filename = "doc/A_bilderzeugung-image-constantcolor.png";
    image.write(filename);
    System.out.println("Wrote image: " + filename); // Ausgabe der Bestätigung

    // Erstellen eines ColoredDiscs-Samplers mit 20 zufällig platzierten Scheiben
    ColoredDiscs actual = new ColoredDiscs(20, width, height, gray);

    // Neues Bild mit den Scheiben erzeugen
    Image imageDisc = new Image(width, height);
    imageDisc.sample(actual);

    // Speichern des Bildes mit den farbigen Scheiben
    final String filenameDisc = "doc/A_bilderzeugung-discs.png";
    imageDisc.write(filenameDisc);
    System.out.println("Wrote image: " + filenameDisc); // Ausgabe der Bestätigung

    // Erstellen eines weiteren ColoredDiscs-Samplers mit 32 Scheiben
    ColoredDiscs actual2 = new ColoredDiscs(32, width, height, gray);

    // Erzeugt ein Bild mit Supersampling (Anti-Aliasing) durch Aufruf der supersample-Methode
    image.supersample(10, actual2);

    // Speichern des Bildes mit Supersampling und Gamma-Korrektur
    final String filenameDisc2 = "doc/A_bilderzeugung-discs-stratified-gamma.png";
    image.writeGamma(filenameDisc2, 2.2);
    System.out.println("Wrote image: " + filenameDisc2); // Ausgabe der Bestätigung
  }
}
