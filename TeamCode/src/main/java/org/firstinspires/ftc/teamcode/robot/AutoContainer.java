package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.ElapsedTime.Resolution;

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
    private Telemetry telemetry;
    ElapsedTime waitTime = new ElapsedTime(Resolution.SECONDS);

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

        this.telemetry = telemetry;

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
            int pointX = (int) ((propRec.getLeft() + propRec.getRight()) / 2);
            if (pointX < 640) {
                propZone = PropZone.LEFT;
            } else if (pointX < 1280) {
                propZone = PropZone.CENTER;
            } else {
                propZone = PropZone.RIGHT;
            }
        } catch (NullPointerException e) {
            propZone = PropZone.CENTER;
        }
    }

    public void runAutoSequence() {

        dt.autoAlign(0);

        arm.setArmPos(-350);
        
        arm.setWristPos(144);

        wait(1);

        dt.driveToPosition(0, 18.6, 1, 3, 0.4, true);
        
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

        wait(1);

        // Aligns to one side of the prop before putting the arm down
        dt.autoAlign(propAngle != 0.0 ? propAngle / 2 : 0.1);

        arm.setArmPos(850);

        wait(1);

        dt.autoAlign(propAngle);

        hand.clamp(false, false);

        wait(1);

        arm.setArmPos(0);

        double parkX;
        if (alliance == Alliance.BLUE) {

            dt.autoAlign(Math.PI / 2);
            parkX = -45;

        } else {
            dt.autoAlign(-Math.PI / 2);
            parkX = 45;
        }

        if (fieldSide == FieldSide.BACKSTAGE) {

            dt.driveToPosition(0, 6, 1, 3, 0.4, false);

            dt.driveToPosition(parkX, 6, 1, 3, 0.4, true);

        }

    }


    // For some reason elapsed time throws exceptions but not really
    private void waitLogic(double seconds) {
        waitTime.reset();
            while (waitTime.time() < seconds) {
                telemetry.addData("WAITING", waitTime.time());
                telemetry.update();
        }
    }

    private void wait(int seconds) {

        try {
            waitLogic((int) seconds);
        } catch (Exception e) {
            // do nothing
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