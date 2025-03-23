package H_beschleunigung;

import static lib_cgtools.Matrix.identity;
import static lib_cgtools.Matrix.multiply;
import static lib_cgtools.Matrix.rotation;
import static lib_cgtools.Matrix.scaling;
import static lib_cgtools.Matrix.translation;
import static lib_cgtools.Vector.direction;
import static lib_cgtools.Vector.zero;

import lib_cgtools.Matrix;


/*
 * Stellt ein Arm oder Bein in der Szene dar.
 */
public class Limb extends Group{
    double shoulderAngle;
    double elbowAngle;
    double shoulderRadius;
    double armWidth;
    double armLength;
    double elbowRadius;
    double lowerArmWidth;
    double lowerArmLength;
    Material material;

    public Limb(
        Material material,
        double shoulderAngle,
        double elbowAngle,
        double shoulderRadius,
        double elbowRadius,
        double armWidth,
        double armLength,
        double lowerArmWidth,
        double lowerArmLength
        ) {
        super(identity);
        this.material = material;
        this.shoulderAngle = shoulderAngle;
        this.elbowAngle = elbowAngle;
        this.shoulderRadius = shoulderRadius;
        this.elbowRadius = elbowRadius;
        this.armWidth = armWidth;
        this.armLength = armLength;
        this.lowerArmWidth = lowerArmWidth;
        this.lowerArmLength = lowerArmLength;

        //Schulter
        Group schoulderGroup = new Group(rotation(direction(1, 0, 0), shoulderAngle));
        schoulderGroup.add(new Sphere(zero, shoulderRadius, this.material));
        this.add(schoulderGroup);

        
        //Oberarm
        Matrix upperArmTransform = Matrix.multiply(
            translation(0, -shoulderRadius/2, 0),
            scaling(armWidth, armLength, armWidth / 2));
        Group upperArmGroup = new Group(identity);
        Group upperArm = new Group(upperArmTransform);
        upperArm.add(new Cube(armWidth, material));
        upperArmGroup.add(upperArm);
        schoulderGroup.add(upperArmGroup);
        

        //Ellbogen
        Group elbowGroup = new Group(multiply(
            translation(0, (-shoulderRadius - armLength - elbowRadius) , 0), 
            rotation(1,0,0, elbowAngle)));
        elbowGroup.add(new Sphere(zero, elbowRadius, material));
        upperArmGroup.add(elbowGroup);
        //this.add(elbowGroup);

        
        //Unterarm
        Matrix lowerArmTransform = Matrix.multiply(
            translation(direction(0,-(elbowRadius/2),0)),
            scaling(lowerArmWidth, lowerArmLength, 1));
        Group lowerArmGroup = new Group(identity);
        Group lowerArm = new Group(lowerArmTransform);
        lowerArm.add(new Cube(lowerArmWidth, material));
        lowerArmGroup.add(lowerArm);
        elbowGroup.add(lowerArmGroup);


        //Hand
        Matrix handTransform = translation(direction(0,-shoulderRadius - armLength - elbowRadius,0));
        Group handGroup = new Group(handTransform);
        handGroup.add(new Sphere(zero, elbowRadius, material));
        lowerArmGroup.add(handGroup);
        //this.add(elbowGroup);

    }
}
