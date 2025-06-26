package org.firstinspires.ftc.teamcode.utils;


import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.LinkedList;
import java.util.Queue;

/**
 * SCurveMotionController enables smooth S-curve motion control for one or two motors.
 * Features:
 * - Smooth acceleration/deceleration (S-curve)
 * - Queued motion segments
 * - Independent motor targets (mirrored or separate)
 * - Interrupt/cancel support
 * - Optional hold at final position
 * - FTC Dashboard tunable defaults
 */
@Config
public class SCurveMotionController {

    // Controlled motors
    private final DcMotor motor1;
    private final DcMotor motor2;
    private final boolean dualMotors;

    // Queue of pending motion segments
    private final Queue<MotionSegment> motionQueue = new LinkedList<>();

    // Motion parameters for the current segment
    private double startPos1, endPos1;
    private double startPos2, endPos2;
    private double duration;
    private double maxVelocity;

    // Timing for motion progress
    private final ElapsedTime timer = new ElapsedTime();

    // State flags
    private boolean isActive = false;
    private boolean isCancelled = false;
    private boolean holdFinalPosition = false;

    // Dashboard-configurable defaults
    public static double defaultDuration = 1.5;    // seconds
    public static double defaultVelocity = 500.0;  // ticks/sec
    public static double MAX_SEGMENT_DURATION = 3.0; // seconds
    private static final double MIN_POWER = 0.4;

    /**
     * Constructor for single motor S-curve control.
     */
    public SCurveMotionController(DcMotor motor) {
        this.motor1 = motor;
        this.motor2 = null;
        this.dualMotors = false;
        this.startPos1 = motor.getCurrentPosition();
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    /**
     * Constructor for dual synchronized motor control.
     */
    public SCurveMotionController(DcMotor motor1, DcMotor motor2) {
        this.motor1 = motor1;
        this.motor2 = motor2;
        this.dualMotors = true;
        this.startPos1 = motor1.getCurrentPosition();
        this.startPos2 = motor2.getCurrentPosition();
        motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    /**
     * Queues a motion segment with the same target for both motors.
     */
    public void queueMotion(double endPos, double duration, double maxVelocity) {
        queueMotion(endPos, endPos, duration, maxVelocity);
    }

    /**
     * Queues a motion segment with independent targets for motor1 and motor2.
     */
    public void queueMotion(double endPos1, double endPos2, double duration, double maxVelocity) {
        motionQueue.add(new MotionSegment(endPos1, endPos2, duration, maxVelocity));

        // Immediately begin motion if idle
        if (!isActive && !isCancelled) {
            startNextSegment();
        }
    }

    /**
     * Cancels all current and queued motion segments.
     * Motors are stopped immediately.
     */
    public void cancel() {
        isCancelled = true;
        isActive = false;
        motionQueue.clear();

        motor1.setPower(0);
        if (dualMotors) motor2.setPower(0);
    }

    /**
     * Call this repeatedly in your OpMode loop.
     * Updates the motor targets based on current time in the S-curve.
     *
     * @return true if motion is active; false if finished or cancelled
     */
    public boolean update() {
        if (!isActive || isCancelled) return false;

        double t = timer.seconds();

        // timeout check
        if (t > duration || t > MAX_SEGMENT_DURATION) {
            startPos1 = endPos1;
            if (dualMotors) startPos2 = endPos2;
            isActive = false;
            startNextSegment();

            if (!isActive && holdFinalPosition) {
                motor1.setTargetPosition((int) endPos1);
                motor1.setPower(0.2);
                if (dualMotors) {
                    motor2.setTargetPosition((int) endPos2);
                    motor2.setPower(0.2);
                }
            }

            return false;
        }

        // Normalized time [0, 1]
        double tNorm = clamp(t / duration);

        // Evaluate position and velocity along the S-curve
        double curve = sCurve(tNorm);
        double dCurve = sCurveDeriv(tNorm);

        double pos1 = startPos1 + curve * (endPos1 - startPos1);
        double vel1 = dCurve * (endPos1 - startPos1) / duration;

        motor1.setTargetPosition((int) pos1);
        motor1.setPower(scalePower(vel1));

        if (dualMotors) {
            double pos2 = startPos2 + curve * (endPos2 - startPos2);
            double vel2 = dCurve * (endPos2 - startPos2) / duration;

            motor2.setTargetPosition((int) pos2);
            motor2.setPower(scalePower(vel2));
        }

        return true;
    }

    /**
     * Enables or disables "hold" mode at the end of all motion segments.
     *
     * @param hold true to hold at the last target; false to stop motors
     */
    public void setHoldFinalPosition(boolean hold) {
        this.holdFinalPosition = hold;
    }

    /**
     * @return true if any motion is running or queued (and not cancelled)
     */
    public boolean isRunning() {
        return !isCancelled && (isActive || !motionQueue.isEmpty());
    }

    /**
     * Starts the next segment in the queue (if any).
     */
    private void startNextSegment() {
        MotionSegment next = motionQueue.poll();
        if (next != null && !isCancelled) {
            startPos1 = motor1.getCurrentPosition();
            endPos1 = next.endPos1;
            duration = next.duration;
            maxVelocity = next.maxVelocity;

            if (dualMotors) {
                startPos2 = motor2.getCurrentPosition();
                endPos2 = next.endPos2;
            }

            timer.reset();
            isActive = true;
        }
    }

    /**
     * S-curve position interpolation:
     */
    private double sCurve(double t) {
        return 10 * Math.pow(t, 3) - 15 * Math.pow(t, 4) + 6 * Math.pow(t, 5); // Minimum-jerk position curve
        //return 1 - Math.pow(1 - t, 3); // turbo: Math.pow(t, 4), smooth and balanced: t * t * (3 - 2 * t), exponential like: 1 - Math.pow(1 - t, 3)
    }
    /**
     * Derivative of S-curve
     */
    private double sCurveDeriv(double t) {
        return 30 * Math.pow(t, 2) - 60 * Math.pow(t, 3) + 30 * Math.pow(t, 4); // Minimum-jerk position curve
        //return 3 * Math.pow(1 - t, 2); //turbo: if (t < 0.001) return 0;  return 0.8 * Math.pow(t, -0.2); smooth and balanced: 6 * t - 6 * t * t, exponential: 3 * Math.pow(1 - t, 2)
    }

    /**
     * Clamps a value between 0 and 1.
     */
    private double clamp(double x) {
        return Math.max(0, Math.min(1, x));
    }

    /**
     * Scales velocity to motor power (range: 0.1 to 1.0).
     *
     * @param velocity current velocity in ticks/sec
     * @return scaled motor power
     */
    private double scalePower(double velocity) {
        double scaled = Math.abs(velocity / maxVelocity);
        return MIN_POWER + (1 - MIN_POWER) * clamp(scaled);
    }

    /**
     * A motion segment describing end targets, timing, and velocity constraints.
     */
    public static class MotionSegment {
        public final double endPos1;
        public final double endPos2;
        public final double duration;
        public final double maxVelocity;

        public MotionSegment(double endPos1, double endPos2, double duration, double maxVelocity) {
            this.endPos1 = endPos1;
            this.endPos2 = endPos2;
            this.duration = duration;
            this.maxVelocity = maxVelocity;
        }
    }
}
