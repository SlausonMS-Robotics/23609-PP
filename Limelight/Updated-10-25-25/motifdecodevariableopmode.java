package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Test Motif Tag ID")
public class motifdecodevariableopmode extends LinearOpMode {


    // private motifdecodevariable tagDetector;

    @Override
    public void runOpMode() {
        //tagDetector = new motifdecodevariable();

        telemetry.addLine("Waiting for start...");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            //tagDetector.updateTagDetection();
            //tagDetector.displayTelemetry(telemetry);
            sleep(250); // update every ¼ second
        }
    }
}
