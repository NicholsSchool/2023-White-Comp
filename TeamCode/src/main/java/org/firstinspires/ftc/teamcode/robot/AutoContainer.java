package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.controller.GameController;
import org.firstinspires.ftc.teamcode.utils.Constants;


import com.qualcomm.robotcore.hardware.Gamepad;

public class AutoContainer implements Constants {
    private DriveTrain drivetrain;
    private Arm arm;
    private Hand hand;
    private GameController driverOI;
    private GameController operatorOI;
    private PropDetector pd;
    private Recognition propRec;
    private PropZone propZone;
    public double angleForTelemetry;

    /**
     *
     * @param hwMap       hardwareMap
     * @param g1          gamepad1
     * @param g2          gamepad2
     */
    public AutoContainer(HardwareMap hwMap, Gamepad g1, Gamepad g2, Alliance alliance, FieldSide fieldSide) {
        drivetrain = new DriveTrain(hwMap, 0, 0 ,
        0
        );
        hand = new Hand(hwMap);
        hand.clamp(true, true);
        pd = new PropDetector(hwMap);
        arm = new Arm(hwMap);
        driverOI = new GameController(g1);
        operatorOI = new GameController(g2);   
    }

    public enum Alliance {

        RED,
        BLUE

    }

    public enum FieldSide {

        BACKSTAGE,
        AUDIENCE

    }

    public enum PropZone {

        LEFT,
        CENTER,
        RIGHT

    }

    /**
     * To be called during init loop
     */
    public void getRecsLoop() {
        propRec = pd.getBestRecognition();
    }

    /**
     * To be called at start
     */
    public void getPropPos() {
        try {
            int point = (int) ((propRec.getLeft() + propRec.getRight()) / 2);
            if (point < 250) {
                propZone = PropZone.CENTER;
            } else {
                propZone = PropZone.RIGHT;
            }
        } catch (NullPointerException e) {
            propZone = PropZone.LEFT;
        }
    }

    public void runAutoSequence() {

        //PSEUDOCODE!!!!!!!!!

        //Move Robot forward to spike marks
        //rotate based on vision result

    }

}