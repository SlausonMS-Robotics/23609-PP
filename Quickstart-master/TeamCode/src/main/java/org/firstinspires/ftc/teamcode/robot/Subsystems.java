package org.firstinspires.ftc.teamcode.robot;


import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Subsystems {
    public DcMotorEx ehMotor1, ehMotor0;
    public Servo servo0, servo1, servo2;
    //public Servo grabServo, wristServo, flipServo;
    //private final Map<DcMotor, myPIDFController> pidfMap = new HashMap<>();


    public void init(HardwareMap hardwareMap) {
        //ehMotor1 = hardwareMap.get(DcMotorEx.class, "motor1");
        //ehMotor0 = hardwareMap.get(DcMotorEx.class, "motor0");
        //ehMotor0.setDirection(DcMotorSimple.Direction.REVERSE);
        //servo1 = hardwareMap.get(Servo.class, "servo1");
        servo0 = hardwareMap.get(Servo.class, "servo0");
        //servo2 = hardwareMap.get(Servo.class, "servo2");

        //ehMotor1.setTargetPosition(0);
        //ehMotor1.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

        //ehMotor0.setTargetPosition(0);
        //ehMotor0.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

        //pidfMap.put(ehMotor1, new myPIDFController(PIDFTuning.arm_kP, PIDFTuning.arm_kI, PIDFTuning.arm_kD, PIDFTuning.arm_kF));
        //pidfMap.put(ehMotor0, new myPIDFController(PIDFTuning.vslide_kP, PIDFTuning.vslide_kI, PIDFTuning.vslide_kD, PIDFTuning.vslide_kF));
    }

    public void setTargetPosition(DcMotorEx motor, int position, int vel) {

        motor.setTargetPosition(position);
        motor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        motor.setVelocity(vel); // Apply velocity limit
    }



    public boolean isAtTarget(DcMotorEx motor, int toleranceTicks) {
        return Math.abs(motor.getCurrentPosition() - motor.getTargetPosition()) <= toleranceTicks;
    }

/*
    public void moveMotorsToPositionPIDF(List<DcMotor> motors, List<Integer> targets, List<Double> targetVelocities,
                                         double tolerance, double timeoutSeconds) {
        ElapsedTime timer = new ElapsedTime();
        timer.reset();

        for (DcMotor motor : motors) {
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            myPIDFController pidf = pidfMap.get(motor);
            if (pidf != null) pidf.reset();
        }

        boolean allReached;
        do {
            allReached = true;

            for (int i = 0; i < motors.size(); i++) {
                DcMotor motor = motors.get(i);
                int target = targets.get(i);
                double velocity = targetVelocities.get(i);
                myPIDFController pidf = pidfMap.get(motor);

                double current = motor.getCurrentPosition();
                double power = pidf.calculate(target, current, velocity, timer.seconds());
                power = Math.max(-1.0, Math.min(1.0, power));
                motor.setPower(power);

                if (Math.abs(current - target) > tolerance) {
                    allReached = false;
                }
            }
        } while (!allReached && timer.seconds() < timeoutSeconds);

        // Hold position with zero velocity feedforward
        for (int i = 0; i < motors.size(); i++) {
            DcMotor motor = motors.get(i);
            int target = targets.get(i);
            myPIDFController pidf = pidfMap.get(motor);
            motor.setPower(pidf.calculate(target, motor.getCurrentPosition(), 0.0, timer.seconds()));
        }
    }*/



}



