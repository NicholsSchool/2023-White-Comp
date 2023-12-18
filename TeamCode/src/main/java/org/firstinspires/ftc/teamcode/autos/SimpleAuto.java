package org.firstinspires.ftc.teamcode.autos;

import org.firstinspires.ftc.teamcode.robot.Arm;
import org.firstinspires.ftc.teamcode.robot.DriveTrain;
import org.firstinspires.ftc.teamcode.utils.Constants;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Simple Move Auto")
public class SimpleAuto extends LinearOpMode implements Constants{
    DriveTrain dt;
    Arm arm;
    private ElapsedTime time = new ElapsedTime();

    public void runOpMode() {
        dt = new DriveTrain(hardwareMap);
        arm = new Arm(hardwareMap);

        telemetry.addLine("AUTO INITIATED");
        telemetry.update();

        waitForStart();

        time.reset();

        dt.drive(1, Math.PI/2, 0, false);
        while (opModeIsActive() && (time.seconds() < 1.6)) {
            telemetry.addData("MOVING! Elapsed time", time.seconds());
            telemetry.update();
        }

        // dt.drive(1, Math.PI, 0, false);
        // time.reset();
        // while (opModeIsActive() && (time.seconds() < 0.2)) {
        //     telemetry.addData("uhh thing", "huh");
        //     telemetry.update();
        // }

        dt.drive(0, 0, 0, false);
        time.reset();
        while (opModeIsActive() && (time.seconds() < 3)) {
            dt.pooperToPosition(PPP_DROP);
        }

        arm.extend(1);
        time.reset();
        while (opModeIsActive() && (time.seconds() < 9)) {
            telemetry.addData("extending", "yes");
            telemetry.update();
        }

    }
}
