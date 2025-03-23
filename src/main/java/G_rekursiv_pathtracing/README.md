# Rekursiver Path tracer
    Wir erweiterten den Raytracer um ein rekursives Monte Carlo Path Tracing, um globale Beleuchtungseffekte darstellen zu können.

## Projektstruktur
- `src/`
    - `main/`
        - `java/`
            - `A_bilderzeugung/...`
            - `B_raytracing_start/...`
            - `C_szene_background_ebene/...`
            - `D_beleuchtung_schatten/...`
            - `E_transformationen/...`
            - `F_texturen/...`
            - `G_rekursiv_pathtracing`
                - `Background.java`
                - `Camera.java`
                - `ClampTexture.java`
                - `ConstantColor.java`
                - `Cube.java`
                - `DirectionalLight.java`
                - `DirectLight.java`
                - `DiscXZ.java`
                - `Group.java`
                - `Hit.java`
                - `Human.java`
                - `Limb.java`
                - `Main.java`
                - `Material.java`
                - `MirrorRepeatTexture.java`
                - `PhongMaterial.java`
                - `PointLight.java`
                - `Ray.java`
                - `Raytracer.java`
                - `RectTexture.java`
                - `RectXZ.java`
                - `RepeatTexture.java`
                - `Scene.java`
                - `Shape.java`
                - `Sphere.java`
                - `Transform.java`

## Funktion der Klassen

**Background**
Stellt den Hintergrund der Szene dar. Sie ist erbt vom Interface Shape und bietet eine einfache Möglichkeit, eine Hintergrundfarbe für die Szene festzulegen. Wenn der Strahl kein Objekt in der Szene trifft, wird die Hintergrundfarbe als Farbwert zurückgegeben.

**Camera**
Stellt die Kameraperspektive auf die Szene dar. Sie berechnet die Strahlen (Rays), die von der Kamera zu den Pixeln auf der Bildebene führen. Jeder Strahl wird durch die Kameraeigenschaften wie Blende (`alpha`), Bildbreite und -höhe generiert.

**Cube**
Stellt einen Würfel in der Szene dar.

**ClampTexture**
Repräsentiert eine Textur, die die Farbwerte aus einer gegebenen Quelle abruft und sicherstellt, dass die Texturkoordinaten (u, v) innerhalb des Bereichs liegen. Liegen die Koordinaten außerhalb dieses Bereichs, wird eine Ersatzfarbe zurückgegeben.

**ConstantColor**
Gibt für jedes Pixel im Bild die gleiche Farbe zurück.

**Cube**
Repräsentiert einen Würfel, der aus sechs rechteckigen Flächen (RectXZ) besteht.

**DirectionalLight**
Repräsentiert gerichtetes Licht, das aus einer festen Richtung einfällt. Das heisst es hat keine Position, sondern nur eine Richtung während die Lichtintensität konstant bleibt.

**DirectLight**
Das Interface DirectLight definiert die grundlegenden Methoden für Lichtquellen in einer Raytracing-Szene. Es ermöglicht die Berechnung der Lichtintensität, Lichtstrahlrichtung und Sichtbarkeit eines Punktes in Bezug auf die Lichtquelle.

**DiscXZ**
Repräsentiert eine scheibenförmige Ebene im XZ-Plane (mit einer bestimmten Position und einem Radius). Sie implementiert ebenfalls das Shape-Interface und wird verwendet, um eine ebene Fläche als Objekt in der Szene zu erstellen. Ein Strahl wird mit dieser Fläche auf ähnliche Weise wie mit anderen Objekten in der Szene geschnitten.

**Group**
Die Group-Klasse repräsentiert eine Sammlung von Objekten (Shapes) in der Szene. Sie ermöglicht es, mehrere Objekte in einer Gruppe zu verwalten und bietet eine Methode, um den nächsten Schnittpunkt eines Strahls mit einem beliebigen Objekt in der Gruppe zu berechnen. Dies ist besonders nützlich, um hierarchische Szenen zu erstellen, die aus verschiedenen Objekttypen bestehen.

**Hit**
Beschreibt einen Schnittpunkt (Hit) eines Strahls mit einem Objekt, in diesem Fall einer Kugel. Sie enthält den Parameter `t`, der angibt, wo der Strahl das Objekt schneidet, sowie den Schnittpunkt und den Normalenvektor an diesem Punkt.

**Human**
Stellt ein Menschen in der Szene dar.

**Limb**
Stellt Gliedmaßen(Arm oder Bein) in der Szene dar.

**Material**
Das Material-Interface definiert Methoden zur Berechnung der Lichtreflexion  an Oberflächen von Objekten in der Szene. Dies umfasst sowohl die Reflexion des Umgebungslichts als auch die Reflexion von direktem Licht, das von Lichtquellen auf ein Objekt trifft.

**MirrorRepeatTexture**
Stellt eine Textur dar, bei der die Koordinaten u und v so gespiegelt werden, dass sie im Bereich bleiben und  wiederholt werden.

**PhongMaterial**
Implementiert das Phong-Beleuchtungsmodell, bestehend aus Ambientem Licht (Umgebungslicht), Diffuser Reflexion (Lambert'sches Modell), Spekularer Reflexion (Spiegelnde Glanzpunkte) und dem Shininess-Exponenten zur Glanzlicht Intensität

**Ray**
Stellt einen Strahl im 3D-Raum dar. Ein Strahl besteht aus einem Ursprungspunkt (`origin`) und einer Richtung (`direction`). Die `Ray`-Klasse enthält Methoden zur Berechnung von Punkten auf dem Strahl und zur Validierung der Strahlenparameter.

**Raytracer**
Die Raytracer-Klasse ist das Herzstück des Raytracing-Prozesses. Sie verwendet die Kamera, die Szene (bestehend aus Objekten wie Kugeln, Ebenen und dem Hintergrund) und die Beleuchtung, um für jedes Pixel im Bild die entsprechende Farbe zu berechnen. Die getColor-Methode berechnet den Schnittpunkt jedes Strahls mit den Objekten der Szene und ermittelt die Farbe basierend auf der Beleuchtung.

**RectTexture**
Erzeugt eine Textur, die abhängig von den Parametern u und v eine Farbe berechnet. Diese Textur wird verwendet, um verschiedene Farbtöne basierend auf den Texturkoordinaten zu erzeugen.

**RectXZ**
Repräsentiert ein 2D-Rechteck.

**RepeatTexture**
Erzeugt eine Textur, die sich wiederholt.

**Scene**
Die Scene-Klasse repräsentiert eine Szene im Raytracer. Sie besteht aus einer Sammlung von Objekten (Shapes) und Lichtquellen (DirectLights).

**Shape**
Das Interface Shape definiert die Methode intersect(Ray ray), die von allen Formen in der Szene implementiert wird. Es stellt sicher, dass alle Objekte der Szene (wie Kugeln, Ebenen oder andere geometrische Formen) in der Lage sind, zu berechnen, ob ein Strahl sie schneidet und wenn ja, die entsprechenden Schnittpunktinformationen zurückzugeben.

**Sphere**
Stellt eine Kugel im 3D-Raum dar und enthält die Methode `intersect`, die den Schnittpunkt zwischen einem Strahl und der Kugel berechnet. Wenn der Strahl die Kugel schneidet, gibt die Methode ein `Hit`-Objekt zurück, das Informationen zum Schnittpunkt und zur Normalen enthält.

**Transform**
Ermöglicht es, eine Textur zu transformieren.

**Main**
Die Main-Klasse erstellt eine Kamera, eine Szene und rendert das Bild.


Weitere  Erklärungen sind als Kommentare in den Klassen zu finden.