package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "SpaceshipToasterOp")
public class SpaceshipToasterOp extends LinearOpMode
{
    private DcMotor motorRight;
    private DcMotor motorLeft;
    @Override
    
    public void runOpMode () throws InterruptedException
    {
        
        motorRight = hardwareMap.dcMotor.get("Right0");
        motorLeft = hardwareMap.dcMotor.get("Left3");  
        motorRight.setDirection(DcMotor.Direction.REVERSE);
        motorLeft.setDirection(DcMotor.Direction.REVERSE);
    
        double powerMod = 1.0;
        waitForStart();
        while(opModeIsActive())
        {
            /*
            Checks if right bumper is pressed.
            If so, power is reduced.
            */
            if(gamepad1.right_bumper){

                powerMod = 0.25;
            }
            else{
                powerMod = 1.0;
            }
            motorLeft.setPower(powerMod * gamepad1.left_stick_y);
            motorRight.setPower(powerMod * gamepad1.right_stick_y);

            idle();
        }
    }
}

