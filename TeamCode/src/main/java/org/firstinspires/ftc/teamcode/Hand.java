package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Hand implements Constants{

    public DcMotor fourBar;
    public Servo leftClamp, rightClamp, wrist;

    HardwareMap hwMap = null;

    public void init(HardwareMap ahwMap){

        HardwareMap hwMap = ahwMap;

        fourBar = hwMap.get(DcMotor.class, "fourbar");

        leftClamp = hwMap.get(Servo.class, "leftClamp");
        rightClamp = hwMap.get(Servo.class, "rightClamp");
        wrist = hwMap.get(Servo.class, "wrist");

        fourBar.setDirection(DcMotor.Direction.FORWARD);
        fourBar.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        
    }

    // public void setFourbar(int armPosition){
    //     if(armPosition < Constants.FOURBAR_THRESHOLD){
    //         fourBar.setTargetPosition(Constants.ARM_DOWN_POSITION);
    //         fourBar.setPower(0.5);
    //     }else{
    //         fourBar.setTargetPosition((int)(armPosition * Constants.FOURBAR_TO_ARM));
    //         fourBar.setPower(0.5);
    //     }
    //     fourBar.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    // }

    public void wristToPosition(double position){
        wrist.setPosition(position);
    }

    /**
     * Clamp the pincers of the hand
    */
    public void clamp(){
        leftClamp.setPosition(SERVO_CLOSED);
        rightClamp.setPosition(1 - SERVO_CLOSED);
    }

    /**
     * Unclamp the pincers of the hand
     */
    public void unclamp(){
        leftClamp.setPosition(SERVO_OPENED);
        rightClamp.setPosition(1 - SERVO_OPENED);
    }
}
