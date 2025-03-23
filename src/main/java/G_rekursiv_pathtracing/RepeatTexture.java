package G_rekursiv_pathtracing;

import lib_cgtools.*;

/*
 * Eine Texture-Klasse, die eine Textur wiederholt, indem sie die 
 * u- und v-Koordinaten auf den Bereich [0, 1] beschränkt.
 */
public record RepeatTexture(Sampler content) implements Sampler {

    /*
     * Diese Methode berechnet die Farbe der Textur an den gegebenen 
     * u- und v-Koordinaten. Wenn die Koordinaten über den Bereich [0, 1]
     * hinausgehen, wird die Textur wiederholt, indem der Modulo-Operator
     * verwendet wird, um die Koordinaten auf den Bereich [0, 1] zu beschränken.
     */
    @Override
    public Color getColor(double u, double v) {
        // Wiederholung der u- und v-Koordinaten innerhalb des Bereichs [0, 1]
        u = u % 1;  // Modulo-Operation für die horizontale Wiederholung
        v = v % 1;  // Modulo-Operation für die vertikale Wiederholung

        // Rückgabe der Texturfarbe an den wiederholten u-, v-Koordinaten
        return content.getColor(u, v);
    }
}
