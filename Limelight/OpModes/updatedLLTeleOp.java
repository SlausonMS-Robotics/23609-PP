package org.firstinspires.ftc.teamcode.teleops;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.util.Timer;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.robot.limelight3A;
import org.firstinspires.ftc.teamcode.robot.motors;
import org.firstinspires.ftc.teamcode.robot.other_helpers;
import org.firstinspires.ftc.teamcode.robot.servos;

// Field Centric Auto Aim Teleop with RGB Indicator
// Updated version 11.9.2025
@TeleOp(name = "Field-Centric Auto Aim Teleop", group = "Examples")
public class FieldCentricTeleopLLMegaTags extends OpMode {
    public static double SERVO_P = 0.002, SERVO_I = 0.0, SERVO_D = 0.0;
    private static final int MOVING_AVERAGE_SIZE = 5;
    private static double scalar = 1.0;
    private static int ticks_per_rev = 28;

    private double ll_goal_dist = 0;
    private double ll_goal_heading = 0;
    private Timer myTimer, llTimer;
    servos turretServo = new servos();
    motors shooter = new motors();
    limelight3A limelight = new limelight3A();

    private other_helpers headingPid = new other_helpers();
    private other_helpers headingAverage = new other_helpers();
    private other_helpers distanceAverage = new other_helpers();

    Follower follower;
    private double turretPosition = 0;
    private boolean use_PP = false;
    private final Pose startPose = new Pose(0,0,0);

    // ✅ Add RGB Indicator
    private RevBlinkinLedDriver ledDriver;

    @Override
    public void init() {
        if (use_PP) {
            follower = Constants.createFollower(hardwareMap);
            follower.setStartingPose(startPose);
        }
        turretServo.init(hardwareMap);
        shooter.init(hardwareMap);
        limelight.init(hardwareMap,0, telemetry);

        headingPid.initPID(SERVO_P, SERVO_I, SERVO_D);
        headingAverage.initMovingAverage(MOVING_AVERAGE_SIZE);
        distanceAverage.initMovingAverage(MOVING_AVERAGE_SIZE);

        myTimer = new Timer();
        llTimer = new Timer();

        // ✅ Initialize RGB Indicator
        ledDriver = hardwareMap.get(RevBlinkinLedDriver.class, "led");
    }

    @Override
    public void init_loop() {}

    @Override
    public void start() {
        if(use_PP) {
            follower.startTeleopDrive(false);
        }
    }

    @Override
    public void loop() {
        if(use_PP) {
            if (gamepad1.left_trigger > .1) {
                scalar = .5;
                follower.setTeleOpDrive(Math.pow(-gamepad1.left_stick_y * scalar, 1), Math.pow(-gamepad1.left_stick_x * scalar, 1), Math.pow(-gamepad1.right_stick_x * scalar, 1), false);
            } else {
                scalar = 1.0;
                follower.setTeleOpDrive(Math.pow(-gamepad1.left_stick_y * scalar, 3), Math.pow(-gamepad1.left_stick_x * scalar, 3), Math.pow(-gamepad1.right_stick_x * scalar, 3), false);
            }
            follower.update();
        }

        LLResult result = limelight.limelight.getLatestResult();
        telemetry.addData("results is", result);

        if(result.isValid()) {
            ll_goal_heading = headingAverage.updateAndGetAverage(result.getTx());
            ll_goal_dist = distanceAverage.updateAndGetAverage(result.getBotposeAvgDist());

            telemetry.addData("dist", ll_goal_dist);
            telemetry.addData("heading", ll_goal_heading);
            telemetry.addData("results", result);

            // ✅ Show green when tag is detected
            ledDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);
        } else {
            ll_goal_dist = 0;
            ll_goal_heading = 0;
            headingAverage.clearReadings();
            distanceAverage.clearReadings();

            // ✅ Turn off light when no tag
            ledDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLACK);
        }

        double shooter_velocity_scalar = 5600.0 / 60.0 / 3.79;
        if (ll_goal_dist > 0.1 && ll_goal_dist < 4){
            shooter.setShooterVelocity(shooter_velocity_scalar * ticks_per_rev * ll_goal_dist);
        }

        if(Math.abs(ll_goal_heading) > 0.1){
            double pidOutput = headingPid.updatePID(ll_goal_heading, 0);
            turretPosition -= pidOutput;
            turretServo.setTurretServoPos(turretPosition);
        }

        telemetry.update();
    }

    @Override
    public void stop() {}
}
