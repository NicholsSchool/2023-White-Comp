package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utils.*;
public class Arm implements Constants{
    public DcMotorEx leftArm, rightArm, fourbar, winch;
    public Servo plane;

    HardwareMap hwMap;

    public Arm(HardwareMap ahwMap) {

        hwMap = ahwMap;

        leftArm = hwMap.get(DcMotorEx.class, "leftArm");
        rightArm = hwMap.get(DcMotorEx.class, "rightArm");
        fourbar = hwMap.get(DcMotorEx.class, "fourbar");
        winch = hwMap.get(DcMotorEx.class, "winch");
        plane = hwMap.get(Servo.class, "planeServo");

        leftArm.setDirection(DcMotorEx.Direction.FORWARD);
        rightArm.setDirection(DcMotorEx.Direction.FORWARD);
        fourbar.setDirection(DcMotorEx.Direction.FORWARD);
        winch.setDirection(DcMotorEx.Direction.FORWARD);

        leftArm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        rightArm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        fourbar.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        winch.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        leftArm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        rightArm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        winch.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        fourbar.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        winch.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        fourbar.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        leftArm.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightArm.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

    }

    public void setPower(double power) {
        leftArm.setPower(- ARM_CONSTANT * power);
        rightArm.setPower(- ARM_CONSTANT * power);
    }
    public void fourbarPower(double power){
        fourbar.setPower(power * FB_POWER_MULT);
    }

    public void fourbarUpdate(double velocity){
        fourbar.setTargetPosition((int)(armPos() * FB_POS_MULT));
        fourbar.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fourbar.setVelocity(velocity);
    }

    public int armPos() {
        return rightArm.getCurrentPosition();
    }

    public int fourBarPos() {
        return fourbar.getCurrentPosition();
    }

    public void extend(double power) {
        winch.setPower(-power);
    }

    public void setPlane(boolean launchPlane) {
        plane.setPosition(launchPlane ? LAUNCH_POSITION : HELD_POSITION);
    }

}
