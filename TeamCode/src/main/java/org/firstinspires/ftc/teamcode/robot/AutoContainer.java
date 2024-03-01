package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.utils.Constants;

public class AutoContainer implements Constants {
    private DriveTrain dt;
    private Arm arm;
    private Hand hand;
    private PropDetector pd;
    private Recognition propRec;
    private PropZone propZone;
    public double angleForTelemetry;
    private Alliance alliance;
    private FieldSide fieldSide;

    /**
     *
     * @param hwMap       hardwareMap
     * @param g1          gamepad1
     * @param g2          gamepad2
     */
    public AutoContainer(HardwareMap hwMap, Alliance alliance, FieldSide fieldSide, Telemetry telemetry) {
        dt = new DriveTrain(hwMap, 0, 0 ,0, telemetry);
        hand = new Hand(hwMap);
        hand.clamp(true, true);
        pd = new PropDetector(hwMap);
        arm = new Arm(hwMap, telemetry);  

        this.alliance = alliance;
        this.fieldSide = fieldSide;

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

        dt.autoAlign(0);

        arm.setArmPos(-300);
        
        arm.setWristPos(144);

        arm.setArmPos(1000);

        dt.driveToPosition(0, 18.6, 1, 3, 0.2, true);
        
        double propAngle;
        switch (propZone) {
            case LEFT:
                propAngle = -0.55;
                break;
        
            case RIGHT:
                propAngle = 0.58;
                break;
            
            default:
                propAngle = 0.0;
        }
        dt.autoAlign(propAngle);

        hand.clamp(false, false);

        arm.setArmPos(0);

        double parkX;

        if (alliance == Alliance.BLUE) {

            dt.autoAlign(0.5);
            parkX = -45;

        } else {
            dt.autoAlign(-0.5);
            parkX = 45;
        }

        if (fieldSide == FieldSide.BACKSTAGE) {

            dt.driveToPosition(parkX, 6, 1, 3, 0.2, true);

        }

    }

    /**
     * Drives 20 in forward (ish)
     */
    public void testDriveToPos() {

        dt.driveToPosition(0, 20, 0.8, 3, 0.2, true);

    }

    /**
     * Aligns robot to 90deg clockwise from starting pos
     */
    public void testAutoAlign() {
        dt.autoAlign(Math.PI / 2);
    }

    /**
     * Raises arm 500 ticks
     */
    public void testArmGoToPos(){
        arm.setArmPos(-500);
    }

    /**
     * Raises wrist 75 ticks
     */
    public void testWristGoToPos() {
        arm.setWristPos(75);
    }

}