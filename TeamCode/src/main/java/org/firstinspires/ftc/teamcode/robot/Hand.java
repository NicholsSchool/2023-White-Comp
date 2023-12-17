package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utils.Constants;

public class Hand implements Constants {

    public Servo leftClamp, rightClamp;
    HardwareMap hwMap = null;
    public boolean clamped;
    public double wristPos;

    public Hand(HardwareMap ahwMap){

        hwMap = ahwMap;

        leftClamp = hwMap.get(Servo.class,"leftClamp");
        rightClamp = hwMap.get(Servo.class,"rightClamp");
        clamp(true, true);
    }

    public void clamp(boolean leftClamped, boolean rightClamped){
        leftClamp.setPosition(leftClamped ? LEFT_CLAMP : LEFT_UNCLAMPED);
        rightClamp.setPosition(rightClamped ? RIGHT_CLAMP : RIGHT_UNCLAMPED);

    }

    public void handTest(double leftPos, double rightPos){
        leftClamp.setPosition(leftPos);
        rightClamp.setPosition(rightPos);

    }

}
