package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;
import com.kauailabs.navx.ftc.AHRS;

import org.firstinspires.ftc.teamcode.utils.*;



public class DriveTrain implements Constants{

    //each motor is hooked up to their dead-wheel
    public DcMotorEx frontLeft, frontRight, backLeft, backRight;
    public AHRS navx;
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

        switch (navx.getBoardYawAxis().board_axis.name()) {

            case "kBoardAxisX":
                return Math.toRadians((double) (navx.getRoll()));
            
            case "kBoardAxisY":
                return Math.toRadians((double) (navx.getPitch()));

            default:
                return Math.toRadians((double) (navx.getYaw()));
        }

    }

    public void updateWithOdometry(){
        int currentBL = backLeft.getCurrentPosition();
        int currentBR = backRight.getCurrentPosition();

        int deltaBR = currentBR - lastBR;
        int deltaBL = currentBL - lastBL;

        double deltaY = deltaBL * ODOMETRY_X_CORRECTOR;
        double deltaX = deltaBR * ODOMETRY_Y_CORRECTOR;

        y += deltaX * Math.sin(getHeadingNavX()) + deltaY * Math.cos(getHeadingNavX());
        x += deltaX * Math.cos(getHeadingNavX()) + deltaY * Math.sin(getHeadingNavX());

        lastBR = currentBR;
        lastBL = currentBL;

    }
    /**
     * drives the bot to a position using lines 
     * @param x x waypoint
     * @param y y waypoint
     * @param power drive power to get there 
     * @param returnThreshold how far the bot should be before it starts slowing down, whileloop stops when it's 1 / 10th 
     * @param endPower how fast it's moving at the end
     */
    public void driveToPosition(double x, double y, double power, double returnThreshold, double endPower, boolean highGear){
        // makes x and y components for the drive vector 
        double xComp, yComp;
        double angle;
        
        //while the distance between the bot and target point is greater than the return threshold run the whileloop
        while(Math.hypot((x - getX()), (y - getY())) > 0.1 * returnThreshold){

            xComp = x - getX();
            yComp = y - getY();

            angle = Math.atan2(yComp, xComp);
            
            drive(Range.clip(power * Math.hypot((x - getX()), (y - getY())) / returnThreshold ,endPower, power), angle, 0, highGear);
        }
        drive(0,0,0,false);
       
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
