# Raytracing Start - Aufgabe 2

In diesem Projekt wird eine grundlegende Raytracing-Implementierung in Java erstellt. Ziel ist es, eine einfache Szene mit zufällig positionierten, farbigen Kugeln zu rendern, wobei Strahlen (Rays) von einer Kamera generiert werden und Schnittpunkte mit den Kugeln berechnet werden. Diese Schnittpunkte werden verwendet, um eine Bilddarstellung der Szene zu erstellen.

## Projektstruktur
- `src/`
    - `main/`
        - `java/`
            - `A_bilderzeugung/...`
            - `B_raytracing_start/`
                - `Camera.java`
                - `ColoredSpheres.java`
                - `Hit.java`
                - `Ray.java`
                - `Sphere.java`
                - `Main.java`

## Funktion der Klassen

**Camera**
Stellt die Kameraperspektive auf die Szene dar. Sie berechnet die Strahlen (Rays), die von der Kamera zu den Pixeln auf der Bildebene führen. Jeder Strahl wird durch die Kameraeigenschaften wie Blende (`alpha`), Bildbreite und -höhe generiert.

**ColoredSpheres**
Erzeugt eine Szene mit mehreren Kugeln, die zufällig im 3D-Raum positioniert sind. Jede Kugel hat eine zufällige Farbe und Größe. Die Klasse verwendet die Methode `intersect` der `Sphere`-Klasse, um zu bestimmen, ob ein Strahl mit einer der Kugeln schneidet, und gibt die entsprechende Farbe zurück.

**Ray**
Stellt einen Strahl im 3D-Raum dar. Ein Strahl besteht aus einem Ursprungspunkt (`origin`) und einer Richtung (`direction`). Die `Ray`-Klasse enthält Methoden zur Berechnung von Punkten auf dem Strahl und zur Validierung der Strahlenparameter.

**Hit**
Beschreibt einen Schnittpunkt (Hit) eines Strahls mit einem Objekt, in diesem Fall einer Kugel. Sie enthält den Parameter `t`, der angibt, wo der Strahl das Objekt schneidet, sowie den Schnittpunkt und den Normalenvektor an diesem Punkt.

**Sphere**
Stellt eine Kugel im 3D-Raum dar und enthält die Methode `intersect`, die den Schnittpunkt zwischen einem Strahl und der Kugel berechnet. Wenn der Strahl die Kugel schneidet, gibt die Methode ein `Hit`-Objekt zurück, das Informationen zum Schnittpunkt und zur Normalen enthält.

**Main**
Die Main Klasse erzeugt eine Kamera und eine Szene mit zufälligen Kugeln und rendert eine Szene mit zufällig generierten Kreise.

Genauere Erklärungen sind als Kommentare in den Klassen zu finden.