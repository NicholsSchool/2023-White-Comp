package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.utils.*;


public class DriveTrain implements Constants{

    //each motor is hooked up to their dead-wheel
    public DcMotorEx frontLeft, frontRight, backLeft, backRight;

    private int deltaFL, deltaFR, deltaBL, deltaBR;
    private int lastFL, lastFR, lastBL, lastBR;
    private int FL, FR, BL, BR;
    public double heading;
    private double x,y;
    HardwareMap hwMap;

    public DriveTrain(HardwareMap ahwMap, double heading, double[] coordinates) {

        hwMap = ahwMap;

        frontLeft = hwMap.get(DcMotorEx.class, "frontLeft");
        frontRight = hwMap.get(DcMotorEx.class, "frontRight");
        backLeft = hwMap.get(DcMotorEx.class, "backLeft");
        backRight = hwMap.get(DcMotorEx.class, "backRight");

        frontLeft.setDirection(DcMotorEx.Direction.FORWARD);
        frontRight.setDirection(DcMotorEx.Direction.FORWARD);
        backLeft.setDirection(DcMotorEx.Direction.FORWARD);
        backRight.setDirection(DcMotorEx.Direction.FORWARD);

        frontLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        frontLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        this.heading = heading;
        this.x = coordinates[0];
        this.y = coordinates[1];


    }

    public void updateWithOdometry() {
        FL = frontLeft.getCurrentPosition();
        FR = frontRight.getCurrentPosition();
        BL = backLeft.getCurrentPosition();
        BR = backRight.getCurrentPosition();

        deltaFL = FL - lastFL;
        deltaFR = FR - lastFR;
        deltaBL = BL - lastBL;
        deltaBR = BR - lastBR;

        double deltaHeading = (deltaFL - deltaBR + deltaFR - deltaBL) * ODOMETRY_HEADING_CORRECTOR;
        heading = Calculator.addAngles(heading, deltaHeading);

        double deltaX = (deltaFL + deltaBR) * 0.5 * ODOMETRY_X_CORRECTOR;
        double deltaY = (deltaFR + deltaBL) * 0.5 * ODOMETRY_Y_CORRECTOR;

        double inRadians = Math.toRadians(heading);
        y += -deltaX * Math.cos(inRadians) + deltaY * Math.sin(inRadians);
        x += deltaX * Math.sin(inRadians) + deltaY * Math.cos(inRadians);

        lastFL = FL;
        lastBL = BL;
        lastFR = FR;
        lastBR = BR;

    }

    public void drive(double power, double angle, double turn, boolean highGear){
        double kReorient = Math.PI / 4;
        double frontLeftPower = -power * Math.sin(angle +kReorient) - turn;
        double frontRightPower = -power * Math.cos(angle + kReorient) - turn;
        double backLeftPower = power * Math.cos(angle + kReorient) - turn;
        double backRightPower = power * Math.sin(angle + kReorient) - turn;

        frontLeft.setPower(highGear ? HIGH_GEAR * frontLeftPower : LOW_GEAR * frontLeftPower);
        frontRight.setPower(highGear ? HIGH_GEAR * frontRightPower : LOW_GEAR * frontRightPower);
        backLeft.setPower(highGear ? HIGH_GEAR * backLeftPower : LOW_GEAR * backLeftPower);
        backRight.setPower(highGear ? HIGH_GEAR * backRightPower : LOW_GEAR * backRightPower);
    }

    public double[] getCoordinates(){
        double[] coordinates = {x, y};
        return coordinates;
    }

}
