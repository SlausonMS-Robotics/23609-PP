package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class limelight3A {

    // Constants
    public static final double LIMELIGHT_HEIGHT = 10.25; // inches
    public static final double LIMELIGHT_ANGLE = 66.0;   // degrees
    public static final double LIMELIGHT_Y_OFFSET = 3.0; // inches
    public static final int LIMELIGHT_OBJECT_COUNT = 5;

    // Hardware
    private Limelight3A limelight;
    private Servo headlight;

    // Results
    public LLResult result;
    public LLObject[] LLObjects = new LLObject[LIMELIGHT_OBJECT_COUNT];

    /**
     * Initializes the Limelight and headlight hardware.
     */
    public boolean init(HardwareMap hwMap, int pipeline) {
        try {
            limelight = hwMap.get(Limelight3A.class, "Limelight");
            headlight = hwMap.get(Servo.class, "Servo0");

            headlight.setPosition(1);  // turn on headlight
            limelight.pipelineSwitch(pipeline);
            limelight.start();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Grabs the latest Limelight result and parses it into LLObjects.
     */
    public void pollLimelight() {
        if (limelight == null) return;

        result = limelight.getLatestResult();
        parseLLData();
    }

    /**
     * Parses Limelight Python output into LLObject[].
     */
    private void parseLLData() {
        if (result == null) return;

        double[] llpython = result.getPythonOutput();
        if (llpython == null || llpython.length < 6) return;

        int count = Math.min(llpython.length / 6, LIMELIGHT_OBJECT_COUNT);
        for (int i = 0; i < count; i++) {
            int base = i * 6;
            LLObjects[i] = new LLObject(
                    llpython[base],         // xDeg
                    llpython[base + 1],     // yDeg
                    llpython[base + 2],     // angle
                    llpython[base + 3],     // area
                    llpython[base + 4],     // ratio
                    (int) llpython[base + 5] // colorId
            );
        }
    }

    /**
     * Represents one detected object from Limelight.
     */
    public static class LLObject {
        public double xDeg, yDeg, angle, area, ratio;
        public int colorId;

        public LLObject(double xDeg, double yDeg, double angle, double area, double ratio, int colorId) {
            this.xDeg = xDeg;
            this.yDeg = yDeg;
            this.angle = angle;
            this.area = area;
            this.ratio = ratio;
            this.colorId = colorId;
        }

        public String getColorName() {
            if (colorId == 0) return "Red";
            else if (colorId == 1) return "Yellow";
            else if (colorId == 2) return "Blue";
            else return "Unknown";
        }
    }

    // ----- Accessors and Calculated Geometry -----

    public double getXDist(int index) {
        if (!isValid(index)) return 0;
        return LIMELIGHT_HEIGHT * Math.tan(Math.toRadians(LIMELIGHT_ANGLE - LLObjects[index].yDeg));
    }


    public double getYDist(int index) {
        if (!isValid(index)) return 0;
        return LIMELIGHT_Y_OFFSET + LLObjects[index].yDeg * Math.tan(Math.toRadians(LLObjects[index].xDeg));
    }

    public double getXDeg(int index) { return getSafe(index).xDeg; }
    public double getYDeg(int index) { return getSafe(index).yDeg; }
    public double getOrientation(int index) { return getSafe(index).angle; }
    public double getHeading(int index) { return getSafe(index).xDeg; }
    public double getArea(int index) { return getSafe(index).area; }
    public double getRatio(int index) { return getSafe(index).ratio; }
    public int getColorId(int index) { return getSafe(index).colorId; }

    /**
     * Stops the Limelight and turns off headlight.
     */
    public boolean stop() {
        if (limelight != null) limelight.stop();
        if (headlight != null) headlight.setPosition(0);
        return false; //lets us know that the limelight was stopped (true = running, false = stopped)
    }

    // ----- Safety Helpers -----

    private boolean isValid(int index) {
        return LLObjects != null && index >= 0 && index < LLObjects.length && LLObjects[index] != null;
    }

    private LLObject getSafe(int index) {
        return isValid(index) ? LLObjects[index] : new LLObject(0, 0, 0, 0, 0, -1);
    }
}
