package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class DriveTrain implements Constants{
    
    public DcMotor redMotor, blueMotor, greenMotor, yellowMotor;
    
    private double heading = 0;
    private int lastRedPos, lastBluePos, lastGreenPos, lastYellowPos;
    private int deltaRedPos, deltaBluePos, deltaGreenPos, deltaYellowPos;
    HardwareMap hwMap = null;

    public void init( HardwareMap ahwMap ){
        
        HardwareMap hwMap = ahwMap;

        redMotor = hwMap.get(DcMotor.class, "redMotor");
        greenMotor  = hwMap.get(DcMotor.class, "greenMotor");
        yellowMotor = hwMap.get(DcMotor.class, "yellowMotor");
        blueMotor = hwMap.get(DcMotor.class, "blueMotor");
        
         yellowMotor.setDirection(DcMotor.Direction.FORWARD);
        redMotor.setDirection(DcMotor.Direction.FORWARD);
        blueMotor.setDirection(DcMotor.Direction.REVERSE);
        greenMotor.setDirection(DcMotor.Direction.REVERSE);

        yellowMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        greenMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        blueMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        redMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        
        yellowMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        greenMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        blueMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        redMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    } 

    /**
     * Resets the odometry encoders and then sets the motors to RUN_WITHOUT_ENCODER
     */
    public void resetEncoder(){
        
        yellowMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        greenMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        blueMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        redMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        yellowMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        greenMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        blueMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        redMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        
    }

    /**
     * Absolute encoder value of the RED encoder.
     * 
     * @return The current position of the RED encoder.
     */
    public int getRedPosition(){
        return redMotor.getCurrentPosition();
    }
    /**
     * Absolute encoder value of the BLUE encoder.
     * 
     * @return The current position of the BLUE encoder.
     */
    public int getBluePosition(){
        return blueMotor.getCurrentPosition();
    }

    /**
     * Absolute encoder value of the GREEN encoder.
     * 
     * @return The current position of the GREEN encoder.
     */
    public int getGreenPosition(){
        return greenMotor.getCurrentPosition();
    }

    /**
     * Absolute encoder value of the YELLOW encoder.
     * 
     * @return The current position of the YELLOW encoder.
     */
    public int getYellowPosition(){
        return yellowMotor.getCurrentPosition();
    }


    /**
     *  Calculates the heading of the robot using odometry. Should be called every loop.
     */
    public void calcHeading() {

        deltaRedPos = -(lastRedPos - redMotor.getCurrentPosition());
        deltaBluePos = -(lastBluePos - blueMotor.getCurrentPosition());
        deltaGreenPos = -(lastGreenPos - greenMotor.getCurrentPosition());
        deltaYellowPos = -(lastYellowPos - yellowMotor.getCurrentPosition());

        double slope = ((deltaGreenPos - deltaRedPos) * ODO_TICKS_TO_IN) / ODO_SPACING;

        heading += slope * SLOPE_TO_HEADING;

        lastRedPos = getRedPosition();
        lastBluePos = getBluePosition();
        lastGreenPos = getGreenPosition();
        lastYellowPos = getRedPosition();
    }

    /**
     * 
     * A method to get the heading of the robot relative to the initial heading using odometry.
     * 
     * @return The current heading of the robot.
     */    
    public double getHeading(){
        double trueHeading;
        double modHeading = heading % (2 * Math.PI);

        if(modHeading > Math.PI){
            trueHeading = modHeading - 2 * Math.PI;
        }else if (modHeading < -Math.PI){
            trueHeading = modHeading + 2 * Math.PI;
        }else{
            trueHeading = modHeading;
        }
        return trueHeading;
    }
    
    //orientation
    //   Y
    //G-----R
    //   B    

    /**
     * 
     * Moves the robot drivetrain using PowerAngle.
     * This function is for teleop control. It is field oriented with odometry.
     * 
     * @param angle The angle at which to turn the robot. Should be in radians.
     * @param power The power (0-1) at which the robot moves.
     * @param turn The power (0-1) at which the robot should rotate
     * @param highGear Whether or not the robot should be in high gear (full power or half)
     * 
     */
    public void drive(double angle, double power, double turn, boolean highGear){
        double redPower = power * Math.sin(angle + getHeading() + Constants.STARTING_ANGLE) - turn;
        double greenPower = power * Math.sin(angle + getHeading() + Constants.STARTING_ANGLE) + turn;
        double yellowPower = -power * Math.cos(angle + getHeading() + Constants.STARTING_ANGLE) - turn;
        double bluePower = -power * Math.cos(angle + getHeading() + Constants.STARTING_ANGLE) + turn;
        if(highGear){
            redMotor.setPower(redPower);
            greenMotor.setPower(greenPower);
            yellowMotor.setPower(yellowPower);
            blueMotor.setPower(bluePower);
        } else {
            redMotor.setPower(0.5 * redPower);
            greenMotor.setPower(0.5 * greenPower);
            yellowMotor.setPower(0.5 * yellowPower);
            blueMotor.setPower(0.5 * bluePower);
        }
    }   
        
}