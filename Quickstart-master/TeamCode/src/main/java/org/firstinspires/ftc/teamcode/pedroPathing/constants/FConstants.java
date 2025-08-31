package org.firstinspires.ftc.teamcode.pedroPathing.constants;

import com.pedropathing.localization.Localizers;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.util.CustomFilteredPIDFCoefficients;
import com.pedropathing.util.CustomPIDFCoefficients;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class FConstants {
    static {
        FollowerConstants.localizers = Localizers.PINPOINT;

        FollowerConstants.leftFrontMotorName = "leftFront";
        FollowerConstants.leftRearMotorName = "leftRear";
        FollowerConstants.rightFrontMotorName = "rightFront";
        FollowerConstants.rightRearMotorName = "rightRear";

        FollowerConstants.leftFrontMotorDirection = DcMotorSimple.Direction.REVERSE;
        FollowerConstants.leftRearMotorDirection = DcMotorSimple.Direction.REVERSE;
        FollowerConstants.rightFrontMotorDirection = DcMotorSimple.Direction.FORWARD;
        FollowerConstants.rightRearMotorDirection = DcMotorSimple.Direction.FORWARD;

        FollowerConstants.mass = 10; //kgs

        FollowerConstants.xMovement = 61.24633;
        FollowerConstants.yMovement = 55.1;

        FollowerConstants.forwardZeroPowerAcceleration = -30;
        FollowerConstants.lateralZeroPowerAcceleration = -45;

        FollowerConstants.translationalPIDFCoefficients.setCoefficients(0.015,0,0.0005,0.01);
        FollowerConstants.useSecondaryTranslationalPID = true;
        FollowerConstants.secondaryTranslationalPIDFCoefficients.setCoefficients(0.01,0,0.0005,0); // Not being used, @see useSecondaryTranslationalPID

        FollowerConstants.translationalIntegral.setCoefficients(
                0,
                0,
                0,
                0);

        FollowerConstants.secondaryTranslationalIntegral.setCoefficients(
                0,
                0,
                0,
                0);

        FollowerConstants.headingPIDFCoefficients.setCoefficients(.015,0,0.001,0.01);
        FollowerConstants.useSecondaryHeadingPID = true;
        FollowerConstants.secondaryHeadingPIDFCoefficients.setCoefficients(.01,0,0.001,0); // Not being used, @see useSecondaryHeadingPID



        FollowerConstants.drivePIDFCoefficients.setCoefficients(0.008,0,0.001,0.47,0.01);
        FollowerConstants.useSecondaryDrivePID = true;
        FollowerConstants.secondaryDrivePIDFCoefficients.setCoefficients(0.006,0,0.001,0.4,0.0); // Not being used, @see useSecondaryDrivePID

        FollowerConstants.zeroPowerAccelerationMultiplier = 5;
        FollowerConstants.centripetalScaling = 0.0005;

        FollowerConstants.pathEndTimeoutConstraint = 50;
        FollowerConstants.pathEndTValueConstraint = 0.95;
        FollowerConstants.pathEndVelocityConstraint = 0.1;
        FollowerConstants.pathEndTranslationalConstraint = 0.1;
        FollowerConstants.pathEndHeadingConstraint = 0.007;
    }
}
