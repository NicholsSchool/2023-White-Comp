package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name="White Robot", group="Iterative Opmode")
public class Teleop extends OpMode implements Constants{
    
    private DriveTrain driver = new DriveTrain();
    private Arm arm = new Arm();
    private Hand hand = new Hand();

    double driveAngle, drivePower, driveTurn;
    double armPower, armExtendPower;
    double wristPower;
    boolean highGear = false;
    
    
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
        wristPower = gamepad2.left_trigger - gamepad2.right_trigger;

        arm.rotateArm(armPower);
        arm.extendArm(armExtendPower);
        hand.rotateWrist(wristPower);

        if(gamepad1.a){
            hand.unclamp();
        }
        if(gamepad1.b){
            hand.clamp();
        }

        driver.calcHeading();
        driver.update();
        
        telemetry.addData("red encoder value", driver.getRedPosition());
        telemetry.addData("green encoder value", driver.getGreenPosition());
        telemetry.addData("blue encoder value", driver.getBluePosition());
        telemetry.addData("yellow encoder value", driver.getYellowPosition());
        telemetry.addData("heading", driver.getHeading());
        telemetry.addData("power", drivePower);

    }

    @Override
    public void stop() {
    }

}