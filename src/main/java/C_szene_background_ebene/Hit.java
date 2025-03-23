package C_szene_background_ebene;

import lib_cgtools.*;

/*
 * Die Hit-Klasse repräsentiert ein Trefferereignis, das auftritt, wenn ein Strahl (Ray) mit 
 * einem Objekt in der 3D-Szene schneidet. Diese Klasse enthält Informationen über den 
 * Treffpunkt, den Normalenvektor an diesem Punkt und die Farbe des Objekts an der 
 * Trefferstelle.
 */
public record Hit(double t, Point hit, Direction normalV, Color color) {

    //- t: Der Strahlparameter, der den Abstand vom Ursprung zum Schnittpunkt beschreibt
    //- hit: Die Position des Trefferpunkts in der 3D-Welt
    //- normalV: Der Normalenvektor des Objekts an der Trefferstelle
    //- color: Die Farbe der Oberfläche des Objekts am Schnittpunkt
}
 