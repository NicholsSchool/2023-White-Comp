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


    /**
     * 
     * Rotates the arm with the specified power.
     * Also rotates the virtual four bar motor to the inverse of the motor position.
     * 
     * @param power Speed at which to rotate the arm (for teleop)
     */
    public void rotateArm(double power){
        
        leftArm.setPower(power);
        rightArm.setPower(power);

        // hand.setFourbar((leftArm.getCurrentPosition() + rightArm.getCurrentPosition()) / 2); 
    }
    
    /**
     * 
     * Extends the arm with the specified power.
     * Uses the winch motor. Does not retract.
     * 
     * @param power The power at which to rotate the winch motor
     */

    public void extendArm(double power){
        winch.setPower(power);
    }
        
}
