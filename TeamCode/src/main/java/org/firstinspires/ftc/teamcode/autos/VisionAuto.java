package org.firstinspires.ftc.teamcode.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.robot.Arm;
import org.firstinspires.ftc.teamcode.robot.DriveTrain;
import org.firstinspires.ftc.teamcode.robot.Hand;
import org.firstinspires.ftc.teamcode.robot.PropDetector;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Autonomous", group = "")
public class VisionAuto extends LinearOpMode {

    // Declare OpMode members.
    private PropDetector pd;
    private ElapsedTime detectTime = new ElapsedTime(ElapsedTime.Resolution.SECONDS);
    private ElapsedTime waitTime = new ElapsedTime(ElapsedTime.Resolution.SECONDS);
    private DriveTrain dt;
    private Arm arm;
    private Hand hand;
    private boolean isRedAlliance = true;
    private boolean isFar = false;

    enum PropZones {

        LEFT,
        CENTER,
        RIGHT

    }

    @Override
    public void runOpMode() {

        pd = new PropDetector(hardwareMap, true);
        dt = new DriveTrain(hardwareMap, 0, 0, 0);
        arm = new Arm(hardwareMap);
        hand = new Hand(hardwareMap);

        hand.clamp(true, true);

        Recognition bestRec = null;

        detectTime.reset();

        while (opModeInInit()) {

            bestRec = pd.getBestRecognitions();

            telemetry.addData("DETECTING PROP...", detectTime.seconds());
            telemetry.update();

        }

        waitForStart();

        double purplePixelAngle;

        PropZones propZone;

        try {
            int point = (int) ((bestRec.getLeft() + bestRec.getRight()) / 2);
            if (point < 250) {
                propZone = PropZones.CENTER;
                purplePixelAngle = 0;
            } else {
                propZone = PropZones.RIGHT;
                purplePixelAngle = 0.56;
            }
        } catch (NullPointerException e) {
            propZone = PropZones.LEFT;
            purplePixelAngle = -0.76;
        }

        telemetry.addData("Prop detected in", propZone);
        telemetry.update();

        waitTime.reset();
        while (waitTime.time() < 1) {

        }

        // telemetry.addLine("Driving to spike marks...");
        // telemetry.update();
        // dt.driveToPosition(0, 20, 0.5, 0.5);

        // telemetry.addData("Aligning to angle", purplePixelAngle);
        // telemetry.update();
        // dt.autoAlign(purplePixelAngle);

        // telemetry.addLine("Rotating Fourbar...");
        // telemetry.update();
        // arm.setFourbarPos(120);

        // telemetry.addLine("Rotating Arm...");
        // telemetry.update();
        // arm.setArmPos(560);

        // telemetry.addLine("Releasing Pixel...");
        // telemetry.update();
        // hand.clamp(true, false);

        // telemetry.addLine("Aligning to audience");
        // telemetry.update();
        // dt.autoAlign(isRedAlliance ? Math.PI / 2 : -Math.PI / 2);

        if (!isFar) {
            telemetry.addLine("Parking");
            telemetry.update();
            dt.driveToPosition(isRedAlliance ? 20 : -20, 5, 0.75, 1);
        }

        telemetry.addLine("Arm Extending");
        telemetry.update();
        waitTime.reset();
        while (waitTime.seconds() < 4) {
            arm.extendWinch(0.25);
        }

    }
}
