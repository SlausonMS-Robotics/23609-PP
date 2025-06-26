package org.firstinspires.ftc.teamcode.robot;

import com.acmerobotics.dashboard.config.Config;

@Config
public class PIDFTuning {
    public static double arm_kP = 0.005;
    public static double arm_kI = 0.000;
    public static double arm_kD = 0.00;
    public static double arm_kF = 0.0001;
    public static double armTargetVelocity = 1000.0;

    public static double vslide_kP = 0.005;
    public static double vslide_kI = 0.0002;
    public static double vslide_kD = 0.0;
    public static double vslide_kF = 0.0001;
    public static double vslideTargetVelocity = 600.0;
}

