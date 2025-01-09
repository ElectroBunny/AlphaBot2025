// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkFlexConfig;

public class Pivot extends SubsystemBase {
  private SparkFlex motor;
  private SparkFlexConfig motorConfig;
  private SparkClosedLoopController closedLoopController;
  private RelativeEncoder encoder;

  private static Pivot instance = null;

  public double tolerance = 2.0;

  private Pivot()
  {
    motor = new SparkFlex(1, MotorType.kBrushless);
    closedLoopController = motor.getClosedLoopController();
    encoder = motor.getEncoder();

    motorConfig.encoder.positionConversionFactor(360);
    
    motorConfig = new SparkFlexConfig();

    motorConfig.idleMode(IdleMode.kBrake);    

    motorConfig.closedLoop
        .feedbackSensor(FeedbackSensor.kAlternateOrExternalEncoder)
        .p(0.4)
        .i(0)
        .d(0)
        .outputRange(-1, 1);

    motorConfig.closedLoop.maxMotion
        .maxVelocity(1000)
        .maxAcceleration(1000)
        .allowedClosedLoopError(1);

    motor.configure(motorConfig, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);

    encoder.setPosition(motor.getAbsoluteEncoder().getPosition());
  }

  public static Pivot GetInstance()
  {
    if (instance == null)
    {
      instance = new Pivot();
    }

    return instance;
  }

  /***
   * The function moves the motor to the wanted angle
   * 
   * @param angle - the angle to move the motor to
   */
  public void movePivotToAngle(double angle)
  {
    closedLoopController.setReference(angle, ControlType.kMAXMotionPositionControl,ClosedLoopSlot.kSlot0);
  }

  public void SetPower(double power)
  {
    motor.set(power);
  }

  public void StopMotor()
  {
    motor.stopMotor();
  }

  /***
   * The function checks if the motor has reached the wanted angle
   * 
   * @param angle - The angle we want the motor to move to
   * @return if the motor has reached the wanted angle
   */
  public boolean isAtAngle(double angle)
  {
    return encoder.getPosition() <= angle + tolerance && encoder.getPosition() >= angle - tolerance;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
