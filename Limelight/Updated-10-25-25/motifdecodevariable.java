package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class motifdecodevariable {

    private int motifTagId = -1;

    public void updateTagDetection() {
        JSONObject data = fetchLimelightData();
        if (data != null) {
            double tagVisible = data.optDouble("tv", 0.0);
            if (tagVisible == 1.0) {
                int detectedId = data.optInt("tid", -1);
                if (detectedId == 21 || detectedId == 22 || detectedId == 23) {
                    motifTagId = detectedId;
                }
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

    private JSONObject fetchLimelightData() {
        try {
            URL url = new URL("http://172.28.0.1:5801/data.json"); // Replace with your Limelight's IP
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream in = conn.getInputStream();
            Scanner scanner = new Scanner(in).useDelimiter("\\A");
            String json = scanner.hasNext() ? scanner.next() : "";
            return new JSONObject(json);
        } catch (Exception e) {
            return null;
        }
    }
}
