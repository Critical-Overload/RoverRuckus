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
    private Servo ColorArm;

    @Override
    public void runOpMode () throws InterruptedException
    {
        ColorArm = hardwareMap.servo.get("clawServo");

        waitForStart();

        while(opModeIsActive())
        {
                ColorArm.setPosition(1);
            sleep(1000);
                ColorArm.setPosition(0);
            sleep(1000);


            idle();
        }
    }

}
