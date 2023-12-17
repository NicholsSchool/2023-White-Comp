package org.firstinspires.ftc.teamcode.autos;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.robot.Arm;
import org.firstinspires.ftc.teamcode.robot.DriveTrain;
import org.firstinspires.ftc.teamcode.robot.Hand;
import org.firstinspires.ftc.teamcode.robot.PropDetector;
import org.firstinspires.ftc.teamcode.utils.Constants;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "the auto (red)")
public class Auto extends LinearOpMode implements Constants {

    @Override
    public void runOpMode() {

        DriveTrain dt = new DriveTrain(hardwareMap);
        Arm arm = new Arm(hardwareMap);
        Hand hand = new Hand(hardwareMap);
        // TODO: Make one for blue alliance
        PropDetector pd = new PropDetector(hardwareMap, false);

        hand.clamp(true, true);


        Recognition bestRec = pd.getBestRecognitions();
        if (bestRec == null) {
            telemetry.addLine("NO PROP DETECTED!!!");
            telemetry.update();
            return;
        }

        int fL, fR, bL, bR;

        if (bestRec.getLeft() < AUTO_PROP_LEFTBOUND) {

            fL = PROP_LEFT_FL_POSITION;
            fR = PROP_LEFT_FR_POSITION;
            bL = PROP_LEFT_BL_POSITION;
            bR = PROP_LEFT_BR_POSITION;


        } else if (bestRec.getRight() > AUTO_PROP_RIGHTBOUND) {

            fL = PROP_RIGHT_FL_POSITION;
            fR = PROP_RIGHT_FR_POSITION;
            bL = PROP_RIGHT_BL_POSITION;
            bR = PROP_RIGHT_BR_POSITION;

        } else {

            fL = PROP_CENTER_FL_POSITION;
            fR = PROP_CENTER_FR_POSITION;
            bL = PROP_CENTER_BL_POSITION;
            bR = PROP_CENTER_BR_POSITION;
        }


        waitForStart();

        //COMAND CALLS FOR AUTO
        if( opModeIsActive() )
        {
            dt.driveToPosition(fL, fR, bL, bR);
             //TODO: ADD ARM
             hand.clamp(true, false);
        }

        //set motars to stop after auto, failsafe
        dt.drive(0,0,0,false); 
        arm.setPower( 0.0 );

    }

}
