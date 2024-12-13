package org.firstinspires.ftc.teamcode;

import static java.lang.Math.PI;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import java.util.ArrayList;
import java.util.Arrays;

import ArmSpecific.ArmAngle;
import ArmSpecific.GravityModelConstants;
import ArmSpecific.Hardware;
import ArmSpecific.SystemConstants;
import ArmSpecific.pso4Arms;
import CommonUtilities.AngleRange;
import CommonUtilities.PIDFParams;
import CommonUtilities.PIDFcontroller;

@Config
public class Constants {

    //CONFIGURATIONS - do before running anything

    //todo Assign Your Motor Specs, direction, and CONFIG Name
    public final Hardware.Motor motor = Hardware.YellowJacket.RPM84; // for geared motors new Hardware.Motor(RPM,EncTicksPerRev,StallTorque,GearRatio);
    public final DcMotorSimple.Direction motorDirection = DcMotorSimple.Direction.REVERSE;
    public final String motorName = "shoulder";

    //todo provide the angles (in radians) that your arm can run to when testing (larger range the better)
    public static double stationaryAngle = Math.toRadians(3.0);
    public final AngleRange testingAngle = new AngleRange(stationaryAngle, PI/2);
    //todo provide angles (in radians) that present as obstacles to the system. If none set to null
    public final AngleRange obstacle = new AngleRange(-.2 * PI, -.3*PI); // = null;

    //TESTING
    //todo change from FRICTION OPMODE results
    public static double frictionRPM = 83.28061527367797;
    public static double inertiaValue = 0.6934848493799478;


    //todo change from Gravity OPMODE & Desmos Graph
    public static double gravityA = -7.41624;
    public static double gravityB = 1.5607;
    public static double gravityK = 18.4051;


    // intake ground 44, drop it -11, deposit sub 120, drag down sub 110
    public static final ArrayList<AngleRange> angleRanges = new ArrayList<AngleRange>() {{
        add(new AngleRange(Math.toRadians(44), Math.toRadians(-73))); // hpdrop
        add(new AngleRange(Math.toRadians(44), Math.toRadians(164))); // specimen deposit prep
        add(new AngleRange(Math.toRadians(164), Math.toRadians(145))); // specimen deposit
        add(new AngleRange(Math.toRadians(3.0), Math.toRadians(-115))); // specimen intake
        add(new AngleRange(Math.toRadians(44), Math.toRadians(107))); //basketDeposit
        add(new AngleRange(Math.toRadians(0), Math.toRadians(44))); // submersible intake
    }};

    //todo LAST STEP - RUN the test in the TEST MODULE -> TeamCode/src/test/java/org.firstinspires.ftc.teamcode/FindConstants.java
    public final static ArrayList<PIDFParams> params = new ArrayList<>(Arrays.asList(
            new PIDFParams(2.809677415841438, 1.5174439513050255, 0.343556630719057, 1.5189510384493834),
            new PIDFParams(3.2919729078942668, 0.06938572404596652, 0.2666585282397416, 0.1529601219820437),
            new PIDFParams(1.8, .2, 0.12, 0.2047307797320996),
            new PIDFParams(2.768874652665356, 0.6363758070970078, 0.3018575219873556, 0.7522356071054006),
            new PIDFParams(2.9499981590055824, 0.45940194570945636, 0.25795294817871595, 0.13867608236918658),
            new PIDFParams(2.5010666086883897, 0.599469808729879, 0.22926314892634186, 0.14661769814279804)));

    SystemConstants constant = new SystemConstants(
            frictionRPM,
            motor,
            new GravityModelConstants(gravityA, gravityB, gravityK),
            inertiaValue
    );
    public pso4Arms sim = new pso4Arms(constant, angleRanges, 1.2, obstacle, 3.3);
    public static boolean gravityRecord = false;
    public static boolean gravityDisplayDataPoints = false;
    public static double gravityMotorPower = 0.0;
    public PIDFcontroller pidfController = new PIDFcontroller(
            new PIDFParams(0.0, 0.0, 0.0, 0.0),
            motor,
            obstacle,
            stationaryAngle
    );

    //for custom usage and finding of arm angle ->
    public ArmAngle armAngle = new ArmAngle(motor,stationaryAngle);

}

