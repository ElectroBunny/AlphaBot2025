// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkMaxConfig;
import frc.robot.Constants;

public class Arm extends SubsystemBase {
  private SparkMax motor;
  private SparkMaxConfig motorConfig;
  private SparkClosedLoopController closedLoopController;
  private RelativeEncoder encoder;
  private static Arm instance=null;
  /** Creates a new Arm. */
  public Arm() {
    this.motor = new SparkMax(Constants.ARM_MOTOR_ID, null); // the type is in need to be changed
    this.closedLoopController = this.motor.getClosedLoopController();
    this.encoder = this.motor.getEncoder();

    /*
     * Create a new SPARK MAX configuration object. This will store the
     * configuration parameters for the SPARK MAX that we will set below.
     */
    motorConfig = new SparkMaxConfig();

    // configure encoder to specific conversion factor.
    this.motorConfig.encoder.positionConversionFactor(Constants.ARM_POSITION_CONVERTION_FACTOR); // maybe need to add velocity control.

    //configuring the encoders close loop 
    this.motorConfig.closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder)
    .p(Constants.ARM_P)
    .i(Constants.ARM_I)
    .d(Constants.ARM_D)
    .outputRange(Constants.MIN_OUTPUT_RANGE, Constants.MAX_OUTPUT_RANGE);

  }

  public static Arm getInstance()
  {
    if(instance == null)
    {
      instance = new Arm();
    }
    return instance;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
