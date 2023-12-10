package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.utils.*;
public class Arm implements Constants{
    public DcMotorEx leftArm, rightArm, fourbar, winch;

    HardwareMap hwMap;

    public Arm(HardwareMap ahwMap){

        hwMap = ahwMap;

        leftArm = hwMap.get(DcMotorEx.class, "leftArm");
        rightArm = hwMap.get(DcMotorEx.class, "rightArm");
        fourbar = hwMap.get(DcMotorEx.class, "fourbar");
        winch = hwMap.get(DcMotorEx.class, "winch");

        leftArm.setDirection(DcMotorEx.Direction.FORWARD);
        rightArm.setDirection(DcMotorEx.Direction.FORWARD);
        fourbar.setDirection(DcMotorEx.Direction.FORWARD);
        winch.setDirection(DcMotorEx.Direction.FORWARD);

        leftArm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        rightArm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        fourbar.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        winch.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        fourbar.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        leftArm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        rightArm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        winch.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        winch.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        fourbar.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        leftArm.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightArm.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

    }

    public void move(double power){
        leftArm.setPower(-power);
        rightArm.setPower(-power);

//        fourbar.setTargetPosition((int)(armPos() * FOURBAR_CONSTANT));
//        fourbar.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
//        fourbar.setPower(FOURBAR_POWER);

    }

    public int armPos(){
        return rightArm.getCurrentPosition();
    }
    public void extend(double power){
        winch.setPower(-power);
    }

}
