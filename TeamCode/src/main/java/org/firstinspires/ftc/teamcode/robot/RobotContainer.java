package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.controller.GameController;
import org.firstinspires.ftc.teamcode.utils.Constants;


import com.qualcomm.robotcore.hardware.Gamepad;

public class RobotContainer implements Constants {
    private DriveTrain drivetrain;
    private Arm arm;
    private Hand hand;
    private GameController driverOI;
    private GameController operatorOI;
    double power, angle, turn;
    double armPower, extendPower;
    boolean highGear;
    boolean leftClamped, rightClamped;
    boolean planeLaunch = false;
    public double angleForTelemetry;

    /**
     *
     * @param hwMap       hardwareMap
     * @param g1          gamepad1
     * @param g2          gamepad2
     */
    public RobotContainer(HardwareMap hwMap, Gamepad g1, Gamepad g2, boolean isRedAlliance, Telemetry telemetry) {
        drivetrain = new DriveTrain(hwMap, 0, 0 ,
        //isRedAlliance ? Math.PI/2 : -Math.PI / 2
        0,
        telemetry
        );
        hand = new Hand(hwMap);
        hand.clamp(true, true);
        arm = new Arm(hwMap);
        power = 0.0;
        angle = 0.0;
        turn = 0.0;
        leftClamped = true;
        rightClamped = true;
        armPower = 0.0;
        extendPower = 0.0;
        highGear = false;
        driverOI = new GameController(g1);
        operatorOI = new GameController(g2);
        
    }

    public int getArmPos() {
        return arm.armPos();
    }

    public void updateInstances() {
        driverOI.updateValues();
        operatorOI.updateValues();
    }

    public void robot() {
        updateInstances();

        power = driverOI.leftStickRadius();
        angle = driverOI.leftStickTheta();
        angleForTelemetry = angle;
        turn = driverOI.right_stick_x.get();
        armPower = operatorOI.left_stick_y.get();
        extendPower = operatorOI.right_trigger.get() - operatorOI.left_trigger.get();

        highGear = (driverOI.right_trigger.get() > 0.5) ? true : false;
        drivetrain.drive(power, angle, turn, highGear);

        arm.setPower(armPower);
        arm.extendWinch(-extendPower);
        //arm.fourbarUpdate(-400);
        arm.wristPower(operatorOI.right_stick_y.get());
        if (operatorOI.x.wasJustReleased()) {
            leftClamped = !leftClamped;
        }
        if (operatorOI.b.wasJustReleased()) {
            rightClamped = !rightClamped;
        }

        if (operatorOI.a.wasJustReleased()) {
            leftClamped = !leftClamped;
            rightClamped = !rightClamped;
        }

        arm.wristPower(operatorOI.right_stick_y.get());

        hand.clamp(leftClamped, rightClamped);

        if (driverOI.dpad_up.wasJustPressed()) {
            drivetrain.resetIMU();
        }

        if (operatorOI.dpad_up.get()) {
            planeLaunch = true;
        } else {
            planeLaunch = false;
        }

        arm.setPlane(planeLaunch);
    }

}
