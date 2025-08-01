package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoImplEx;

public class servos {

    // ---- Constants ----

    private static final double SLIDE_FULL_EXTENSION_POS = 0.6;
    private static final double SLIDE_FULL_RETRACTION_POS = 0.05;
    private static final double WRIST_FULL_EXTENSION_POS = 0.45;
    private static final double WRIST_FULL_RETRACTION_POS = 0.75;
    private static final double SLIDE_FULL_TRAVEL_DEG = 80.0;

    private static final double SLIDE_FULL_EXTENSION_IN = 14.0;

    private static final double GRIPPER_OPEN = 0.2;
    private static final double GRIPPER_CLOSE = 0.5;

    // ---- Servos ----
    private ServoImplEx slideServo;    // servo5
    private ServoImplEx headlight;     // servo0
    private ServoImplEx wristServo;    // servo4
    private ServoImplEx gripperServo;  // servo3

    /**
     * Initializes all servos.
     */
    public void init(HardwareMap hardwareMap) {
        slideServo = hardwareMap.get(ServoImplEx.class, "Servo5");
        headlight = hardwareMap.get(ServoImplEx.class, "servo0");
        wristServo = hardwareMap.get(ServoImplEx.class, "Servo4");
        gripperServo = hardwareMap.get(ServoImplEx.class, "Servo3");
    }

    // ---- Slide Control ----

    /**
     * Converts linear extension (inches) to servo position using polynomial.
     */
    public double setSlideInches(double inches) {
        inches = Math.max(0, Math.min(SLIDE_FULL_EXTENSION_IN, inches));
        return 0.04953510
                + 0.03943799 * inches
                - 0.00021206 * Math.pow(inches, 2)
                + 0.00003250 * Math.pow(inches, 3);
    }

    public void setSlideServoPos(double pos) {
        if (slideServo != null) slideServo.setPosition(pos);
    }

    public boolean slideFullExtend() {
        setSlideServoPos(SLIDE_FULL_EXTENSION_POS);
        return true;
    }

    public boolean slideFullRetract() {
        setSlideServoPos(SLIDE_FULL_RETRACTION_POS);
        return false;
    }

    public void slideServoOff() {
        if (slideServo != null) slideServo.setPwmDisable();
    }

    public void slideServoOn() {
        if (slideServo != null) slideServo.setPwmEnable();
    }

    public void slideTrackLL(double inches) {
        double pos = setSlideInches(inches);
        setSlideServoPos(pos);
    }

    // ---- Gripper Control ----

    public void setGripperServo(double pos) {
        if (gripperServo != null) gripperServo.setPosition(pos);
    }

    public boolean openGripper() {
        setGripperServo(GRIPPER_OPEN);
        return true;
    }

    public boolean closeGripper() {
        setGripperServo(GRIPPER_CLOSE);
        return false;
    }

    // ---- Wrist Control ----

    public void setWristServo(double pos) {
        if (wristServo != null) wristServo.setPosition(pos);
    }

    public boolean wristFullExtend() {
        setWristServo(WRIST_FULL_EXTENSION_POS);
        return true;
    }

    public boolean wristFullRetract() {
        setWristServo(WRIST_FULL_RETRACTION_POS);
        return false;
    }

    // ---- Headlight ----

    public boolean headlightOn() {
        if (headlight != null) headlight.setPosition(1);
        return true;
    }

    public boolean headlightOff() {
        if (headlight != null) headlight.setPosition(0);
        return false;
    }
}
