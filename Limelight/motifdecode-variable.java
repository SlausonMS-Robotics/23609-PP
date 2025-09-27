package org.firstinspires.ftc.teamcode.decode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class MotifTagDetector {

    private final NetworkTable limelightTable;
    private int motifTagId = -1; // Default: no tag detected

    public MotifTagDetector() {
        limelightTable = NetworkTableInstance.getDefault().getTable("limelight");
    }

    // Call this periodically (e.g., in loop()) to update the detected tag
    public void updateTagDetection() {
        double tagVisible = limelightTable.getEntry("tv").getDouble(0.0); // Tag visible?
        if (tagVisible == 1.0) {
            int detectedId = (int) limelightTable.getEntry("tid").getDouble(-1);
            if (detectedId == 21 || detectedId == 22 || detectedId == 23) {
                motifTagId = detectedId;
            }
        }
    }

    public int getMotifTagId() {
        return motifTagId;
    }

    public boolean isMotifDetected() {
        return motifTagId != -1;
    }

    public void displayTelemetry(Telemetry telemetry) {
        if (isMotifDetected()) {
            telemetry.addData("Motif Tag ID", motifTagId);
        } else {
            telemetry.addData("Motif Tag", "Not detected");
        }
        telemetry.update();
    }
}
