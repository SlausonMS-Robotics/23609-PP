package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.decode.MotifTagDetector;
import org.firstinspires.ftc.teamcode.decode.MotifDecoder;
import org.firstinspires.ftc.teamcode.decode.ClassifierScanner;

@TeleOp(name = "Test Classifier Scanner")
public class TestClassifierScannerOpMode extends LinearOpMode {

    MotifTagDetector tagDetector;
    MotifDecoder motifDecoder;
    ClassifierScanner scanner;

    @Override
    public void runOpMode() {
        tagDetector = new MotifTagDetector();
        motifDecoder = new MotifDecoder();
        scanner = new ClassifierScanner(tagDetector, motifDecoder);

        telemetry.addLine("Ready to scan classifier...");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            String nextColor = scanner.getNextRequiredArtifact();
            int tagID = tagDetector.getMotifTagId();

            telemetry.addData("Motif Tag ID", tagID);
            telemetry.addData("Next Artifact Needed", nextColor);
            telemetry.update();

            sleep(1000);
        }
    }
}
