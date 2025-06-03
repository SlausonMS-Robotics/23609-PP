package pedroPathing.examples;


import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.acmerobotics.dashboard.config.Config;
import robot.LinearSlideSubsystem;
import robot.Subsystems;

@TeleOp(name = "Linear Slide Dashboard Test")
@Config
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
        linear1 = robot.vslideMotor;
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
