// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Arm;

public class setArmPosition extends Command {
  /** Creates a new setArmPosition. */

  private Arm m_arm;
  private double targetPosition;
  private double initialHeight;
  private boolean isDone = false;

  public setArmPosition(Arm m_arm, double targetPosition) {

    this.m_arm = m_arm;
    this.targetPosition = targetPosition; // Position is the Pot Value

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_arm);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    this.isDone = false;
    this.initialHeight = m_arm.getPotValue();

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
    if (targetPosition > Constants.Arm.UpperSafety || targetPosition < Constants.Arm.LowerSafety) 
        {
            end(true);
        }
  
        if(initialHeight < targetPosition)
        {
            if(m_arm.getPotValue() < targetPosition) 
            {
                m_arm.moveArmUp(Constants.Arm.AutoMotorSpeed);
            } 
            else 
            {
                m_arm.stop();
                isDone = true;
            }
        } 
        else if (initialHeight > targetPosition) 
        {
            if(m_arm.getPotValue() > targetPosition) 
            {
                m_arm.moveArmDown(Constants.Arm.AutoMotorSpeed);
            } 
            else 
            {
                m_arm.stop();
                isDone = true;
            }
        }



  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_arm.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isDone;
  }
}
