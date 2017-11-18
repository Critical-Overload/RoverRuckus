package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by mingch on 9/9/17.
 */

@TeleOp(name = "AAMainTeleOp")
public class AAMainTeleOp extends LinearOpMode
{
    public LynxI2cColorRangeSensor colorSensor;
    private DcMotor motorFrontRight;
    private DcMotor motorFrontLeft;
    private DcMotor motorBackRight;
    private DcMotor motorBackLeft;
    private DcMotor liftMotor;
    private Servo clawServo;
    public Servo ColorArm;


    @Override
    public void runOpMode () throws InterruptedException
    {
        motorFrontRight = hardwareMap.dcMotor.get("FrontRight2");
        motorFrontLeft = hardwareMap.dcMotor.get("FrontLeft3");
        motorBackRight= hardwareMap.dcMotor.get("BackRight0");
        motorBackLeft = hardwareMap.dcMotor.get("BackLeft1");

        liftMotor = hardwareMap.dcMotor.get("liftMotor");
        clawServo = hardwareMap.servo.get("clawServo");
        ColorArm = hardwareMap.servo.get("ColorArm");


        liftMotor.setDirection(DcMotor.Direction.REVERSE);

        motorBackRight.setDirection(DcMotor.Direction.REVERSE);
        motorFrontRight.setDirection(DcMotor.Direction.REVERSE);


        double powerMod = 1.0;

        waitForStart();

        ColorArm.setPosition(1);


        while(opModeIsActive())
        {
            ColorArm.setPosition(1);

            //Driving

            /*
            Checks if right bumper is pressed.
            If so, power is reduced.
             */
            if(gamepad1.right_bumper){
                powerMod = 0.25;
            }else{
                powerMod = 1.0;
            }

            motorBackLeft.setPower(powerMod * gamepad1.left_stick_y);
            motorFrontLeft.setPower(powerMod * gamepad1.left_stick_y);
            motorBackRight.setPower(powerMod * gamepad1.right_stick_y);
            motorFrontRight.setPower(powerMod * gamepad1.right_stick_y);

            //Claw And Lift
            if(gamepad2.x){
                clawServo.setPosition(0);
            }

            if(gamepad2.y){
                clawServo.setPosition(1);
            }

            if(gamepad2.dpad_up) {
                liftMotor.setPower(-0.5);
            }else if(gamepad2.dpad_down){
                liftMotor.setPower(0.5);
            }else{
                liftMotor.setPower(0);
            }

            idle();
        }
    }

}
