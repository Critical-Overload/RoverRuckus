package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "AutoNear")

public class AutoNear extends LinearOpMode{

    //declares motors and sensors
    private DcMotor motorFrontRight;
    private DcMotor motorFrontLeft;
    private DcMotor motorBackLeft;
    private DcMotor motorBackRight;
    private DcMotor liftMotor;
    private DcMotor bucketMotor;
    private DigitalChannel touchSensor;
    
    public void runOpMode() throws InterruptedException{
        
        //initializes motors and sensors
        motorFrontRight = hardwareMap.dcMotor.get("FR");
        motorFrontLeft = hardwareMap.dcMotor.get("FL");
        motorBackRight = hardwareMap.dcMotor.get("BR");
        motorBackLeft = hardwareMap.dcMotor.get("BL");
        liftMotor = hardwareMap.dcMotor.get("lift");
        bucketMotor = hardwareMap.dcMotor.get("bucket");
        touchSensor = hardwareMap.digitalChannel.get("touch");
        
        //REVERSES these motors for proper driving
        motorFrontLeft.setDirection(DcMotor.Direction.REVERSE);
        motorBackLeft.setDirection(DcMotor.Direction.REVERSE);
        
        
        /*
        DriveAutonomous contains all our methods for robot automation
        during the autonomous round. Here we create an DriveAutonomous 
        object named "robot", which we will use to call methods.
        */
        DriveAutonomous robot = new DriveAutonomous(motorFrontRight, motorFrontLeft,
        motorBackRight, motorBackLeft, liftMotor, bucketMotor, touchSensor);
        
        waitForStart();
        
        robot.resetBucket();
        robot.land();
        robot.moveSideTime(0.5, 1);
        robot.turnTime(-0.5, 1);
        robot.moveTime(0.5, 2);
        
    }
    
}