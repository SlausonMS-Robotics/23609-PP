package org.firstinspires.ftc.teamcode.robot;


import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.Servo;

public class limelight3A {

    public static double LIMELIGHT_HEIGHT = 10.25; //height of sensor from the ground
    public static double LIMELIGHT_ANGLE = 66.0; //angle of sensor relative to the ground
    public static double LIMELIGHT_Y_OFFSET = 3.0; //distance of sensor from robot center line
    private Limelight3A limelight;

    private Servo headlight;


    public void init(int pipeline){
        limelight = hardwareMap.get(Limelight3A .class, "Limelight");
        headlight = hardwareMap.get(Servo .class, "Servo0");
        headlight.setPosition(1);
        limelight.pipelineSwitch(pipeline);
        limelight.start();
    }

    public double[] getData(){
        LLResult result = limelight.getLatestResult();
        double isgood = 0.0;
        if (result != null) {
            if (!result.isValid()) {
                isgood = 1.0;
            }
        }
        return new double[]{isgood, getX(), getY(), getHeading()};
    }


    public double getX(){  //return position relative to the sensor position
        LLResult result = limelight.getLatestResult();
        return (LIMELIGHT_HEIGHT * (Math.tan(Math.toRadians(LIMELIGHT_ANGLE + result.getTy()))));
    }

    public double getY(){ //return position relative to the center line of the robot
        LLResult result = limelight.getLatestResult();
        return LIMELIGHT_Y_OFFSET + (getX() * (Math.tan(Math.toRadians(result.getTx()))));
    }

    public double getHeading(){
        LLResult result = limelight.getLatestResult();
        return result.getTx();
    }

    public void stop(){
        limelight.stop();
        headlight.setPosition(0); //turn off headlight
    }
}
