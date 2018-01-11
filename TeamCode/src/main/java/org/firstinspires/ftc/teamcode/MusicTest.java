package org.firstinspires.ftc.teamcode;

import android.app.ActionBar;
import android.media.MediaPlayer;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

/**
 * Created by mingch on 9/9/17.
 */

/*
Side Project to play music from the Android Phone
 */

@TeleOp(name = "MusicTest")
public class MusicTest extends LinearOpMode
{
    MediaPlayer Thunder;
    MediaPlayer Soviet;
    MediaPlayer Ferret;
    @Override
    public void runOpMode ()
    {
        Thunder = MediaPlayer.create(AppUtil.getInstance().getRootActivity(),  R.raw.thunder);
        Soviet = MediaPlayer.create(AppUtil.getInstance().getRootActivity(),  R.raw.soviet);
        Ferret = MediaPlayer.create(AppUtil.getInstance().getRootActivity(),  R.raw.ferret);

        waitForStart();

        while(opModeIsActive())
        {
            if(gamepad1.x){
                Thunder.release();
                Soviet.release();
                Ferret.release();
                Thunder.start();
            }
            if(gamepad1.y){
                Thunder.release();
                Soviet.release();
                Ferret.release();
                Soviet.start();
            }
            if(gamepad1.a){
                Thunder.release();
                Soviet.release();
                Ferret.release();
                Ferret.start();
            }
            idle();
        }
    }

}
