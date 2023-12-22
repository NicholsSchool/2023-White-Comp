package org.firstinspires.ftc.teamcode.utils;

/**
 * Robot Constants contained in a convenient interface
 */
public interface Constants {

    /** Tuning for Odometry Heading Calculation */
    double ODOMETRY_HEADING_CORRECTOR = 1;

    /** Tuning for Odometry Localization X */
    double ODOMETRY_X_CORRECTOR = 1;

    /** Tuning for Odometry Localization Y */
    double ODOMETRY_Y_CORRECTOR = 1;

    /** Drivetrain Low Gear Multiplier */
    double LOW_GEAR = 0.5;

    /** Drivetrain High Gear Multiplier */
    double HIGH_GEAR = 0.7;

    /** Controller Deadband */
    double DEFAULT_DEADBAND = 0;

    /** Loops before axis is zero for enough time*/
    double LOOPS_TO_WAIT = 0;
    /** Turning limiter (it limits turning)*/
    double TURN_LIMITER = 0.5;

    /** Encoder Position Modifier for FB Motor */
    double FB_POWER_MULT = 0.6;

    /** Left clamp position when clamping */
    double LEFT_CLAMP = 0;

    /** Right clamp position when clamping */
    double RIGHT_CLAMP = 0.3;

    /** Left clamp position when not clamping */
    double LEFT_UNCLAMPED = 0.35;

    /** Right clamp position when not clamping */
    double RIGHT_UNCLAMPED = 0;

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

    double AUTO_DT_POWER = 0.5;

    double AUTO_PROP_LEFTBOUND = 213;

    double AUTO_PROP_RIGHTBOUND = 427;

    int AUTO_FB_POS = 105;

    int PROP_LEFT_FL_POSITION = -343;
    int PROP_LEFT_FR_POSITION = 681;
    int PROP_LEFT_BL_POSITION = -304;
    int PROP_LEFT_BR_POSITION = 591;

    int PROP_RIGHT_FL_POSITION = -994;
    int PROP_RIGHT_FR_POSITION = 199;
    int PROP_RIGHT_BL_POSITION = -619;
    int PROP_RIGHT_BR_POSITION = 231;

    int PROP_CENTER_FL_POSITION = -1015;
    int PROP_CENTER_FR_POSITION = 537;
    int PROP_CENTER_BL_POSITION = -583;
    int PROP_CENTER_BR_POSITION = 415;

    double FB_POS_MULT = 0.08;

    double PPP_UP = 0.6;
    double PPP_DROP = 0.0;
}
