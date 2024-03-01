package org.firstinspires.ftc.teamcode.autos;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robot.AutoContainer;
import org.firstinspires.ftc.teamcode.robot.AutoContainer.Alliance;
import org.firstinspires.ftc.teamcode.robot.AutoContainer.FieldSide;

@Autonomous(name="Test Auto", group="")
public class TestAuto extends LinearOpMode {

    // Declare OpMode members.
    private AutoContainer ac;

    @Override
    public void runOpMode() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        ac = new AutoContainer(hardwareMap, Alliance.RED, FieldSide.AUDIENCE, telemetry);

        while (opModeInInit()) {
             ac.getRecsLoop();
        }


        waitForStart();

        ac.getPropPos();
        
        ac.runAutoSequence();

    }
}
