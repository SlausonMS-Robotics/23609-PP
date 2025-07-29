package org.firstinspires.ftc.teamcode.pedroPathing.examples;


// Example OpMode usage (in a LinearOpMode)


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.archive.Subsystems;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "PIDF Dashboard Example", group = "Test")
public class PIDFDashboardExample extends LinearOpMode {
    Subsystems robot = new Subsystems();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        waitForStart();
/*
        robot.moveMotorsToPositionPIDF(
                Arrays.asList(robot.ehMotor1, robot.ehMotor0),
                Arrays.asList(800, 1200),
                Arrays.asList(PIDFTuning.armTargetVelocity, PIDFTuning.vslideTargetVelocity),
                5,
                3.0
        );

 */
    }
}
