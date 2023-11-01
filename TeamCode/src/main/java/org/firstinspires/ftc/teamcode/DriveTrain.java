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


    public int getRedPosition(){
        return redMotor.getCurrentPosition();
    }
    
    public int getBluePosition(){
        return blueMotor.getCurrentPosition();
    }

    public int getGreenPosition(){
        return greenMotor.getCurrentPosition();
    }

    public int getYellowPosition(){
        return yellowMotor.getCurrentPosition();
    }

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