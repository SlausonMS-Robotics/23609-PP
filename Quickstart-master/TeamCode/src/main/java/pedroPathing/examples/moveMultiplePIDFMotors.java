package pedroPathing.examples;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcontroller.external.samples.RobotHardware;

import java.util.Arrays;

import robot.Subsystems;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "PIDF Multi-Motor Control", group = "Test")
public class moveMultiplePIDFMotors extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Subsystems robot = new Subsystems();
        robot.init(hardwareMap);

        waitForStart();

        // Example: move 2 motors to 1000 encoder ticks
        robot.moveMotorsToPositionPIDF(
                Arrays.asList(robot.armMotor, robot.vslideMotor),
                Arrays.asList(1000, 1000),
                0.01, 0.0, 0.001, 0.01,  // PIDF coefficients
                10,       // Tolerance
                2.0      // Timeout in seconds
        );
    }
}
