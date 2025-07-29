package org.firstinspires.ftc.teamcode.pedroPathing.examples;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.archive.Subsystems;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "PIDF Multi-Motor Control", group = "Test")
public class moveMultiplePIDFMotors extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Subsystems robot = new Subsystems();
            robot.init(hardwareMap);

        waitForStart();
/*
        // Example: move 2 motors to 1000 encoder ticks
        robot.moveMotorsToPositionPIDF(
                Arrays.asList(robot.ehMotor1, robot.ehMotor0),
                Arrays.asList(1000, 1000),
                0.01, 0.0, 0.001, 0.01,  // PIDF coefficients
                10,       // Tolerance
                2.0      // Timeout in seconds
        );

 */
    }
}
