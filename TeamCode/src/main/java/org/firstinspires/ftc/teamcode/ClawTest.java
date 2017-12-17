package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by mingch on 9/9/17.
 */

@TeleOp(name = "ClawTest")
public class ClawTest extends LinearOpMode
{
    private Servo leftServo;
    private Servo rightServo;

    @Override
    public void runOpMode () throws InterruptedException
    {
        leftServo = hardwareMap.servo.get("leftServo");
        rightServo = hardwareMap.servo.get("rightServo");

        waitForStart();

        while(opModeIsActive())
        {
                leftServo.setPosition(0);
                sleep(1000);
                rightServo.setPosition(1);

            idle();
        }
    }

}
