package org.firstinspires.ftc.teamcode.robot;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

import org.firstinspires.ftc.robotcore.external.JavaUtil;

public class sensors {

    private NormalizedColorSensor colorV3;


    public void sensors_init(){
        colorV3 = hardwareMap.get(NormalizedColorSensor.class, "i2c2");
    }
    public double[] getHueD(){
        NormalizedRGBA normColor = colorV3.getNormalizedColors();
        return new double[] {JavaUtil.colorToHue(normColor.toColor()), ((OpticalDistanceSensor) colorV3).getLightDetected()};
    }

    public double[] getRGBD(){
        NormalizedRGBA normColor = colorV3.getNormalizedColors();
        return new double[] {normColor.red, normColor.green, normColor.blue, ((OpticalDistanceSensor) colorV3).getLightDetected()}; //Distance is value between 0-1
    }
}
