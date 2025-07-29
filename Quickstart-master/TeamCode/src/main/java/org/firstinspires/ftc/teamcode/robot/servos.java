package org.firstinspires.ftc.teamcode.robot;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.ServoImplEx;

public class servos {

    private static double SLIDE_FULL_EXTENSION = .6;
    private static double SLIDE_FULL_RETRACTION = .05;
    private static double WRIST_FULL_EXTENSION = .6;
    private static double WRIST_FULL_RETRACTION = .6;

    private static double GRIPPER_OPEN = .6;
    private static double GRIPPER_CLOSE = .6;
    public ServoImplEx servo5, servo4, servo3, servo0;

    public void init(){
        servo5 = hardwareMap.get(ServoImplEx .class, "Servo5");
        servo0 = hardwareMap.get(ServoImplEx .class, "servo0");
        servo4 = hardwareMap.get(ServoImplEx .class, "Servo4");
        servo3 = hardwareMap.get(ServoImplEx .class, "Servo3");
    }


    public boolean headlightOn(){
        servo0.setPosition(1);
        return true;
    }

    public boolean headlightOff(){
        servo0.setPosition(0);
        return false;
    }

    public void setGripperServo(double pos) {
        servo3.setPosition(pos);
    }

    public boolean openGripper(){
        setGripperServo(GRIPPER_OPEN);
        return true;
    }

    public boolean closeGripper(){
        setGripperServo(GRIPPER_CLOSE);
        return false;
    }
    public void setWristServo(double pos) {
        servo4.setPosition(pos);
    }

    public boolean wristFullExtend(){
        setWristServo(WRIST_FULL_EXTENSION);
        return true;
    }

    public boolean wristFullRetract(){
        setWristServo(WRIST_FULL_RETRACTION);
        return false;
    }

    public void setSlideServo(double pos) {
        servo5.setPosition(pos);
    }

    public boolean slideFullExtend(){
        setSlideServo(SLIDE_FULL_EXTENSION);
        return true;
    }
    public boolean slideFullRetract(){
        setSlideServo(SLIDE_FULL_RETRACTION);
        return false;
    }

    public void SlideServoOff(){
        servo5.setPwmDisable();
    }

    public void SlideServoOn(){
        servo5.setPwmEnable();
    }
}

