package org.firstinspires.ftc.teamcode.decode;

import java.util.*;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class ClassifierScanner {

    private final NetworkTable limelightTable;
    private final MotifTagDetector tagDetector;

    public ClassifierScanner(MotifTagDetector tagDetector) {
        this.limelightTable = NetworkTableInstance.getDefault().getTable("limelight");
        this.tagDetector = tagDetector;
    }

    public String getNextRequiredArtifact() {
        // Scan classifier for purple and green artifacts
        List<String> artifacts = new ArrayList<>();

        // Switch to purple pipeline (assume pipeline 1)
        limelightTable.getEntry("pipeline").setNumber(2);
        sleep(100); // allow pipeline to settle
        int purpleCount = getArtifactCount();
        for (int i = 0; i < purpleCount; i++) artifacts.add("Purple");

        // Switch to green pipeline (assume pipeline 2)
        limelightTable.getEntry("pipeline").setNumber(3);
        sleep(100);
        int greenCount = getArtifactCount();
        for (int i = 0; i < greenCount; i++) artifacts.add("Green");

        // Get current motif tag ID
        tagDetector.updateTagDetection();
        int tagID = tagDetector.getMotifTagId();

        // Decode next required artifact
        return MotifDecoder.getNextArtifact(tagID, artifacts);
    }

    private int getArtifactCount() {
        double tv = limelightTable.getEntry("tv").getDouble(0.0);
        if (tv < 1.0) return 0;

        double[] taArray = limelightTable.getEntry("ta").getDoubleArray(new double[0]);
        return taArray.length;
    }

    private void sleep(int millis) {
        try { Thread.sleep(millis); } catch (InterruptedException e) { }
    }
}

// OpMode example:
MotifTagDetector tagDetector = new MotifTagDetector();
ClassifierScanner scanner = new ClassifierScanner(tagDetector);

String nextColor = scanner.getNextRequiredArtifact();
telemetry.addData("Next Artifact Needed", nextColor);
telemetry.update();
