package org.firstinspires.ftc.teamcode.modules;

public class myPIDFController { // formerly MyPIDFController // formerly PIDFController {
    private double kP, kI, kD, kF;
    private double integralSum = 0;
    private double lastError = 0;
    private double lastTime = 0;

    public myPIDFController(double kP, double kI, double kD, double kF) {
        setCoefficients(kP, kI, kD, kF);
    }

    public void reset() {
        integralSum = 0;
        lastError = 0;
        lastTime = 0;
    }

    public double calculate(double target, double current, double feedforward, double currentTime) {
        double error = target - current;
        double deltaTime = currentTime - lastTime;

        if (deltaTime > 0) {
            integralSum += error * deltaTime;
            double derivative = (error - lastError) / deltaTime;
            lastError = error;
            lastTime = currentTime;
            return kP * error + kI * integralSum + kD * derivative + kF * feedforward;
        } else {
            return kF * feedforward;
        }
    }

    public void setCoefficients(double kP, double kI, double kD, double kF) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kF = kF;
    }
}
