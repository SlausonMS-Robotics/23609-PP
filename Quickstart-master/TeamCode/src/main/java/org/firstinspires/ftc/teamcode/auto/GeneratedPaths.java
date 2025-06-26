package org.firstinspires.ftc.teamcode.auto;


//import com.pedropathing.pathgen.BezierCurve;

import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathBuilder;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;

public class GeneratedPaths {

    public static PathBuilder builder = new PathBuilder();

    public static PathChain line1 = builder
            .addPath(
                    new BezierLine(
                            new Point(13.000, 119.000, Point.CARTESIAN),
                            new Point(18.000, 125.000, Point.CARTESIAN)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(-45), Math.toRadians(-45))
            .build();

    public static PathChain line2 = builder
            .addPath(
                    new BezierLine(
                            new Point(18.000, 125.000, Point.CARTESIAN),
                            new Point(18.000, 125.000, Point.CARTESIAN)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(-45), Math.toRadians(-8))
            .build();

    public static PathChain line3 = builder
            .addPath(
                    new BezierLine(
                            new Point(18.000, 125.000, Point.CARTESIAN),
                            new Point(18.000, 125.000, Point.CARTESIAN)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(-8), Math.toRadians(15))
            .build();

    public static PathChain line4 = builder
            .addPath(
                    new BezierLine(
                            new Point(18.000, 125.000, Point.CARTESIAN),
                            new Point(18.000, 125.000, Point.CARTESIAN)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(15), Math.toRadians(40))
            .build();

    public static PathChain line5 = builder
            .addPath(
                    new BezierLine(
                            new Point(18.000, 125.000, Point.CARTESIAN),
                            new Point(61.000, 115.000, Point.CARTESIAN)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(40), Math.toRadians(-90))
            .build();

    public static PathChain line6 = builder
            .addPath(
                    new BezierLine(
                            new Point(61.000, 115.000, Point.CARTESIAN),
                            new Point(61.000, 100.000, Point.CARTESIAN)
                    )
            )
            .setTangentHeadingInterpolation()
            .build();
}