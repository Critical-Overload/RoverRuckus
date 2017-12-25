package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by mingch on 9/9/17.
 */

@TeleOp(name = "ClawTest")
public class ClawTest extends LinearOpMode
{
    private Servo leftServo;
    private Servo rightServo;

    private DcMotor liftMotor;



    @Override
    public void runOpMode () throws InterruptedException
    {
        leftServo = hardwareMap.servo.get("leftServo");
        rightServo = hardwareMap.servo.get("rightServo");

        liftMotor = hardwareMap.dcMotor.get("liftMotor");


        leftServo.setPosition(0.2);
        rightServo.setPosition(0.5);

        waitForStart();

        while(opModeIsActive())
        {


            leftServo.setPosition(1);
            rightServo.setPosition(0);

            sleep(1000);


            liftMotor.setPower(0.5);
            sleep(300);
            liftMotor.setPower(0);


            idle();
        }
    }

}
