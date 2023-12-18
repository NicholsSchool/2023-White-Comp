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

        int fL, fR, bL, bR;

        if (bestRec != null && bestRec.getRight() < AUTO_PROP_LEFTBOUND) {

            fL = PROP_LEFT_FL_POSITION;
            fR = PROP_LEFT_FR_POSITION;
            bL = PROP_LEFT_BL_POSITION;
            bR = PROP_LEFT_BR_POSITION;
            telemetry.addLine("pROP iS lEFT");

        } else if (bestRec != null && bestRec.getRight() > AUTO_PROP_RIGHTBOUND) {

            fL = PROP_RIGHT_FL_POSITION;
            fR = PROP_RIGHT_FR_POSITION;
            bL = PROP_RIGHT_BL_POSITION;
            bR = PROP_RIGHT_BR_POSITION;
            telemetry.addLine("pROP iS right");

        } else {

            fL = PROP_CENTER_FL_POSITION;
            fR = PROP_CENTER_FR_POSITION;
            bL = PROP_CENTER_BL_POSITION;
            bR = PROP_CENTER_BR_POSITION;
            if (bestRec == null)
                telemetry.addLine("no");
            telemetry.addLine("centre");
        }

        waitForStart();

        time.reset();

        while (opModeIsActive()) {
            while (time.seconds() < 3) {
                drivetrain.driveToPosition(fL, fR, bL, bR);
                telemetry.addData("motor", drivetrain.frontLeft.getCurrentPosition());
            }

            drivetrain.pooperToPosition(PPP_DROP);
            
        }

    }

}
