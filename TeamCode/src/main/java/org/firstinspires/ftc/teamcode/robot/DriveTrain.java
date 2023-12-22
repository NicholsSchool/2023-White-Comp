package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utils.*;



public class DriveTrain implements Constants{

    //each motor is hooked up to their dead-wheel
    public DcMotorEx frontLeft, frontRight, backLeft, backRight;
    public AHRS navx;
    public Servo ppp;
    HardwareMap hwMap;

    public DriveTrain(HardwareMap hwMap) {

        this.hwMap = hwMap;

        frontLeft = hwMap.get(DcMotorEx.class, "frontLeft");
        frontRight = hwMap.get(DcMotorEx.class, "frontRight");
        backLeft = hwMap.get(DcMotorEx.class, "backLeft");
        backRight = hwMap.get(DcMotorEx.class, "backRight");

        ppp = hwMap.get(Servo.class, "pixelPooper");

        navx = AHRS.getInstance(hwMap.get(NavxMicroNavigationSensor.class, "navx"), AHRS.DeviceDataType.kProcessedData);

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
        double kReorient = (Math.PI / 4) + getHeadingNavX();
        double frontLeftPower = -power * Math.sin(angle + kReorient) - turn * TURN_LIMITER;
        double frontRightPower = -power * Math.cos(angle + kReorient) - turn * TURN_LIMITER;
        double backLeftPower = power * Math.cos(angle + kReorient) - turn * TURN_LIMITER;
        double backRightPower = power * Math.sin(angle + kReorient) - turn * TURN_LIMITER;

        frontLeft.setPower(highGear ? HIGH_GEAR * frontLeftPower : LOW_GEAR * frontLeftPower);
        frontRight.setPower(highGear ? HIGH_GEAR * frontRightPower : LOW_GEAR * frontRightPower);
        backLeft.setPower(highGear ? HIGH_GEAR * backLeftPower : LOW_GEAR * backLeftPower);
        backRight.setPower(highGear ? HIGH_GEAR * backRightPower : LOW_GEAR * backRightPower);
    }

    public void pooperToPosition(double pooperPosition) {
        ppp.setPosition(pooperPosition);
    }

    public double getHeadingNavX() {
        
        double heading;

        heading = Math.toRadians((double) (navx.getYaw()));

        return heading;
    }
}
