package org.firstinspires.ftc.teamcode.pedroPathing.tuners_tests.limelight;


import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;
import org.firstinspires.ftc.teamcode.robot.limelight3A;
import org.firstinspires.ftc.teamcode.robot.servos;

@TeleOp(name = "Limelight Test", group = "Examples")

public class LLTest extends OpMode {
    limelight3A limelight = new limelight3A();
    private Follower follower;
    servos robotservo = new servos();
    private final Pose startPose = new Pose(0,0,0);

    /** This method is call once when init is played, it initializes the follower **/
    @Override
    public void init() {

        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(startPose);
        robotservo.init(hardwareMap);
        limelight.init(hardwareMap,5, follower, telemetry);


    }

    /** This method is called once at the start of the OpMode. **/
    @Override
    public void start() {
        limelight.startLL(300);
    }

    /** This is the main loop of the opmode and runs continuously after play **/
    @Override
    public void loop() {
        limelight.pollLimelight();
        telemetry.addData("                                Tx", limelight.result.getTx());
        telemetry.addData("                                Ty", limelight.result.getTy());
        telemetry.addData("                                Ta", limelight.result.getTa());
        telemetry.addData("                                Xdeg", limelight.getXDeg(0));
        telemetry.addData("                                Xdist", limelight.getXDist(0));
        telemetry.addData("                                Ydeg", limelight.getYDeg(0));
        telemetry.addData("                                Ydist", limelight.getYDist(0));

    }
}
