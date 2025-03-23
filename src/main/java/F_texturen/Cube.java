package F_texturen;

import lib_cgtools.*;
import static lib_cgtools.Matrix.*;
import static lib_cgtools.Vector.*;

/**
 * Die Cube-Klasse repräsentiert einen Würfel, der aus sechs rechteckigen Flächen (RectXZ) besteht.
 * Die einzelnen Flächen werden als Gruppen (Group) organisiert und entsprechend gedreht und verschoben,
 * um einen vollständigen Würfel zu formen.
 * 
 * Der Würfel wird durch Transformationen (Translation und Rotation) der sechs Seiten aufgebaut.
 * Jede Seite wird als eigene Gruppe verwaltet, um die Transformationen korrekt zu handhaben.
 */
public class Cube extends Group {

    /**
     * Erstellt einen Würfel mit der angegebenen Größe und dem zugehörigen Material.
     * Die sechs Seiten des Würfels werden durch rechteckige Flächen (RectXZ) gebildet.
     * Diese Flächen werden durch Drehung und Verschiebung an die richtige Position gebracht.
     * 
     * - size: Die Kantenlänge des Würfels.
     * - material: Das Material, das für alle Seiten des Würfels verwendet wird.
     */
    public Cube(double size, Material material) {
        super(identity); // Setzt die Transformationsmatrix der übergeordneten Gruppe auf die Einheitsmatrix
        
        // Oberseite des Würfels (Deckfläche)
        Group top = new Group(translation(0, 0, 0)); // Keine zusätzliche Transformation nötig
        top.add(new RectXZ(zero, size, size, material)); // Rechteck in der XZ-Ebene hinzufügen
        this.add(top);

        // Unterseite des Würfels (Bodenfläche)
        Matrix translationDown = translation(direction(0, -size, 0)); // Nach unten verschieben
        Matrix rotationFlip = rotation(direction(-size, 0, 0), 180); // Um 180° um die X-Achse drehen
        Matrix matrixDownside = multiply(translationDown, rotationFlip); // Transformation kombinieren
        Group down = new Group(matrixDownside);
        down.add(new RectXZ(zero, size, size, material));
        this.add(down);

        // Linke Seite des Würfels
        Matrix rotationLeft = rotation(direction(0, 0, size), 90); // Um 90° um die Z-Achse drehen
        Matrix translationLeftAndDown = translation(direction(-size / 2, -size / 2, 0)); // Position korrigieren
        Matrix matrixLeftside = multiply(translationLeftAndDown, rotationLeft);
        Group left = new Group(matrixLeftside);
        left.add(new RectXZ(zero, size, size, material));
        this.add(left);

        // Rechte Seite des Würfels
        Matrix rotationRight = rotation(direction(0, 0, size), -90); // Um -90° um die Z-Achse drehen
        Matrix translationRightAndDown = translation(direction(size / 2, -size / 2, 0)); // Position korrigieren
        Matrix matrixRightside = multiply(translationRightAndDown, rotationRight);
        Group right = new Group(matrixRightside);
        right.add(new RectXZ(zero, size, size, material));
        this.add(right);

        // Hintere Seite des Würfels
        Matrix rotationBack = rotation(direction(size, 0, 0), -90); // Um -90° um die X-Achse drehen
        Matrix translationBackAndDown = translation(direction(0, -size / 2, -size / 2)); // Position korrigieren
        Matrix matrixBackside = multiply(translationBackAndDown, rotationBack);
        Group back = new Group(matrixBackside);
        back.add(new RectXZ(zero, size, size, material));
        this.add(back);

        // Vordere Seite des Würfels
        Matrix rotationFront = rotation(direction(size, 0, 0), 90); // Um 90° um die X-Achse drehen
        Matrix translationFrontAndDown = translation(direction(0, -size / 2, size / 2)); // Position korrigieren
        Matrix matrixFrontside = multiply(translationFrontAndDown, rotationFront);
        Group front = new Group(matrixFrontside);
        front.add(new RectXZ(zero, size, size, material));
        this.add(front);
    }
}
