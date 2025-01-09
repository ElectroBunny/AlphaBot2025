// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Telescope;

public class MoveTeloToPlaceManually extends Command 
{
  private static Telescope telescope;
  private double point;
  private DoubleSupplier doubleSupplier;

  public MoveTeloToPlaceManually( DoubleSupplier doubleSupplier ) 
  {
    this.doubleSupplier = doubleSupplier;

    telescope = Telescope.getInstance();
    addRequirements(telescope);
  }

  @Override
  public void initialize() {}

  
  @Override
  public void execute() 
  {
    telescope.setPower(doubleSupplier.getAsDouble());
  }

  @Override
  public void end(boolean interrupted) 
  {
    telescope.stopMotor();
  }

  @Override
  public boolean isFinished() 
  {
    return false;
  }
}
