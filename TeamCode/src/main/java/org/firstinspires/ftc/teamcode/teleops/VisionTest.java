package org.firstinspires.ftc.teamcode.teleops;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.robot.PropDetector;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name="Vision Testing", group = "Develop")
@Disabled
public class VisionTest extends OpMode {

    PropDetector pd;

    public void init() {

        pd = new PropDetector(hardwareMap);

    }
    
    public void loop() {

        Recognition bestRec = pd.getBestRecognition();

        if (bestRec == null) { return; }
        
        telemetry.addData("> PREDICTION CONFIDENCE", bestRec.getConfidence());
        telemetry.addData("> LABEL", bestRec.getLabel());

    }

    public void stop() {

        pd.stopDetecting();

    }

}