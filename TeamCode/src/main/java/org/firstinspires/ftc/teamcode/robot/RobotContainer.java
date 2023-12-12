package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.controller.GameController;
import org.firstinspires.ftc.teamcode.utils.Constants;

import com.qualcomm.robotcore.hardware.Gamepad;
public class RobotContainer implements Constants {
    private DriveTrain drivetrain;
    private Arm arm;
    private Hand hand;
    private GameController driverOI;
    private GameController operatorOI;
    private double heading;
    double[] coordinates;
    double power, angle, turn;
    double armPower, extendPower;
    boolean highGear;
    boolean clamped;
    boolean planeLaunch;

    public double angleForTelemetry;

    /**
     *
     * @param hwMap hardwareMap
     * @param heading robot starting heading
     * @param coordinates robot starting coordinates
     * @param clamped is the hand grabbing anything
     * @param wristPos is the wrist in any direction
     * @param g1 gamepad1
     * @param g2 gamepad2
     */
    public RobotContainer (HardwareMap hwMap, double heading, double[] coordinates, boolean clamped, double wristPos, boolean planeLaunch, Gamepad g1, Gamepad g2){
        drivetrain = new DriveTrain(hwMap, heading, coordinates);
        hand = new Hand(hwMap, clamped, wristPos);
        arm = new Arm(hwMap);
        power = 0.0;
        angle = 0.0;
        turn = 0.0;
        armPower = 0.0;
        extendPower = 0.0;
        highGear = false;
        driverOI = new GameController(g1);
        operatorOI = new GameController(g2);
        this.planeLaunch = planeLaunch;
    }

    public void updateInstances() {
        driverOI.updateValues();
        operatorOI.updateValues();
        drivetrain.updateWithOdometry();
    }

    public void robot(){
        updateInstances();

        power = driverOI.leftStickRadius();
        angle = driverOI.leftStickTheta();
        angleForTelemetry = angle;
        turn = driverOI.right_stick_x.get();
        armPower = operatorOI.left_stick_y.get();
        extendPower = operatorOI.right_stick_y.get();

        highGear = (driverOI.right_trigger.get() > 0.5) ? true : false;
            drivetrain.drive(power, angle, turn, highGear);



        arm.move(armPower);
        arm.extend(-extendPower);

        if(operatorOI.a.wasJustReleased()){
            clamped = !clamped;
        }
        hand.clamp(clamped);

        if(operatorOI.dpad_up.get()){
            planeLaunch = true;
        }

        arm.setPlane(planeLaunch);

        if(operatorOI.left_trigger.get() > 0.5){
            hand.wristToPos(WRIST_RIGHT);
        } else if (operatorOI.right_trigger.get() > 0.5){
            hand.wristToPos(Constants.WRIST_LEFT);
        }else{
            hand.wristToPos(WRIST_DEFAULT);
        }
    }



}
