package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

//Created October 28, 2018 by Jonathan
//Authors: Jonathan and Robert

/* 
Uses Mecanum Trigonometric Driving
Incorporates Lift Motor
*/

@TeleOp(name = "MainTeleOp")
public class MainTeleOp extends LinearOpMode
{
    //Init motors and servos
    private DcMotor motorFrontRight;
    private DcMotor motorFrontLeft;
    private DcMotor motorBackRight;
    private DcMotor motorBackLeft;
    private DcMotor liftMotor;
    private DcMotor hingeMotor;
    private Servo bucketServo;
    private CRServo intakeServo;
    private DigitalChannel touchSensor;
    

    @Override
    public void runOpMode () throws InterruptedException
    {
        //Define motors and servos
        motorFrontRight = hardwareMap.dcMotor.get("FR");
        motorFrontLeft = hardwareMap.dcMotor.get("FL");
        motorBackRight= hardwareMap.dcMotor.get("BR");
        motorBackLeft = hardwareMap.dcMotor.get("BL");
        liftMotor = hardwareMap.dcMotor.get("lift");
        hingeMotor = hardwareMap.dcMotor.get("hingeMotor");
        bucketServo = hardwareMap.servo.get("bucketServo");
        intakeServo = hardwareMap.crservo.get("intakeServo");
        touchSensor = hardwareMap.digitalChannel.get("touchSensor");

        /*These motors need to have reversed directions
        Because of how they are placed on the robot*/
        
        motorBackLeft.setDirection(DcMotor.Direction.REVERSE);
        motorBackRight.setDirection(DcMotor.Direction.REVERSE);
        liftMotor.setDirection(DcMotor.Direction.REVERSE);
        hingeMotor.setDirection(DcMotor.Direction.REVERSE);
        
        //set lift and hinge motors to encoder run-to-position mode
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hingeMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hingeMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //configure lift variables
        int liftLevel=0;
        int liftTicks=0;
        int ticksPerLiftCycle=1440;

        //powerMod variables can reduce robot speed
        double powerMod = 1.0;
        double mechMod = 1.0;
        
        telemetry.addData("Hey", "Listen");
        telemetry.addData("Level of lift: ", liftLevel);
        telemetry.addData("Encoder Position ", liftMotor.getCurrentPosition());
        telemetry.addData("Lift Ticks ", liftTicks);
        telemetry.addData("Touch Sensor", "Ready");
        telemetry.update();

        waitForStart();
        liftMotor.setPower(1);
        hingeMotor.setPower(0.8);
        while(opModeIsActive())
        {
            /*
            Checks if right bumper is pressed.
            If so, power is reduced.
             */
            if(gamepad1.right_bumper){
                powerMod = 0.5;
            }else{
                powerMod = 1.0;
            }
            if(gamepad2.right_bumper){
                mechMod = 0.5;
                mechMod = 1.0;
            }
            
            //move lift
            if (gamepad1.dpad_left || gamepad1.dpad_right) {
                liftTicks = (int)(5.75*ticksPerLiftCycle);
                liftLevel = 1;
                liftMotor.setTargetPosition(liftTicks);
            }    
            else if (gamepad1.dpad_up) {
                liftTicks = (int)(6.75*ticksPerLiftCycle);
                liftLevel = 2;
                liftMotor.setTargetPosition(liftTicks);
            } 
            else if (gamepad1.dpad_down){
                liftTicks = 0;
                liftLevel = 0;
                liftMotor.setTargetPosition(liftTicks);
            }
            if (gamepad1.left_bumper){
                liftTicks -= 10*powerMod;
                liftMotor.setTargetPosition(liftTicks);
            }

            /*
            Mecanum wheel drive using trigonometry
            */
            double angle = Math.atan2(gamepad1.right_stick_y, gamepad1.right_stick_x) + (Math.PI)/4;
            double r = Math.hypot(gamepad1.right_stick_x, gamepad1.right_stick_y);
            double rotation = gamepad1.left_stick_x;
            
            motorFrontLeft.setPower((r * Math.cos(angle) - rotation)*powerMod);
            motorBackRight.setPower((r * Math.cos(angle) + rotation)*powerMod);
            motorFrontRight.setPower((r * Math.sin(angle) - rotation)*powerMod);
            motorBackLeft.setPower((r * Math.sin(angle) + rotation)*powerMod);
            
            //controls bucketservo (REMOVED:WILL BE REPLACED BY ARM-SENSING AUTOMATION)
            /*if(gamepad2.a){
                bucketServo.setPosition(0);
            }else if(gamepad2.b){
                bucketServo.setPosition(1);
            }else if(gamepad2.left_bumper){
                bucketServo.setPosition(0.4);
            }*/

            //intake:spins in intake direction unless override button (x) pressed
            if(gamepad2.x){
            intakeServo.setPower(Math.abs(gamepad2.left_trigger*0.75));
            }
            else{
            intakeServo.setPower(-Math.abs(gamepad2.left_trigger*0.75));
            }
            
            //manual bucket positioning
            if(gamepad2.dpad_up){
                bucketServo.setPosition(bucketServo.getPosition()+0.01*powerMod);
            }else if(gamepad2.dpad_down){
                bucketServo.setPosition(bucketServo.getPosition()-0.01*powerMod);
            }
            
            //Arm movement
            if(gamepad2.right_stick_y!=0){
                hingeMotor.setTargetPosition((int)(hingeMotor.getCurrentPosition()+(gamepad2.right_stick_y*30*powerMod))); 
            }
            else if(gamepad2.a){
                hingeMotor.setTargetPosition(0);
            }
            else if(gamepad2.b){
                hingeMotor.setTargetPosition((int)(1.2*ticksPerLiftCycle));
            }
            if(gamepad2.right_stick_button){
                hingeMotor.setTargetPosition(hingeMotor.getCurrentPosition());
            }
            
            //insert after removing all other arm controls to move arm to starting position
            /*if (gamepad2.right_stick_button){
                hingeMotor.setPower(-1);
            }*/
            
            
            //if the touch sensor is pressed, then stop motors
            if(touchSensor.getState() == false){
                telemetry.addData("Touch Sensor","Pressed");
                //hingeMotor.setPower(0); won't work with encoding
                //NEED TO READD TOUCH SENSOR BY ZEROING ENCODERS hingeMotor
            }else{
                telemetry.addData("Touch Sensor","Not pressed");
            }
            
            telemetry.addData("Hey", "Listening");
            telemetry.addData("Level of lift: ", liftLevel);
            telemetry.addData("Encoder Position ", liftMotor.getCurrentPosition());
            telemetry.addData("Lift Ticks ", liftTicks);
            telemetry.addData("Servo position", bucketServo.getPosition());
            telemetry.addData("Arm Position: ", hingeMotor.getCurrentPosition());
            telemetry.addData("2lY ", gamepad2.left_stick_y);
            telemetry.update();

            idle();
        }
    }
}
