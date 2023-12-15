package org.firstinspires.ftc.teamcode.utils;

/**
 * Robot Constants contained in a convenient interface
 */
public interface Constants {

    /** Drivetrain Low Gear Multiplier */
    double LOW_GEAR = 0.5 * 2800;

    /** Drivetrain High Gear Multiplier */
    double HIGH_GEAR = 0.7 * 2800;

    /** Controller Deadband */
    double DEFAULT_DEADBAND = 0;

    /** Loops before axis is zero for enough time*/
    double LOOPS_TO_WAIT = 0;
    /** Turning limiter (it limits turning)*/
    double TURN_LIMITER = 0.5;

    /** Encoder Position Modifier for FB Motor */
    double FOURBAR_CONSTANT = 0.1;

    /** Left clamp position when clamping */
    double LEFT_CLAMP = 0.31;

    /** Right clamp position when clamping */
    double RIGHT_CLAMP = 0.02;

    /** Left clamp position when not clamping */
    double LEFT_UNCLAMPED = 0.12;

    /** Right clamp position when not clamping */
    double RIGHT_UNCLAMPED = 0.15;

    /** Arm speed governor */
    double ARM_CONSTANT = 0.4;

    /** Arm Drone Launch Position */
    double LAUNCH_POSITION = 0;

    /** Default position for arm when not launching */
    double HELD_POSITION = 0.7;

    /** Forward distance of AT Camera from Center of Robot */
    double CAM_X_DIST = 0;

    /** Horizontal distance of AT Camera from Center of Robot */
    double CAM_Y_DIST = 0;

    // thanks anthony :D

    /** X Coordinate of Intake Side April Tags */
    double APRIL_TAG_INTAKE_X = 72;

    /** Y Coordinate of Tag #7 */
    double APRIL_TAG_7_Y = 42;

    /** Y Coordinate of Tag #8 */
    double APRIL_TAG_8_Y = 36;

    /** Y Coordinate of Tag #9 */
    double APRIL_TAG_9_Y = -36;

    /** Y Coordinate of Tag #10 */
    double APRIL_TAG_10_Y = -42;

    /** X Coordinate of Scoring Side April Tags */
    double APRIL_TAG_SCORING_X = -61.5;

    /** Y Coordinate of Tag #1 */
    double APRIL_TAG_1_Y = -42;

    /** Y Coordinate of Tag #2 */
    double APRIL_TAG_2_Y = -36;

    /** Y Coordinate of Tag #3 */
    double APRIL_TAG_3_Y = -30;

    /** Y Coordinate of Tag #4 */
    double APRIL_TAG_4_Y = 30;

    /** Y Coordinate of Tag #5 */
    double APRIL_TAG_5_Y = 36;

    /** Y Coordinate of Tag #6 */
    double APRIL_TAG_6_Y = 42;

    //end of thanks anthony D:

    
}
