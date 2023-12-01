package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;



public class IndicatorLights {
    
    public RevBlinkinLedDriver blinkin;

    public void init(HardwareMap hwMap) {
        blinkin = hwMap.get(RevBlinkinLedDriver.class,"blinkin");
    }

    /** Sets both left and right LED strips to a certain colour pattern.
     * 
     * @param pattern The pattern to set the LEDs to (BlinkinPattern)
     * 
     * Possible patterns: https://first-tech-challenge.github.io/SkyStone/com/qualcomm/hardware/rev/RevBlinkinLedDriver.BlinkinPattern.html
     */
    public void setColour(RevBlinkinLedDriver.BlinkinPattern pattern) {
        blinkin.setPattern(pattern);
    }
}