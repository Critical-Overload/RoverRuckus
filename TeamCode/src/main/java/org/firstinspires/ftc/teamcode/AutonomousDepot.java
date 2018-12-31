package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="AutonomousDepot")

public class AutonomousDepot extends LinearOpMode {
    
    //declares motors and servos
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
        
        //This program uses DriveAutonomous, which defines all of our mehtods.
        DriveAutonomous robot = new DriveAutonomous(motorFrontRight, motorFrontLeft, motorBackRight, motorBackLeft, liftMotor, bucketMotor, touchSensor);
        waitForStart();
        
        //turn 90 degrees time
        double a = 0.0;
        
        //move half diagonal time (23.5sqrt(2)/2)
        double b = 0.0;
        
        //move full diagonal time (23.4sqrt(2))
        double c = 0.0;
        
        //turn 45 degrees time
        double d = 0.0;
        
        //move one square side time (23.5)
        double e = 0.0;
        robot.resetBucket();
        robot.land();
        //robot.turnTime(-0.5,a);
        //robot.moveTime(0.5,b);
        //SCAN
        //if left;
            //robot.moveSideTime(0.5,b);
            //robot.moveTime(0.5,c);
            //robot.moveSideTime(-0.5,b);
            //dump arm, bucket
            //robot.resetBucket();
            //robot.moveSideTime(-0.5,b);
            //robot.turnTime(-0.5,d);
            //robot.moveTime(-0.5,3.5*e);
        //if center;
            //robot.moveTime(0.5,c);
            //dump arm, bucket
            //robot.resetBucket();
            //robot.moveSideTime(-0.5,b);
            //robot.turnTime(-0.5,d);
            //robot.moveTime(-0.5,3.5*e);
        //if right;
            //robot.moveSideTime(-0.5,b);
            //robot.moveTime(0.5,c);
            //robot.turnTime(0.5,d);
            //dump arm, bucket
            //robot.resetBucket();
            //robot.moveTime(-0.5,3.5*e);
    }
}
