package org.firstinspires.ftc.teamcode.pedroPathing.examples;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.acmerobotics.dashboard.config.Config;
import org.firstinspires.ftc.teamcode.robot.LinearSlideSubsystem;
import org.firstinspires.ftc.teamcode.robot.Subsystems;

@Config
@Autonomous(name = "Linear Slide Dashboard Test")

public class LinearSlideTestOpMode extends LinearOpMode {



    // Dashboard "button" triggers
    public static boolean goHigh = false;
    public static boolean goMid = false;
    public static boolean goLow = false;
    public static boolean goReset = false;
    public static boolean testSeparate = false;
    public static boolean cancelMotion = false;

    private LinearSlideSubsystem slide;
    private DcMotorEx linear1, linear2;

    @Override
    public void runOpMode() {

        Subsystems robot = new Subsystems();
        robot.init(hardwareMap);
        linear1 = robot.ehMotor0;
        linear2 = null;
        slide = new LinearSlideSubsystem(linear1, linear2);

        // INIT loop
        while (!isStarted() && !isStopRequested()) {
            telemetry.addLine("Dashboard buttons ready");
            telemetry.addData("Motor 1 Pos", linear1.getCurrentPosition());
            if (linear2 != null) {
                telemetry.addData("Motor 2 Pos", linear2.getCurrentPosition());
            }
            telemetry.update();
        }
        waitForStart();

        while (opModeIsActive()) {
            slide.update();

            // Trigger motions from dashboard buttons
            if (goHigh) {
                slide.goToHigh();
                goHigh = false;
            }
            if (goMid) {
                slide.goToMid();
                goMid = false;
            }
            if (goLow) {
                slide.goToLow();
                goLow = false;
            }
            if (goReset) {
                slide.resetPosition();
                goReset = false;
            }

            if (cancelMotion) {
                slide.cancel();
                cancelMotion = false;
            }

            telemetry.addData("Motor 1 Pos", linear1.getCurrentPosition());
            if (linear2 != null) {
                telemetry.addData("Motor 2 Pos", linear2.getCurrentPosition());
            }
            telemetry.addData("Running", slide.isBusy());
            telemetry.update();
        }
    }
}
