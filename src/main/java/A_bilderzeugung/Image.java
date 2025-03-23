package A_bilderzeugung;

import lib_cgtools.*;

/*
 * Die Image-Klasse repräsentiert ein Bild und bietet Methoden zur Manipulation und 
 * Speicherung von Pixelwerten. Sie unterstützt das Setzen von Pixeln, das Sampling 
 * von Farben aus einem Sampler sowie das Schreiben des Bildes mit oder ohne 
 * Gammakorrektur. Zusätzlich bietet sie die Möglichkeit, Anti-Aliasing durch 
 * Supersampling anzuwenden.
 */
public class Image {
    private double[] data; // Ein eindimensionales Array, das die RGB-Werte für jedes Pixel speichert
    private int width;     // Die Breite des Bildes
    private int height;    // Die Höhe des Bildes

    public Image(int width, int height) {
        data = new double[width * height * 3];
        this.width = width;
        this.height = height;
    }

    /*
     * Setzt die RGB-Werte für das Pixel an der Position (i, j).
     * - i: Die x-Koordinate des Pixels
     * - j: Die y-Koordinate des Pixels
     * - color: Die Farbe, die für das Pixel gesetzt wird
     * Das Pixel wird in das Array 'data' eingefügt, wobei jede Farbe (R, G, B) einzeln gespeichert wird.
     */
    public void setPixel(int i, int j, Color color) {
        int k = 3 * (j * width + i); // Berechnet die Position des Pixels im Array
        data[k] = color.r();         // Rotanteil
        data[k+1] = color.g();       // Grünanteil
        data[k+2] = color.b();       // Blauanteil
    }

    /*
     * Schreibt das Bild ohne Gammakorrektur in eine Datei.
     * - filename: Der Name der Datei, in der das Bild gespeichert wird
     */
    public void write(String filename) { 
        ImageWriter.write(filename, data, width, height);
    }

    /*
     * Schreibt das Bild mit Gammakorrektur in eine Datei.
     */
    public void writeGamma(String filename, double gamma) {
        ImageWriter.write(filename, data, width, height, gamma);
    }

    /*
     * Durchläuft jedes Pixel im Bild und setzt dessen Farbe basierend auf der getColor-Methode des Samplers.
     */
    public void sample(Sampler sampler) {
        for (int i = 0; i != width; i++) {
            for (int j = 0; j != height; j++) {
                setPixel(i, j, sampler.getColor(i, j));
            }
        }
    }

    /*
     * Wendet Supersampling an, um den Alias-Effekt zu reduzieren und ein schärferes Bild zu erzeugen.
     * Das Bild wird in kleinere Unterbereiche unterteilt, und für jedes Unterpixel wird ein zufälliger
     * Abtastpunkt gewählt. Die Farben dieser Unterpunkte werden gemittelt und die resultierende Farbe
     * wird auf das Pixel gesetzt.
     * - number: Die Anzahl der Unterteilungen pro Pixel (d.h. die Anzahl der Samples pro Pixel)
     * - sampler: Der Sampler, der die Farbe für jedes Sub-Pixel liefert
     */
    public void supersample(int number, Sampler sampler) {
        for (int i = 0; i != width; i++) { // Iteriert über jedes Pixel im Bild
            for (int j = 0; j != height; j++) { // Iteriert über jedes Pixel im Bild

                Color averageColor = new Color(0, 0, 0); // Initialisiert die durchschnittliche Farbe als schwarz

                // Unterteilt jedes Pixel in n x n Sub-Pixel
                for (int ii = 0; ii != number; ii++) {
                    for (int jj = 0; jj != number; jj++) {
                        double x = i + (ii + Random.random()) / number; // Zufälliger x-Abtastpunkt innerhalb des Pixels
                        double y = j + (jj + Random.random()) / number; // Zufälliger y-Abtastpunkt innerhalb des Pixels
                        Color samplerColor = sampler.getColor(x, y);  // Holt die Farbe vom Sampler für den Abtastpunkt
                        averageColor = new Color(  // Addiert die Farbe zum Durchschnitt
                            averageColor.r() + samplerColor.r(),
                            averageColor.g() + samplerColor.g(),
                            averageColor.b() + samplerColor.b()
                        );
                    }
                }

                // Setzt die Farbe des Pixels basierend auf dem Durchschnitt der Samples
                setPixel(i, j, new Color(
                    averageColor.r() / (number * number),
                    averageColor.g() / (number * number),
                    averageColor.b() / (number * number)
                ));
            }
        }
    }

    /*
     * Gibt die Breite des Bildes zurück.
     */
    public int getWidth() {
        return width;
    }

    /*
     * Gibt die Höhe des Bildes zurück.
     */
    public int getHeight() {
        return height;
    }
}
