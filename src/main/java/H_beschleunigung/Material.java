package H_beschleunigung;

import lib_cgtools.*;

/*
 * Diese Schnittstelle definiert die grundlegenden Methoden für Materialien, die
 * in einer Raytracing-Simulation verwendet werden. Sie stellt Methoden zur
 * Berechnung der Reflexionen, der Emission und der Lichtinteraktionen bereit.
 */
public interface Material {
    
    /*
     * Berechnet die Reflexion des Umgebungslichts. Diese Reflexion ist für alle
     * Ausgabewinkel (Blickrichtungen) gleich und unabhängig von der Lichtrichtung.
     */
    public Color calculateAmbientReflection(Color ambient_light, Hit hit);

    /*
     * Berechnet die Reflexion des direkten Lichts von einer spezifischen Lichtquelle
     * zum Betrachter. Es handelt sich hierbei um das Licht, das von einer Lichtquelle
     * zum Zuschauer (z. B. Kamera) reflektiert wird.
     */
    public Color calculateDirectLightReflection(Direction to_viewer, Hit hit, DirectLight light);

    /*
     * Berechnet die Emission des Materials. Emission beschreibt das Licht, das
     * vom Material selbst ausgestrahlt wird (z. B. bei leuchtenden Oberflächen).
     */
    public Color getEmission(Direction to_viewer, Hit hit);

    /*
     * Berechnet die Reflexionsrichtung eines Strahls, der auf das Material trifft.
     * Diese Richtung beschreibt den Weg des reflektierten Strahls nach dem Aufeinandertreffen
     * mit der Oberfläche des Materials.
     */
    public Direction getReflectionDirection(Direction to_viewer, Hit hit);

    /*
     * Berechnet, wie viel Licht reflektiert wird, wenn der Strahl in eine bestimmte
     * Richtung reflektiert wird. Es geht hierbei um den Reflexionsanteil des Lichts
     * in der Richtung des reflektierten Strahls.
     */
    public Color getReflectionFraction(Direction to_viewer, Direction reflection, Hit hit);
}
