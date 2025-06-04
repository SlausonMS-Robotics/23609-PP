package pedroPathing.examples;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import utils.SCurveMotionController;

@TeleOp(name = "S-Curve Motion Example")
public class SCurveExampleOpMode extends LinearOpMode {
    private SCurveMotionController armController;

    @Override
    public void runOpMode() {
        // Get the motor from the hardware map
        DcMotor armMotor = hardwareMap.get(DcMotor.class, "armMotor");

        // Wait for start on Driver Station
        waitForStart();

        // Move arm to position 1000 over 2.0 seconds with max 600 ticks/sec velocity
        armController = new SCurveMotionController(armMotor);
        //armController.queueMotion();

        // Update loop: keep updating the motion profile while it's running
        while (opModeIsActive() && armController.update()) {
            telemetry.addData("Target Pos", armMotor.getTargetPosition());
            telemetry.addData("Current Pos", armMotor.getCurrentPosition());
            telemetry.update();
        }

        // Stop the motor after motion is done
        armMotor.setPower(0);
    }
}

