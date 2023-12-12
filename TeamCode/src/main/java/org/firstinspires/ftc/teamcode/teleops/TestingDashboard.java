package org.firstinspires.ftc.teamcode.teleops;

import org.firstinspires.ftc.teamcode.robot.Arm;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="TeleopWhichTestsTheArmMotorAndEncoderInRelationToTheEncoderLocatedWithinTheCoreHexMotorThatIsBeingUsedAsAVirtualFourBarMotorInOrderToMakeThemMoveInverselySoThatUltimatelyTheHandStaysParallelToTheGround")
@Config
public class TestingDashboard extends OpMode{

    Arm arm;

    public static double armPower;
    
    public void init() {

        arm = new Arm(hardwareMap);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

    }

    public void loop() {

        arm.setPower(armPower);
        telemetry.addData("> ARM MOTOR POS", arm.armPos());
        telemetry.addData("> FB MOTOR POS", arm.fourBarPos());
    }

}