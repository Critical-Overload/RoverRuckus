package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by mingch on 9/9/17.
 */

/*
Test for best driving control
Steer Drive:
Left Stick - Controls Turning Left and Right
Right Stick - Controls Forward and Backwards
 */

@TeleOp(name = "SteerDrivingOp")
public class SteerDrivingOp extends LinearOpMode
{
    private DcMotor motorFrontRight;
    private DcMotor motorFrontLeft;
    private DcMotor motorBackRight;
    private DcMotor motorBackLeft;

    @Override
    public void runOpMode () throws InterruptedException
    {
        motorFrontRight = hardwareMap.dcMotor.get("FrontRight2");
        motorFrontLeft = hardwareMap.dcMotor.get("FrontLeft3");
        motorBackRight= hardwareMap.dcMotor.get("BackRight0");
        motorBackLeft = hardwareMap.dcMotor.get("BackLeft1");

        motorBackLeft.setDirection(DcMotor.Direction.REVERSE);
        motorBackRight.setDirection(DcMotor.Direction.REVERSE);
        motorFrontRight.setDirection(DcMotor.Direction.REVERSE);
        motorFrontLeft.setDirection(DcMotor.Direction.REVERSE);

        double powerMod = 1.0;
        int leftTurn = 1;
        int rightTurn = 1;

        waitForStart();

        while(opModeIsActive())
        {
            /*
            Checks if right bumper is pressed.
            If so, power is reduced.
             */
            if(gamepad1.right_bumper){
                powerMod = 0.25;
            }else{
                powerMod = 1.0;
            }

            float turnDirection = gamepad1.left_stick_x;

            if(turnDirection == -1){
                leftTurn = -1;
            }else{
                leftTurn = 1;
            }
            if(turnDirection == 1){
                rightTurn = -1;
            }else{
                rightTurn = 1;
            }
            motorBackLeft.setPower(leftTurn * powerMod * gamepad1.right_stick_y);
            motorFrontLeft.setPower(leftTurn * powerMod * gamepad1.right_stick_y);
            motorBackRight.setPower(rightTurn * powerMod * gamepad1.right_stick_y);
            motorFrontRight.setPower(rightTurn * powerMod * gamepad1.right_stick_y);

        }
    }

}