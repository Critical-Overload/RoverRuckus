package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by mingch on 9/9/17.
 */

@Autonomous(name = "AutonomousMovementTest")
public class AutonomousMovementTest extends LinearOpMode {
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
        motorFrontLeft.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        driveForward(1, 3);
    }

    public void driveForward(double power, double inches) {
        motorBackLeft.setPower(power);
        motorFrontRight.setPower(power);
        motorFrontLeft.setPower(power);
        motorBackRight.setPower(power);
        double w = (inches/23)*1000;
        int y = (int) Math.rint(w);
        String x = Integer.toString(y);
        telemetry.addLine(x);
        telemetry.update();
        sleep(y);
        completeStop();
    }

    public void completeStop(){
        motorBackLeft.setPower(0);
        motorFrontRight.setPower(0);
        motorFrontLeft.setPower(0);
        motorBackRight.setPower(0);
    }
}