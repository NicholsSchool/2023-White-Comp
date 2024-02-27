package org.firstinspires.ftc.teamcode.autos;

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
        
        ac = new AutoContainer(hardwareMap, Alliance.RED, FieldSide.AUDIENCE);

        waitForStart();
        
        ac.runAutoSequence();

    }
}
