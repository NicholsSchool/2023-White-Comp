package org.firstinspires.ftc.teamcode.autos;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.robot.DriveTrain;
import org.firstinspires.ftc.teamcode.robot.PropDetector;
import org.firstinspires.ftc.teamcode.utils.Constants;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Disabled
@Autonomous(name = "the auto (red)")
public class Auto extends LinearOpMode implements Constants {

    @Override
    public void runOpMode() {

        DriveTrain drivetrain = new DriveTrain(hardwareMap);
        // TODO: Make one for blue alliance
        PropDetector pd = new PropDetector(hardwareMap, false);

        Recognition bestRec = pd.getBestRecognitions();

        ElapsedTime time = new ElapsedTime();

        double x, y;

        if (bestRec != null && bestRec.getRight() < AUTO_PROP_LEFTBOUND) {

            x = PROP_LEFT_X;
            y = PROP_LEFT_Y;
            telemetry.addLine("pROP iS lEFT");

        } else if (bestRec != null && bestRec.getRight() > AUTO_PROP_RIGHTBOUND) {

            x = PROP_RIGHT_X;
            y = PROP_RIGHT_Y;
            telemetry.addLine("pROP iS right");

        } else {

            x = PROP_CENTER_X;
            y = PROP_CENTER_Y;
            if (bestRec == null)
                telemetry.addLine("no");
            telemetry.addLine("centre");
        }

        waitForStart();

        time.reset();

        while (opModeIsActive()) {
            while (time.seconds() < 3) {
                //drivetrain.driveToPosition(x, y);
                telemetry.addData("motor", drivetrain.frontLeft.getCurrentPosition());
            }

            drivetrain.pooperToPosition(PPP_DROP);
            
        }

    }

}