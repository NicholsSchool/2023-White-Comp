package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.kauailabs.navx.ftc.AHRS;

import org.firstinspires.ftc.teamcode.utils.*;



public class DriveTrain implements Constants{

    //each motor is hooked up to their dead-wheel
    public DcMotorEx frontLeft, frontRight, backLeft, backRight;
    private AHRS navx;
    HardwareMap hwMap;
    //odometry stuff
    public double x, y;
    private int lastBR, lastBL;
    private final double angleOffset;



    public DriveTrain(HardwareMap hwMap, double startX, double startY, double angleOffset) {

        this.hwMap = hwMap;
        this.x = startX;
        this.y = startY;
        this.angleOffset = angleOffset;


        frontLeft = hwMap.get(DcMotorEx.class, "frontLeft");
        frontRight = hwMap.get(DcMotorEx.class, "frontRight");
        backLeft = hwMap.get(DcMotorEx.class, "backLeft");
        backRight = hwMap.get(DcMotorEx.class, "backRight");

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
        double kReorient = ((3 * Math.PI) / 4 + getHeadingNavX()) + angleOffset;
        double frontLeftPower = power * Math.cos(angle + kReorient) - turn * TURN_LIMITER;
        double frontRightPower = -power * Math.sin(angle + kReorient) - turn * TURN_LIMITER;
        double backLeftPower = power * Math.sin(angle + kReorient) - turn * TURN_LIMITER;
        double backRightPower = -power * Math.cos(angle + kReorient) - turn * TURN_LIMITER;

        frontLeft.setPower(highGear ? HIGH_GEAR * frontLeftPower : LOW_GEAR * frontLeftPower);
        frontRight.setPower(highGear ? HIGH_GEAR * frontRightPower : LOW_GEAR * frontRightPower);
        backLeft.setPower(highGear ? HIGH_GEAR * backLeftPower : LOW_GEAR * backLeftPower);
        backRight.setPower(highGear ? HIGH_GEAR * backRightPower : LOW_GEAR * backRightPower);
    }

    public void autoAlign(double desiredAngle){
        
        double alignError =  Calculator.addAngles(desiredAngle, -getHeadingNavX());
        while (Math.abs(alignError) > 0.05) {
            alignError = Calculator.addAngles(desiredAngle, -getHeadingNavX());
            drive(0,0,alignError * 0.5, false);
        }
        drive(0, 0, 0, false);

    }

    public double getHeadingNavX() {
        
        return Math.toRadians((double) (navx.getYaw()));

    }

    public void updateWithOdometry(){
        int currentBL = backLeft.getCurrentPosition();
        int currentBR = backRight.getCurrentPosition();

        int deltaBR = currentBR - lastBR;
        int deltaBL = currentBL - lastBL;

        double deltaY = deltaBL * ODOMETRY_X_CORRECTOR;
        double deltaX = deltaBR * ODOMETRY_Y_CORRECTOR;

        y += deltaX * Math.cos(getHeadingNavX()) + deltaY * Math.sin(getHeadingNavX());
        x += deltaX * Math.sin(getHeadingNavX()) + deltaY * Math.cos(getHeadingNavX());

        lastBR = currentBR;
        lastBL = currentBL;

    }

    public void driveToPosition(double x, double y, double power, double returnThreshold){

        while ( !((x - returnThreshold) < this.x && this.x < (x + returnThreshold) && (y - returnThreshold) < this.y && this.y < (y + returnThreshold))) {
            updateWithOdometry();
            double slope = (this.y - y) / (this.x - x); 
            double angle = this.x < x ? Math.atan(slope) : Calculator.addAngles(Math.atan(slope), Math.PI);
            drive(power, angle, 0, true);
        }

        drive(0, 0, 0, false);

        return;
    }

    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }

    public void resetIMU() {
        navx.zeroYaw();
    }

}
