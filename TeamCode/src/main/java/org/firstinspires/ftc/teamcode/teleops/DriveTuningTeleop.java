package org.firstinspires.ftc.teamcode.teleops;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.robot.DriveTrain;
import org.firstinspires.ftc.teamcode.utils.Constants;

/**
 * A teleop for tuning drive motors using
 * FTC Dashboard
 */
@Config
@TeleOp(name="[DASHBOARD] Drive Motor Tuning")
public class DriveTuningTeleop extends OpMode implements Constants
{
    private final ElapsedTime runtime = new ElapsedTime();
    public DriveTrain drivetrain;
    public static double lbp = 0;
    public static double lbi = 0;
    public static double lbd = 0;
    public static double lbf = 0;
    public static double rbp = 0;
    public static double rbi = 0;
    public static double rbd = 0;
    public static double rbf = 0;
    public static double lfp = 0;
    public static double lfi = 0;
    public static double lfd = 0;
    public static double lff = 0;
    public static double rfp = 0;
    public static double rfi = 0;
    public static double rfd = 0;
    public static double rff = 0;
//    public static double p = 10;
//    public static double i = 0;
//    public static double d = 1;
//    public static double f = 14;
    public static double targetMotorVelocity = 0.5;

    @Override
    public void init() {
        drivetrain = new DriveTrain(hardwareMap);
        drivetrain.backLeft.setVelocityPIDFCoefficients(lbp, lbi, lbd, lbf);
        drivetrain.backRight.setVelocityPIDFCoefficients(rbp, rbi,rbd, rbf);
        drivetrain.frontLeft.setVelocityPIDFCoefficients(lfp, lfi, lfd, lff);
        drivetrain.frontRight.setVelocityPIDFCoefficients(rfp, rfi, rfd, rff);
//        drivetrain.backLeft.setVelocityPIDFCoefficients(p, i, d, f);
//        drivetrain.backRight.setVelocityPIDFCoefficients(p, i ,d, f);
//        drivetrain.frontLeft.setVelocityPIDFCoefficients(p, i, d, f);
//        drivetrain.frontRight.setVelocityPIDFCoefficients(p, i, d, f);

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    }

    @Override
    public void loop() {
        if(runtime.time() > 5.0) {
            runtime.reset();
            targetMotorVelocity *= -1;
        }
//        drivetrain.backLeft.setVelocityPIDFCoefficients(p, i, d, f);
//        drivetrain.backRight.setVelocityPIDFCoefficients(p, i ,d, f);
//        drivetrain.frontLeft.setVelocityPIDFCoefficients(p, i, d, f);
//        drivetrain.frontRight.setVelocityPIDFCoefficients(p, i, d, f);

        drivetrain.backLeft.setVelocityPIDFCoefficients(lbp, lbi, lbd, lbf);
        drivetrain.backRight.setVelocityPIDFCoefficients(rbp, rbi,rbd, rbf);
        drivetrain.frontLeft.setVelocityPIDFCoefficients(lfp, lfi, lfd, lff);
        drivetrain.frontRight.setVelocityPIDFCoefficients(rfp, rfi, rfd, rff);

        drivetrain.driveTest(targetMotorVelocity);

        double backLeftVel = drivetrain.backLeft.getVelocity();
        double backRightVel = -drivetrain.backRight.getVelocity();
        double frontLeftVel = drivetrain.frontLeft.getVelocity();
        double frontRightVel = -drivetrain.frontRight.getVelocity();

        telemetry.addData("back left vel", backLeftVel);
        telemetry.addData("back right vel", backRightVel);
        telemetry.addData("front left vel", frontLeftVel);
        telemetry.addData("front right vel", frontRightVel);
        telemetry.addData("target vel", targetMotorVelocity);
        telemetry.update();
    }
}