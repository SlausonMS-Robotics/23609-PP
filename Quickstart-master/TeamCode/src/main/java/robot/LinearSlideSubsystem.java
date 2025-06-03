package robot;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.acmerobotics.dashboard.config.Config;

import utils.SCurveMotionController;

/**
 * LinearSlideSubsystem controls one or two linear slide motors using S-curve motion profiles.
 *
 * Features:
 * - Smooth acceleration and deceleration
 * - Queued motion segments
 * - Optional second motor (gracefully falls back if not present)
 * - Hold-at-end behavior
 */
@Config
public class LinearSlideSubsystem {

    // Dashboard-tunable positions
    public static int HIGH = 1100;
    public static int MID = 700;
    public static int LOW = 300;
    public static int RESET = 0;

    // Dashboard-tunable timing parameters (in seconds and ticks/sec)
    public static double HIGH_DURATION = 1.5;
    public static double HIGH_VELOCITY = 600;

    public static double MID_DURATION = 1.2;
    public static double MID_VELOCITY = 500;

    public static double LOW_DURATION = 1.0;
    public static double LOW_VELOCITY = 400;

    public static double RESET_DURATION = 1.0;
    public static double RESET_VELOCITY = 500;

    // The primary (always required) slide motor
    private final DcMotorEx linear1;

    // Optional second slide motor (used if not null)
    private final DcMotorEx linear2;

    // Whether we are using two motors
    private final boolean hasSecondMotor;

    // Internal motion controller that handles all queued movement
    private final SCurveMotionController controller;

    // Predefined positions for known scoring levels (in encoder ticks)


    /**
     * Constructor for LinearSlideSubsystem.
     * Accepts one or two motors and automatically configures appropriate controller logic.
     *
     * @param linear1 The main slide motor (required).
     * @param linear2 The second slide motor (optional; may be null).
     */
    public LinearSlideSubsystem(DcMotorEx linear1, DcMotorEx linear2) {
        this.linear1 = linear1;
        this.linear2 = linear2;
        this.hasSecondMotor = (linear2 != null);

        // Create a single- or dual-motor SCurveMotionController depending on configuration
        if (hasSecondMotor) {
            this.controller = new SCurveMotionController(linear1, linear2);
        } else {
            this.controller = new SCurveMotionController(linear1);
        }

        // Automatically hold at final position when queue is finished
        controller.setHoldFinalPosition(true);
    }

    /**
     * Updates the controller — must be called continuously in your OpMode loop.
     * Applies the latest power/position based on motion profiles.
     */
    public void update() {
        controller.update();
    }

    /**
     * Checks whether any motion is currently running or queued.
     *
     * @return true if controller is busy; false otherwise.
     */
    public boolean isBusy() {
        return controller.isRunning();
    }

    /**
     * Moves the slide(s) to a shared target position using an S-curve profile.
     * Works for one or two motors.
     *
     * @param target    Target encoder position (ticks).
     * @param duration  Time (in seconds) to reach the position.
     * @param velocity  Max velocity (ticks/sec) to scale motion curve.
     */
    public void moveTo(int target, double duration, double velocity) {
        controller.queueMotion(target, duration, velocity);
    }

    /**
     * Moves the slides to separate targets.
     * If only one motor is configured, falls back to using just target1.
     *
     * @param target1   Encoder position for motor 1.
     * @param target2   Encoder position for motor 2 (ignored if only one motor).
     * @param duration  Time (in seconds) for the move.
     * @param velocity  Max velocity (ticks/sec) to scale motion.
     */
    public void moveToSeparate(int target1, int target2, double duration, double velocity) {
        if (hasSecondMotor) {
            // Dual-motor move with individual targets
            controller.queueMotion(target1, target2, duration, velocity);
        } else {
            // Single-motor fallback: ignore target2
            controller.queueMotion(target1, duration, velocity);
        }
    }

    /**
     * Immediately cancels all queued and running motions.
     * Motors are stopped immediately.
     */
    public void cancel() {
        controller.cancel();
    }
    /**
     * Moves both motors to the HIGH position.
     */
    public void goToHigh() {
        moveTo(HIGH, HIGH_DURATION, HIGH_VELOCITY);
    }

    public void goToMid() {
        moveTo(MID, MID_DURATION, MID_VELOCITY);
    }

    public void goToLow() {
        moveTo(LOW, LOW_DURATION, LOW_VELOCITY);
    }

    public void resetPosition() {
        moveTo(RESET, RESET_DURATION, RESET_VELOCITY);
    }
}
