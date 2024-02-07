package org.firstinspires.ftc.teamcode.utils;

/**
 * Robot Constants contained in a convenient interface
 */
public interface Constants {

    /** Tuning for Odometry Localization X */
    double ODOMETRY_X_CORRECTOR = 0.000909166544136;

    /** Tuning for Odometry Localization Y */
    double ODOMETRY_Y_CORRECTOR = 0.000920761516143;

    /** Drivetrain Low Gear Multiplier */
    double LOW_GEAR = 0.5;

    /** Drivetrain High Gear Multiplier */
    double HIGH_GEAR = 0.8;

    /** Controller Deadband */
    double DEFAULT_DEADBAND = 0;

    /** Loops before axis is zero for enough time*/
    double LOOPS_TO_WAIT = 0;
    /** Turning limiter (it limits turning)*/
    double TURN_LIMITER = 0.5;

    /** Encoder Position Modifier for FB Motor */
    double FB_POWER_MULT = 0.6;

    /** Left clamp position when clamping */
    double LEFT_CLAMP = 0.35;

    /** Right clamp position when clamping */
    double RIGHT_CLAMP = 0;

    /** Left clamp position when not clamping */
    double LEFT_UNCLAMPED = 0;

    /** Right clamp position when not clamping */
    double RIGHT_UNCLAMPED = 0.3;

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

    double AUTO_DT_POWER = 0.5;

    double AUTO_PROP_LEFTBOUND = 213;

    double AUTO_PROP_RIGHTBOUND = 427;

    int AUTO_FB_POS = 105;

    double FB_POS_MULT = 0.08;

    double PPP_UP = 0.6;
    double PPP_DROP = 0.0;
}
