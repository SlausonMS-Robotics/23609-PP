package org.firstinspires.ftc.teamcode.pedroPathing.examples;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;
import org.firstinspires.ftc.teamcode.archive.Subsystems;
import org.firstinspires.ftc.teamcode.robot.limelight3A;
import org.firstinspires.ftc.teamcode.robot.servos;

/**
 * This is an example teleop that showcases movement and field-centric driving.
 *
 * @author Baron Henderson - 20077 The Indubitables
 * @version 2.0, 12/30/2024
 */

@TeleOp(name = "Example Field-Centric Teleop", group = "Examples")
public class ExampleFieldCentricTeleop extends OpMode {
    private Follower follower;
    private static double scalar = .75;
    private double xval = 0;
    private double yval = 0;
    private double hval = 0;
    servos servo = new servos();
    limelight3A limelight = new limelight3A();

    private final Pose startPose = new Pose(0,0,0);

    /** This method is call once when init is played, it initializes the follower **/
    @Override
    public void init() {
        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(startPose);
        servo.init(hardwareMap);
        //limelight.init(hardwareMap,5);
        //servo0 = robot.headlight;

    }

    /** This method is called continuously after Init while waiting to be started. **/
    @Override
    public void init_loop() {
    }

    /** This method is called once at the start of the OpMode. **/
    @Override
    public void start() {
        follower.startTeleopDrive();
    }

    /** This is the main loop of the opmode and runs continuously after play **/
    @Override
    public void loop() {

        /* Update Pedro to move the robot based on:
        - Forward/Backward Movement: -gamepad1.left_stick_y
        - Left/Right Movement: -gamepad1.left_stick_x
        - Turn Left/Right Movement: -gamepad1.right_stick_x
        - Robot-Centric Mode: false
        */
        //limelight.pollLimelight();
        //servo.setSlideServoPos(servo.setSlideInches(limelight.getXDist(0)));

        follower.setTeleOpMovementVectors(Math.pow(-gamepad1.left_stick_y * scalar,3), Math.pow(-gamepad1.left_stick_x * scalar,3), Math.pow(-gamepad1.right_stick_x * scalar,3), false);
        follower.update();

        if(gamepad1.a){
            servo.slideFullRetract();
        }
        if(gamepad1.y){
            servo.slideFullExtend();
        }

        /* Telemetry Outputs of our Follower */
        telemetry.addData("X", follower.getPose().getX());
        telemetry.addData("Y", follower.getPose().getY());
        telemetry.addData("Heading in Degrees", Math.toDegrees(follower.getPose().getHeading()));
        telemetry.addData("Xdist",limelight.getXDist(0));
        telemetry.addData("SPos", servo.setSlideInches(limelight.getXDist(0)));
        telemetry.addData("Xdeg",limelight.getXDeg(0));
        telemetry.addData("Ydeg",limelight.getYDeg(0));
        telemetry.addData("area",limelight.getArea(0));
        /* Update Telemetry to the Driver Hub */
        telemetry.update();

    }

    /** We do not use this because everything automatically should disable **/
    @Override
    public void stop() {
        servo.slideServoOff();
    }
}