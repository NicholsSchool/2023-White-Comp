package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utils.Constants;

public class Hand implements Constants {

    public Servo leftClamp, rightClamp, wrist;
    HardwareMap hwMap = null;
    public boolean clamped;
    public double wristPos;

    public Hand(HardwareMap ahwMap, boolean clamped, double wristPos){

        hwMap = ahwMap;

        leftClamp = hwMap.get(Servo.class,"leftClamp");
        rightClamp = hwMap.get(Servo.class,"rightClamp");
        wrist = hwMap.get(Servo.class,"wrist");
    }

    public void clamp(boolean clamped){
        leftClamp.setPosition(clamped ? LEFT_CLAMP : LEFT_UNCLAMPED);
        rightClamp.setPosition(clamped ? RIGHT_CLAMP : RIGHT_UNCLAMPED);

    }

    public void wristToPos(double pos){
        wrist.setPosition(pos);
    }

}
