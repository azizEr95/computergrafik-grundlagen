# Aufgabe Bilderzeugung

Dieses Projekt demonstriert, wie man mit Hilfe der Raytracing-Techniken Bilder erstellt, die aus zufällig platzierten, farbigen Scheiben bestehen. Es zeigt, wie man mit Java ein einfaches Bildbearbeitungs-Toolkit implementiert, das verschiedene Techniken wie Antialiasing und Gammakorrektur zur Bildgenerierung verwendet.

## Projektstruktur
- `src/`
    - `main/`
        - `java/`
            - `A_bilderzeugung/`
                - `ColoredDics.java`
                - `ConstantColor.java`
                - `DiscModel.java`
                - `Image.java`
                - `Main.java`
            - `B_raytracing_start/...`


### Funktion der Klassen

**ColoredDiscs**
    Ein Sampler, der zufällig positionierte Scheiben (Discs) erzeugt, die farbig gefüllt sind. Der Hintergrund des Bildes wird mit einer bestimmten Farbe ausgefüllt, die festgelegt werden kann. Diese Scheiben werden innerhalb des Bildbereichs zufällig platziert, wobei Größe und Farbe der Scheiben variieren.

**ConstantColor**
    Ein Sampler, der für jedes Pixel im Bild die gleiche Farbe zurückgibt. Diese Klasse wird verwendet, um einen konstanten Hintergrund oder eine Grundfarbe zu erstellen, die das gesamte Bild ausfüllt.

**DiscModel**
    Modelliert eine einzelne Disc (Scheibe) mit einem Mittelpunkt, einem Radius und einer Farbe. Die Klasse enthält eine Methode, um zu prüfen, ob ein Punkt innerhalb der Scheibe liegt, basierend auf dem Abstand zwischen dem Punkt und dem Mittelpunkt.

**Image**
    Die Image-Klasse verwaltet die Bilddaten, einschließlich der Festlegung von Pixelwerten und der Speicherung des Bildes. Sie bietet Methoden zum Setzen von Farben für einzelne Pixel und zum Schreiben der Bilddatei auf die Festplatte. Zudem wird Antialiasing durch die Methode supersample unterstützt, um Kanten zu glätten und "Treppenstufen"-Artefakte zu vermeiden.

**Main**
    Main Klasse erstellt und speichert drei Bilder im Ordner doc:
    ***1. Ein Bild mit einer konstanten Farbe***: Alle Pixel des Bildes sind auf eine feste Farbe gesetzt.
    ***2. Ein Bild mit zufällig generierten Scheiben***: Hier werden zufällig positionierte Scheiben auf dem Bild angezeigt, wobei die Kanten der Scheiben deutlich sichtbar sind und Treppenstufen-Artefakte erzeugen.
    ***3. Ein Bild mit zufällig generierten Scheiben und Anti-Aliasing:*** Die Scheiben werden mit der Supersampling-Technik (Anti-Aliasing) berechnet, um die Kanten zu glätten und ein sauberes Bild zu erzeugen.

Genauere Erklärungen sind als Kommentare in den Klassen zu finden.


