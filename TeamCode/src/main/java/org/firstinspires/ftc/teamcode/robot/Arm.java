package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.utils.*;
public class Arm implements Constants{
    public DcMotorEx leftArm, rightArm, wrist, winch;
    public Servo plane;
    private ElapsedTime timeout = new ElapsedTime(ElapsedTime.Resolution.SECONDS);

    HardwareMap hwMap;

    public Arm(HardwareMap ahwMap) {

        hwMap = ahwMap;

        leftArm = hwMap.get(DcMotorEx.class, "leftArm");
        rightArm = hwMap.get(DcMotorEx.class, "rightArm");
        wrist = hwMap.get(DcMotorEx.class, "wrist");
        winch = hwMap.get(DcMotorEx.class, "winch");
        plane = hwMap.get(Servo.class, "planeServo");

        leftArm.setDirection(DcMotorEx.Direction.FORWARD);
        rightArm.setDirection(DcMotorEx.Direction.FORWARD);
        wrist.setDirection(DcMotorEx.Direction.FORWARD);
        winch.setDirection(DcMotorEx.Direction.FORWARD);

        leftArm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        rightArm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        wrist.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        winch.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        leftArm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        rightArm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        winch.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        wrist.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        winch.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        wrist.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        leftArm.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightArm.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

    }

    public void setPower(double power) {
        leftArm.setPower(- ARM_CONSTANT * power);
        rightArm.setPower(- ARM_CONSTANT * power);
    }
    public void wristPower(double power){
        wrist.setPower(power * FB_POWER_MULT);
    }
    
    public void setWristPos(int targetPos) {
        double error = targetPos - wristPos();
        timeout.reset();
        while (Math.abs(error) > 15){
            error = targetPos - wristPos();
            wristPower(error * 0.008);
        }
        wristPower(0);
        
    }

    public void setArmPos(int targetPos) {
        double error = targetPos - armPos();
        while (Math.abs(error) > 15){
            error = targetPos - armPos();
            setPower(error * -0.001);
        }
        setPower(0);
    }

    public int armPos() {
        return rightArm.getCurrentPosition();
    }

    public int wristPos() {
        return wrist.getCurrentPosition();
    }

    public void extendWinch(double power) {
        winch.setPower(-power);
    }

    public void setPlane(boolean launchPlane) {
        plane.setPosition(launchPlane ? LAUNCH_POSITION : HELD_POSITION);
    }

}
