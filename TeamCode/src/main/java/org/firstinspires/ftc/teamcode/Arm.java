package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Arm {
    
    public DcMotor winch, leftArm, rightArm;

    HardwareMap hwMap = null;
    Hand hand = new Hand();

    public void init( HardwareMap ahwMap ){
        // Save reference to Hardware map
        HardwareMap hwMap = ahwMap;

        winch  = hwMap.get(DcMotor.class, "winch");
        leftArm  = hwMap.get(DcMotor.class, "leftArm");
        rightArm = hwMap.get(DcMotor.class, "rightArm");
        
        winch.setDirection(DcMotor.Direction.FORWARD);
        leftArm.setDirection(DcMotor.Direction.FORWARD);
        rightArm.setDirection(DcMotor.Direction.REVERSE);

        winch.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        winch.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        winch.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    } 

    public void rotateArm(double power){
        
        leftArm.setPower(power);
        rightArm.setPower(power);

        // hand.setFourbar((leftArm.getCurrentPosition() + rightArm.getCurrentPosition()) / 2); 
    }
    
    public void extendArm(double power){
        winch.setPower(power);
    }
        
}
