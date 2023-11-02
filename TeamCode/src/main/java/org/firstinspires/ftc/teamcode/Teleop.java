package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Gamepad.RumbleEffect;

@TeleOp(name="White Robot", group="Iterative Opmode")
public class Teleop extends OpMode implements Constants{
    
    private DriveTrain driver = new DriveTrain();
    private Arm arm = new Arm();
    private Hand hand = new Hand();

    double driveAngle, drivePower, driveTurn;
    double armPower, armExtendPower;
    double wristPosition;
    boolean highGear = false;

    RumbleEffect clampEffect = new RumbleEffect.Builder()
        .addStep(0.5, 0, 400)
        .addStep(0, 0, 200)
        .addStep(0, 0.5, 400)
        .build();
    
    RumbleEffect unClampEffect = new RumbleEffect.Builder()
        .addStep(0, 0.5, 400)
        .addStep(0, 0, 200)
        .addStep(0.5, 0, 400)
        .build();
    
    
    @Override
    public void init() {
        driver.init(hardwareMap);
        arm.init(hardwareMap);
        hand.init(hardwareMap);
        driver.resetEncoder();
    }

    @Override
    public void loop() {
    
         //set up power angle
        driveAngle = Math.atan2(gamepad1.left_stick_y,gamepad1.left_stick_x);
        drivePower = Math.pow(Math.pow(gamepad1.left_stick_x,2) + Math.pow(gamepad1.left_stick_y,2),0.5); 
        driveTurn = gamepad1.right_stick_x;
        highGear = gamepad1.right_trigger > 0.5;
        
        driver.drive(driveAngle, drivePower * Constants.DRIVE_LIMITER, driveTurn * Constants.TURN_LIMITER, highGear);

        armPower = gamepad2.left_stick_y;
        armExtendPower = gamepad2.right_stick_y;

        if(gamepad2.right_bumper) {
            wristPosition = Constants.WRIST_POSITION;
        } else if(gamepad2.left_bumper) {
            wristPosition = - Constants.WRIST_POSITION;
        } else {
            wristPosition = 0;
        }

        arm.rotateArm(armPower);
        arm.extendArm(armExtendPower);

        hand.wristToPosition(wristPosition);

        if(gamepad1.a){
            hand.clamp();
            gamepad1.runRumbleEffect(clampEffect); //Rumbles controller left to right (if applicable)
            gamepad1.setLedColor(0, 1, 0, 2000); //Sets LED to green for 2sec (if applicable)
        }
        
        if(gamepad1.b){
            hand.unclamp();
            gamepad1.runRumbleEffect(unClampEffect); //Rumbles controller right to left (if applicable)
            gamepad1.setLedColor(1, 0, 0, 1000); //Sets LED to red for 1sec (if applicable)
        }

        driver.calcHeading();
        
        telemetry.addData("red encoder value", driver.getRedPosition());
        telemetry.addData("green encoder value", driver.getGreenPosition());
        telemetry.addData("blue encoder value", driver.getBluePosition());
        telemetry.addData("yellow encoder value", driver.getYellowPosition());
        telemetry.addData("heading", driver.getHeading() * 180 / Math.PI);
        telemetry.addData("power", drivePower);

    }

    @Override
    public void stop() {
    }

}