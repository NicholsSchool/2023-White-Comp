package org.firstinspires.ftc.teamcode.teleops;

import org.firstinspires.ftc.teamcode.robot.DriveTrain;
import org.firstinspires.ftc.teamcode.utils.Constants;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Config
@TeleOp(name="[DASHBOARD] Auto Tuning")
public class AutoConstantsTeleop extends OpMode implements Constants{

    DriveTrain dt = new DriveTrain(hardwareMap);

    public void init() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    }

    public void loop() {

        int[] positions = dt.getDriveWheelPositions();

        telemetry.addData("> FL POSITION", positions[0]);
        telemetry.addData("> FR POSITION", positions[1]);
        telemetry.addData("> BL POSITION", positions[2]);
        telemetry.addData("> BR POSITION", positions[3]);
    }
    
}
