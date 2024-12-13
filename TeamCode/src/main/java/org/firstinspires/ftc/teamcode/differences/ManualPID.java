package org.firstinspires.ftc.teamcode.differences;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Constants;

import java.util.ArrayList;

import ArmSpecific.ArmAngle;
import CommonUtilities.AngleRange;
import CommonUtilities.PIDFParams;
import CommonUtilities.PIDFcontroller;

@Config
@TeleOp(name = "AutoPid", group = "Linear OpMode")
public class ManualPID extends LinearOpMode {

    public static double p, i, d, f = 0.0;

    @Override
    public void runOpMode() {
        telemetry = new MultipleTelemetry(FtcDashboard.getInstance().getTelemetry(), telemetry);

        Constants constants = new Constants();

        // Code executed when initialized
        DcMotorEx motor = hardwareMap.get(DcMotorEx.class, constants.motorName);
        motor.setDirection(constants.motorDirection);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor.setPower(0.0);
        ArrayList<AngleRange> targets = Constants.angleRanges;
        AngleRange target = targets.get(0);
        ElapsedTime timer = new ElapsedTime();
        constants.pidfController.resetConstantsAndTarget(new PIDFParams(p, i, d, f), target);
        ArmAngle armAngle = new ArmAngle(constants.motor, Constants.stationaryAngle);
        int x = 0;
        ElapsedTime startToStop = new ElapsedTime();
        ArrayList<Double> itaeList = new ArrayList<>();
        ArrayList<Double> listofIteaLists = new ArrayList<>();
        ArrayList<Double> timerTimes = new ArrayList<>();


        while (opModeInInit()) {
            timer.reset();
            startToStop.reset();
        }
        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            double looptime = timer.seconds();
            timer.reset();

            double angle = armAngle.findAngle(motor.getCurrentPosition());
            if (gamepad1.a) {
                x += 1;
                if (targets.size() != x) {
                    target = targets.get(x);
                    constants.pidfController.resetConstantsAndTarget(new PIDFParams(p, i, d, f), target);

                    double ITAE = 0.0;
                    for (double num : itaeList) {
                        ITAE += num;
                    }
                    ITAE /= itaeList.size();

                    listofIteaLists.add(ITAE);
                    timerTimes.add(startToStop.seconds());
                    itaeList.clear();
                    startToStop.reset();
                } else {
                    //when complete
                    telemetry.addData("Itae", listofIteaLists.toString());
                    telemetry.addData("times", timerTimes.toString());
                }
            }

            PIDFcontroller.Result piderror = constants.pidfController.calculate(new AngleRange(angle, target.getTarget()), constants.obstacle, looptime);
            double error = piderror.getError();
            double errorRelativeToTime = Math.pow(startToStop.seconds(), 3) * Math.abs(error);
            itaeList.add(errorRelativeToTime);

            motor.setPower(piderror.getMotorPower());
            telemetry.update();
        }
    }
}
/*
if(atTarget){
        if (firstTime){
        waitTime.reset();
firstTime = false;
        }

        if(waitTime.seconds() >= .1){
//velo check
double angularVelocity = (angle - lastAngle) / looptime;

                    veloList.add(angularVelocity);
waitTime = new ElapsedTime(5);
                }

                        }

            lastAngle = angle;
 */