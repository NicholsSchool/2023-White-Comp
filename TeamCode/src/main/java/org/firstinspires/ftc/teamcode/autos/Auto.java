package org.firstinspires.ftc.teamcode.autos;

import java.util.Arrays;

import org.firstinspires.ftc.teamcode.robot.DriveTrain;
import org.firstinspires.ftc.teamcode.utils.Constants;
import org.firstinspires.ftc.teamcode.utils.Spline;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "the auto (red)")
public class Auto extends LinearOpMode implements Constants {

    @Override
    public void runOpMode() {

        DriveTrain dt = new DriveTrain(hardwareMap, -18, -61.5, 0);

        double[][] points = new double[][]{{-18.4, -61.5}, {99.7, -72}, {88, 55}, {-14, 55}};
      
        Spline spline = new Spline(points, 3000, dt, 250);

        waitForStart();

        while (opModeIsActive()) {
            spline.update();

            dt.drive(1, spline.angle(), 0, false);

            telemetry.addData("DESIDERD TTTTT", spline.desiredT());
            telemetry.addData("angle", spline.angle());
            telemetry.addData("bobot pos", Arrays.toString(spline.robotPosition));
            telemetry.update();

        }

    }

}