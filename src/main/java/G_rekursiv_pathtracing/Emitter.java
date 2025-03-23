package G_rekursiv_pathtracing;

import lib_cgtools.*;
import static lib_cgtools.Vector.*;

/*
 * Die `Emitter`-Klasse repräsentiert ein Material, das Licht emittiert, also eine Lichtquelle
 * darstellt. Ein solches Material gibt Licht ab, anstatt das einfallende Licht zu reflektieren.
 * Die Emission des Lichts wird über das `Sampler`-Objekt (das in der Klasse gespeichert ist)
 * gesteuert.
 */
public record Emitter(Sampler emission) implements Material {

    /*
     * Diese Methode gibt die Emission des Materials zurück, das Licht, das von
     * dem Material abgegeben wird. Die Emission wird durch die Textur oder Farbe
     * bestimmt, die über den `Sampler`-Parameter übergeben wird.
     */
    @Override
    public Color getEmission(Direction to_viewer, Hit hit) {
        return emission.getColor(hit.u(), hit.v()); // Die Emission wird durch den Sampler bestimmt
    }

    /*
     * Diese Methode ist für die Berechnung der Reflexion des Materials zuständig.
     * Da es sich um ein Licht emittierendes Material handelt (und nicht um ein reflektierendes),
     * wird hier `null` zurückgegeben, da keine Reflexion stattfindet.
     */
    @Override
    public Direction getReflectionDirection(Direction to_viewer, Hit hit) {
        return null; // Keine Reflexion für Licht emittierende Materialien
    }

    /*
     * Diese Methode berechnet den Reflexionsanteil des Materials.
     * Da es sich um einen Lichtemitter handelt, gibt es keine Reflexion des Lichts,
     * also wird schwarz (kein Licht) zurückgegeben.
     */
    @Override
    public Color getReflectionFraction(Direction to_viewer, Direction reflection, Hit hit) {
        return black; // Keine Reflexion für Licht emittierende Materialien
    }

    /*
     * Berechnet die ambienten (Umgebungs-)Reflexion des Materials. Da es sich um einen Lichtemitter
     * handelt, wird keine Umgebungsreflexion berechnet, also wird schwarz (kein Licht) zurückgegeben.
     */
    @Override
    public Color calculateAmbientReflection(Color ambient_light, Hit hit) {
        return black; // Keine Umgebungsreflexion für Licht emittierende Materialien
    }

    /*
     * Berechnet die direkte Lichtreflexion des Materials. Da es sich um ein Lichtemitter handelt,
     * gibt es keine direkte Reflexion des Lichts, also wird schwarz (kein Licht) zurückgegeben.
     */
    @Override
    public Color calculateDirectLightReflection(Direction to_viewer, Hit hit, DirectLight light) {
        return black; // Keine direkte Reflexion für Licht emittierende Materialien
    }
}
