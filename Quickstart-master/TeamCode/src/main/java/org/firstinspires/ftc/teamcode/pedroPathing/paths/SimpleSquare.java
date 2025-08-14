package org.firstinspires.ftc.teamcode.pedroPathing.paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;

public class SimpleSquare {


    private final Pose startPose = new Pose(0, 0, Math.toRadians(0));

    /** Scoring Pose of our robot. It is facing the submersible at a -45 degree (315 degree) angle. */
    private final Pose Pose2 = new Pose(15, 0, Math.toRadians(0));

    /** Lowest (First) Sample from the Spike Mark */
    private final Pose Pose3 = new Pose(15, 15, Math.toRadians(0));

    /** Middle (Second) Sample from the Spike Mark */
    private final Pose Pose4 = new Pose(0, 15, Math.toRadians(0));
    private Path Path1;
    private PathChain Path2;
    private PathChain Path3;
    private PathChain Path4;
    private Follower follower;
    private PathChain[] Paths;

    public void buildPaths(HardwareMap hardwareMap) {


        follower = new Follower(hardwareMap, FConstants .class, LConstants .class);
        Paths[0] = follower.pathBuilder()
                .addPath(new BezierLine(new Point(startPose), new Point(Pose2)))
                .setLinearHeadingInterpolation(startPose.getHeading(), Pose2.getHeading())
                .build();

        Paths[1] = follower.pathBuilder()
                .addPath(new BezierLine(new Point(Pose2), new Point(Pose3)))
                .setLinearHeadingInterpolation(Pose2.getHeading(), Pose3.getHeading())
                .build();

        Paths[2] = follower.pathBuilder()
                .addPath(new BezierLine(new Point(Pose3), new Point(Pose4)))
                .setLinearHeadingInterpolation(Pose2.getHeading(), Pose3.getHeading())
                .build();

        Paths[3] = follower.pathBuilder()
                .addPath(new BezierLine(new Point(Pose4), new Point(startPose)))
                .setLinearHeadingInterpolation(Pose3.getHeading(), startPose.getHeading())
                .build();

    }


}
