package robot;

import com.acmerobotics.dashboard.config.Config;

@Config
public class PIDFTuning {
    public static double arm_kP = 0.01;
    public static double arm_kI = 0.0;
    public static double arm_kD = 0.001;
    public static double arm_kF = 0.01;
    public static double armTargetVelocity = 300.0;

    public static double vslide_kP = 0.012;
    public static double vslide_kI = 0.0;
    public static double vslide_kD = 0.001;
    public static double vslide_kF = 0.012;
    public static double vslideTargetVelocity = 400.0;
}

