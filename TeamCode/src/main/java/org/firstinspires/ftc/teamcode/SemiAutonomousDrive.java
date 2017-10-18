package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by mingch on 9/9/17.
 */

@Autonomous(name = "Semi-AutonomousDrive")
public class SemiAutonomousDrive extends LinearOpMode {
    private DcMotor motorFrontRight;
    private DcMotor motorFrontLeft;
    private DcMotor motorBackRight;
    private DcMotor motorBackLeft;

    @Override
    public void runOpMode() throws InterruptedException {
        motorFrontRight = hardwareMap.dcMotor.get("FrontRight2");
        motorFrontLeft = hardwareMap.dcMotor.get("FrontLeft3");
        motorBackRight = hardwareMap.dcMotor.get("BackRight0");
        motorBackLeft = hardwareMap.dcMotor.get("BackLeft1");

        motorBackLeft.setDirection(DcMotor.Direction.REVERSE);
        motorBackRight.setDirection(DcMotor.Direction.REVERSE);

        double quarterpower = 0.25;
        double halfpower = 0.5;
        int fullpower = 1;
        int tenseconds = 10;
        int halfminute = 30;
        int minute = 60;

        waitForStart();

    }

    public void DriveForward (double Power, long Time){
        motorBackLeft.setPower(Power);
        motorFrontRight.setPower(Power);
        motorFrontLeft.setPower(Power);
        motorBackRight.setPower(Power);
        sleep(Time * 100);
    }

    public void DriveBackwards (double Power, long Time){
        motorBackLeft.setPower(-Power);
        motorFrontRight.setPower(-Power);
        motorFrontLeft.setPower(-Power);
        motorBackRight.setPower(-Power);
        sleep(Time * 100);
    }

    public void TwoSideTurnLeft (double Power, long Time){
        motorBackLeft.setPower(-Power);
        motorFrontRight.setPower(Power);
        motorFrontLeft.setPower(-Power);
        motorBackRight.setPower(Power);
        sleep(Time * 100);
    }

    public void TwoSideTurnRight (double Power, long Time){
        motorBackLeft.setPower(Power);
        motorFrontRight.setPower(-Power);
        motorFrontLeft.setPower(Power);
        motorBackRight.setPower(-Power);
        sleep(Time * 100);
    }

    public void OneSideTurnLeft (double Power, long Time){
        motorBackLeft.setPower(0);
        motorFrontRight.setPower(Power);
        motorFrontLeft.setPower(0);
        motorBackRight.setPower(Power);
        sleep(Time * 100);
    }

    public void OneSideRightTurn (double Power, long Time){
        motorBackLeft.setPower(Power);
        motorFrontRight.setPower(0);
        motorFrontLeft.setPower(Power);
        motorBackRight.setPower(0);
        sleep(Time * 100);
    }


}