package org.firstinspires.ftc.teamcode.decode;

import java.util.*;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class ClassifierScanner {

    private final NetworkTable limelightTable;
    private final MotifTagDetector tagDetector;
    private final MotifDecoder motifDecoder;

    public ClassifierScanner(MotifTagDetector tagDetector, MotifDecoder motifDecoder) {
        this.limelightTable = NetworkTableInstance.getDefault().getTable("limelight");
        this.tagDetector = tagDetector;
        this.motifDecoder = motifDecoder;
    }

    public String getNextRequiredArtifact() {
        List<String> artifacts = new ArrayList<>();

        // Scan purple artifacts (pipeline 1)
        limelightTable.getEntry("pipeline").setNumber(1);
        sleep(100);
        int purpleCount = getArtifactCount();
        for (int i = 0; i < purpleCount; i++) artifacts.add("Purple");

        // Scan green artifacts (pipeline 2)
        limelightTable.getEntry("pipeline").setNumber(2);
        sleep(100);
        int greenCount = getArtifactCount();
        for (int i = 0; i < greenCount; i++) artifacts.add("Green");

        // Update and retrieve current motif tag
        tagDetector.updateTagDetection();
        int tagID = tagDetector.getMotifTagId();

        // Use real motif decoder logic
        return motifDecoder.getNextArtifact(tagID, artifacts);
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
MotifDecoder motifDecoder = new MotifDecoder();
ClassifierScanner scanner = new ClassifierScanner(tagDetector, motifDecoder);

String nextColor = scanner.getNextRequiredArtifact();
telemetry.addData("Next Artifact Needed", nextColor);
telemetry.update();
