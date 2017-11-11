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

@TeleOp(name = "MusicTest")
public class MusicTest extends LinearOpMode
{
    MediaPlayer myMusic;

    @Override
    public void runOpMode ()
    {
        myMusic = MediaPlayer.create(AppUtil.getInstance().getRootActivity(),  R.raw.ferret);//this, R.raw.ferret);

        waitForStart();

        while(opModeIsActive())
        {
            myMusic.start();
            idle();
        }
        myMusic.release();
    }

}
