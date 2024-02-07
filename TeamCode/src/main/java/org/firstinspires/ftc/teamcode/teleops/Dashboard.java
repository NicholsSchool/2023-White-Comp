package org.firstinspires.ftc.teamcode.teleops;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.Arrays;

import org.firstinspires.ftc.teamcode.controller.GameController;
import org.firstinspires.ftc.teamcode.robot.ATPoseCalculator;
import org.firstinspires.ftc.teamcode.robot.Arm;
import org.firstinspires.ftc.teamcode.robot.DriveTrain;
import org.firstinspires.ftc.teamcode.robot.Hand;
import org.firstinspires.ftc.teamcode.utils.Constants;
import org.firstinspires.ftc.teamcode.utils.VectorPath;

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
    private GameController driverOI;
    public static int fourbarTarget;
    public static double armPower;
    public VectorPath vecpath;
    @Override
    public void init() {
        hand = new Hand(hardwareMap);
        arm = new Arm(hardwareMap);
        dt = new DriveTrain(hardwareMap, 0, 0, 0);
        at = new ATPoseCalculator(hardwareMap);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        driverOI = new GameController(gamepad1);
        vecpath = new VectorPath(hardwareMap, "robot_oriented_test.json");
    }

    public void start() {
        // dt.driveToPosition(0, 50, 0.7, 1);
    }

    @Override
    public void loop() {

        double armPos = arm.armPos();
        double wristPos = arm.wristPos();
        double leftPos = hand.leftClamp.getPosition();
        double rightPos = hand.rightClamp.getPosition();

        hand.handTest(leftClamp, rightClamp);

        arm.setPower(armPower);

        arm.fourbarUpdate(fourbarPower);

        dt.updateWithOdometry();

        driverOI.updateValues();

        telemetry.addData("armPos", armPos);
        telemetry.addData("wrist Position", wristPos);
        telemetry.addData("Yaw from AHRS", dt.getHeadingNavX());
        telemetry.addData("leftServo", leftPos);
        telemetry.addData("rightServo", rightPos);
        telemetry.addData("x", dt.getX());
        telemetry.addData("y", dt.getY());
        telemetry.update();
    }
}