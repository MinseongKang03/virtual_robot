package org.firstinspires.ftc.teamcode.ftc16072;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp

public class TwoWheelBot extends LinearOpMode {

    private DcMotor motorTest, leftMotor, rightMotor;

    @Override
    public void runOpMode() throws InterruptedException {
        leftMotor = hardwareMap.get(DcMotor.class, "left_motor");
        rightMotor = hardwareMap.get(DcMotor.class, "right_motor");

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double lefty = this.gamepad1.left_stick_y;
            double rightx = this.gamepad1.right_stick_x;

            double denominator = Math.max(Math.abs(lefty) + Math.abs(rightx), 1);
            double leftPower = (-lefty + rightx) / denominator;
            double rightPower = (lefty + rightx) / denominator;

            leftMotor.setPower(leftPower);
            rightMotor.setPower(rightPower);
        }
    }
}