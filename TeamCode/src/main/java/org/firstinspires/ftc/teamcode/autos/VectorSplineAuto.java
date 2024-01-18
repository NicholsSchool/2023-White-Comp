package org.firstinspires.ftc.teamcode.autos;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.utils.VectorPath;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class VectorSplineAuto instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(name="direction and magnitude", group="")
public class VectorSplineAuto extends LinearOpMode {

    VectorPath path;

    @Override
    public void runOpMode() {

        path = new VectorPath(hardwareMap, "robot_oriented_test.json");

        waitForStart();

        path.follow(false);

    }
}

