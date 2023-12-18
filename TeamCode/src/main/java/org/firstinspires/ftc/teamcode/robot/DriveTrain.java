package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor.RunMode;

import org.firstinspires.ftc.teamcode.utils.*;



public class DriveTrain implements Constants{

    //each motor is hooked up to their dead-wheel
    public DcMotorEx frontLeft, frontRight, backLeft, backRight;
    public Servo ppp;
    HardwareMap hwMap;

    public DriveTrain(HardwareMap ahwMap) {

        hwMap = ahwMap;

        frontLeft = hwMap.get(DcMotorEx.class, "frontLeft");
        frontRight = hwMap.get(DcMotorEx.class, "frontRight");
        backLeft = hwMap.get(DcMotorEx.class, "backLeft");
        backRight = hwMap.get(DcMotorEx.class, "backRight");

        ppp = hwMap.get(Servo.class, "pixelPooper");


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
    }

    public void drive(double power, double angle, double turn, boolean highGear){
        double kReorient = Math.PI / 4;
        double frontLeftPower = -power * Math.sin(angle + kReorient) - turn * TURN_LIMITER;
        double frontRightPower = -power * Math.cos(angle + kReorient) - turn * TURN_LIMITER;
        double backLeftPower = power * Math.cos(angle + kReorient) - turn * TURN_LIMITER;
        double backRightPower = power * Math.sin(angle + kReorient) - turn * TURN_LIMITER;

        frontLeft.setVelocity(highGear ? HIGH_GEAR * frontLeftPower : LOW_GEAR * frontLeftPower);
        frontRight.setVelocity(highGear ? HIGH_GEAR * frontRightPower : LOW_GEAR * frontRightPower);
        backLeft.setVelocity(highGear ? HIGH_GEAR * backLeftPower : LOW_GEAR * backLeftPower);
        backRight.setVelocity(highGear ? HIGH_GEAR * backRightPower : LOW_GEAR * backRightPower);
    }

    public void driveTest(double velocity){
        frontLeft.setVelocity(velocity);
        frontRight.setVelocity(-velocity);
        backRight.setVelocity(-velocity);
        backLeft.setVelocity(velocity);
    }

    public void driveToPosition(int fL, int fR, int bL, int bR) {
        frontLeft.setTargetPosition(fL);
        frontRight.setTargetPosition(fR);
        backLeft.setTargetPosition(bL);
        backRight.setTargetPosition(bR);

        frontLeft.setMode(RunMode.RUN_TO_POSITION);
        frontRight.setMode(RunMode.RUN_TO_POSITION);
        backLeft.setMode(RunMode.RUN_TO_POSITION);
        backRight.setMode(RunMode.RUN_TO_POSITION);

        frontLeft.setPower(AUTO_DT_POWER);
        frontRight.setPower(AUTO_DT_POWER);
        backLeft.setPower(AUTO_DT_POWER);
        backRight.setPower(AUTO_DT_POWER);
    }

    public int[] getDriveWheelPositions() {
        int fL = frontLeft.getCurrentPosition();
        int fR = frontRight.getCurrentPosition();
        int bL = backLeft.getCurrentPosition();
        int bR = backRight.getCurrentPosition();

        return new int[]{fL, fR, bL, bR};
    }

    public void pooperToPosition(double pooperPosition) {
        ppp.setPosition(pooperPosition);
    }
}
