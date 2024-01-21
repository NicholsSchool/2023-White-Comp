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
    private AHRS navx;
    HardwareMap hwMap;
    //odometry stuff
    public double x, y;
    private int lastFR, lastFL, lastBR, lastBL;
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
        double kReorient = (Math.PI / 4) + getHeadingNavX() + angleOffset;
        double frontLeftPower = -power * Math.sin(angle + kReorient) - turn * TURN_LIMITER;
        double frontRightPower = -power * Math.cos(angle + kReorient) - turn * TURN_LIMITER;
        double backLeftPower = power * Math.cos(angle + kReorient) - turn * TURN_LIMITER;
        double backRightPower = power * Math.sin(angle + kReorient) - turn * TURN_LIMITER;

        frontLeft.setPower(highGear ? HIGH_GEAR * frontLeftPower : LOW_GEAR * frontLeftPower);
        frontRight.setPower(highGear ? HIGH_GEAR * frontRightPower : LOW_GEAR * frontRightPower);
        backLeft.setPower(highGear ? HIGH_GEAR * backLeftPower : LOW_GEAR * backLeftPower);
        backRight.setPower(highGear ? HIGH_GEAR * backRightPower : LOW_GEAR * backRightPower);
    }


    public void autoAlign(double desiredAngle){
        
        double error = SplineMath.addAngles(getHeadingNavX(), -desiredAngle);
        while (Math.abs(error) >  0.3) {
            drive(0, 0, error * 0.5, false);
            error = SplineMath.addAngles(getHeadingNavX(), -desiredAngle);
        }
        return;
    }

    public double getHeadingNavX() {
        
        double heading;

        heading = Math.toRadians((double) (navx.getYaw()));

        return heading;
    }

    public void updateWithOdometry(){
        int currentFR = frontRight.getCurrentPosition();
        int currentFL = frontLeft.getCurrentPosition();
        int currentBR = backRight.getCurrentPosition();
        int currentBL = backLeft.getCurrentPosition();

        int deltaFR = currentFR - lastFR;
        int deltaFL = currentFL - lastFL;
        int deltaBR = currentBR - lastBR;
        int deltaBL = currentBL - lastBL;

        double deltaY = (deltaBL - deltaFR) / 2 * ODOMETRY_X_CORRECTOR;
        double deltaX = -(deltaFL - deltaBR) / 2 * ODOMETRY_Y_CORRECTOR;

        y += deltaX * Math.cos(getHeadingNavX()) + deltaY * Math.sin(getHeadingNavX());
        x += deltaX * Math.sin(getHeadingNavX()) + deltaY * Math.cos(getHeadingNavX());

        lastFR = currentFR;
        lastBL = currentBL;
        lastBR = currentBR;
        lastFL = currentFL;

    }

    public void driveToPosition(double x, double y, double power, double returnThreshold){

        while ( !((x - returnThreshold) < this.x && this.x < (x + returnThreshold) && (y - returnThreshold) < this.y && this.y < (y + returnThreshold))) {
            updateWithOdometry();
            double slope = (this.y - y) / (this.x - x); 
            double angle = this.x < x ? Math.atan(slope) : Calculator.addAngles(Math.atan(slope), Math.PI);
            drive(power, angle, 0, true);
        }
        return;
    }

    public double getX(){
        return x;
    }
    public double getY(){
        return y;

    }

}
