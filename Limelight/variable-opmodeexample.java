MotifTagDetector motifDetector;

@Override
public void init() {
    motifDetector = new MotifTagDetector();
}

@Override
public void loop() {
    motifDetector.updateTagDetection();
    motifDetector.displayTelemetry(telemetry);

    int tagId = motifDetector.getMotifTagId();
    if (tagId == 21) {
        // Run GPP logic
    } else if (tagId == 22) {
        // Run PGP logic
    } else if (tagId == 23) {
        // Run PPG logic
    }
}
