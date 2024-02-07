package org.firstinspires.ftc.teamcode.utils;

/**
 * A Math Utilities Class for the Robot.
 */
public class Calculator implements Constants {
    /**
     * Adds two angles that are measured in degrees
     *
     * @param angle1 the first angle
     * @param angle2 the second angle
     * @return the sum in the range [-Pi, Pi]
     */
    public static double addAngles(double angle1, double angle2) {
        double sum = angle1 + angle2;
        while(sum >= Math.PI)
            sum -= 2 * Math.PI;
        while(sum < -Math.PI)
            sum += 2 * Math.PI;
        return sum;
    }
}
