package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.acmerobotics.dashboard.config.Config;

@Config
public class LinearSlideSubsystem {

    // Target positions
    public static int HIGH = 550;
    public static int MID = 400;
    public static int LOW = 300;
    public static int RESET = 0;

    // Control parameters
    public static int POSITION_TOLERANCE_TICKS = 10;
    public static double SLIDE_POWER_EXTEND = 0.9;
    public static double SLIDE_POWER_RETRACT = 0.5;
    public static double BRAKE_POWER_EXTEND = -0.1;
    public static double BRAKE_POWER_RETRACT = -0.2;
    public static double HOLD_POWER = 0.2;

    private final DcMotorEx linear1;
    private final DcMotorEx linear2;
    private final boolean hasSecondMotor;

    public LinearSlideSubsystem(DcMotorEx linear1, DcMotorEx linear2) {
        this.linear1 = linear1;
        this.linear2 = linear2;
        this.hasSecondMotor = (linear2 != null);

        linear1.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        if (hasSecondMotor) {
            linear2.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        }
    }

    /** Call in OpMode loop to apply power logic based on distance to target */
    public void update() {
        applyPowerLogic(linear1);
        if (hasSecondMotor) {
            applyPowerLogic(linear2);
        }
    }

    private void applyPowerLogic(DcMotorEx motor) {
        int error = motor.getTargetPosition() - motor.getCurrentPosition();
        int absError = Math.abs(error);
        boolean retracting = error < 0;

        double power;
        if (absError <= POSITION_TOLERANCE_TICKS) {
            power = HOLD_POWER;
        } else if (absError <= POSITION_TOLERANCE_TICKS * 2) {
            power = retracting ? BRAKE_POWER_RETRACT : BRAKE_POWER_EXTEND;
        } else {
            power = retracting ? -SLIDE_POWER_RETRACT : SLIDE_POWER_EXTEND;
        }

        motor.setPower(power);
    }

    public boolean isBusy() {
        return linear1.isBusy() || (hasSecondMotor && linear2.isBusy());
    }

    public void moveTo(int target) {
        linear1.setTargetPosition(target);
        if (hasSecondMotor) linear2.setTargetPosition(target);
    }

    public void moveToSeparate(int target1, int target2) {
        linear1.setTargetPosition(target1);
        if (hasSecondMotor) linear2.setTargetPosition(target2);
    }

    public void cancel() {
        linear1.setPower(0);
        if (hasSecondMotor) linear2.setPower(0);
    }

    public void goToHigh() {
        moveTo(HIGH);
    }

    public void goToMid() {
        moveTo(MID);
    }

    public void goToLow() {
        moveTo(LOW);
    }

    public void resetPosition() {
        moveTo(RESET);
    }
}
