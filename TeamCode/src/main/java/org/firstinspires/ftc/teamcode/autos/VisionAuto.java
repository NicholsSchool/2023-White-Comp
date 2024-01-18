package org.firstinspires.ftc.teamcode.autos;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.List;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.robot.PropDetector;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class VisionAutio instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Vision Auto", group="")
@Disabled
public class VisionAuto extends LinearOpMode {

    // Declare OpMode members.
    private PropDetector pd = new PropDetector(hardwareMap, true);
    private ElapsedTime detectTime = new ElapsedTime(ElapsedTime.Resolution.SECONDS);
    private List<Recognition> recs;

    @Override
    public void runOpMode() {

        detectTime.reset();

        while (opModeInInit()) {

            recs = pd.getRecognitions();

            if (recs != null) {
                break;
            }

            telemetry.addData("DETECTING PROP...", detectTime.seconds());
            telemetry.update();

        }

        Recognition bestRec = pd.getBestRecognitions();

        telemetry.addData("FOUND!", bestRec.getLeft());
        telemetry.update();


        waitForStart();


        while (opModeIsActive()) {

            telemetry.addLine("yeah woo yeah we're moving oh yeah");

            telemetry.update();

        }
    }
}
