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

    public void setFourbar(int armPosition){
        if(armPosition < Constants.FOURBAR_THRESHOLD){
            fourBar.setTargetPosition(0);
            fourBar.setPower(0.5);
        }else{
            fourBar.setTargetPosition((int)(armPosition * Constants.FOURBAR_TO_ARM));
            fourBar.setPower(0.5);
        }
        fourBar.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        
    }

    public void wristToPosition(double position){
        wrist.setPosition(position);
    }

    public void rotateWrist(double power){
        double position =+ power;
        wrist.setPosition(position);
    }

    public void clamp(){
        leftClamp.setPosition(1);
        rightClamp.setPosition(1);
    }

    public void unclamp(){
        leftClamp.setPosition(0);
        rightClamp.setPosition(0);
    }
}