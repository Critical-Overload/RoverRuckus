package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by mingch on 9/9/17.
 */

@TeleOp(name = "MireyaJolieTest")
public class MireyaJolieTest extends LinearOpMode
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
        motorBackRight = hardwareMap.dcMotor.get("BackRight0");
        motorBackLeft = hardwareMap.dcMotor.get("BackLeft1");

        motorBackLeft.setDirection(DcMotor.Direction.REVERSE);
        motorBackRight.setDirection(DcMotor.Direction.REVERSE);
        motorFrontRight.setDirection(DcMotor.Direction.REVERSE);
        motorFrontLeft.setDirection(DcMotor.Direction.REVERSE);

        double powerMod = 1.0;

        while(opModeIsActive())
        {
            //Slow Down Button
            if (gamepad1.right_bumper) {
                powerMod = 0.25;
            } else {
                powerMod = 1.0;
            }

            //Driving
            motorBackLeft.setPower(powerMod * gamepad1.left_stick_y);
            motorBackRight.setPower(powerMod * gamepad1.right_stick_y);
            motorFrontLeft.setPower(powerMod * gamepad1.left_stick_y);
            motorFrontRight.setPower(powerMod * gamepad1.right_stick_y);

            idle();
        }
    }

}
