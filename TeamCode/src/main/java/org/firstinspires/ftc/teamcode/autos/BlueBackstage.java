package org.firstinspires.ftc.teamcode.autos;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robot.AutoContainer;
import org.firstinspires.ftc.teamcode.robot.AutoContainer.Alliance;
import org.firstinspires.ftc.teamcode.robot.AutoContainer.FieldSide;

@Autonomous(name="Auto - Blue/Backstage", group = "CompAutos")
public class BlueBackstage extends LinearOpMode {

    // Declare OpMode members.
    private AutoContainer ac;

    @Override
    public void runOpMode() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        ac = new AutoContainer(hardwareMap, Alliance.BLUE, FieldSide.BACKSTAGE, telemetry);

        while (opModeInInit()) {
             ac.getRecsLoop();
        }


        waitForStart();

        ac.getPropPos();
        
        ac.runAutoSequence();

    }
}