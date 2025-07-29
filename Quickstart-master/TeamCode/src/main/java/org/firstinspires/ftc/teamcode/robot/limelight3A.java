package org.firstinspires.ftc.teamcode.robot;


import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.Servo;

public class limelight3A {

    private Limelight3A limelight;

    private Servo headlight;

    public limelight3A(){

    }


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


    public double getX(){
        LLResult result = limelight.getLatestResult();
        return (10.25 * (Math.tan(Math.toRadians(66 + result.getTy()))));
    }

    public double getY(){
        LLResult result = limelight.getLatestResult();
        return 3 + (getX() * (Math.tan(Math.toRadians(result.getTx()))));
    }

    public double getHeading(){
        LLResult result = limelight.getLatestResult();
        return result.getTx();
    }

    public void stop(){
        limelight.stop();
        headlight.setPosition(0);
    }
}
