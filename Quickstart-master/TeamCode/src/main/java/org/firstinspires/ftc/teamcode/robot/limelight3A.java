package org.firstinspires.ftc.teamcode.robot;


import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.Servo;

public class limelight3A {

    public static double LIMELIGHT_HEIGHT = 10.25; //height of sensor from the ground
    public static double LIMELIGHT_ANGLE = 66.0; //angle of sensor relative to the ground
    public static double LIMELIGHT_Y_OFFSET = 3.0; //distance of sensor from robot center line

    public static int LIMELIGHT_OBJECT_COUNT = 5; //this also needs to be set in the limelight pipeline
    private Limelight3A limelight;

    public LLResult result;
    public LLObject[] LLObjects;

    private Servo headlight;


    public void init(int pipeline){
        limelight = hardwareMap.get(Limelight3A .class, "Limelight");
        headlight = hardwareMap.get(Servo .class, "Servo0");
        headlight.setPosition(1);
        limelight.pipelineSwitch(pipeline);
        limelight.start();
    }

    public void pollLimelight(){

        result = limelight.getLatestResult(); //doing this in a method allows it to be run once for all other methods instead of running it each method call
        LLObjects = new LLObject[LIMELIGHT_OBJECT_COUNT];
        parseLLData();
    }

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

    // Method to parse Limelight Python output
    public void parseLLData() {
        //LLResult result = limelight.getLatestResult();

        double[] llpython = result.getPythonOutput();  // from Limelight

        int count = llpython.length / 6;

        for (int i = 0; i < count; i++) {
            int base = i * 6;
            LLObjects[i] = new LLObject(
                    llpython[base],     // xDeg
                    llpython[base + 1], // yDeg
                    llpython[base + 2], // orientation angle
                    llpython[base + 3], // area
                    llpython[base + 4], // ratio
                    (int) llpython[base + 5] // colorId
            );
        }


    }

    public double getXDist(int index){  //return position relative to the sensor position


        return (LIMELIGHT_HEIGHT * (Math.tan(Math.toRadians(LIMELIGHT_ANGLE + LLObjects[index].yDeg))));
    }

    public double getYDist(int index){ //return position relative to the center line of the robot

        return LIMELIGHT_Y_OFFSET + (LLObjects[index].yDeg * (Math.tan(Math.toRadians(LLObjects[index].xDeg))));
    }

    public double getXDeg(int index){

        return LLObjects[index].xDeg;
    }

    public double getYDeg(int index){

        return LLObjects[index].yDeg;
    }

    public double getOrientation(int index){

        return LLObjects[index].angle;
    }


    public double getHeading(int index){

        return LLObjects[index].xDeg;
    }

    public double getArea(int index){

        return LLObjects[index].area;
    }

    public double getRatio(int index){

        return LLObjects[index].ratio;
    }

    public double getColorId(int index){

        return LLObjects[index].colorId;
    }

    public void stop(){
        limelight.stop();
        headlight.setPosition(0); //turn off headlight
    }
}
