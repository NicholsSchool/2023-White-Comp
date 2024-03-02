package org.firstinspires.ftc.teamcode.teleops;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.controller.GameController;
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
@TeleOp(name="[DASHBOARD] DevTesting", group = "Develop")
public class Dashboard extends OpMode implements Constants {
    public DriveTrain dt;
    public Arm arm;
    public Hand hand;
    public static double fourbarPower;
    private GameController driverOI;
    public static int fourbarTarget;
    public static double armPower;
    public double fLPower, fRPower, bLPower, bRPower;
    public VectorPath vecpath;
    @Override
    public void init() {
        hand = new Hand(hardwareMap);
        arm = new Arm(hardwareMap, telemetry);
        dt = new DriveTrain(hardwareMap, 0, 0, 0, telemetry);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        driverOI = new GameController(gamepad1);
        dt.resetIMU();
    }

    public void start() {
        dt.setFloat();
        // dt.driveToPosition(0, 50, 0.7, 1);
    }

    @Override
    public void loop() {

        double armPos = arm.armPos();
        double wristPos = arm.wristPos();
        double leftPos = hand.leftClamp.getPosition();
        double rightPos = hand.rightClamp.getPosition();

        dt.frontLeft.setPower(driverOI.y.get() ? 1.0 : 0.0);
        dt.frontRight.setPower(driverOI.x.get() ? 1.0 : 0.0);
        dt.backLeft.setPower(driverOI.a.get() ? 1.0 : 0.0);
        dt.backRight.setPower(driverOI.b.get() ? 1.0 : 0.0);

        arm.setPower(armPower);

        dt.updateWithOdometry();

        driverOI.updateValues();

        telemetry.addData("armPos", armPos);
        telemetry.addData("wrist Position", wristPos);
        telemetry.addData("RAW Yaw from AHRS", dt.getHeadingNavX());
        telemetry.addData("leftServo", leftPos);
        telemetry.addData("rightServo", rightPos);
        telemetry.addData("raw f/b odom val", dt.backLeft.getCurrentPosition());
        telemetry.addData("raw l/r odom val", dt.backRight.getCurrentPosition());
        telemetry.addData("x", dt.getX());
        telemetry.addData("y", dt.getY());
        telemetry.update();
    }
}