package org.firstinspires.ftc.teamcode.teleops;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.Arrays;

import org.firstinspires.ftc.teamcode.robot.ATPoseCalculator;
import org.firstinspires.ftc.teamcode.robot.Arm;
import org.firstinspires.ftc.teamcode.robot.DriveTrain;
import org.firstinspires.ftc.teamcode.robot.Hand;
import org.firstinspires.ftc.teamcode.utils.Constants;

/**
 * A teleop for tuning drive motors using
 * FTC Dashboard
 */
@Config
@TeleOp(name="[DASHBOARD] DevTesting")
public class Dashboard extends OpMode implements Constants {
    public DriveTrain dt;
    public Arm arm;
    public ATPoseCalculator at;
    public Hand hand;
    public static double leftClamp;
    public static double rightClamp;
    public static double fourbarPower;
    public static int fourbarTarget;
    public static double armPower;
    @Override
    public void init() {
        hand = new Hand(hardwareMap);
        arm = new Arm(hardwareMap);
        dt = new DriveTrain(hardwareMap, 0, 0, 0);
        at = new ATPoseCalculator(hardwareMap);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    }

    @Override
    public void loop() {

        double armPos = arm.armPos();
        double fourbarPos = arm.fourBarPos();
        double leftPos = hand.leftClamp.getPosition();
        double rightPos = hand.rightClamp.getPosition();

        hand.handTest(leftClamp, rightClamp);

        arm.setPower(armPower);

        arm.fourbarUpdate(fourbarPower);

        dt.updateWithOdometry();

        telemetry.addData("armPos", armPos);
        telemetry.addData("fourbarPos", fourbarPos);
        telemetry.addData("leftPos", leftPos);
        telemetry.addData("rightPos", rightPos);
        telemetry.addData("Yaw from AHRS", dt.getHeadingNavX());
        telemetry.addData("ATPose", Arrays.toString(at.update()));
        telemetry.addData("robot X", dt.getX());
        telemetry.addData("robot y", dt.getY());
        telemetry.update();
    }
}