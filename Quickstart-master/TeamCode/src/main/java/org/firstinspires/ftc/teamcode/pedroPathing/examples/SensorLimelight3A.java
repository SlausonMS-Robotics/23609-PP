/*
Copyright (c) 2024 Limelight Vision

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of FIRST nor the names of its contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode.pedroPathing.examples;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
//import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.Point;

import com.qualcomm.hardware.limelightvision.LLResult;
//import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;


import org.firstinspires.ftc.robotcore.external.Telemetry;
//import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

//import java.util.List;

import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;

/*
 * This OpMode illustrates how to use the Limelight3A Vision Sensor.
 *
 * @see <a href="https://limelightvision.io/">Limelight</a>
 *
 * Notes on configuration:
 *
 *   The device presents itself, when plugged into a USB port on a Control Hub as an ethernet
 *   interface.  A DHCP server running on the Limelight automatically assigns the Control Hub an
 *   ip address for the new ethernet interface.
 *
 *   Since the Limelight is plugged into a USB port, it will be listed on the top level configuration
 *   activity along with the Control Hub Portal and other USB devices such as webcams.  Typically
 *   serial numbers are displayed below the device's names.  In the case of the Limelight device, the
 *   Control Hub's assigned ip address for that ethernet interface is used as the "serial number".
 *
 *   Tapping the Limelight's name, transitions to a new screen where the user can rename the Limelight
 *   and specify the Limelight's ip address.  Users should take care not to confuse the ip address of
 *   the Limelight itself, which can be configured through the Limelight settings page via a web browser,
 *   and the ip address the Limelight device assigned the Control Hub and which is displayed in small text
 *   below the name of the Limelight on the top level configuration screen.
 */
@TeleOp(name = "Sensor: Limelight3A", group = "Sensor")
//@Disabled
public class SensorLimelight3A extends LinearOpMode {

    private Limelight3A limelight;
    private Telemetry telemetryA;
    private Follower follower;
    private Servo Headlight;
    private final Pose startPose = new Pose(0,0,0);
    private Path TargetPath;

    private Pose Targetpose;
    private int target_flag = 0;

    @Override
    public void runOpMode() throws InterruptedException
    {
        limelight = hardwareMap.get(Limelight3A.class, "Limelight");
        Headlight = hardwareMap.get(Servo.class, "Headlight");

        telemetry.setMsTransmissionInterval(11);
        //telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());

        limelight.pipelineSwitch(5);

        /*
         * Starts polling for data.  If you neglect to call start(), getLatestResult() will return null.
         */
        limelight.start();

        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setPose(startPose);
        Pose startPose = new Pose(0, 0, Math.toRadians(0));
        follower.setStartingPose(startPose);


        telemetry.addData(">", "Robot Ready.  Press Play.");
        telemetry.update();
        Headlight.setPosition(1);

        waitForStart();

        follower.startTeleopDrive();


        while (opModeIsActive()) {
            LLStatus status = limelight.getStatus();
            telemetry.addData("Name", "%s",
                    status.getName());
            telemetry.addData("LL", "Temp: %.1fC, CPU: %.1f%%, FPS: %d",
                    status.getTemp(), status.getCpu(),(int)status.getFps());
            telemetry.addData("Pipeline", "Index: %d, Type: %s",
                    status.getPipelineIndex(), status.getPipelineType());

            LLResult result = limelight.getLatestResult();

            if (result != null) {
                // Access general information
               // Pose3D botpose = result.getBotpose();
               // double captureLatency = result.getCaptureLatency();
                //double targetingLatency = result.getTargetingLatency();
                //double parseLatency = result.getParseLatency();
                //double[] pythonOutputs = result.getPythonOutput();
                //telemetry.addData("LL Latency", captureLatency + targetingLatency);
                //telemetry.addData("Parse Latency", parseLatency);
               // telemetry.addData("PythonOutput", java.util.Arrays.toString(result.getPythonOutput()));

                if (!result.isValid()) {
                    /*
                    telemetry.addData("tx", result.getTx());
                    telemetry.addData("txnc", result.getTxNC());
                    telemetry.addData("ty", result.getTy());
                    telemetry.addData("tync", result.getTyNC());
                    telemetry.addData("py0", pythonOutputs[0]);
                    telemetry.addData("py1", pythonOutputs[1]);
                    telemetry.addData("py2", pythonOutputs[2]);
                    telemetry.addData("py3", pythonOutputs[3]);
                    telemetry.addData("py4", pythonOutputs[4]);
                    telemetry.addData("py5", pythonOutputs[5]);


                    if (result.getTy() > -20){
                        follower.setTeleOpMovementVectors(.25,0,0,true);
                        follower.update();
                    }
                    else {
                        follower.setTeleOpMovementVectors(0,0,0,true);
                        follower.update();
                    }
                    if (result.getTx() > 20.9){
                        follower.setTeleOpMovementVectors(0,-0.25,0,true);
                        follower.update();
                        follower.followPath();
                    }
                    else {
                        follower.setTeleOpMovementVectors(0,0,0,true);
                        follower.update();
                    }
                    if (result.getTx() < 21.1){
                        follower.setTeleOpMovementVectors(0,0.25,0,true);
                        follower.update();
                    }
                    else {
                        follower.setTeleOpMovementVectors(0,0,0,true);
                        follower.update();
                    }

                    */

                    Pose Curpose = startPose;
                     double CurposeX = Curpose.getX();
                     double CurposeY = Curpose.getY();

                     double Xdistance = 10.25 * (Math.tan(Math.toRadians(66 + result.getTy())));
                     double Ydistance = 3 + (-Xdistance * (Math.tan(Math.toRadians(result.getTx()))));


                     if (Xdistance > 10 & Xdistance < 50) {
                         if (target_flag == 0) {
                              Targetpose = new Pose(CurposeX + Xdistance, CurposeY + Ydistance, 0);
                             TargetPath = new Path(new BezierLine(new Point(Curpose), new Point(Targetpose)));
                             TargetPath.setConstantHeadingInterpolation(0);
                             follower.followPath(TargetPath, false);
                              target_flag = 1;
                              Headlight.setPosition(0);
                         }


                     }
                    follower.update();

                     telemetry.addData("XDistance", Xdistance);
                    telemetry.addData("CurposeX", CurposeX);
                    telemetry.addData("CurposeY", CurposeY);
                    telemetry.addData("YDistance", Ydistance);
/*
                     // Access barcode results
                    List<LLResultTypes.BarcodeResult> barcodeResults = result.getBarcodeResults();
                    for (LLResultTypes.BarcodeResult br : barcodeResults) {
                        telemetry.addData("Barcode", "Data: %s", br.getData());
                    }

                    // Access classifier results
                    List<LLResultTypes.ClassifierResult> classifierResults = result.getClassifierResults();
                    for (LLResultTypes.ClassifierResult cr : classifierResults) {
                        telemetry.addData("Classifier", "Class: %s, Confidence: %.2f", cr.getClassName(), cr.getConfidence());
                    }

                    // Access detector results
                    List<LLResultTypes.DetectorResult> detectorResults = result.getDetectorResults();
                    for (LLResultTypes.DetectorResult dr : detectorResults) {
                        telemetry.addData("Detector", "Class: %s, Area: %.2f", dr.getClassName(), dr.getTargetArea());
                    }

                    // Access fiducial results
                    List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();
                    for (LLResultTypes.FiducialResult fr : fiducialResults) {
                        telemetry.addData("Fiducial", "ID: %d, Family: %s, X: %.2f, Y: %.2f", fr.getFiducialId(), fr.getFamily(),fr.getTargetXDegrees(), fr.getTargetYDegrees());
                    }

                    // Access color results
                    List<LLResultTypes.ColorResult> colorResults = result.getColorResults();
                    for (LLResultTypes.ColorResult cr : colorResults) {
                        telemetry.addData("Color", "X: %.2f, Y: %.2f", cr.getTargetXDegrees(), cr.getTargetYDegrees());
                    }

 */
                }
                else {
                    telemetry.addData("Limelight", "invalid data");
                }
            } else {
                telemetry.addData("Limelight", "No data available");
            }

            telemetry.update();
            if (target_flag != 0 && !follower.isBusy()){
                limelight.stop();
                terminateOpModeNow();
            }
        }


    }
}
