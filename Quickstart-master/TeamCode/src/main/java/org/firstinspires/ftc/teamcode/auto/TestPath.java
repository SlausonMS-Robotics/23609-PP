package org.firstinspires.ftc.teamcode.pedroPathing.examples;



import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@TeleOp(name = "slide test", group = "Examples")
public class TestPath extends OpMode {

private Servo Servo5;

@Override
    public void init()
    {
        Servo5 = hardwareMap.get(Servo.class, "Servo5");



    }
@Override
    public void loop() {


}




}









