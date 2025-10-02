package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.decode.MotifTagDetector;
import org.firstinspires.ftc.teamcode.decode.MotifDecoder;

import java.util.Arrays;
import java.util.List;

@TeleOp(name = "Test Motif Decoder")
public class TestMotifDecoderOpMode extends LinearOpMode {

    MotifTagDetector tagDetector;
    MotifDecoder motifDecoder;

    @Override
    public void runOpMode() {
        tagDetector = new MotifTagDetector();
        motifDecoder = new MotifDecoder();

        telemetry.addLine("Waiting for start...");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            tagDetector.updateTagDetection();
            int tagID = tagDetector.getMotifTagId();

            // Simulate a classifier state manually
            List<String> artifacts = Arrays.asList("Purple", "Green");

            String nextArtifact = motifDecoder.getNextArtifact(tagID, artifacts);

            telemetry.addData("Motif Tag ID", tagID);
            telemetry.addData("Artifacts in Classifier", artifacts.toString());
            telemetry.addData("Next Artifact Needed", nextArtifact);
            telemetry.update();

            sleep(1000);
        }
    }
}
