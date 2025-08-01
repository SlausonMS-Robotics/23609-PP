package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class motors {

    private DcMotorEx liftMotor; // formerly motor0

    /**
     * Initializes the lift motor.
     * Add other motors here as needed.
     */
    public void init(HardwareMap hardwareMap) {
        liftMotor = hardwareMap.get(DcMotorEx.class, "ehmotor0");
    }

    /**
     * Sets the lift motor target position and velocity.
     */
    public void setLiftMotor(int pos, int vel) {
        if (liftMotor == null) return;

        liftMotor.setTargetPosition(pos);
        liftMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        liftMotor.setVelocity(vel);
    }

    /**
     * Stops the lift motor.
     */
    public void stopLiftMotor() {
        if (liftMotor != null) {
            liftMotor.setPower(0);
        }
    }

    /**
     * Sets the lift motor's target position tolerance.
     */
    public void setLiftMotorPosTolerance(int tolerance) {
        if (liftMotor != null) {
            liftMotor.setTargetPositionTolerance(tolerance);
        }
    }

    /**
     * Enables RUN_TO_POSITION mode for lift motor.
     */
    public void runToPosition(int pos, double power) {
        if (liftMotor == null) return;

        liftMotor.setTargetPosition(pos);
        liftMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        liftMotor.setPower(power);
    }

    /**
     * Checks if lift motor is still moving toward target.
     */
    public boolean isLiftBusy() {
        return liftMotor != null && liftMotor.isBusy();
    }

    /**
     * Gets the current position of the lift motor.
     */
    public int getLiftPosition() {
        return liftMotor != null ? liftMotor.getCurrentPosition() : 0;
    }

    /**
     * Sets lift motor to brake or float when power is zero.
     */
    public void setLiftZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior behavior) {
        if (liftMotor != null) {
            liftMotor.setZeroPowerBehavior(behavior);
        }
    }
}
