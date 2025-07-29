package org.firstinspires.ftc.teamcode.robot;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import com.qualcomm.robotcore.hardware.DcMotorEx;



public class motors {

    public DcMotorEx motor0, motor1, motor2, motor3;

    public void init(){
        motor0 = hardwareMap.get(DcMotorEx.class, "ehmotor0");
    }

    public void setLiftMotor(int pos, int vel) {
        motor0.setTargetPosition(pos);
        motor0.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        motor0.setVelocity(vel);
    }

    public void stopLiftMotor(){
        motor0.setPower(0);
    }

    public void setLiftMotorPosTol (int tol){
        motor0.setTargetPositionTolerance(tol);
    }

}


