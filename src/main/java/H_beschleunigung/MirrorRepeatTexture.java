package H_beschleunigung;

import lib_cgtools.*;

/*
 * Die Klasse MirrorRepeatTexture implementiert das Sampler-Interface und stellt eine Textur dar, 
 * bei der die Koordinaten u und v so gespiegelt werden, dass sie im Bereich [0, 1] bleiben und 
 * wiederholt werden. Wenn u oder v einen Wert über 1 hinaus hat, wird es so behandelt, 
 * als ob es in den Bereich [0, 1] durch Spiegelung und Wiederholung zurückgeführt wird.
 * Diese Technik wird als "spiegelnde Wiederholung" bezeichnet.
 */
public record MirrorRepeatTexture(Sampler content) implements Sampler {

    /*
     * Die Methode getColor berechnet die Farbe der Textur basierend auf den gegebenen 
     * u- und v-Koordinaten. Sie spiegelt u und v, sodass die Textur wie in einem Mosaik 
     * wiederholt wird.
     *
     * - u Die u-Koordinate der Textur (im Bereich [0, 1])
     * - v Die v-Koordinate der Textur (im Bereich [0, 1])
     * rückgabe: Die Farbe, die aus der zugrunde liegenden Textur an der angegebenen Position
     *         abgerufen wird
     */
    @Override
    public Color getColor(double u, double v) {
        // Normalisiere u und v auf den Bereich [0, 1], indem der ganzzahlige Anteil entfernt wird
        u = u - Math.floor(u);  // Modulo für u, sodass u im Bereich [0, 1] bleibt
        v = v - Math.floor(v);  // Modulo für v, sodass v im Bereich [0, 1] bleibt

        // Rufe die Farbe der zugrunde liegenden Textur ab
        return content.getColor(u, v);
    }
}
