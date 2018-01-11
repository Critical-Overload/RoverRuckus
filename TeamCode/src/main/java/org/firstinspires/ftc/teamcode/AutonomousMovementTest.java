package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by mingch on 9/9/17.
 */

/*
Main Code to test different types of way to model movement
 */

@Autonomous(name = "AutonomousMovementTest")
public class AutonomousMovementTest extends LinearOpMode {
    private DcMotor motorFrontRight;
    private DcMotor motorFrontLeft;
    private DcMotor motorBackRight;
    private DcMotor motorBackLeft;
    private DcMotor liftMotor;
    private Servo leftServo;
    private Servo rightServo;

    /*
    Drive Control is our driving class
     */

    DriveControl move = new DriveControl(motorFrontRight,motorFrontLeft,motorBackRight,motorBackLeft,leftServo,rightServo,liftMotor);


    @Override
    public void runOpMode() throws InterruptedException {
        motorFrontRight = hardwareMap.dcMotor.get("FrontRight2");
        motorFrontLeft = hardwareMap.dcMotor.get("FrontLeft3");
        motorBackRight = hardwareMap.dcMotor.get("BackRight0");
        motorBackLeft = hardwareMap.dcMotor.get("BackLeft1");

        motorBackLeft.setDirection(DcMotor.Direction.REVERSE);
        motorFrontLeft.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        move.drive(1,10);

        move.fourWheelTurn(1, 90);
        sleep(500);
        move.fourWheelTurn(1,180);
        sleep(500);
        move.fourWheelTurn(1,360);

    }

    
}
