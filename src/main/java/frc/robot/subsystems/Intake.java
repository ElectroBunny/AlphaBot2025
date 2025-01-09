// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  private RelativeEncoder encoder;
  private SparkFlex intakeMotor;
  private static Intake instance = null;
    private Intake() {
      intakeMotor = new SparkFlex(Constants.INTAKE_SPARK_ID, MotorType.kBrushless );
      encoder = intakeMotor.getEncoder();
    }


  
  public void setPower(double power){
    intakeMotor.set(power);
  }
  }
  public void stopMotor (){
    intakeMotor.set(0);
  }
  public void speedCheck(){
    
  }

  public static Intake getInstance() {
    if (instance == null) {
      instance = new Intake();
    }

    return instance;
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}