package A_bilderzeugung;

import java.util.ArrayList;

import lib_cgtools.*;

/*
 * Die Klasse ColoredDiscs erzeugt zufällig platzierte farbige Scheiben auf einem Bild.
 * Bereiche des Bildes, die nicht von Discs abgedeckt sind, werden mit einer Standard-Hintergrundfarbe gefüllt.
 */
public class ColoredDiscs implements Sampler {
    private ArrayList<DiscModel> discs;  // Liste der Discs, die im Bild gezeichnet werden
    private Color bgcolor;  // Hintergrundfarbe für nicht abgedeckte Bereiche

    /*
     * - numbdiscs: Anzahl der Discs, die im Bild erzeugt werden sollen.
     * - width: Breite des Bildes.
     * - height: Höhe des Bildes.
     * - bgcolor: Hintergrundfarbe, die für Bereiche verwendet wird, die keine Disc abdecken.
     *
     * Jede Disc wird mit zufälligen Werten für den Mittelpunkt und den Radius erstellt.
     * Der Radius wird dabei auf die Hälfte der kleineren Dimension des Bildes begrenzt,
     * um sicherzustellen, dass die Discs nicht außerhalb des Bildes liegen.
     * Die Discs werden anschließend nach dem Radius sortiert.
     */
    public ColoredDiscs(int numbdiscs, int width, int height, Color bgcolor) {
        discs = new ArrayList<>();
        this.bgcolor = bgcolor;

        // Erzeugt die Discs mit zufälligen Parametern
        for (int i = 0; i < numbdiscs; i++) {
            double midX = Random.random() * width;  // Zufällige x-Koordinate des Mittelpunkts
            double midY = Random.random() * height;  // Zufällige y-Koordinate des Mittelpunkts
            double radius = Random.random() * Math.min(width, height) / 2;  // Zufälliger Radius, begrenzt auf die halbe Bildgröße

            discs.add(new DiscModel(midX, midY, radius, new Color(Random.random(), Random.random(), Random.random())));
        }

        discs.sort((a, b) -> Double.compare(a.getRadius(), b.getRadius()));
    }

    /*
     * Gibt die Farbe für einen bestimmten Punkt (x, y) zurück.
     * - Wenn der Punkt innerhalb einer der Discs liegt, wird die Farbe der entsprechenden Disc zurückgegeben.
     * - Wenn der Punkt keine Disc berührt, wird die Hintergrundfarbe zurückgegeben.
     * 
     * Da die Discs nach dem Radius sortiert sind, können wir davon ausgehen, dass die größte Disc zuletzt geprüft wird.
     */
    @Override 
    public Color getColor(double x, double y) {
        // Durchläuft alle Discs und prüft, ob der Punkt (x, y) innerhalb einer Disc liegt
        for (int i = 0; i < discs.size(); i++) {
            if (discs.get(i).coversPoint(x, y)) {
                return discs.get(i).getColor();
            }
        }
        // Wenn keine Disc den Punkt abdeckt, wird die Hintergrundfarbe zurückgegeben
        return bgcolor;
    }
}
