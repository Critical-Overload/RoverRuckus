package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/*
Our code to test different lift movements
 */

@TeleOp(name = "LiftTest")
public class LiftTest extends LinearOpMode {
  
  private DcMotor liftMotor;
  
  private Servo clawServo;
  
  double CLAW_RETRACTED = 0;
  double CLAW_EXTENDED = 1;
  
  @Override
  
  public void runOpMode(){
  
    liftMotor = hardwareMap.dcMotor.get("liftMotor");
    clawServo = hardwareMap.servo.get("clawServo");

    liftMotor.setDirection(DcMotor.Direction.REVERSE);

    waitForStart();
    
    while(opModeIsActive()){
    
      if(gamepad2.x){

          clawServo.setPosition(0);
      }
      
      if(gamepad2.y){
        clawServo.setPosition(1);
      }
      
      /*
      Uses continous motion to control lift
      */
      
      if(gamepad2.dpad_up) {
        liftMotor.setPower(-0.5);
      }else if(gamepad1.dpad_down){
        liftMotor.setPower(0.5);
      }else{
        liftMotor.setPower(0);
      }



      //Not Usingbelow
      /* 
      Or this:
      (moves to set positions)
      (less flexible, more accurate, needs more testing)
     
      if(gamepad2.dpad_up){
        liftMotor.setPower(-0.5);
        sleep(1000)
      }
      
      if(gamepad2.dpad_down){
        liftMotor.setPower(0.5);
        sleep(1000)
      }
      */
      
      idle();
    }
  }
}
